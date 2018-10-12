/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.contacteditor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gjuni
 */
public class OuvirConversa extends Thread{
    protected static int portaServidor;
    protected static String usuarioSelecionado;
    public OuvirConversa(int portaServidor, String usuarioSelecionado){
        OuvirConversa.portaServidor=portaServidor;
        OuvirConversa.usuarioSelecionado=usuarioSelecionado;
    }
    @Override
    public void run(){
        ServerSocket welcomeSocket=null;
        try {
            welcomeSocket = new ServerSocket (portaServidor);
            while(true){
                Socket socketAmigo = welcomeSocket.accept();
                int porta = socketAmigo.getPort();
                ObjectOutputStream oos;
                try {
                    oos = new ObjectOutputStream(socketAmigo.getOutputStream());
                    oos.writeObject(usuarioSelecionado);
                } catch (IOException ex) {
                    Logger.getLogger(JanelaContatos.class.getName()).log(Level.SEVERE, null, ex);
                }
                new Mensagem(socketAmigo, usuarioSelecionado).setVisible(true);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(OuvirConversa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OuvirConversa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
