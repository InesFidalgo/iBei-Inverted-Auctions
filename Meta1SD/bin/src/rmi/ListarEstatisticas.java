package rmi;
import server.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import rmi.*;
public class ListarEstatisticas extends Pedido {

	public ListarEstatisticas() {
		super(Pedido);
		
	}

	 @Override
	    public Response execute(RMI rmiServer){
		 boolean verifica = false;
	        while(!verifica){
	            try {
	                rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	                
	               
	                String[] estatisticas3 = rmiServer.LeiloesDezDias();
	                
	                
	                
	                verifica = true;
	                return new Response("esatisticas",estatisticas3);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response("estatisticas", new String[]{""});
		 
	 }
	

}
