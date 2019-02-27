package rmi;
import java.io.Serializable;

public class ResponseP implements Serializable {
	private int id;
	public Pedido pedido;
    public Response resposta;

    public ResponseP(){
    }

}
