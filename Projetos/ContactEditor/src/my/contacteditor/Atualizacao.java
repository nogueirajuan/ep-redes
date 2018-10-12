/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.contacteditor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;

/**
 * @author gjuni
 */
public class Atualizacao extends Thread {
    protected static ArrayList<String> nomesDisponiveis;
    protected static ObjectInputStream ois;
    protected static ArrayList<String> nomesIndisponiveis;
    protected static JList jList1;
    protected static JList jList2;
    protected static String[] vetorDisponiveis;
    protected static String usuarioSelecionado;
    protected static Socket s;
    protected static String[] amigos;

    public Atualizacao(ArrayList<String> nomesDisponiveis, ObjectInputStream ois, ArrayList<String> nomesIndisponiveis, JList jList1, JList jList2, String[] vetorDisponiveis, String usuarioSelecionado, Socket clienteSocket, String[] amigos) throws IOException {
        Atualizacao.nomesDisponiveis = nomesDisponiveis;
        Atualizacao.nomesIndisponiveis = nomesIndisponiveis;
        Atualizacao.jList1 = jList1;
        Atualizacao.jList2 = jList2;
        Atualizacao.vetorDisponiveis = vetorDisponiveis;
        Atualizacao.usuarioSelecionado = usuarioSelecionado;
        Atualizacao.s = clienteSocket;
        Atualizacao.amigos = amigos;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Atualizacao.ois = new ObjectInputStream(s.getInputStream());
                nomesDisponiveis = new ArrayList();
                nomesDisponiveis = (ArrayList) ois.readObject();
                Iterator<String> itDisp = nomesDisponiveis.iterator();
                while (itDisp.hasNext()) {
                    String paraRemoverOriginal = (String) itDisp.next();
                    String[] vetorPara = paraRemoverOriginal.split(" ");
                    String paraRemover = vetorPara[0] + " " + vetorPara[1];
                    if (paraRemover.equalsIgnoreCase(usuarioSelecionado)) {
                        nomesDisponiveis.remove(paraRemoverOriginal);
                        break;
                    }
                }
                nomesIndisponiveis = new ArrayList();
                nomesIndisponiveis = (ArrayList) ois.readObject();

                Iterator<String> itera = nomesDisponiveis.iterator();
                while (itera.hasNext()) {
                    String x = itera.next();
                    String[] nomeSplit = x.split(" ");
                    x = nomeSplit[0] + " " + nomeSplit[1];
                    boolean b = false;
                    for (String y : amigos) {
                        if (y.equalsIgnoreCase(x)) {
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        nomesDisponiveis.remove(x);
                        itera = nomesDisponiveis.iterator();
                    }
                }
                itera = nomesIndisponiveis.iterator();
                while (itera.hasNext()) {
                    String x = itera.next();
                    boolean b = false;
                    for (String y : amigos) {
                        if (y.equalsIgnoreCase(x)) {
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        nomesIndisponiveis.remove(x);
                        itera = nomesIndisponiveis.iterator();
                    }
                }


                jList1.setModel(new javax.swing.AbstractListModel() {
                    String[] strings = nomesDisponiveis.toArray(vetorDisponiveis);

                    public int getSize() {
                        return strings.length;
                    }

                    public Object getElementAt(int i) {
                        return strings[i];
                    }
                });

                jList2.setModel(new javax.swing.AbstractListModel() {
                    String[] strings = nomesIndisponiveis.toArray(vetorDisponiveis);

                    public int getSize() {
                        return strings.length;
                    }

                    public Object getElementAt(int i) {
                        return strings[i];
                    }
                });
            } catch (SocketException e) {
                break;
            } catch (Exception ex) {
                Logger.getLogger(Atualizacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
