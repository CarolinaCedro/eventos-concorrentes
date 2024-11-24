package com.cedro.eventos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private String nome;
    private int vagasDisponiveis;

    private LocalDateTime date;

    public Evento(String nome, int vagas) {
        this.nome = nome;
        this.vagasDisponiveis = vagas;
        this.date = LocalDateTime.now(); // Definindo a data no momento da criação
    }


    @PrePersist
    private void prePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now(); // Definindo a data atual
        }
    }


    // Getters e Setters
    public synchronized boolean reservarVaga() {
        if (vagasDisponiveis > 0) {
            vagasDisponiveis--;
            return true;
        }
        return false;
    }

    public boolean temVagasDisponiveis() {
        return this.vagasDisponiveis > 0;
    }


    public synchronized void liberarVaga() {
        vagasDisponiveis++;
    }

}
