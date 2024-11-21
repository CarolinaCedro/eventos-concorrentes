package com.cedro.eventos.model;


import com.cedro.eventos.model.enums.PerfilUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated
    private PerfilUser perfilUser;
    private String nome;
    private String telefone;
    private boolean possuiReservaAtiva;
    private boolean emFilaDeEspera;

    @ManyToMany(mappedBy = "filaDeEspera")
    private List<FilaDeEspera> filas;

}

