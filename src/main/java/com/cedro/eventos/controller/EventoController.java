package com.cedro.eventos.controller;

import com.cedro.eventos.model.Evento;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import com.cedro.eventos.services.impl.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos-concorrentes/evento")
public class EventoController extends AbstractController<Evento> {

    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    @Override
    protected RestServiceAbstractImpl<Evento> getService() {
        return this.service;
    }


    @GetMapping("/reservas/count")
    public ResponseEntity<Long> reservasCount() {
        return ResponseEntity.ok(this.service.reservaCount());
    }
}
