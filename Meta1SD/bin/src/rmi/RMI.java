//package ProjectoSD;
package rmi;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.*;
import java.util.*;
import server.*;


public interface RMI extends Remote, Serializable{
	
	
    ArrayList <Leilao> leiloes = new ArrayList<>();
    ArrayList <Users> users = new ArrayList<>();
    ArrayList <Users> admin = new ArrayList<>();
    ArrayList<Leilao> acabados = new ArrayList<>();
    ArrayList<Users> online = new ArrayList<>();
    
    public void leFichUsers()throws IOException, ClassNotFoundException;
    public void leFichLeilao()throws IOException, ClassNotFoundException;
    

    
    public void actualizaFicheiros() throws IOException;
	public String[] CreateLeilao(String type, String username2,int id,String codigo,String titulo,String descricao, int ano, int mes, int dia, int hora ,int minuto, int amount) throws RemoteException; 
 
	public String[] Login(String username, String password ) throws RemoteException; 
	
	public String[] Registar(String username, String password) throws RemoteException; 

	public String[] SearchLeilao(String codigo) throws RemoteException;

	public String[] SearchLeilaoDetalhe(String type,int id) throws RemoteException;

	public String[] SearchLeilaoPorUser(String user) throws RemoteException;/*corre todos os leiloes, marca os que têm user=argumento, depois imprime tudo da escolha do utilizador*/
	
	public String[] EditarLeilao(int id,int ano, int mes, int dia, int hora ,int minuto, String title, String descricao, int amount) throws RemoteException; 
	 

	public String[] MakeLicitacao(int id, String user, float amount) throws RemoteException;/*ver mais tarde se é leilao.Make ou user.Make*/

	public void EditProp(String id) throws RemoteException;
	
	public String[] ListarUsersOnline() throws RemoteException;
	
	public String[] WriteMensagem(int id, String mensagem, String username ) throws RemoteException;


	

	public void BestBid() throws RemoteException;/*imprime nome de utilizador on*/
	

	//public int DataActual() throws RemoteException;

	public void VerificaTermino() throws RemoteException;

	public void TerminaLeilao() throws RemoteException;/* (verifica relogio interno pc) print leilão X acabou*/

	public String[] CancelaLeilao(int id) throws RemoteException;/*leilao.status = false;*/

	public String[] BanUser(String username) throws RemoteException;

	public void TCPTeste() throws RemoteException;
	public String[] RegisterAdmin(String username, String password) throws RemoteException;
	public void leFichAdmins() throws IOException, ClassNotFoundException;
	public String[] TopTenLeiloesCriados() throws RemoteException;
	public String[] TopTenLeiloesGanhos() throws RemoteException;
	public String[] LeiloesDezDias() throws RemoteException;
	public ArrayList getAdmins() throws RemoteException;
	public ArrayList getUsers() throws RemoteException;
	public void setUsers(ArrayList users) throws RemoteException;
	public String[] ListarAntigas(int id)throws RemoteException;
	
	public int comparaTo(int ano, int mes, int dia, int hora, int minuto) throws RemoteException;
	public String[] ListarLicitacoesUser(String user) throws RemoteException;
	public void sair(String username) throws RemoteException;
	public String[] printMensagem(String username, Mensagem mensagem, String numLeilao) throws RemoteException;
	
	
	
	
}
