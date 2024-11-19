package com.cedro.eventos.repository;

import com.cedro.eventos.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento,String> {
}
