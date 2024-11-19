package com.cedro.eventos.model;

import lombok.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FilaDeEspera {


    private int idEvento; // ID do evento associado a esta fila de espera
    private Queue<Usuario> filaDeEspera = new ConcurrentLinkedQueue<>(); // Fila de espera dos usuários para este evento


    // Adiciona um usuário à fila de espera
    public void adicionarUsuario(Usuario usuario) {
        if (usuario != null) {
            usuario.setEmFilaDeEspera(true);
            filaDeEspera.add(usuario);
        }
    }

    // Remove e retorna o próximo usuário da fila de espera (ou null se a fila estiver vazia)
    public Usuario proximoUsuario() {
        Usuario usuario = filaDeEspera.poll();
        if (usuario != null) {
            usuario.setEmFilaDeEspera(false);
        }
        return usuario;
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
