package com.cedro.eventos.services.impl;

import com.cedro.eventos.model.Evento;
import com.cedro.eventos.model.FilaDeEspera;
import com.cedro.eventos.model.Reserva;
import com.cedro.eventos.model.Usuario;
import com.cedro.eventos.repository.EventoRepository;
import com.cedro.eventos.repository.FilaEsperaRepository;
import com.cedro.eventos.repository.ReservaRepository;
import com.cedro.eventos.repository.UsuarioRepository;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService extends RestServiceAbstractImpl<Reserva> {

    private final ReservaRepository repository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    private final FilaEsperaRepository filaEsperaRepository;


    public ReservaService(ReservaRepository repository, EventoRepository eventoRepository, UsuarioRepository usuarioRepository, FilaEsperaRepository filaEsperaRepository) {
        this.repository = repository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.filaEsperaRepository = filaEsperaRepository;
    }


    @Override
    protected JpaRepository<Reserva, String> getRepository() {
        return this.repository;
    }


    public synchronized Reserva reservar(String eventoId, String usuarioId) {
        // Buscar evento
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        // Verificar se há vaga disponível
        if (!evento.temVaga()) {
            throw new IllegalStateException("Evento sem vagas disponíveis");
        }

        // Criar reserva
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (usuario.isPossuiReservaAtiva()) {
            throw new IllegalStateException("Usuário já possui uma reserva ativa");
        }

        Reserva reserva = new Reserva(usuario, evento, LocalDateTime.now());
        evento.diminuirVaga();
        usuario.setPossuiReservaAtiva(true);

        // Salvar dados atualizados
        eventoRepository.save(evento);
        usuarioRepository.save(usuario);
        return repository.save(reserva);
    }

    public synchronized void cancelarReserva(String reservaId) {
        // Buscar reserva
        Reserva reserva = repository.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        // Restaurar vaga no evento
        Evento evento = reserva.getEvento();
        evento.aumentarVaga();

        // Atualizar status do usuário
        Usuario usuario = reserva.getUsuario();
        usuario.setPossuiReservaAtiva(false);

        // Salvar mudanças
        eventoRepository.save(evento);
        usuarioRepository.save(usuario);
        repository.delete(reserva);
    }


    @Scheduled(fixedRate = 60000) // Executa a cada 1 minuto
    public void verificarTimeouts() {
        LocalDateTime agora = LocalDateTime.now();
        List<Reserva> reservasExpiradas = repository.findAll().stream()
                .filter(reserva -> reserva.isExpirada(agora))
                .toList();

        reservasExpiradas.forEach(reserva -> cancelarReserva(reserva.getId()));
    }


    public synchronized void entrarNaFila(String eventoId, String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (usuario.isEmFilaDeEspera()) {
            throw new IllegalStateException("Usuário já está na fila de espera");
        }

        Optional<Evento> event = this.eventoRepository.findById(eventoId);

        if (event.isPresent()) {

            Evento evento = event.get();


            FilaDeEspera fila = new FilaDeEspera();
            fila.setEvento(evento);


            fila.adicionarUsuario(usuario);
            filaEsperaRepository.save(fila);

        }


    }


}
