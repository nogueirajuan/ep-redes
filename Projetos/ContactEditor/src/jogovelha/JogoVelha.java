package jogovelha;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class JogoVelha extends javax.swing.JFrame {

    private int[][] tabuleiro = new int[3][3];
    private JButton[][] btnTabuleiro = new JButton[3][3];
    private int NUMERO_X = 1;
    private int NUMERO_O = 2;
    private int NUMERO_VAZIO = 0;
    private int TURNO_1 = 1;
    private int TURNO_2 = 2;
    private int turno = TURNO_1;
    private Cronometro cronometro;

    public JogoVelha() {
        initComponents();
        this.cronometro = new Cronometro(this);
        iniciarMatriz();
        iniciarMatrizBotoes();
        iniciaTabuleiroInterface();
        lblV1.setText("V");
        lblV2.setText("");
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

            cronometro.zeraTimer();

            if (turno == TURNO_1) {
                JOptionPane.showMessageDialog(this, "Vencedor: Jogador 2");
            } else {
                JOptionPane.showMessageDialog(this, "Vencedor: Jogador 1");
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

            this.cronometro.resetTimer();

            if (!this.cronometro.isAlive()) {
                this.cronometro.start();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Posicao ja marcada");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        btnJogarNovamente = new javax.swing.JButton();
        lblJogador1 = new javax.swing.JLabel();
        lblJogador2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        btnJogarNovamente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnJogarNovamente.setText("Novo Jogo");
        btnJogarNovamente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJogarNovamenteActionPerformed(evt);
            }
        });

        lblJogador1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblJogador1.setForeground(new java.awt.Color(0, 0, 204));
        lblJogador1.setText("Jogador 1");

        lblJogador2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblJogador2.setForeground(new java.awt.Color(0, 153, 0));
        lblJogador2.setText("Jogador 2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnJogarNovamente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTempo)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblV1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblJogador1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblV2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblJogador2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTempo)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblV1)
                    .addComponent(lblJogador1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblV2)
                    .addComponent(lblJogador2))
                .addGap(18, 18, 18)
                .addComponent(btnJogarNovamente, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos1ActionPerformed
        realizaJogada(0, 0);
        verificaGanhador(0, 0);
    }//GEN-LAST:event_btnPos1ActionPerformed

    private void btnPos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos2ActionPerformed
        realizaJogada(0, 1);
        verificaGanhador(0, 1);
    }//GEN-LAST:event_btnPos2ActionPerformed

    private void btnPos3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos3ActionPerformed
        realizaJogada(0, 2);
        verificaGanhador(0, 2);
    }//GEN-LAST:event_btnPos3ActionPerformed

    private void btnPos4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos4ActionPerformed
        realizaJogada(1, 0);
        verificaGanhador(1, 0);
    }//GEN-LAST:event_btnPos4ActionPerformed

    private void btnPos5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos5ActionPerformed
        realizaJogada(1, 1);
        verificaGanhador(1, 1);
    }//GEN-LAST:event_btnPos5ActionPerformed

    private void btnPos6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos6ActionPerformed
        realizaJogada(1, 2);
        verificaGanhador(1, 2);
    }//GEN-LAST:event_btnPos6ActionPerformed

    private void btnPos7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos7ActionPerformed
        realizaJogada(2, 0);
        verificaGanhador(2, 0);
    }//GEN-LAST:event_btnPos7ActionPerformed

    private void btnPos8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos8ActionPerformed
        realizaJogada(2, 1);
        verificaGanhador(2, 1);
    }//GEN-LAST:event_btnPos8ActionPerformed

    private void btnPos9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPos9ActionPerformed
        realizaJogada(2, 2);
        verificaGanhador(2, 2);
    }//GEN-LAST:event_btnPos9ActionPerformed

    private void btnJogarNovamenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJogarNovamenteActionPerformed
        iniciaTabuleiroInterface();
        iniciarMatriz();
        cronometro.resetTimer();
        lblV1.setText("V");
        lblV2.setText("");
    }//GEN-LAST:event_btnJogarNovamenteActionPerformed

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
            java.util.logging.Logger.getLogger(JogoVelha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JogoVelha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JogoVelha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JogoVelha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JogoVelha().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnJogarNovamente;
    private javax.swing.JButton btnPos1;
    private javax.swing.JButton btnPos2;
    private javax.swing.JButton btnPos3;
    private javax.swing.JButton btnPos4;
    private javax.swing.JButton btnPos5;
    private javax.swing.JButton btnPos6;
    private javax.swing.JButton btnPos7;
    private javax.swing.JButton btnPos8;
    private javax.swing.JButton btnPos9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblJogador1;
    private javax.swing.JLabel lblJogador2;
    public javax.swing.JLabel lblTempo;
    private javax.swing.JLabel lblV1;
    private javax.swing.JLabel lblV2;
    // End of variables declaration//GEN-END:variables
}
