/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.contacteditor;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Mensagem extends javax.swing.JFrame {

    //Controladores do Jogos
    private int[][] tabuleiro = new int[3][3];
    private JButton[][] btnTabuleiro = new JButton[3][3];
    private int NUMERO_X = 1;
    private int NUMERO_O = 2;
    private int NUMERO_VAZIO = 0;
    private int TURNO_1 = 1;
    private int TURNO_2 = 2;
    private int turno = TURNO_1;
    private static int numeroJogador = 1;
    private Cronometro cronometro;

    //Gerenciadores msg
    public static String CABECALHO_MSG = "1";
    public static String CABECALHO_JOGO = "2";
    public static String CABECALHO_NOVO_JOGO = "3";
    public static String INDICE_POS_X = "1";
    public static String INDICE_POS_Y = "2";

    public static String usuarioRecebido;
    public static String nomeAmigo;
    public static Thread threadConversa;
    //public static String usuarioSelecionado;
    public static Socket iniciaConversa;

    /**
     * Creates new form Mensagem
     */
    public Mensagem(Socket iniciaConversa, String usuarioRecebido, int numeroJogador) throws IOException, ClassNotFoundException {
        this(iniciaConversa, usuarioRecebido);
        this.numeroJogador = numeroJogador;
        if(numeroJogador == TURNO_1){
            lblJogador1.setText(usuarioRecebido);
            lblJogador2.setText(nomeAmigo);
        }else{
            lblJogador2.setText(usuarioRecebido);
            lblJogador1.setText(nomeAmigo);
        }
        bloqueiaTabuleiroPeloTurno();
    }

    public Mensagem(Socket iniciaConversa, String usuarioRecebido) throws IOException, ClassNotFoundException {
        Mensagem.iniciaConversa = iniciaConversa;
        Mensagem.usuarioRecebido = usuarioRecebido;
        ObjectInputStream ois = new ObjectInputStream(iniciaConversa.getInputStream());
        nomeAmigo = (String) ois.readObject();
        initComponents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        threadConversa = new Conversa(iniciaConversa, label1, jTextArea2, nomeAmigo, this);
        threadConversa.start();

        //inicializadoresJogo
        this.cronometro = new Cronometro(this);
        iniciarMatrizBotoes();
        novoJogo();
    }
    
    public int[] procuraPosicaoVazia(){
        int[] retorno = new int[2];
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[0].length; j++) {
                if(tabuleiro[i][j] == NUMERO_VAZIO){
                    retorno[0] = i;
                    retorno[1] = j;
                    return retorno;
                }
            }
        }
        return null;
    }

    public void iniciarMatrizBotoes() {
        btnTabuleiro[0][0] = btnPos1;
        btnTabuleiro[0][1] = btnPos2;
        btnTabuleiro[0][2] = btnPos3;
        btnTabuleiro[1][0] = btnPos4;
        btnTabuleiro[1][1] = btnPos5;
        btnTabuleiro[1][2] = btnPos6;
        btnTabuleiro[2][0] = btnPos7;
        btnTabuleiro[2][1] = btnPos8;
        btnTabuleiro[2][2] = btnPos9;
    }

    public boolean verificaEmpate(){
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[0].length; j++) {
                if(tabuleiro[i][j] == NUMERO_VAZIO){
                    return false;
                }
            }
        }
        return true;
    }
    
    public void verificaGanhador(int x, int y) {
        boolean ganhador = false;
        JButton btnVencedor1 = null;
        JButton btnVencedor2 = null;
        JButton btnVencedor3 = null;

        if (tabuleiro[x][0] == tabuleiro[x][1] && tabuleiro[x][1] == tabuleiro[x][2]) {
            btnVencedor1 = btnTabuleiro[x][0];
            btnVencedor2 = btnTabuleiro[x][1];
            btnVencedor3 = btnTabuleiro[x][2];
            ganhador = true;
        }

        if (tabuleiro[0][y] == tabuleiro[1][y] && tabuleiro[1][y] == tabuleiro[2][y]) {
            btnVencedor1 = btnTabuleiro[0][y];
            btnVencedor2 = btnTabuleiro[1][y];
            btnVencedor3 = btnTabuleiro[2][y];
            ganhador = true;
        }

        if (tabuleiro[0][0] == tabuleiro[1][1]
                && tabuleiro[1][1] == tabuleiro[2][2]
                && tabuleiro[1][1] != NUMERO_VAZIO) {
            btnVencedor1 = btnTabuleiro[0][0];
            btnVencedor2 = btnTabuleiro[1][1];
            btnVencedor3 = btnTabuleiro[2][2];
            ganhador = true;
        }

        if (tabuleiro[0][2] == tabuleiro[1][1]
                && tabuleiro[1][1] == tabuleiro[2][0]
                && tabuleiro[1][1] != NUMERO_VAZIO) {
            btnVencedor1 = btnTabuleiro[0][2];
            btnVencedor2 = btnTabuleiro[1][1];
            btnVencedor3 = btnTabuleiro[2][0];
            ganhador = true;
        }

        if (ganhador) {
            btnVencedor1.setBackground(Color.GREEN);
            btnVencedor2.setBackground(Color.GREEN);
            btnVencedor3.setBackground(Color.GREEN);

            if (turno == TURNO_1) {
                JOptionPane.showMessageDialog(this, "Vencedor: Jogador 2");
            } else {
                JOptionPane.showMessageDialog(this, "Vencedor: Jogador 1");
            }
            this.cronometro.paraCronometro();
            this.cronometro.interrupt();
            this.dispose();
        }else{
            if(verificaEmpate()){
                cronometro.zeraTimer();
                JOptionPane.showMessageDialog(this, "Jogo empatado");
                this.cronometro.paraCronometro();
                this.cronometro.interrupt();
                this.dispose();
            }
        }
    }

    public void iniciarMatriz() {
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[0].length; j++) {
                tabuleiro[i][j] = 0;
            }
        }
    }

    public void iniciaTabuleiroInterface() {
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[0].length; j++) {
                btnTabuleiro[i][j].setText("");
                btnTabuleiro[i][j].setBackground(Color.gray);
            }
        }
    }

    public void bloqueiaTabuleiroPeloTurno() {
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[0].length; j++) {
                if (tabuleiro[i][j] == NUMERO_VAZIO) {
                    if (numeroJogador == turno) {
                        btnTabuleiro[i][j].setEnabled(true);
                    } else {
                        btnTabuleiro[i][j].setEnabled(false);
                    }
                }else{
                    btnTabuleiro[i][j].setEnabled(false);
                }
            }
        }
    }

    public void atualizarJLabel(String texto) {
        lblTempo.setText(texto);
    }

    public void realizaJogada(int x, int y) {
        if (tabuleiro[x][y] == NUMERO_VAZIO) {
            if (turno == TURNO_1) {
                tabuleiro[x][y] = NUMERO_X;
                btnTabuleiro[x][y].setText("X");
                turno = TURNO_2;
                lblV1.setText("");
                lblV2.setText("V");
            } else {
                tabuleiro[x][y] = NUMERO_O;
                btnTabuleiro[x][y].setText("O");
                turno = TURNO_1;
                lblV1.setText("V");
                lblV2.setText("");
            }

            bloqueiaTabuleiroPeloTurno();
            this.cronometro.resetTimer();

            if (!this.cronometro.isAlive()) {
                this.cronometro.start();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Posicao ja marcada");
        }
    }

    public void enviaMensagemNovoJogo() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(iniciaConversa.getOutputStream());
            oos.writeObject(CABECALHO_NOVO_JOGO);
        } catch (IOException ex) {
            Logger.getLogger(Mensagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviaMensagemJogo(int x, int y) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(iniciaConversa.getOutputStream());
            oos.writeObject("" + CABECALHO_JOGO + x + y);
        } catch (IOException ex) {
            Logger.getLogger(Mensagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void novoJogo() {
        turno = TURNO_1;
        iniciaTabuleiroInterface();
        iniciarMatriz();
        cronometro.resetTimer();
        
        lblV1.setText("V");
        lblV2.setText("");
        bloqueiaTabuleiroPeloTurno();
    }

    public void jogadaCronometroZerado(){
        if(numeroJogador == turno){
            int[] pos = procuraPosicaoVazia();
            realizaJogada(pos[0], pos[1]);
            enviaMensagemJogo(pos[0], pos[1]);
            verificaGanhador(pos[0], pos[1]);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        btnPos1 = new javax.swing.JButton();
        btnPos3 = new javax.swing.JButton();
        btnPos2 = new javax.swing.JButton();
        btnPos4 = new javax.swing.JButton();
        btnPos5 = new javax.swing.JButton();
        btnPos6 = new javax.swing.JButton();
        btnPos7 = new javax.swing.JButton();
        btnPos8 = new javax.swing.JButton();
        btnPos9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTempo = new javax.swing.JLabel();
        lblV1 = new javax.swing.JLabel();
        lblV2 = new javax.swing.JLabel();
        lblJogador1 = new javax.swing.JLabel();
        lblJogador2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        label1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jInternalFrame1.setVisible(true);
        jInternalFrame1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jInternalFrame1MouseExited(evt);
            }
        });

        btnPos1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos1.setText("X");
        btnPos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos1ActionPerformed(evt);
            }
        });

        btnPos3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos3.setText("X");
        btnPos3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos3ActionPerformed(evt);
            }
        });

        btnPos2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos2.setText("O");
        btnPos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos2ActionPerformed(evt);
            }
        });

        btnPos4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos4.setText("X");
        btnPos4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos4ActionPerformed(evt);
            }
        });

        btnPos5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos5.setText("O");
        btnPos5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos5ActionPerformed(evt);
            }
        });

        btnPos6.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos6.setText("X");
        btnPos6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos6ActionPerformed(evt);
            }
        });

        btnPos7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos7.setText("X");
        btnPos7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos7ActionPerformed(evt);
            }
        });

        btnPos8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos8.setText("O");
        btnPos8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos8ActionPerformed(evt);
            }
        });

        btnPos9.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnPos9.setText("X");
        btnPos9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPos9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPos1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPos2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPos3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPos4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPos5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPos6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPos7, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPos8, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPos9, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnPos3, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(btnPos2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPos1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnPos6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPos5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPos4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnPos9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPos8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPos7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTempo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblTempo.setText("00:20");

        lblV1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblV1.setForeground(new java.awt.Color(0, 0, 204));
        lblV1.setText("X");

        lblV2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblV2.setForeground(new java.awt.Color(0, 153, 0));
        lblV2.setText("O");

        lblJogador1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblJogador1.setForeground(new java.awt.Color(0, 0, 204));
        lblJogador1.setText("Jogador 1");

        lblJogador2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblJogador2.setForeground(new java.awt.Color(0, 153, 0));
        lblJogador2.setText("Jogador 2");

        jButton2.setText("Fechar mensagem");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblV1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblJogador1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblV2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblJogador2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTempo)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblV1)
                    .addComponent(lblJogador1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblV2)
                    .addComponent(lblJogador2))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        label1.setText(nomeAmigo);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(label1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jInternalFrame1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String mensagem = jTextArea1.getText();
        mensagem = CABECALHO_MSG + mensagem;
        System.out.println(mensagem);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(iniciaConversa.getOutputStream());
            oos.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Mensagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextArea2.append(usuarioRecebido + " diz: " + mensagem + "\n");
        jTextArea1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnPos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos1ActionPerformed
        realizaJogada(0, 0);
        enviaMensagemJogo(0, 0);
        verificaGanhador(0, 0);
    }//GEN-LAST:event_btnPos1ActionPerformed

    private void btnPos3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos3ActionPerformed
        realizaJogada(0, 2);
        enviaMensagemJogo(0, 2);
        verificaGanhador(0, 2);
    }//GEN-LAST:event_btnPos3ActionPerformed

    private void btnPos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos2ActionPerformed
        realizaJogada(0, 1);
        enviaMensagemJogo(0, 1);
        verificaGanhador(0, 1);
    }//GEN-LAST:event_btnPos2ActionPerformed

    private void btnPos4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos4ActionPerformed
        realizaJogada(1, 0);
        enviaMensagemJogo(1, 0);
        verificaGanhador(1, 0);
    }//GEN-LAST:event_btnPos4ActionPerformed

    private void btnPos5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos5ActionPerformed
        realizaJogada(1, 1);
        enviaMensagemJogo(1, 1);
        verificaGanhador(1, 1);
    }//GEN-LAST:event_btnPos5ActionPerformed

    private void btnPos6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos6ActionPerformed
        realizaJogada(1, 2);
        enviaMensagemJogo(1, 2);
        verificaGanhador(1, 2);
    }//GEN-LAST:event_btnPos6ActionPerformed

    private void btnPos7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos7ActionPerformed
        realizaJogada(2, 0);
        enviaMensagemJogo(2, 0);
        verificaGanhador(2, 0);
    }//GEN-LAST:event_btnPos7ActionPerformed

    private void btnPos8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos8ActionPerformed
        realizaJogada(2, 1);
        enviaMensagemJogo(2, 1);
        verificaGanhador(2, 1);
    }//GEN-LAST:event_btnPos8ActionPerformed

    private void btnPos9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos9ActionPerformed
        realizaJogada(2, 2);
        enviaMensagemJogo(2, 2);
        verificaGanhador(2, 2);
    }//GEN-LAST:event_btnPos9ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Mensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mensagem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Mensagem(iniciaConversa, usuarioRecebido, numeroJogador).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Mensagem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Mensagem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPos1;
    private javax.swing.JButton btnPos2;
    private javax.swing.JButton btnPos3;
    private javax.swing.JButton btnPos4;
    private javax.swing.JButton btnPos5;
    private javax.swing.JButton btnPos6;
    private javax.swing.JButton btnPos7;
    private javax.swing.JButton btnPos8;
    private javax.swing.JButton btnPos9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel lblJogador1;
    private javax.swing.JLabel lblJogador2;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JLabel lblV1;
    private javax.swing.JLabel lblV2;
    // End of variables declaration//GEN-END:variables
}
