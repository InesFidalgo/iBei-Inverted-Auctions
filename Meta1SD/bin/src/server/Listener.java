package server;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class Listener extends Thread{
    public ArrayList<Socket> clientesligados = new ArrayList();

    int[] portasTCP;
    int [] portasUDP;
    private int Sport;
    int udpport;
    ServerSocket lsocket = null;
    DatagramSocket aSocket = null;

    public int getSport() {
        return Sport;
        
    }

    public Listener(int[] serverPorts, int []UDPPorts){

        this.portasTCP= serverPorts;
        this.portasUDP = UDPPorts;
       
    }

	public void run(){
            try {
                lsocket = new ServerSocket(portasTCP[0]);
                aSocket = new DatagramSocket(portasUDP[0]);
                udpport = portasUDP[0];
            } catch (IOException ex) {
                try {
                    lsocket = new ServerSocket(portasTCP[1]);
                    aSocket = new DatagramSocket(portasUDP[1]);
                    udpport = portasUDP[1];

                } catch (IOException ex1) {
                    try {
                        lsocket = new ServerSocket(portasTCP[2]);
                        aSocket = new DatagramSocket(portasUDP[2]);
                        udpport = portasUDP[2];
                    } catch (IOException ex2) {
                        System.out.println("Nao e possivel criar");
                    }
                }
            }
            RecebeUDP recebe= new RecebeUDP(aSocket, clientesligados,portasUDP);
            EnviaUDP message = new EnviaUDP(udpport, portasUDP, clientesligados);
            message.Envia();
            new Thread(){
                public void run (){
                    
                    while(true){
                        try {
                            //System.out.println("ola");
                            Socket ssocket = lsocket.accept();
                            System.out.println("ola");
                            synchronized (clientesligados) {
                                clientesligados.add(ssocket);
                            }
                            new Connection(ssocket, clientesligados);
                            System.out.println("esta a escutar:"+lsocket);
                        } catch (IOException ex) {
                            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
             }.start();
            new EnviaCliente (recebe.getLotacao(),clientesligados).Envialotacao();
        }
} 
        

            
    
