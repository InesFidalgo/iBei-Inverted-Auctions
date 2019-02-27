package rmi;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import rmi.*;
public class CancelarLeilao extends Pedido {
	public int id;
	
	public CancelarLeilao(int id) {
		super(Pedido);
		this.id=id;
		
	}
	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	            	/*
	            	 
	                rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");*/
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                String[] cancelarLeilao = rmiServer.CancelaLeilao(id);
	                verifica = true;
	                return new Response("cancelar",cancelarLeilao);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response("cancelar",new String[]{""});
	    }


}
