package rmi;
import server.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import rmi.*;
public class EscreverMural extends Pedido {
	
	public String type, texto,user;
	public int id;

	public EscreverMural(String type, int id, String texto,String user) {
		super(Pedido);
		this.type=type;
		this.id=id;
		this.texto=texto;
		this.user=user;

	}
	

	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	                //rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	                
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                String[] mensagem = rmiServer.WriteMensagem(id, texto,user);
	                verifica = true;
	                return new Response(type,mensagem);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response(type,new String[]{""});
	    }


}
