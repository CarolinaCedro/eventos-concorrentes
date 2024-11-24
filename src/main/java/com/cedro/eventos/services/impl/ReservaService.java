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

        // Verificar se há vaga disponível e reservar uma
        if (!evento.reservarVaga()) {
            throw new IllegalStateException("Evento sem vagas disponíveis");
        }

        // Criar reserva
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (usuario.isPossuiReservaAtiva()) {
            throw new IllegalStateException("Usuário já possui uma reserva ativa");
        }

        Reserva reserva = new Reserva(usuario, evento, LocalDateTime.now());
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
        evento.liberarVaga();

        // Atualizar status do usuário
        Usuario usuario = reserva.getUsuario();
        usuario.setPossuiReservaAtiva(false);

        // Salvar mudanças
        eventoRepository.save(evento);
        usuarioRepository.save(usuario);
        repository.delete(reserva);

        // Gerenciar fila de espera
        FilaDeEspera fila = filaEsperaRepository.findByEvento(evento);
        if (fila != null && !fila.getUsuarios().isEmpty()) {
            Usuario proximoUsuario = fila.removerProximoUsuario();
            if (proximoUsuario != null) {
                proximoUsuario.setPossuiReservaAtiva(true); // Atualiza status do próximo usuário
                usuarioRepository.save(proximoUsuario); // Salva o status atualizado
                filaEsperaRepository.save(fila); // Persistir alterações na fila
            }
        }
    }






    @Scheduled(fixedRate = 30000) // Executa a cada 30 segundos
    public void verificarTimeoutsNaFila() {
        List<FilaDeEspera> filas = filaEsperaRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();

        filas.forEach(fila -> {
            List<Usuario> expirados = fila.getFilaDeEspera().stream()
                    .filter(usuario -> usuario.getTempoNaFila() != null &&
                            usuario.getTempoNaFila().isBefore(agora.minusSeconds(30)))
                    .toList();

            expirados.forEach(usuario -> {
                fila.removerUsuario(usuario); // Remove da posição atual
                usuario.setTempoNaFila(LocalDateTime.now()); // Atualiza o tempo na fila
                fila.adicionarUsuario(usuario); // Reinsere no final
            });

            filaEsperaRepository.save(fila); // Salva as mudanças na fila
        });
    }




    public synchronized void entrarNaFila(String eventoId, String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (usuario.isEmFilaDeEspera()) {
            throw new IllegalStateException("Usuário já está na fila de espera");
        }

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado"));

        // Busca a fila associada ao evento ou cria uma nova fila, se não existir
        FilaDeEspera fila = filaEsperaRepository.findByEvento(evento);
        if (fila == null) {
            fila = new FilaDeEspera();
            fila.setEvento(evento);
        }

        // Adiciona o usuário à fila
        usuario.setTempoNaFila(LocalDateTime.now());
        fila.adicionarUsuario(usuario);

        // Salva a fila e o usuário
        filaEsperaRepository.save(fila);
        usuarioRepository.save(usuario);
    }




}
