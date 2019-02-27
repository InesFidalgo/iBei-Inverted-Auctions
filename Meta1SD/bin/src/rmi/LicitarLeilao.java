package rmi;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import rmi.*;
public class LicitarLeilao extends Pedido {

	public String type, username;
	public int id;
	public float money;
	public LicitarLeilao(String type, int id, float money, String username ) {
		super(Pedido);
		this.id=id;
		this.money=money;
		this.type=type;
		this.username=username;
	}
	
	

	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	            	
	                //rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                System.out.println("fez ligação no licitar");
	                String[] licitar = rmiServer.MakeLicitacao(id,username, money);
	                verifica = true;
	                return new Response(type,licitar);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response(type,new String[]{""});
	    }
	

}
