package rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Listarversoesantigas extends Pedido{

	public String type="";
	public int id;
	public Listarversoesantigas(String type, int id) {
		super(Pedido);
		this.type =type;
		this.id=id;
		
		
	}
	

	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	                //rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	            	
	                String[] procurarleilao = rmiServer.ListarAntigas(id);
	                verifica = true;
	                return new Response(type,procurarleilao);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response(type,new String[]{""});
	    }

	
	

}
