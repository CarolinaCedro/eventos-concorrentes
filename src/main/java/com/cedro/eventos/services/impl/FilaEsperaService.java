package com.cedro.eventos.services.impl;

import com.cedro.eventos.model.FilaDeEspera;
import com.cedro.eventos.repository.FilaEsperaRepository;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class FilaEsperaService extends RestServiceAbstractImpl<FilaDeEspera> {

    private final Lock lock = new ReentrantLock();
    private final List<String> filaDeEspera = new ArrayList<>();

    private final FilaEsperaRepository repository;

    public FilaEsperaService(FilaEsperaRepository repository) {
        this.repository = repository;
    }


    public void entrarNaFila(String usuario) {
        lock.lock();
        try {
            filaDeEspera.add(usuario);
        } finally {
            lock.unlock();
        }
    }

    public String proximoUsuario() {
        lock.lock();
        try {
            return filaDeEspera.isEmpty() ? null : filaDeEspera.remove(0);
        } finally {
            lock.unlock();
        }
    }


    @Override
    protected JpaRepository<FilaDeEspera, String> getRepository() {
        return this.repository;
    }
}
