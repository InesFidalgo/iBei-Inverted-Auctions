package rmi;
import rmi.*;
import java.io.Serializable;

/**
 *
 * @author Asus
 */
public class Pedido implements Serializable{
    public static String Pedido;
    public Pedido(String Pedido) {
        this.Pedido = Pedido;
    }

    public String getPedido() {
        return Pedido;
    }
    public Response execute(RMI rmiserver){
    	System.out.println("executa");
		return new Response("Resposta");
    	
    }

}
