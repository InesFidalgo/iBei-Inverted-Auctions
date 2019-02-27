/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class RecebeUDP extends Thread {
    DatagramSocket aSocket;
    int []lotacao = new int [3];
    int port;
    ArrayList <Socket>listaClientes;
    int []portsUDP;
    public int[] getLotacao() {
        return lotacao;
    }
    
    public RecebeUDP(DatagramSocket port, ArrayList <Socket>listaClientes, int[]portsUDP){
        this.aSocket = port;
        this.port = aSocket.getLocalPort();
        this.listaClientes = listaClientes;
        this.portsUDP = portsUDP;
        start();
    }
    public void run(){
        //DatagramSocket aSocket = null;
        String s;
        try{
            //aSocket = new DatagramSocket(this.port);
            System.out.println("Socket Datagram à escuta no porto"+aSocket.getLocalPort());
            while(true){
                byte[] buffer = new byte[1000]; 			
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                s=new String(request.getData(), 0, request.getLength());	
                System.out.println(s);
                String []dividemensagem = s.split(":");
                int server = Integer.parseInt(dividemensagem[1]);
                int numeroC =Integer.parseInt(dividemensagem[2]);
                this.lotacao[server-1]= numeroC;
                for (int i=0;i<portsUDP.length;i++){
                    if (portsUDP[i]==port){
                        this.lotacao[i]=listaClientes.size();
                    }
                }

                /*DatagramPacket reply = new DatagramPacket(request.getData(), 
                                request.getLength(), request.getAddress(), request.getPort());
                aSocket.send(reply);*/
            }
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
        }
}


