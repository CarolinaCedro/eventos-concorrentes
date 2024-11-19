package com.cedro.eventos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer vagasDisponiveis;

    public synchronized boolean temVaga() {
        return vagasDisponiveis > 0;
    }

    public synchronized void diminuirVaga() {
        if (vagasDisponiveis > 0) {
            vagasDisponiveis--;
        }
    }

    public synchronized void aumentarVaga() {
        vagasDisponiveis++;
    }

}
