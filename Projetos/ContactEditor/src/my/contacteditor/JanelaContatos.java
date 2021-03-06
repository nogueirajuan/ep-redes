/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.contacteditor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gjuni
 */
public class JanelaContatos extends javax.swing.JFrame{
    public static String [] vetorDisponiveis=new String[0];
    public static String [] vetorIndisponiveis;
    public static String [] amigos;
    public static String usuarioSelecionado;
    public static Socket clienteSocket=null;
    public static DataOutputStream envio=null;
    public static ArrayList <Usuario> disponiveis = new ArrayList();
    public static ArrayList <Usuario> indisponiveis = new ArrayList();
    public static ArrayList <String> nomesDisponiveis;
    public static ArrayList <String> nomesIndisponiveis;
    public static ObjectInputStream ois=null;
    public static ObjectOutputStream oos=null;
    public static String recebeUsuario;
    public static Thread novaThread=null;
    public static int portaServidor;
    public static String ipServidor;
    public static Thread iniciarConversa;
    /**
     * Creates new form JanelaContatos
     * @param recebeUsuario
     */
    public JanelaContatos(String recebeUsuario, Socket clienteSocketRecebido, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        JanelaContatos.usuarioSelecionado=recebeUsuario;
        JanelaContatos.clienteSocket=clienteSocketRecebido;
        JanelaContatos.ois=ois;
        JanelaContatos.oos=oos;
        try{
            amigos=(String []) ois.readObject();
            nomesDisponiveis = new ArrayList();
            nomesDisponiveis = (ArrayList)ois.readObject();
            nomesDisponiveis.remove(usuarioSelecionado);
            nomesIndisponiveis = new ArrayList();
            nomesIndisponiveis= (ArrayList)ois.readObject();
            portaServidor=Integer.parseInt((String)ois.readObject());
            ipServidor=InetAddress.getLocalHost().getHostAddress();;
//            Thread keepAlive = new KeepAlive(clienteSocket);
//            keepAlive.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //atualizaListas();
        initComponents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        novaThread = new Atualizacao(nomesDisponiveis, ois, nomesIndisponiveis, jList1, jList2, vetorDisponiveis, usuarioSelecionado, clienteSocket, amigos);
        novaThread.start();
        iniciarConversa = new OuvirConversa(portaServidor, usuarioSelecionado);
        iniciarConversa.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setVisible(true);

        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText(usuarioSelecionado);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String [] strings = nomesDisponiveis.toArray(vetorDisponiveis);
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setPreferredSize(new java.awt.Dimension(50, 50));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String [] strings = nomesIndisponiveis.toArray(vetorDisponiveis);
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setPreferredSize(new java.awt.Dimension(50, 50));
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel2.setText("Disponíveis:");

        jLabel3.setText("Indisponíveis");

        jLabel4.setText(ipServidor);

        jLabel5.setText(Integer.toString(portaServidor));

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5))
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        try {
            oos.writeObject(usuarioSelecionado);
        } catch (IOException ex) {
            Logger.getLogger(JanelaContatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            novaThread.interrupt();
            iniciarConversa.interrupt();
            ois.close();
            oos.close();
            clienteSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(JanelaContatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Desconectado().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jList2MouseClicked

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2){
            String nomeAmigoComIp = (String)jList1.getSelectedValue();
            String [] vetorNomeAmigo = nomeAmigoComIp.split(" ");
            String ipAmigo = vetorNomeAmigo[2];
            int portaAmigo = Integer.parseInt(vetorNomeAmigo[3]);
            Socket iniciaConversa=null;
            try {
                iniciaConversa = new Socket (ipAmigo, portaAmigo);
                String nomeCompleto = vetorNomeAmigo[0] + " " + vetorNomeAmigo[1];
                new Mensagem(iniciaConversa, usuarioSelecionado, 1).setVisible(true);
                ObjectOutputStream oos;
                oos = new ObjectOutputStream(iniciaConversa.getOutputStream());
                oos.writeObject(usuarioSelecionado);
            } catch (IOException ex) {
                Logger.getLogger(JanelaContatos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JanelaContatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

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
            java.util.logging.Logger.getLogger(JanelaContatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaContatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaContatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaContatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        
      
        
         
        
    }
//    public static void atualizaListas (){
//        Iterator it = Servidor.disponiveis.iterator();
//        int contador=0;
//        while(it.hasNext()){
//            String nome = (String) it.next();
//            vetorDisponiveis[contador]=nome;
//            contador++;
//        }
//        it = Servidor.indisponiveis.iterator();
//        contador=0;
//        while(it.hasNext()){
//            String nome = (String) it.next();
//            vetorIndisponiveis[contador]=nome;
//            contador++;
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
