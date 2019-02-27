/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class EnviaUDP extends Thread {
    int port;
    int []portasUDP;
    ArrayList <Socket> listaClientes;
    public EnviaUDP (int port,int []portasUDP,ArrayList <Socket> listaClientes){
        this.port = port;
        this.portasUDP = portasUDP;
        this.listaClientes = listaClientes;
    }
    public void Envia(){
        start();
        
    }
   
    public void run (){
        
        DatagramSocket aSocket = null;
        byte []m = null;
        try {
        aSocket = new DatagramSocket();
        
        String texto = "";
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        

        
                InetAddress aHost = InetAddress.getByName("127.0.0.1");
                while (!aSocket.isClosed()){
                for (int i=0;i<portasUDP.length;i++){
                    if (portasUDP[i]== this.port){
                        texto = "servidor:"+(i+1)+":"+lotacao();
                        m = texto.getBytes();
                    }
                }for (int i=0;i<portasUDP.length;i++){
                    if (portasUDP[i] != this.port){
                      //  texto = "lotacao servidor"+portasUDP[i]+":"+listaClientes.size();
                        
                        DatagramPacket request = new DatagramPacket(m,m.length,aHost,portasUDP[i]);
                        aSocket.send(request);
                    }
                }
               /* byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
                aSocket.receive(reply);
                System.out.println("Recebeu: " + new String(reply.getData(), 0, reply.getLength()));*/	
                Thread.sleep(6000);
                }
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){System.out.println("IO: " + e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(EnviaUDP.class.getName()).log(Level.SEVERE, null, ex);
        }finally {if(aSocket != null) aSocket.close();}
        } 
    public String lotacao (){
        int i = listaClientes.size();
        String a = i+"";
        return a;
    }
    }
