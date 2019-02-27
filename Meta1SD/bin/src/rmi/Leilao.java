package rmi;
import java.io.Serializable;
import java.util.*;


public class Leilao implements Serializable{
	public int id;
    public String titulo, codigo, descricao, detalhe;
    public int ano, mes;
	public int dia;
	public int hora;
	public int minuto;;
    public Users user;
    public ArrayList <Licitacao> licitacoes = new ArrayList<>(); 
    public ArrayList <Mensagem> mensagens = new ArrayList<>();
    public ArrayList <Leilao>  versoesanteriores = new ArrayList<>();
    public float amount;
    public Date data;
	
    private static final long serialVersionUID = -3791369629242447616L;
   



    public Leilao(int id, String titulo, String codigo, String descricao,int ano, int mes, int dia, int hora,
			int minuto, Users user, float amount) {
    	this.id = id;
        this.titulo = titulo;
        this.codigo = codigo;
        this.descricao = descricao;
        
        this.ano=ano;
        this.mes=mes;
        this.dia=dia;
        this.hora=hora;
        this.minuto=minuto;
        this.user = user;
        this.amount=amount;
        this.data = new Date();
        
        
    }



	public int getID() {
        return id;
    }


    public String getTitulo() {
        return titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDetalhe() {
        return detalhe;
    }

  

    public ArrayList<Licitacao> getLicitacoes()
    {
        return licitacoes;
    }
    
    public ArrayList<Mensagem> getMensagens()
    {
        return mensagens;
    }

    public Users getUser()
    {
        return user;
    }
    
    public String getNome(){
    	return user.nome;
    }

    

    public boolean equalsV(Leilao x)
    {
        if(id == (x.id)){
            return true;
        }
        return false;
    }

}