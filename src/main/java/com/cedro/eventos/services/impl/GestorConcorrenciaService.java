//package com.cedro.eventos.services.impl;
//
//import com.cedro.eventos.model.GestorDeConcorrencia;
//import com.cedro.eventos.repository.GestorConcorrenciaRepository;
//import com.cedro.eventos.services.RestServiceAbstractImpl;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GestorConcorrenciaService extends RestServiceAbstractImpl<GestorDeConcorrencia> {
//
//    private final GestorConcorrenciaRepository repository;
//
//    public GestorConcorrenciaService(GestorConcorrenciaRepository repository) {
//        this.repository = repository;
//    }
//
//
//    @Override
//    protected JpaRepository<GestorDeConcorrencia, String> getRepository() {
//        return this.repository;
//    }
//}
