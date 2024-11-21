package com.cedro.eventos.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void enviarAtualizacao(String eventoId, String mensagem) {
        messagingTemplate.convertAndSend("/topic/eventos", "Evento " + eventoId + ": " + mensagem);
    }
}
