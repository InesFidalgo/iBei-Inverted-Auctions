package rmi;
import rmi.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class CriarLeilao extends Pedido {
	
	public String username;
	public String descricao, titulo, codigo;
	public int dia, mes, ano, hora, minuto, amount;
	public String type;
	public int idpedido;
	public String detalhe;
	


	public CriarLeilao(String type, String username2,int id,String codigo,String titulo,String descricao, int ano, int mes, int dia, int hora ,int minuto, int amount){
		super(Pedido);
		this.username = username2;
		this.type = type;
		this.idpedido = id;
		this.codigo = codigo;
		this.titulo = titulo;
		this.descricao = descricao;
		this.ano = ano;
		this.mes = mes;
		this.dia = dia;
		this.hora = hora;
		this.minuto = minuto;
		this.detalhe = detalhe;
		this.amount=amount;
		
	}
	
	
	
	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	                //rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	            	
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                
	                String[] criarleilao = rmiServer.CreateLeilao("create_auction",username,idpedido,codigo,titulo,descricao, ano, mes, dia, hora ,minuto, amount);
	                
	                System.out.println("olaines"+criarleilao);
	                verifica = true;
	                return new Response(type,criarleilao);
	            } catch (RemoteException e) {
	                verifica = false;
	            }  catch (NotBoundException e) {
	                System.out.println(e);
	            }
	        }

	        return new Response(type,new String[]{""});
	
	
	

	 }
}
