package rmi;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import rmi.*;
public class ListarUsersOnline extends Pedido {

	public String type;
	public ListarUsersOnline(String type) {
		super(Pedido);
		this.type=type;
	}

	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	                //rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	                rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                
	                String[] usersonline = rmiServer.ListarUsersOnline();
	                verifica = true;
	                return new Response(type,usersonline);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response(type,new String[]{""});
	    }


}
