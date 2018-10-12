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

/**
 *
 * @author gjuni
 */
public class Conversa extends Thread{
    private Socket conexao=null;
    protected static JLabel label1;
    protected static JTextArea jTextArea2;
    protected static String nomeAmigo;
    public Conversa (Socket s, JLabel label1, JTextArea jTextArea2, String nomeAmigo){
        conexao=s;
        Conversa.label1=label1;
        Conversa.jTextArea2=jTextArea2;
        Conversa.nomeAmigo=nomeAmigo;
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
                jTextArea2.append(nomeAmigo + " diz: " +mensagemRecebida +"\n");
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
