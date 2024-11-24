package com.cedro.eventos.controller;

import com.cedro.eventos.model.CreatedProcess;
import com.cedro.eventos.services.impl.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class ProcessController {

    private ProcessorService service;

    @Autowired
    public ProcessController(ProcessorService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<CreatedProcess> execute() {
        service.execute();
        return ResponseEntity.ok().body(new CreatedProcess(LocalDateTime.now()));
    }

    @GetMapping("/connected-users")
    public Set<SimpUser> getConnectedUsers() {
        return service.logConnectedUsers();
    }
}
