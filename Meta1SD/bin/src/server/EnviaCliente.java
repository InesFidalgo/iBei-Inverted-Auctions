/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class EnviaCliente extends Thread{
    PrintWriter out;
    int []lotacao;
    ArrayList <Socket> listaClientes;

    public EnviaCliente(int []lotacao, ArrayList<Socket>listaClientes) {
        this.lotacao = lotacao;
        this.listaClientes=listaClientes;
    }
    public void Envialotacao(){
        start();
    }
    public void run (){
        String mensagem;
        try {
            
            while (true){
                for (Socket socket:listaClientes){
                    out = new PrintWriter(socket.getOutputStream(), true);
                    for (int i=0;i<3;i++){
                        mensagem = "Servidor"+(i+1)+":"+lotacao[i];
                        out.println(mensagem);
                }
                }
                Thread.sleep(60000);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(EnviaCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EnviaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

