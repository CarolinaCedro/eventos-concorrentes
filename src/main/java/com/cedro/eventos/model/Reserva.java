package com.cedro.eventos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Evento evento;
    private LocalDateTime dataReserva;
    private static final Integer TEMPO_EXPIRACAO_MINUTOS = 10;

    public Reserva(Usuario usuario, Evento evento, LocalDateTime dataReserva) {
        this.usuario = usuario;
        this.evento = evento;
        this.dataReserva = dataReserva;
    }

    public boolean isExpirada(LocalDateTime agora) {
        return dataReserva.plusMinutes(TEMPO_EXPIRACAO_MINUTOS).isBefore(agora);
    }

}
