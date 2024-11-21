package com.cedro.eventos.repository;

import com.cedro.eventos.model.FilaDeEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaDeEspera, String> {


}
