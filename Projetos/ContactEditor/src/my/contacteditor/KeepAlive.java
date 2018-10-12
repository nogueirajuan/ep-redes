/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.contacteditor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gjuni
 */
public class KeepAlive extends Thread{
    private static Socket s;
    public KeepAlive(Socket conexao){
        s=conexao;
    }
    @Override
    public void run (){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            while(true){
                long tempoInicio = System.currentTimeMillis();
                oos.writeObject(".");
                while (true){
                    long tempoAtual = System.currentTimeMillis();
                    if((tempoAtual-tempoInicio)>60000) break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(KeepAlive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
