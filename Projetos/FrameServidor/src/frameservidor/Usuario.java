/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameservidor;

import java.net.InetAddress;
import java.net.Socket;

/**
 * @author nogueirajuan
 */
public class Usuario {
    String nome;
    String sobrenome;
    String[] amigos;
    boolean disponivel = false;
    InetAddress ip;
    int porta;
    Socket socket;

    public Usuario(String nome, String sobrenome, String[] amigos) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.amigos = amigos;
    }
}
