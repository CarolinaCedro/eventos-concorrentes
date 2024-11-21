package com.cedro.eventos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class FilaDeEspera {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Relacionamento com Evento (OneToOne)
    @OneToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "id", nullable = false)
    private Evento evento;

    // Relacionamento com Usuario (ManyToMany ou OneToMany)
    @ManyToMany
    @JoinTable(
            name = "fila_usuarios",
            joinColumns = @JoinColumn(name = "fila_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> filaDeEspera = new ArrayList<>();


    // Adiciona um usuário à fila de espera
    public void adicionarUsuario(Usuario usuario) {
        if (usuario != null) {
            usuario.setEmFilaDeEspera(true);
            filaDeEspera.add(usuario);
        }
    }

    // Remove e retorna o próximo usuário da fila de espera (ou null se a fila estiver vazia)
    public Usuario proximoUsuario() {
        if (!filaDeEspera.isEmpty()) {
            Usuario usuario = filaDeEspera.remove(0);
            usuario.setEmFilaDeEspera(false);
            return usuario;
        }
        return null;
    }

    // Retorna o número de usuários atualmente na fila de espera
    public int tamanhoFila() {
        return filaDeEspera.size();
    }

    // Verifica se a fila de espera está vazia
    public boolean estaVazia() {
        return filaDeEspera.isEmpty();
    }
}
