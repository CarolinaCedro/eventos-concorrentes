package com.cedro.eventos.controller;

import com.cedro.eventos.model.CreatedProcess;
import com.cedro.eventos.services.impl.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


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
}
