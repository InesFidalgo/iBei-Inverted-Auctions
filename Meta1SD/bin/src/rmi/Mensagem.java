package rmi;

import java.io.Serializable;

public class Mensagem implements Serializable{
    String nome, mensagem;
    int id;

    public Mensagem(String nome, int id, String mensagem) {
        this.nome = nome;
        this.id = id;
        this.mensagem = mensagem;
    }
   
    public String getNome() {
        return nome;
    }
    
    public int getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
	

}