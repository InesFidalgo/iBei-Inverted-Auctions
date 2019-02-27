package rmi;
import java.io.Serializable;

public class Response  implements Serializable {
    private String resposta;
    public String[] values;
    public String type;
    
   
    public Response(String type,String[] values){
    	
    	this.values = values;
    	this.type = type;

    }
    
    public Response(String resposta){
    	this.resposta = resposta;
    }

    /*
    public String getResposta() {
    	
    	
        return resposta;
    }*/
    
    
}
