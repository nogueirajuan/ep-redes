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
 * @author nogueirajuan
 */
public class RecebeSinal extends Thread{
    protected static Socket s;
    public RecebeSinal (Socket conexao){
        s=conexao;
    }
    @Override
    public void run (){
        try {
            ObjectInputStream ois = new ObjectInputStream (s.getInputStream());
            String sinal = (String) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(RecebeSinal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecebeSinal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
