/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameservidor;

/**
 * @author nogueirajuan
 */

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Servidor extends Thread {
    public static ArrayList<Usuario> usuarios = new ArrayList();
    public static ArrayList<Usuario> disponiveis = new ArrayList();
    public static ArrayList<String> nomesDisponiveis = new ArrayList();
    public static ArrayList<Usuario> indisponiveis = new ArrayList();
    public static ArrayList<String> nomesIndisponiveis = new ArrayList();
    public static ArrayList<String> nomesUsuarios = new ArrayList();
    private String usuarioRecebido = "";
    public static String[][] matrizServidores;

    public static void execucao() throws IOException {
        adicionaContatos();
//        nomesUsuarios = new ArrayList();
//        nomesUsuarios=nomesUsuarios1;

        int porta = 5555;
        ServerSocket socketServidor = new ServerSocket(porta);
        System.out.println("Servidor em execucao pela porta " + porta);
        while (true) {

            Socket socketConexao = socketServidor.accept();
            System.out.println("Conexao aceita");
            Thread t = new Servidor(socketConexao);
            t.start();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        adicionaContatos();
//        nomesUsuarios = new ArrayList();
//        nomesUsuarios=nomesUsuarios1;

        int porta = 5555;
        ServerSocket socketServidor = new ServerSocket(porta);
        System.out.println("Servidor em execucao pela porta " + porta);
        while (true) {

            Socket socketConexao = socketServidor.accept();
            System.out.println("Conexao aceita");
            Thread t = new Servidor(socketConexao);
            t.start();
        }
    }

    private Socket conexao = null;

    public Servidor(Socket s) {
        conexao = s;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
            oos.writeObject(nomesUsuarios);
            usuarioRecebido = (String) entrada.readObject();
            Iterator<String> itNomD = nomesDisponiveis.iterator();
            boolean bWhile = false;
            while (itNomD.hasNext()) {
                String inteiro = (String) itNomD.next();
                String[] inteiroQuebrado = inteiro.split(" ");
                inteiro = inteiroQuebrado[0] + " " + inteiroQuebrado[1];
                if (inteiro.equalsIgnoreCase(usuarioRecebido)) {
                    String msg = "erro";
                    oos.writeObject(msg);
                    bWhile = true;
                    this.interrupt();
                    break;
                }
            }
            if (!bWhile) {
                String msg = "ok";
                oos.writeObject(msg);
                InetAddress ipCliente = conexao.getInetAddress();
                int portaCliente = conexao.getPort();
                Iterator<Usuario> it = usuarios.iterator();
                it = usuarios.iterator();
                Usuario pegaUsuario = null;
                while (it.hasNext()) {
                    pegaUsuario = (Usuario) it.next();
                    String aux = pegaUsuario.nome + " " + pegaUsuario.sobrenome;
                    for (int i = 0; i < matrizServidores.length; i++) {
                        if (matrizServidores[i][0].equalsIgnoreCase(aux))
                            matrizServidores[i][1] = conexao.getInetAddress().getHostAddress();
                    }
                    if (aux.equalsIgnoreCase(usuarioRecebido)) {
                        pegaUsuario.ip = ipCliente;
                        pegaUsuario.porta = portaCliente;
                        pegaUsuario.disponivel = true;
                        pegaUsuario.socket = conexao;
                        break;
                    }
                }
                String auxUsuarioRecebido = usuarioRecebido;
                for (int i = 0; i < matrizServidores.length; i++) {
                    int porta;
                    if (matrizServidores[i][0].equalsIgnoreCase(usuarioRecebido)) {
                        porta = Integer.parseInt(matrizServidores[i][2]);
                        auxUsuarioRecebido += " " + conexao.getInetAddress().getHostAddress() + " " + porta;
                    }
                }
                disponiveis.add(pegaUsuario);
                nomesDisponiveis.add(auxUsuarioRecebido);
                nomesIndisponiveis.remove((usuarioRecebido));
                indisponiveis.remove(pegaUsuario);
                ArrayList<String> auxDisponiveis = new ArrayList();
                ArrayList<String> auxIndisponiveis = new ArrayList();
                it = usuarios.iterator();
                String[] auxAmigos = pegaUsuario.amigos;
                for (String nomeAmigo : auxAmigos) {
                    Iterator<String> operacao = nomesDisponiveis.iterator();
                    boolean b = false;
                    while (operacao.hasNext()) {
                        String recebeu = (String) operacao.next();
                        String[] recebeuQuebrado = recebeu.split(" ");
                        String novoRecebeu = recebeuQuebrado[0] + " " + recebeuQuebrado[1];
                        b = false;
                        if (novoRecebeu.equalsIgnoreCase(nomeAmigo)) {
                            auxDisponiveis.add(recebeu);
                            b = true;
                            break;
                        }
                    }
                    if (!b) auxIndisponiveis.add(nomeAmigo);
                }
                atualizaLista(oos, pegaUsuario, nomesDisponiveis, nomesIndisponiveis);

                oos.writeObject(pegaUsuario.amigos);
                oos.writeObject(auxDisponiveis);
                oos.writeObject(auxIndisponiveis);
                String aux = pegaUsuario.nome + " " + pegaUsuario.sobrenome;
                for (int i = 0; i < matrizServidores.length; i++) {
                    if (matrizServidores[i][0].equalsIgnoreCase(aux)) {
                        matrizServidores[i][1] = conexao.getInetAddress().getHostAddress();
                        oos.writeObject(matrizServidores[i][2]);
                    }
                }
                //            Thread keepAlive = new KeepAliveServidor(conexao);
                //            keepAlive.start();
                String usuarioSaida = (String) entrada.readObject();
                String[] separado = usuarioSaida.split(" ");
                it = disponiveis.iterator();
                Usuario encontra = null;
                while (it.hasNext()) {
                    encontra = (Usuario) it.next();
                    if (encontra.nome.equalsIgnoreCase(separado[0]) && encontra.sobrenome.equalsIgnoreCase(separado[1]))
                        break;
                }
                disponiveis.remove(encontra);
                Iterator itAuxDisp = nomesDisponiveis.iterator();
                while (itAuxDisp.hasNext()) {
                    String nomeComIp = (String) itAuxDisp.next();
                    String[] vetorNome = nomeComIp.split(" ");
                    String nomeSemIp = (String) vetorNome[0] + " " + vetorNome[1];
                    if (nomeSemIp.equalsIgnoreCase(usuarioSaida)) {
                        nomesDisponiveis.remove(nomeComIp);
                        break;
                    }
                }
                nomesIndisponiveis.add(usuarioSaida);
                indisponiveis.add(encontra);
                Collections.sort(nomesIndisponiveis);
                // Atualiza
                atualizaLista(oos, encontra, nomesDisponiveis, nomesIndisponiveis);
            }
        } catch (SocketException se) {
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void atualizaLista(ObjectOutputStream oos, Usuario pegaUsuario, ArrayList<String> auxDisponiveis, ArrayList<String> auxIndisponiveis) throws IOException {
        String nomeCompleto = pegaUsuario.nome + " " + pegaUsuario.sobrenome;
        Iterator<Usuario> iteratorUsuario = disponiveis.iterator();
        while (iteratorUsuario.hasNext()) {
            Usuario possivelAmigo = (Usuario) iteratorUsuario.next();
            for (int i = 0; i < possivelAmigo.amigos.length; i++) {
                if (nomeCompleto.equalsIgnoreCase(possivelAmigo.amigos[i])) {
                    oos = new ObjectOutputStream(possivelAmigo.socket.getOutputStream());
                    oos.writeObject(auxDisponiveis);
                    oos.writeObject(auxIndisponiveis);
                }
            }
        }
    }

    public static void adicionaContatos() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Dados.txt"));
            int qtdeUsuarios = Integer.parseInt(br.readLine());
            matrizServidores = new String[qtdeUsuarios][3];
            int porta = 5600;
            for (int i = 0; i < qtdeUsuarios; i++) {
                String linha = br.readLine();
                String[] primeiraLinha = linha.split(" ");
                String nomeUsuario = primeiraLinha[0];
                String sobrenomeUsuario = primeiraLinha[1];
                int nroAmigos = Integer.parseInt(primeiraLinha[2]);
                String[] amigos = new String[nroAmigos];
                matrizServidores[i][0] = nomeUsuario + " " + sobrenomeUsuario;
                matrizServidores[i][2] = Integer.toString(porta);
                porta++;
                for (int j = 0; j < nroAmigos; j++) {
                    String amigo = br.readLine();
                    amigos[j] = amigo;
                }
                Usuario novo = new Usuario(nomeUsuario, sobrenomeUsuario, amigos);
                usuarios.add(novo);
                nomesUsuarios.add(nomeUsuario + " " + sobrenomeUsuario);
                nomesIndisponiveis.add(nomeUsuario + " " + sobrenomeUsuario);
            }
            Collections.sort(nomesUsuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
