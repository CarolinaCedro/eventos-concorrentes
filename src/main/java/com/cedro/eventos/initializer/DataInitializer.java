package com.cedro.eventos.initializer;

import com.cedro.eventos.model.Reserva;
import com.cedro.eventos.services.impl.EventoService;
import com.cedro.eventos.services.impl.ReservaService;
import com.cedro.eventos.services.impl.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService; // Caso precise criar usuários
    private final EventoService eventoService;   // Caso precise criar eventos

    public DataInitializer(ReservaService reservaService, UsuarioService usuarioService, EventoService eventoService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Criar dados de exemplo para usuários e eventos
        var usuario1 = usuarioService.criarUsuarioMock("joao");
        var usuario2 = usuarioService.criarUsuarioMock("Carol");
        var usuario3 = usuarioService.criarUsuarioMock("Pedro");

        var evento1 = eventoService.criarEventoMock("Evento de Tecnologia",5);
        var evento2 = eventoService.criarEventoMock("Workshop de Spring Boot",3);
        var evento3 = eventoService.criarEventoMock("Angular",0);
        var evento4 = eventoService.criarEventoMock("Desbravando RXjs",8);
        var evento5 = eventoService.criarEventoMock("Universo Webfluxõ",2);

        // Criar reservas mockadas
        Reserva reserva1 = new Reserva(usuario1, evento1, LocalDateTime.now());
        Reserva reserva2 = new Reserva(usuario1, evento1, LocalDateTime.now());
        Reserva reserva3 = new Reserva(usuario2, evento1, LocalDateTime.now());
        Reserva reserva4 = new Reserva(usuario1, evento1, LocalDateTime.now());
        Reserva reserva5 = new Reserva(usuario2, evento2, LocalDateTime.now());

        // Salvar reservas no banco
        reservaService.create(reserva1);
        reservaService.create(reserva2);
        reservaService.create(reserva3);
        reservaService.create(reserva4);
        reservaService.create(reserva5);

        System.out.println("Dados mockados criados com sucesso.");
    }
}

