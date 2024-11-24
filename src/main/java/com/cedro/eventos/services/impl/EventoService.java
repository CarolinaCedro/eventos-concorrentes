package com.cedro.eventos.services.impl;

import com.cedro.eventos.model.Evento;
import com.cedro.eventos.repository.EventoRepository;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService extends RestServiceAbstractImpl<Evento> {


    private final List<Evento> eventos = new ArrayList<>();
    private final List<String> filaDeEspera = new ArrayList<>();


    private final EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;

    }

    public synchronized List<Evento> listarEventos() {
        return eventos;
    }

    public synchronized void adicionarFila(String usuario) {
        filaDeEspera.add(usuario);
    }

    public synchronized String proximoNaFila() {
        return filaDeEspera.isEmpty() ? null : filaDeEspera.remove(0);
    }

    public Evento criarEventoMock(String nomeEvento, int vagas) {
        Evento evento = new Evento();
        evento.setNome(nomeEvento);
        evento.setVagasDisponiveis(vagas);
        evento.setDate(LocalDateTime.now()); // Exemplo: Evento para o dia seguinte
        return repository.save(evento);
    }


    @Override
    protected JpaRepository<Evento, String> getRepository() {
        return this.repository;
    }

    public Long reservaCount() {
        return repository.count();
    }
}
