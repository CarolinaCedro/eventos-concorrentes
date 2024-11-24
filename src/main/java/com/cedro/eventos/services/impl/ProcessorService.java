package com.cedro.eventos.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Slf4j
@Service
public class ProcessorService {

    private SimpMessagingTemplate template;
    private final WebSocketUserService webSocketUserService;

    @Autowired
    public ProcessorService(SimpMessagingTemplate template, WebSocketUserService webSocketUserService) {
        this.template = template;
        this.webSocketUserService = webSocketUserService;
    }

    @Async
    public void execute() {
        try {
            Thread.sleep(2000L);
            template.convertAndSend("/statusProcessor", gerarMensagem(1));
            Thread.sleep(2000L);
            template.convertAndSend("/statusProcessor", gerarMensagem(2));
            Thread.sleep(2000L);
            template.convertAndSend("/statusProcessor", gerarMensagem(3));
        } catch (InterruptedException e) {
            log.error("Erro durante o procesamento.", e);
        }
    }


    public Set<SimpUser> logConnectedUsers() {
        return webSocketUserService.getConnectedUsersCount();
    }

    private String gerarMensagem(int etapa) {
        return String.format("Executada a etapa %s às %s", etapa,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
