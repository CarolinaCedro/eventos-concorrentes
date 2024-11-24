package com.cedro.eventos.services.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificacaoService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyAll(String destination, Object message) {
        messagingTemplate.convertAndSend(destination, message);
    }
}
