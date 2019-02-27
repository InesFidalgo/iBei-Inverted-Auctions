package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import rmi.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

    public class Connection extends Thread{
    	public String Resposta;
    	public String Pedidos;
    	public Socket socket;
        public RMI rmi;
        BufferedReader in;
        PrintWriter out;
        public Response resposta;
        public Pedido pedido;
        public String username2;
        public ArrayList<Socket>clientesligados;
       
       
        public synchronized Pedido getPedido() {
            return pedido;
        }
        public synchronized void responde(Response resposta) {
            this.resposta=resposta;
        }
        
        
        public Connection(Socket socket,ArrayList<Socket>clientesligados) throws IOException{
            System.out.println("fez connect!");
            this.socket = socket;
            this.clientesligados = clientesligados;
            
            start();
        }
    
        public void run(){
        		int vezes=0;
                try {
               
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                    
                } catch (IOException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
            try {
                do{
                	
                    
                    Pedidos = in.readLine();
                    System.out.println("Pedido: "+Pedidos);
                    String copia = Pedidos;
                    
                    if(vezes==0){
 
                    	String comando = copia.split(", ")[1];
                    	//System.out.println(comando);
                    	String username = comando.split(": ")[1];
                    	
    					username2 = username;
                    	Menu ola2 = new Menu (Pedidos,socket);
                    	//out.println(ola2.getResposta());
                    	
                    	
                    	
                    }
                    else{

                    	
                    	
                    	//System.out.println("Nome inserido:"+username2);
                    	rmi = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
                        ArrayList<Users> admins = rmi.getAdmins();
                        MenuAdmin ola = null;
                        for(int i=0;i<admins.size();i++){
    						if(admins.get(i).getNome().equals(username2)){
    							ola = new MenuAdmin(Pedidos,socket, username2);
    						}
    					}
                        if(ola==null){
                        	Menu2 ola2 = new Menu2 (Pedidos,socket, username2);
                        	//out.println(ola2.getResposta());
                    	}
                        
                    	
                    	
                    	
                    }
                    vezes+=1;

                    //System.out.println("vezes:"+vezes);
                	
                	
                	
                   
            }while (!Pedidos.equals("exit"));
            }catch(IOException | NotBoundException e){
            	System.out.println("ENTROU NO CATCH AO SAIR");
            	try {
                    rmi = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
                    rmi.sair(username2);
                                
                } catch (AccessException e1) {
                        //e1.printStackTrace();
                } catch (RemoteException e1) {
                        //e1.printStackTrace();
                } catch (NotBoundException e1) {
                        //e1.printStackTrace();
                }

                for (int i =0; i<clientesligados.size();i++){
                    if (clientesligados.get(i).equals(socket)){
                        clientesligados.remove(i);
                        System.out.println("Foi removido com sucesso");
                    }
                }
                            
            }
      }
    }
    
                

       
        
    
