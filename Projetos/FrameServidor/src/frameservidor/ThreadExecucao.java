package frameservidor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gjuni
 */
public class ThreadExecucao extends Thread{
    protected static Servidor server;
    public ThreadExecucao(Servidor s){
        server=s;
    }
    public void run(){
        try {
            server.execucao();
        } catch (IOException ex) {
            Logger.getLogger(ThreadExecucao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
