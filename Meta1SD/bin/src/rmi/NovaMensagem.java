package rmi;
import rmi.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class NovaMensagem extends Pedido{
	
	public String username = "";
	public Mensagem mensagem = null;
	public String numLeilao = "";
	
	public NovaMensagem(String username, Mensagem mensagem, String numLeilao) {
		super(Pedido);
		this.username=username;
		this.mensagem = mensagem;
		this.numLeilao = numLeilao;
		
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
	            	
	            	System.out.println("vai fazer a ligacao");
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	            	System.out.println("passou");
	                String[] impressao = rmiServer.printMensagem(username, mensagem, numLeilao);
	                       
	                verifica = true;
	                return new Response("message",impressao);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response("message", new String[]{""});
		 
	 }

}
