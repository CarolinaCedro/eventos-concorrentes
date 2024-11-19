package com.cedro.eventos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

@Data
@AllArgsConstructor
public class GestorDeConcorrencia {
    private final Map<String, Evento> eventos; // Lista de eventos por ID
    private final Map<String, Reserva> reservasAtivas; // Reservas ativas por ID de usuário
    private final Map<String, ConcurrentLinkedQueue<Reserva>> filaEspera; // Fila de espera por evento
    private final ReentrantLock lock = new ReentrantLock(); // Lock para garantir exclusão mútua

    public GestorDeConcorrencia() {
        this.eventos = new ConcurrentHashMap<>();
        this.reservasAtivas = new ConcurrentHashMap<>();
        this.filaEspera = new ConcurrentHashMap<>();
    }

    // Método para inicializar um evento no sistema
    public void adicionarEvento(Evento evento) {
        eventos.put(evento.getId(), evento);
    }

    // Tenta reservar uma vaga em um evento para um usuário
    public synchronized boolean reservar(String idEvento, String idUsuario) {
        lock.lock();
        try {
            Evento evento = eventos.get(idEvento);
            if (evento != null && evento.temVaga()) {
                Reserva reserva = new Reserva(idUsuario, idEvento, LocalDateTime.now());
                reservasAtivas.put(idUsuario, reserva);
                evento.diminuirVaga();
                return true;
            } else {
                // Adiciona o usuário na fila de espera se não houver vagas
                filaEspera.computeIfAbsent(idEvento, k -> new ConcurrentLinkedQueue<>()).add(new Reserva(idUsuario, idEvento, LocalDateTime.now()));
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    // Cancela uma reserva ativa e processa a fila de espera
    public synchronized void cancelarReserva(String idUsuario) {
        lock.lock();
        try {
            Reserva reserva = reservasAtivas.remove(idUsuario);
            if (reserva != null) {
                Evento evento = eventos.get(reserva.getEvento());
                evento.aumentarVaga();

                // Verifica a fila de espera e processa o próximo usuário
                ConcurrentLinkedQueue<Reserva> fila = filaEspera.get(reserva.getEvento());
                if (fila != null && !fila.isEmpty()) {
                    Reserva proximaReserva = fila.poll();
                    if (proximaReserva != null) {
                        reservar(proximaReserva.getEvento(), proximaReserva.getUsuario());
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // Método para verificar e remover reservas expiradas
    public void verificarTimeouts() {
        lock.lock();
        try {
            LocalDateTime agora = LocalDateTime.now();
            reservasAtivas.values().removeIf(reserva -> {
                if (reserva.isExpirada(agora)) {
                    cancelarReserva(reserva.getUsuario());
                    return true;
                }
                return false;
            });
        } finally {
            lock.unlock();
        }
    }


}
