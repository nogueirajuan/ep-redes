/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.contacteditor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Conversa extends Thread{
    private Socket conexao=null;
    protected static JLabel label1;
    protected static JTextArea jTextArea2;
    protected static String nomeAmigo;
    protected static Mensagem msg;
    
    public Conversa (Socket s, JLabel label1, JTextArea jTextArea2, String nomeAmigo, Mensagem msg){
        conexao=s;
        Conversa.label1=label1;
        Conversa.jTextArea2=jTextArea2;
        Conversa.nomeAmigo=nomeAmigo;
        this.msg = msg;
    }
    
    @Override
    public void run(){
        ObjectInputStream ois=null;
        while(!Thread.interrupted()){
            try{
                ois = new ObjectInputStream(conexao.getInputStream());
                String mensagemRecebida = (String) ois.readObject();
                System.out.println(mensagemRecebida);
                // Imprime
                
                if(Mensagem.CABECALHO_JOGO.equals(mensagemRecebida.substring(0, 1))){
                    int x = Integer.parseInt("" + mensagemRecebida.charAt(1));
                    int y = Integer.parseInt("" + mensagemRecebida.charAt(2));
                    this.msg.realizaJogada(x, y);
                    this.msg.verificaGanhador(x, y);
                }
                
                if(Mensagem.CABECALHO_MSG.equals(mensagemRecebida.substring(0, 1))){
                    mensagemRecebida = mensagemRecebida.substring(1, mensagemRecebida.length()-1);
                    jTextArea2.append(nomeAmigo + " diz: " +mensagemRecebida +"\n");
                }
                
            }
            catch (SocketException e){
                break;
            } catch (IOException ex) {
                Logger.getLogger(Conversa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conversa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
}
