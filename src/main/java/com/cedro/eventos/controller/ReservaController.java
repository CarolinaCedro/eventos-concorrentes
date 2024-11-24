package com.cedro.eventos.controller;

import com.cedro.eventos.model.Reserva;
import com.cedro.eventos.services.RestServiceAbstractImpl;
import com.cedro.eventos.services.impl.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos-concorrentes/reserva")
public class ReservaController extends AbstractController<Reserva> {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @Override
    protected RestServiceAbstractImpl<Reserva> getService() {
        return this.service;
    }


    @PostMapping("/reservar")
    public ResponseEntity<Reserva> reservar(
            @RequestParam String eventoId,
            @RequestParam String usuarioId
    ) {
        Reserva reserva = service.reservar(eventoId, usuarioId);
        return ResponseEntity.ok(reserva);
    }


    @DeleteMapping("/{reservaId}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable String reservaId) {
        service.cancelarReserva(reservaId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/fila")
    public ResponseEntity<Void> entrarNaFila(
            @RequestParam String eventoId,
            @RequestParam String usuarioId
    ) {
        service.entrarNaFila(eventoId, usuarioId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/timeout")
    public ResponseEntity<Void> verificarTimeout() {
        service.verificarTimeoutsNaFila();
        return ResponseEntity.ok().build();
    }


}
