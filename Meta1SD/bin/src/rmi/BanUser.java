package rmi;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import server.*;
public class BanUser extends Pedido {
	public String username;
	public BanUser(String username) {
		super(Pedido);
		this.username=username;
	}

	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	                rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	                
	                
	                String[] banir = rmiServer.BanUser(username);
	                verifica = true;
	                return new Response("Banir",banir);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response("Banir",new String[]{""});
	    }

	

}
