package rmi;
import rmi.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RegistarAdmin extends Pedido{
	
	public String username = "";
    public String password = "";
    public String type = "";
	
	public RegistarAdmin(String type, String username, String password) {
		super(Pedido);
		this.username=username;
		this.password=password;
		this.type = type;
		
	}
	
	
	 @Override
	    public Response execute(RMI rmiServer){
		 System.out.println("fez o executa");
		 boolean verifica = false;
	        while(!verifica){
	            try {
	            	System.out.println("fez ligacao no executa");
	            	
	            	/*System.out.println("endereco "+TCPserver.RMIadress);
	                rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	                System.out.println("tem a lista de strings");*/
	            	
	            	System.out.println("vai fazer a liga��o");
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	            	System.out.println("passou");
                        System.out.println(username+"\n"+password);
	                String[] registar = rmiServer.RegisterAdmin(username, password);
	                       
	                verifica = true;
	                return new Response(type,registar);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response(type, new String[]{""});
		 
	 }

}
