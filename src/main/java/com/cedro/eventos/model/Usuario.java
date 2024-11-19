package com.cedro.eventos.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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
    private String nome;
    private String email;
    private boolean possuiReservaAtiva; // Indica se o usuário tem uma reserva ativa
    private boolean emFilaDeEspera; // Indica se o usuário está em uma fila de espera

}

