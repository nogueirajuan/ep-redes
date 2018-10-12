package jogovelha;

public class Cronometro extends Thread {

    private JogoVelha label;
    private boolean paraCronometro;
    private Integer i;

    public Cronometro(JogoVelha label) {
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
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {

        }
    }

}
