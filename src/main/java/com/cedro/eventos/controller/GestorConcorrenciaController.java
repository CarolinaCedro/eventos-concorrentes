//package com.cedro.eventos.controller;
//
//import com.cedro.eventos.model.GestorDeConcorrencia;
//import com.cedro.eventos.services.RestServiceAbstractImpl;
//import com.cedro.eventos.services.impl.GestorConcorrenciaService;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/eventos-concorrentes/gestor-concorrencia")
//public class GestorConcorrenciaController extends AbstractController<GestorDeConcorrencia> {
//
//    private final GestorConcorrenciaService service;
//
//    public GestorConcorrenciaController(GestorConcorrenciaService service) {
//        this.service = service;
//    }
//
//    @Override
//    protected RestServiceAbstractImpl<GestorDeConcorrencia> getService() {
//        return this.service;
//    }
//}
