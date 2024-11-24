package com.cedro.eventos.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WebSocketUserService {

    private final SimpUserRegistry simpUserRegistry;

    @Autowired
    public WebSocketUserService(SimpUserRegistry simpUserRegistry) {
        this.simpUserRegistry = simpUserRegistry;
    }

    public Set<SimpUser> getConnectedUsersCount() {
        System.out.println(simpUserRegistry.getUsers());
        return simpUserRegistry.getUsers();
    }
}
