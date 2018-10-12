/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.contacteditor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gjuni
 */
public class KeepAliveServidor extends Thread{
    private static Socket s;
    public KeepAliveServidor (Socket conexao){
        s=conexao;
    }
    @Override
    public void run (){
        try {
            ObjectInputStream ois = new ObjectInputStream (s.getInputStream());
            boolean b=false;
            while (true){
                long tempoInicio = System.currentTimeMillis();
                Thread recebeSinal = new RecebeSinal(s);
                recebeSinal.start();
                while (true){
                    long tempoAtual = System.currentTimeMillis();
                    if(recebeSinal.isAlive()){
                        if((tempoAtual-tempoInicio)>180000){
                            recebeSinal.interrupt();
                            b=true;
                            // Tira o usuario do disponivel e coloca como indisponivel
                            break;
                        }
                    }
                    else break;
                }
                if(!b) break;
            }
        } catch (IOException ex) {
            Logger.getLogger(KeepAliveServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
