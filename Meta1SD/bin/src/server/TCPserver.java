package server;
import rmi.*;
import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class TCPserver extends UnicastRemoteObject {
	
	//////////////////////////////////////////////////
	
	  ////////////////////////////////////////////////////
	public static TCPserver a;
	
	 public static ServerSocket socket;
		//int type;
	   public int timeout;
		public int serverport;
		public String secondserverip;
		public int secondserverudpport;
		PrintWriter outToServer;
		public int udpport;
		public static String RMIadress;
		public static int rmiport;
		public DatagramSocket udpsocket;
		public Listener listeners;
                int serverPorts[]= new int [3];
                int UDPPorts [] = new int [3];
	        
	    BufferedReader  inFromServer = null;
		ServerSocket server;
		
		public TCPserver() throws RemoteException{
			try{
	            
	            inFromServer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("configserverfile.txt")));
	            serverport = Integer.parseInt(inFromServer.readLine());
	            udpport =Integer.parseInt(inFromServer.readLine());
	            secondserverip = inFromServer.readLine();
	            secondserverudpport = Integer.parseInt(inFromServer.readLine());
	            String thirdserverip = inFromServer.readLine();
	            int thirserverudpport = Integer.parseInt(inFromServer.readLine());
	            RMIadress = inFromServer.readLine();
	            rmiport = Integer.parseInt(inFromServer.readLine());
	            timeout = Integer.parseInt(inFromServer.readLine());
	            inFromServer.close();

                    serverPorts[0] = 1000;
                    serverPorts[1] = 1001;
                    serverPorts[2] = 1002;
                    UDPPorts[0]=1500;
                    UDPPorts[1]=1501;
                    UDPPorts[2]=1502;
				}
				catch(Exception e){
					System.out.println("erro a ler ficheiro " + e);
					e.printStackTrace();
				}
				/*
				try{
					this.udpsocket = new DatagramSocket(udpport);
					this.udpsocket.setSoTimeout(timeout);

					
				
				}
				catch(SocketException e){
					System.out.println("erro a conectar"+e);
					e.printStackTrace();
				
				}*/
				
				
				listeners = new Listener(serverPorts,UDPPorts);
				listeners.run();
				



		}
		
		
	public void MudarServer(){
		
	}
	

	
		
		
	

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		
		a  = new TCPserver();
		
		
	}

}


