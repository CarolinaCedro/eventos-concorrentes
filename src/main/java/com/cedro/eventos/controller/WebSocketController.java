package com.cedro.eventos.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class WebSocketController {

    // Método para processar mensagens enviadas pelos usuários
    @MessageMapping("/evento/reserva")
    @SendTo("/topic/eventos")
    public String handleReserva(String message) {
        // Você pode processar a mensagem recebida e retorná-la para todos os inscritos
        return "Atualização: " + message;
    }

}
