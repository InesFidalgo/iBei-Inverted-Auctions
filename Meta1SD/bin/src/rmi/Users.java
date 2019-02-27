package rmi;
import java.io.Serializable;
import java.util.ArrayList;

public class Users implements Serializable{

	ArrayList<Leilao> leiloesganhos = new ArrayList<>();
	
	ArrayList<Mensagem> pendentes = new ArrayList<>();
	
	String nome, password;
	
	
	
	public boolean admin;

	boolean log;
	private static final long serialVersionUID = 1563048908290644157L;

	/*funcs de admin implementadas aqui*/

    public Users(String nome, String password, boolean admin, boolean log) {
        this.nome = nome;

        this.password = password;
        
        this.admin = admin;

        this.log = log;
    }

    public String getNome() {
        return nome;
    }
    
    public ArrayList getLeiloesGanhos () {
        return leiloesganhos;
    }

    public ArrayList getPendentes () {
        return pendentes;
    }
    
    public void setPendentes(Mensagem x) {
        this.pendentes.add(x);
    }

    public String getPassword() {
        return password;
    }


    public boolean isAdmin() {
        return admin;
    }

    public boolean isLog() {
        return log;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    

    public void setLog(boolean log) {
        this.log = log;
    }


    public boolean equalsV(Users x){
        if(nome == (x.nome)){
            return true;
        }
        return false;
    }
	


}
