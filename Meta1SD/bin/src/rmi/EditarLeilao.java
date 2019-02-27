package rmi;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class EditarLeilao extends Pedido {

	public String type, titulo, descricao;
	public int id, ano, mes, dia, hora, minuto, amount;
	
	public EditarLeilao(String type, int id, int ano, int mes, int dia, int hora, int minuto, String titulo, String descricao, int amount) {
		
		super(Pedido);
		this.type=type;
		this.id=id;
		this.ano=ano;
		this.mes=mes;
		this.dia=dia;
		this.hora=hora;
		this.minuto=minuto;
		this.titulo=titulo;
		this.descricao=descricao;
		this.amount=amount;
		
		
		
	}

	 @Override
	    public Response execute(RMI rmiServer){
		 	
	        boolean verifica = false;
	        while(!verifica){
	            try {
	            	/*
	                rmiServer  = (RMI) LocateRegistry.getRegistry(TCPserver.RMIadress, TCPserver.rmiport).lookup("rmi");
	                */
	            	rmiServer  = (RMI) LocateRegistry.getRegistry("localhost", 12345).lookup("rmi");
	                String[] editarleilao = rmiServer.EditarLeilao(id,ano,mes,dia,hora,minuto, titulo, descricao, amount);

	                verifica = true;
	                return new Response(type,editarleilao);
	            } catch (RemoteException | NotBoundException e) {
	                verifica = false;}/*
	            } catch (NotBoundException e) {
	                System.out.println(e);
	            }*/
	        }

	        return new Response(type,new String[]{""});
	    }

}
