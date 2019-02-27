package rmi;
import server.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import rmi.*;
public class TopTenLeiloesGanhos extends Pedido {

	public TopTenLeiloesGanhos() {
		super(Pedido);
		
	}

	 @Override
	    public Response execute(RMI rmiServer){
		 boolean verifica = false;
	        while(!verifica){
	            try {
	                //rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                String[] estatisticas2 = rmiServer.TopTenLeiloesGanhos();
	                verifica = true;
	                return new Response("esatisticas",estatisticas2);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response("estatisticas", new String[]{""});
		 
	 }
	

}
