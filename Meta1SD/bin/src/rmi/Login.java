package rmi;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import rmi.*;
public class Login extends Pedido {
	
	public String username = "";
    public String password = ""; 
    public String type = "";
    
	public Login(String type,String username, String password) {
		super(Pedido);
		this.username=username;
		this.password = password;
		this.type = type;

	}
	
	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	            	/*
	                rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	                */
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                
	                String[] login = rmiServer.Login(username, password);
	                verifica = true;
	                return new Response(type,login);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response(type,new String[]{""});
	    }

	

}
