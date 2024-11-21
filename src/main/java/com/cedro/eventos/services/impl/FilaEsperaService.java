package com.cedro.eventos.services.impl;

import com.cedro.eventos.model.FilaDeEspera;
import com.cedro.eventos.repository.FilaEsperaRepository;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FilaEsperaService extends RestServiceAbstractImpl<FilaDeEspera> {

    private final FilaEsperaRepository repository;

    public FilaEsperaService(FilaEsperaRepository repository) {
        this.repository = repository;
    }


    @Override
    protected JpaRepository<FilaDeEspera, String> getRepository() {
        return this.repository;
    }
}
