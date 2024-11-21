package com.cedro.eventos.services.impl;

import com.cedro.eventos.model.Usuario;
import com.cedro.eventos.repository.UsuarioRepository;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends RestServiceAbstractImpl<Usuario> {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }


    @Override
    protected JpaRepository<Usuario, String> getRepository() {
        return this.repository;
    }
}
