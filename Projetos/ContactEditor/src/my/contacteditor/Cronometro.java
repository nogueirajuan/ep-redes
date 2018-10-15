package my.contacteditor;

import javax.swing.Timer;
import jogovelha.JogoVelha;

public class Cronometro extends Thread {

    private Mensagem label;
    private boolean paraCronometro;
    private Integer i;

    public Cronometro(Mensagem label) {
        this.label = label;
        paraCronometro = false;
        i = 20;
    }

    public void resetTimer() {
        this.i = 20;
    }
    public void zeraTimer() {
        this.i = 0;
    }
    
    public void paraCronometro(){
        this.paraCronometro = true;
    }

    @Override
    public void run() {
        try {
            while (i >= 0 && (!paraCronometro)) {
                if (i > 0) {
                    if (i >= 10) {
                        label.atualizarJLabel("00:" + i);
                    } else {
                        label.atualizarJLabel("00:0" + i);
                    }
                    i--;
                }else{
                    label.jogadaCronometroZerado();
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {

        }
    }

}
