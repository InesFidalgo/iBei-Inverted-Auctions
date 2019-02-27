
package rmi;

import java.io.Serializable;

public class Licitacao implements Serializable{

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String user;
	int id;
	float preco;

    public  Licitacao(String user, int id, float preco) {
        this.user = user;
        this.id = id;
        this.preco = preco;
    }

    public  String getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public float getPreco() {
        return preco;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

}