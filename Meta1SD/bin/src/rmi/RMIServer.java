package rmi;
import server.*;
import client.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class RMIServer extends UnicastRemoteObject implements RMI  {
	
	public static RMIServer b  ;
	public static RMIServer c ;

    
	BufferedReader  inFromServer = null;
	ServerSocket server;
	static int rmiport;
	static int rmiport2;

	public RMIServer() throws RemoteException, NumberFormatException, IOException{
	       
        super();
        try{
            inFromServer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("configrmifile.txt")));
            rmiport = Integer.parseInt(inFromServer.readLine());
            rmiport2 = Integer.parseInt(inFromServer.readLine());
        }catch(Exception e){
            System.out.println("");
        }
	new Thread(){
	        public void run(){
	            while (true){
	                VerificaTermino();
	            }
	        }
	    }.start(); 

}

	 public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException{
	        /////////////////////////////////ESTA MAL
	       
	       
	             
	            try{
	               
	                Registry r = LocateRegistry.createRegistry(12345);
	                               
	                b = new RMIServer();
	                r.rebind("rmi", b);
	               
	                System.out.println("RMIServer esta pronto");
	            }
	            catch(Exception e){
	                            int check=0;
	                            while(check ==0){
	                                try{
	                                    Registry r = LocateRegistry.createRegistry(12345);
	                                    b  = new RMIServer();
	                                    r.rebind("rmi", b);
	                                    System.out.println("RMIServer esta pronto");
	                                    check =1;
	                //System.out.println("RMIServer2 esta pronto");
	                            }catch (Exception e1){
	                               
	                            }
	                        }
	                }
	 
	       b.leFichUsers();
	       b.leFichLeilao();
	       b.leFichAdmins();
	        Scanner c = new Scanner(System.in);
	            String ola = c.nextLine();
	            if(ola.equals("exit")){
	               try {
	                    b.actualizaFicheiros();
	                    System.exit(1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	            }
	        /////////////////////////////////////////////////DEVE ESTAR MAL - APGAR SEGUNDO RMI
	    }
	@Override
	public ArrayList getAdmins(){
		return b.admin;
	}
	@Override
	public ArrayList getUsers(){
		return b.online;
	}
	@Override
	public void setUsers(ArrayList users){
		 for(int i=0;i<b.users.size();i++){
			 Users a = b.users.get(i);
			 a=(Users) users.get(i);
		 }
	}
	
	@Override
	public String[] RegisterAdmin(String username, String password){
		System.out.println("ENTROU NO REGISTAR");
        System.out.println(username+"\n"+password);
		Users utilizador = new Users(username, password, false, true);
		
		String[] fim =  {"true", "true"}; 
		b.users.add(utilizador);
		b.admin.add(utilizador);
		b.online.add(utilizador);
		
		
		try {
			System.out.println("vai actualizar");
		
		
			actualizaFicheiros();
		} catch (IOException e) {
			System.out.println("deu erro");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fim;
		
	}

	@Override
	public void leFichLeilao() throws IOException, ClassNotFoundException {
		FicheirosDeObjectos f5= new FicheirosDeObjectos();
        Leilao leilao;
        ArrayList<Leilao> encontradas = new ArrayList();
        
        if(f5.abreLeitura("Leiloes.dat")==true){
        	
        	System.out.println("tua mae");
            try{
            	
            		
		            while(true) {
		            leilao = (Leilao) f5.leObjecto();
		            
		            if(leilao==null){
		                System.out.println("O ficheiro leiloes.dat esta vazio");
		                f5.fechaLeitura();
		                break;
		            }
		            boolean criadas=false;
		            
		            for(int i = 0 ;i<encontradas.size();i++){
		                if(leilao.equalsV(encontradas.get(i))){
		                    criadas = true;
		                    
		                }
		                
		            }
		            if(criadas==false){
		            	b.leiloes.add(leilao);
		            }
		
	            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Leilao.dat nao existe");
            f5.fechaLeitura();
        }
        
        
		
		
	}



	@Override
	public void leFichUsers() throws IOException, ClassNotFoundException {
		FicheirosDeObjectos f5= new FicheirosDeObjectos();
        Users user;
        ArrayList<Users> encontradas = new ArrayList();
        
        if(f5.abreLeitura("Users.dat")==true){
        	
            try{

            	while(true) {
            		user = (Users) f5.leObjecto();
            		System.out.println(user.nome);
            		
		            if(user==null){
		                System.out.println("O ficheiro users.dat esta vazio");
		                f5.fechaLeitura();
		                break;
		            }
		            boolean criadas=false;
		            
		            for(int i = 0 ;i<encontradas.size();i++){
		                if(user.equalsV(encontradas.get(i))){
		                    criadas = true;
		                    
		                }
		                
		            }
		            if(criadas==false){
		            	b.users.add(user);
		            }

            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Users.dat nao existe");
            f5.fechaLeitura();
        }
        
		
	}
	
	@Override
	public void leFichAdmins() throws IOException, ClassNotFoundException {
		FicheirosDeObjectos f5= new FicheirosDeObjectos();
        Users user;
        ArrayList<Users> encontradas = new ArrayList();
        
        if(f5.abreLeitura("Admins.dat")==true){
            try{
            	
            while(true) {
            user = (Users) f5.leObjecto();
            
            if(user==null){
                System.out.println("O ficheiro admins.dat esta vazio");
                f5.fechaLeitura();
                break;
            }
            boolean criadas=false;
            
            for(int i = 0 ;i<encontradas.size();i++){
                if(user.equalsV(encontradas.get(i))){
                    criadas = true;
                    
                }
                
            }
            if(criadas==false){
            	b.admin.add(user);
            }

            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Admins.dat nao existe");
            f5.fechaLeitura();
        }
        
		
	}
	
	
	
	public void actualizaFicheiros() throws IOException {
        FicheirosDeObjectos f1 = new FicheirosDeObjectos();
        FicheirosDeObjectos f2 = new FicheirosDeObjectos();
        FicheirosDeObjectos f3 = new FicheirosDeObjectos();
       
        System.out.println("size dentro func:"+b.users.size());
        if(b.users.isEmpty()==false){
            
            f1.abreEscrita("Users.dat");
            for(int i=0; i<b.users.size();i++){
            	
            	System.out.println("estï¿½ a escrver");
                f1.escreveObjecto(b.users.get(i));
            }
            f1.fechaEscrita();
            
       }
        if(b.admin.isEmpty()==false){
            
            f1.abreEscrita("Admins.dat");
            for(int i=0; i<b.admin.size();i++){
            	
            	System.out.println("estï¿½ a escrver");
                f1.escreveObjecto(b.admin.get(i));
            }
            f1.fechaEscrita();
            
       }
         if(b.leiloes.isEmpty()==false){
            f2.abreEscrita("Leiloes.dat");
            for(int i=0; i<b.leiloes.size();i++){
                f2.escreveObjecto(b.leiloes.get(i));
            }
            f2.fechaEscrita();  
        }
        
       
        
    }
        

	public String[] ListarAntigas(int id){
		String fim2 = "";
		for(int i=0;i<b.leiloes.size();i++){
			for(int j=0;j<b.leiloes.get(i).versoesanteriores.size();j++){
				if(b.leiloes.get(i).id==id){
					
					System.out.println(b.leiloes.get(i).versoesanteriores.get(j).titulo);
					 fim2 = ""+b.leiloes.get(i).versoesanteriores.get(j).getCodigo()+"/"+b.leiloes.get(i).versoesanteriores.get(j).titulo+"/"+b.leiloes.get(i).versoesanteriores.get(j).descricao+"/"+b.leiloes.get(i).versoesanteriores.get(j).ano+"-"+ b.leiloes.get(i).versoesanteriores.get(j).mes+"-"+b.leiloes.get(i).versoesanteriores.get(j).dia+" "+b.leiloes.get(i).versoesanteriores.get(j).hora+"h-"+b.leiloes.get(i).versoesanteriores.get(j).minuto+"m";
				}
			}
			
		}
		String[]fim = fim2.split("/");
		return fim;
	}



	@Override
	public String[] EditarLeilao(int id, int ano, int mes, int dia, int hora, int minuto, String title, String descricao, int amount ) throws RemoteException
	{
		String fim2="false";
		for (Leilao obj: b.leiloes)
		{
			if (obj.getID()==(id))
			{
				Leilao copia = new Leilao(obj.id, obj.titulo,obj.codigo, obj.descricao, obj.ano, obj.mes, obj.dia, obj.hora, obj.minuto, obj.getUser(), obj.amount);
				System.out.println("encontrou o leilao a editar");
				obj.versoesanteriores.add(copia); 
				obj.ano=ano;
				obj.mes=mes;
				obj.dia=dia;
				obj.hora=hora;
				obj.minuto=minuto;
				
				obj.amount=amount;
				obj.descricao=descricao;
				obj.titulo=title;
				
				fim2 = "true";
			}
			
		}
		
		
		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[]fim = {fim2};
		System.out.println(fim2);
		return fim;
	}



	


	@Override
	public String[] SearchLeilao(String codigo)
	{
		System.out.println("entrou");
		String qualquercoisa = "";
		int count = 0;
		for (Leilao obj: b.leiloes)
		{
			System.out.println(obj.getCodigo()+ "|" + codigo);
			if (obj.getCodigo().equals(codigo))
				
			{
				System.out.println("encontrou o leilao");
				count++;
				qualquercoisa = qualquercoisa + "/" + obj.getID() + "/" + obj.getCodigo() + "/" + obj.getTitulo();
			}
			
		}
		qualquercoisa = count + qualquercoisa;
		String[] fim = qualquercoisa.split("/");
		
		
		return fim;

		
		
	}
	
	

	



	@Override
	public String[] SearchLeilaoPorUser(String user) throws RemoteException
	{
		int count = 0;
		String qualquercoisa = "";

		for(int i=0;i<leiloes.size();i++)
		{
			System.out.println("Nome do user no procura por user"+user);
			if (leiloes.get(i).getNome().equals(user))
			{
				System.out.println("encontrou user com nome igual");
				count++;
				qualquercoisa += "/" + leiloes.get(i).getID() + "/" +leiloes.get(i).getCodigo() + "/" + leiloes.get(i).getTitulo();

				
			}
			
			
			
			
			
		}

		qualquercoisa = count + qualquercoisa;
		System.out.println(qualquercoisa);
		String[] fim = qualquercoisa.split("/");
		return fim;
	}

	

	@Override
	public String[] SearchLeilaoDetalhe(String type, int id)
	{
		String qualquercoisa = "";
		System.out.println("procurou detalhe");

		for (Leilao obj: b.leiloes)
		{
			System.out.println(obj.getID() + "|" + id);
			if (obj.getID() == id)
			{
				System.out.println("encontrou um igual");
				qualquercoisa = qualquercoisa + "/" + obj.getTitulo() + "/" + obj.getDescricao() + "/" + obj.ano + "/"+ obj.mes + "/"+
				obj.dia + "/"+ obj.hora + "/"+ obj.minuto + "/";
				
				System.out.println("meteu atï¿½ aqui");
				if(obj.getMensagens()!=null){
					
					//System.out.println("A puta tinha: "+obj.getMensagens().size());
					qualquercoisa += obj.getMensagens().size();
					
					for (Mensagem obj2 : obj.getMensagens())
					{
						qualquercoisa += "/" + obj2.getNome() + "/" + obj2.getMensagem();
					}
					}
				else{
					qualquercoisa += 0+"/";
				}

				if(obj.getLicitacoes()!=null){
					qualquercoisa += "/" + obj.getLicitacoes().size();
					for(Licitacao obj3 : obj.getLicitacoes()){
						qualquercoisa += "/" + obj3.getUser() + "/" + obj3.preco;
					}
				}
				else{
					qualquercoisa += 0+"/";
				}
				
			}
			
		}
		String[] fim = qualquercoisa.split("/");
		return fim;
		
	}


	

	/*
	@Override
	public String[] Login(String username, String password) throws RemoteException
	{
		String admin="";
		String hello="false";
		for (Users obj : b.users)
		{
			
			if (obj.getNome().equals(username))
			{
				
				if (obj.getPassword().equals(password))
				{
					
					admin = ""+obj.admin;
					
					obj.setLog(true);
					hello = "true";
					b.online.add(obj);
					System.out.println("Login feito!");
					try {
						actualizaFicheiros();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				else
				{
					hello = "false";
					System.out.println("Palavra passe errada!");
					
				}
			}
		}
		System.out.println("admins:"+admin);
		String final2 = hello;
		String[]fim = final2.split("-");
		
		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("o que devia estar no admin:"+fim[1]);
		return fim;
	
	}*/
	
	

	@Override
	public String[] Login(String username, String password) throws RemoteException
	{
		String admin="";
		String hello="false";
		for (Users obj : b.users)
		{
			
			if (obj.getNome().equals(username))
			{
				
				if (obj.getPassword().equals(password))
				{
					
					admin = ""+obj.admin;
					String ola ="";
					obj.setLog(true);
					b.online.add(obj);
					hello = "true";
					System.out.println("Login feito!");
					if(obj.pendentes.size()>0){
						System.out.println("O SIZE E!!!! " + obj.getPendentes().size());
						
						ArrayList<Mensagem> pendentes = obj.getPendentes();
						
						
						if (!(obj.getPendentes().isEmpty()))
						{
							System.out.println("entrou no if");
							
							for (Mensagem obj2 : pendentes)
							{
								
								hello+="/"+
								"type : notification_message , id :" + obj2.getId() + ", user : " + obj2.getNome() + ", text : "
										+ obj2.getMensagem();
								
								
								
							}
							/////////////////////////
							obj.pendentes = new ArrayList<Mensagem>();
							///////////////////////////
					}}
					try {
						actualizaFicheiros();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				else
				{
					//hello = "false";
					System.out.println("Palavra passe errada!");
					
				}
			}
		}
		System.out.println("admins:"+admin);
		String final2 = hello;
		String[]fim = final2.split("/");
		
		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("o que devia estar no admin:"+fim[1]);
		return fim;
	
	}




	
	


	@Override
	public String[] CreateLeilao(String type, String username2, int id, String codigo, String titulo, String descricao,
			 int ano, int mes, int dia, int hora, int minuto, int amount) throws RemoteException
	{
		
		System.out.println("create leilao");
		Users username = new Users(username2," ",false,true);

		for (Users obj : b.users)
		{
			if (obj.getNome().equals(username))
			{
				username = obj;
				break;
			}
		}
		Leilao leilao = new Leilao(id, titulo, codigo, descricao, ano, mes, dia, hora, minuto, username, amount);
		String[] fim =  {"true"};
		b.leiloes.add(leilao);
		
		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return fim;


	}
	

	
	




	@Override
	public String[] ListarUsersOnline() throws RemoteException {

		String qualquercoisa = "";
		int count = 0;
		for (Users obj : b.online)
		{
			count++;
			qualquercoisa+="/"+obj.nome+"/";
		}
		qualquercoisa = count + qualquercoisa;
		String[] fim = qualquercoisa.split("/");
		return fim;
	}
	@Override
	public String[] ListarLicitacoesUser(String user) throws RemoteException{

		int count2 = 0;
		String qualquercoisa2="";
		for(int i=0;i<leiloes.size();i++){
				for(int j=0;j<leiloes.get(i).getLicitacoes().size(); j++){
					System.out.println("user dado"+ user +"|" + leiloes.get(i).getLicitacoes().get(j).getUser());
					if(leiloes.get(i).getLicitacoes().get(j).getUser().equals(user)){
						count2++;
						qualquercoisa2 += "/" + leiloes.get(i).getLicitacoes().get(j).id + "/" + leiloes.get(i).getLicitacoes().get(j).preco;
					}
					
				}
			
		}
		
		String comando = count2 + qualquercoisa2;
		System.out.println(comando);
		String [] fim = comando.split("/");
		
		
		
		return fim;
	}


	
	


	@Override
	public void EditProp(String id) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public void BestBid() {
		// TODO Auto-generated method stub
		
	}


	 @Override
	    public void VerificaTermino() {
	            synchronized (b.leiloes){
	        for(int i=0;i<b.leiloes.size();i++){
	            int ano = b.leiloes.get(i).ano;
	            int mes = b.leiloes.get(i).mes;
	            int dia = b.leiloes.get(i).dia;
	            int hora = b.leiloes.get(i).hora;
	            int minuto = b.leiloes.get(i).minuto;
	           
	            if((comparaTo(ano,mes,dia,hora,minuto)== -1)||((comparaTo(ano,mes,dia,hora,minuto)== 0))){
	                            System.out.println("merda merda merda merda merda merda merda merda");
	                if(b.leiloes.get(i).getLicitacoes().size()>0){
	                                    int indice = b.leiloes.get(i).getLicitacoes().size()-1;
	                                    String ganhou = b.leiloes.get(i).getLicitacoes().get(indice).user;
	                                    for(int j=0;j<b.users.size();j++){
	                                            if(b.users.get(j).equals(ganhou)){
	                                                    b.users.get(j).leiloesganhos.add(b.leiloes.get(i));
	                                            }
	                                    }
	 
	                                    for(int j=0;j<b.admin.size();j++){
	                                            if(b.admin.get(j).equals(ganhou)){
	                                                    b.admin.get(j).leiloesganhos.add(b.leiloes.get(i));
	                                            }
	                                    }
	                                }
	                                System.out.println("leiloes:"+b.leiloes);
	                                b.acabados.add(b.leiloes.get(i));
	                                System.out.println("acabaods:"+b.acabados);
	                                b.leiloes.remove(b.leiloes.get(i));
	                                System.out.println("leilos:"+b.leiloes);
	                               
	                            }
	                           
	                //RECEBER NOTIFICACAO
	               
	           
	               
	            }
	        }      
	    }

	/*
	
	public void TerminaLeilao() {
		// TODO Auto-generated method stub
		
	}*/
	
	
	


	@Override
	public String[] CancelaLeilao(int id) {
		

		String[] fim = {"false"};
		for (int i = 0; i < b.leiloes.size(); i++)
		{
			if (b.leiloes.get(i).getID() == id)
			{
				b.leiloes.remove(b.leiloes.get(i));
				fim[0] = "true";

			}
			
		}
		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fim;
	}
	



	@Override
	public String[] BanUser(String username) {

		String hello = "";
		
		//apagar leiloes

		for(int j=0; j<b.leiloes.size();j++){
			if(b.leiloes.get(j).getNome().equals(username)){
				b.leiloes.remove(j);
			}
		}
		
		//apagar licitacoes
		for(int i=0;i<b.leiloes.size();i++){
			for(int j=0;j<b.leiloes.get(i).getLicitacoes().size();j++){
				if(b.leiloes.get(i).getLicitacoes().get(j).getUser().equals(username)){
					b.leiloes.get(i).getLicitacoes().remove(j);
				}
			}
			
		}
	
		//apagar user
		for (int i = 0; i < b.users.size(); i++)
		{
			System.out.println(b.users.get(i).getNome() + "|"+ username);
			if (b.users.get(i).getNome().equals(username))
			{
				hello+="true";
				System.out.println(hello);
				b.users.remove(i);
			}

		}
		
		for(int i=0;i<b.online.size();i++){
			if(b.online.get(i).nome.equals(username)){
				b.online.remove(i);
			}
		}
		
		

		
	
		
		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] fim = {hello};
		
		return fim;
		
	}

	



	

	public void TCPTeste() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public String[] Registar(String username, String password) throws RemoteException
	{
		
		String ola ="";
		System.out.println("ENTROU NO REGISTAR");
        System.out.println(username+"\n"+password);
        int cnt=0;
        for(int i=0;i<b.users.size();i++){
        	if(username.equals(b.users.get(i).nome)){
        		cnt+=1;
        		 ola =  "false";
        		 
        	}
        	
        }
        if(cnt==0){
        	Users utilizador = new Users(username, password, false, true);
    		//System.out.println("PUTA:"+utilizador.isAdmin());
    		 ola =  "true"; //estÃ¡ sempre a criar user normal, nunca admin
    		b.users.add(utilizador);
    		b.online.add(utilizador);
    		try {
    			System.out.println("vai actualizar");
    			actualizaFicheiros();
    		} catch (IOException e) {
    			System.out.println("deu erro");
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
		
		
		
		String [] fim = {ola};
		return fim;
	}
	
	/*
	@Override
	public String[] WriteMensagem(int id, String mensagem, String user) throws RemoteException {

		Random a = new Random();
		int id2 = a.nextInt(1000000);
		for (Leilao obj : b.leiloes)
		{
			if (obj.getID() == id)
			{
				Mensagem nova = new Mensagem(user, id2, mensagem);
				obj.getMensagens().add(nova);

			}
		}
		String[] fim = {"true"};

		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fim;
	}*/
	/*
	@Override
	public String[] MakeLicitacao(int id, String user, float amount) throws RemoteException {
		Random a = new Random();
		int id2 = a.nextInt(1000000);
		String ola ="";
		for (Leilao obj : b.leiloes)
		{
			if (obj.getID() == id)
			{
				if(amount<=obj.amount){
					System.out.println("licitado:"+amount +"|" + obj.amount);
					Licitacao nova = new Licitacao(user, id2, amount);
					obj.getLicitacoes().add(nova);
					ola+="true";
					
				}
				
				else{
					ola+="false";
				}
				
				
				System.out.println("leilão ficou com estas licitaçoes: "+obj.getLicitacoes().size());
				

			}
		}
		
		String[] fim={ola};
		

		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return fim;
	}

	*/
	
	
	@Override
	public String[] MakeLicitacao(int id, String user, float amount) throws RemoteException {
		Random a = new Random();
		int id2 = a.nextInt(1000000);
		String ola ="";
		String hello="";
		for (Leilao obj2 : b.leiloes)
		{
			if (obj2.getID() == id)
			{
				if((amount<=obj2.amount))
				{
					System.out.println("licitado:"+amount +"|" + obj2.amount);
					Licitacao nova = new Licitacao(user, id2, amount);
					obj2.getLicitacoes().add(nova);
					ola+="true";
					
					//for(int j=0;j<b.leiloes.get(i)) .get user da licitação envia mensagem
					for(int i = 0;i<obj2.licitacoes.size();i++){
						for(int k = 0;k<b.users.size();k++){
							if(b.users.get(k).getNome().equals(obj2.licitacoes.get(i).user)){
								if(b.users.get(k).pendentes.size()>0){
									System.out.println("O SIZE E!!!! " + b.users.get(k).getPendentes().size());
									
									ArrayList<Mensagem> pendentes = b.users.get(k).getPendentes();
									
									
									if (!(b.users.get(k).getPendentes().isEmpty()))
									{
										System.out.println("entrou no if");
										
										for (Mensagem obj3 : pendentes)
										{
											
											hello+="/"+
											"type : notification_message , id :" + obj3.getId() + ", user : " + obj3.getNome() + ", amount : "
													+ obj3.getMensagem();

										}
										/////////////////////////
										b.users.get(k).pendentes = new ArrayList<Mensagem>();
										///////////////////////////
								}}
							}
						}
						
					}
					
				}
				else{
					ola+="false";
				}
				
				
				if(obj2.licitacoes.size()>1){
					if(amount<=obj2.licitacoes.get(obj2.licitacoes.size()-1).preco){
						System.out.println("licitado:"+amount +"|" + obj2.amount);
						Licitacao nova = new Licitacao(user, id2, amount);
						obj2.getLicitacoes().add(nova);
						
						
						for(int i = 0;i<obj2.licitacoes.size();i++){
							for(int k = 0;k<b.users.size();k++){
								if(b.users.get(k).getNome().equals(obj2.licitacoes.get(i).user)){
									if(b.users.get(k).pendentes.size()>0){
										System.out.println("O SIZE E!!!! " + b.users.get(k).getPendentes().size());
										
										ArrayList<Mensagem> pendentes = b.users.get(k).getPendentes();
										
										
										if (!(b.users.get(k).getPendentes().isEmpty()))
										{
											System.out.println("entrou no if");
											
											for (Mensagem obj3 : pendentes)
											{
												
												hello+="/"+
												"type : notification_message , id :" + obj3.getId() + ", user : " + obj3.getNome() + ", amount : "
														+ obj3.getMensagem();

											}
											/////////////////////////
											b.users.get(k).pendentes = new ArrayList<Mensagem>();
											///////////////////////////
									}
								}
							}
							
						}
							}
					}
					else{
						ola+="";
					}
					
					
				}
				
				
				
				System.out.println("leilão ficou com estas licitaçoes: "+obj2.getLicitacoes().size());
				

			}
		}
		
		String[] fim={ola};
		

		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return fim;
	}


	@Override
    public String[] TopTenLeiloesCriados() throws RemoteException
    {
		System.out.println("ENTROU NA TOP10CRIADOS");
        int flag;
        ArrayList <String> nomes = new ArrayList<>();
        ArrayList <Integer> numero = new ArrayList<>();
        nomes.add("dummy");
        numero.add(-1);
        for (int i = 0; i < leiloes.size(); i++)
        {
        	System.out.println("primeiro ciclo");
            flag = 0;
            for (int j = 0; j < nomes.size(); j++)
            {
            	System.out.println("segundo ciclo");
                if (nomes.get(j).equals(leiloes.get(i).getUser().getNome()))
                {
                    flag = numero.get(j);
                    numero.set(j, flag++);
                   
                }
                if (flag == 0 && j == nomes.size()-1)
                {
                    nomes.add(leiloes.get(i).getUser().getNome());
                    numero.add(0);
                   
                }
 
            }
        }
        String auxS;
        int auxI;
        for (int k = 0; k < numero.size(); k++)
        {
            for (int l = k; l < numero.size(); l++)
            {
                if (numero.get(k) < numero.get(l))
                {
                    auxS = nomes.get(l);
                    auxI = numero.get(l);
                    nomes.set(l, nomes.get(k));
                    numero.set(l, numero.get(k));
                    nomes.set(k, auxS);
                    numero.set(k, auxI);
                }
               
            }
           
        }
        String qualquercoisa = "";
       
 
        for (int i = 0; i < nomes.size() && i < 10 ; i++ )
        {
        	System.out.println("Nome dos users: " + nomes.get(i));
            qualquercoisa += nomes.get(i) + "/";
           
        }
 
        String[] fim = qualquercoisa.split("/");
        return fim;
 
 
    }
   
 
   
	@Override
    public String[] TopTenLeiloesGanhos() throws RemoteException
    {
       
        Users auxU = new Users("", "", false, false);
 
        for (int k = 0; k < users.size(); k++)
        {
            for (int l = k; l < users.size(); l++)
            {
                if (users.get(k).getLeiloesGanhos().size() < users.get(l).getLeiloesGanhos().size())
                {
                    auxU = users.get(l);
                   
                    users.set(l, users.get(k));
                   
                    users.set(k, auxU);
                   
                }
               
            }
           
        }
        String qualquercoisa = "";
       
 
        for (int i = 0; i < users.size() && i < 10 ; i++ )
        {
        	qualquercoisa += users.get(i).getNome() + "/";
           
        }
        String[] fim = qualquercoisa.split("/");
        System.out.println("ACABOU A FUNC YEAH--------------------------------------------------------------------------------");
        return fim;
 
 
    }
 
   
    @Override
    public String[] LeiloesDezDias() throws RemoteException

    {
    	
    	//System.out.println("E ISTO QUE PROCURO!!!!! ");		

        int count = 0;
 
        for (Leilao obj : leiloes)
        {
        	Date today =new Date();
        	if(obj.data == null)
        		obj.data = new Date();
        	  if (( ((int) today.getTime() - obj.data.getTime()) /(1000 * 60 * 60 * 24))<10)
        	  {
        		  count++;
        		  
        	  }
        	
        }
 
        String[] fim = {""+count};
 
        return fim;
 
 
 
    }
 

    
   
    @Override
    public int comparaTo(int ano, int mes, int dia, int hora, int minuto) {
        
        Calendar cal = Calendar.getInstance(); //data de hoje
        int ano1 = cal.get(Calendar.YEAR);
        int mes1 = cal.get(Calendar.MONTH)+1;
        int dia1 = cal.get(Calendar.DAY_OF_MONTH);
        int hour =  cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        
        
        System.out.println("dia:"+dia1+"mes"+mes1+"ano:"+ano1);
                
        if((ano == ano1) && (dia== dia1) && (mes==mes1) && (hora==hour) && (minuto==minute)){ //igual
            return 0;
        }
        
        else if((ano == ano1) && (dia== dia1) && (mes==mes1) && (hora==hour) && (minuto>minute)){ //maior
        	return 1;
        	
        }
        
        else if((ano == ano1) && (dia== dia1) && (mes==mes1) && (hora==hour) && (minuto<minute)){ //menor
        	return -1;
        	
        }
        
        else if((ano == ano1) && (dia== dia1) && (mes==mes1) && (hora>hour)){ //maior
        	return 1;
        	
        }
        
        else if((ano == ano1) && (dia== dia1) && (mes==mes1) && (hora<hour)){ //menor
        	return -1;
        	
        }
        
        else if((dia>dia1) && (ano==ano1) && (mes==mes1) && (hora==hour) && (minuto==minute)){ //maior
            return 1;
        }
        else if((dia<dia1) && (ano==ano1) && (mes==mes1) && (hora==hour)  && (minuto==minute)){ //menor
            return -1;
        }
        else if((mes>mes1) && (ano==ano1) && (hora==hour) && (minuto==minute)){ //maior
            return 1;
        }
        else if((mes<mes1) && (ano==ano1) && (hora==hour)  && (minuto==minute)){ //menor
            return -1;
        }
        else if((ano>ano1)){ //maior
            return 1;
        }
        else if((ano < ano1)){ //menor
            return -1;
        }
        return 0;
    }

	@Override
	public void TerminaLeilao() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
    public void sair (String username){
        for(int i=0;i<online.size();i++){
            if(online.get(i).getNome().equals(username)){
                online.remove(i);
            }
        } 
    }
	
	
	

	@Override
	public String[] printMensagem(String username, Mensagem mensagem, String numLeilao) throws RemoteException
	{
		String intermedio = "" + username + " " + numLeilao + " " + mensagem.getNome() + " " + mensagem.getMensagem() ;
		String[] fim = intermedio.split(" ");
		
		
		return fim;
		
	}

	@Override
	public String[] WriteMensagem(int id, String mensagem, String user) throws RemoteException {

		Random a = new Random();
		int id2 = a.nextInt(1000000);
		for (Leilao obj : b.leiloes)
		{
			if (obj.getID() == id)
			{
				Mensagem nova = new Mensagem(user, id2, mensagem);
				obj.getMensagens().add(nova);
				
				for (Users obj2 : b.users)
				{
					if (obj.getUser().getNome().equals(obj2.getNome()))
					{
						System.out.println("O NOME DO CRIADOR E: " + obj2.getNome());
						
						int cnt = 0;
						for(int i=0;i<b.online.size();i++){
							if(obj2.nome.equals(b.online.get(i).nome)){
								cnt+=1;
								System.out.println("ENTROU NO ISLOG");  //User esta online e recebe imediatamente
							}
							
							
						}
						if(cnt==0){
							System.out.println("ENTROU NO ELSE ISLOG"); //user nao está online e fica pendente
							obj2.setPendentes(nova);	
						}
						
					}
					
					
				}
				
				int cnt2=0;
				for(int j=0;j<b.leiloes.size();j++){
					for(int k=0;k<b.leiloes.get(j).licitacoes.size();k++){
						
						for(int o=0;o<b.users.size();o++){
							
							System.out.println("user lici:"+b.leiloes.get(j).licitacoes.get(k).user+" | "+ "user no array:"+b.users.get(o).nome);
							if(b.leiloes.get(j).licitacoes.get(k).user.equals(b.users.get(o).nome)){
								System.out.println("entrou");
							for(int i=0;i<b.online.size();i++){
								System.out.println(b.users.get(o)+ "|" +b.online.get(i));
								if(b.users.get(o).nome.equals(b.online.get(i))){
									cnt2+=1;
									System.out.println("ENTROU NO ISLOG");  //User esta online e recebe imediatamente
								}
							}
							if(cnt2==0){
								System.out.println("ENTROU NO ELSE ISLOG"); //user nao está online e fica pendente
								if(b.users.get(o).getPendentes().contains(nova)){
									continue;
								}
								else{
									b.users.get(o).setPendentes(nova);
								}
								
								
								
							}
							
							}
						}
						
					}
				}
				

			}
		}
		String[] fim = {"true"};

		try {
			actualizaFicheiros();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fim;
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	

	




	

}

