package com.cedro.eventos.controller;

import com.cedro.eventos.model.Evento;
import com.cedro.eventos.services.impl.EventoService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class WebSocketController {


    private final EventoService eventoService;

    public WebSocketController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @MessageMapping("/reservar")
    @SendTo("/topic/eventos")
    public String reservar(String nomeEvento) {
        for (Evento evento : eventoService.listarEventos()) {
            if (evento.getNome().equals(nomeEvento)) {
                boolean reservado = evento.reservarVaga();
                return reservado ? "Reserva confirmada para " + nomeEvento : "Sem vagas para " + nomeEvento;
            }
        }
        return "Evento não encontrado.";
    }

}
