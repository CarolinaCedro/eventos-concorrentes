package com.cedro.eventos.controller;

import com.cedro.eventos.model.Usuario;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import com.cedro.eventos.services.impl.UsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos-concorrentes/user")
public class UsuarioController extends AbstractController<Usuario> {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Override
    protected RestServiceAbstractImpl<Usuario> getService() {
        return this.service;
    }
}
