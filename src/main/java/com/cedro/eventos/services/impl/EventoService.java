package com.cedro.eventos.services.impl;

import com.cedro.eventos.model.Evento;
import com.cedro.eventos.repository.EventoRepository;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class EventoService extends RestServiceAbstractImpl<Evento> {

    private final EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }


    @Override
    protected JpaRepository<Evento, String> getRepository() {
        return this.repository;
    }

    public Long reservaCount() {
        return repository.count();
    }
}
