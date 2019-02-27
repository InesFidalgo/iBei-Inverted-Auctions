package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import rmi.*;
import server.TCPserver;


//import client.Client;


public class Menu extends Thread {
    String mensagem;
    Response resposta;
    Socket clien;
    PrintWriter out;

    public Response getResposta() {
        return resposta;
    }
	public BufferedReader le;
	//public TCPserver tcp;
	
	public Menu(String mensagem ,Socket clien) throws IOException{
            this.mensagem = mensagem;
            this.clien = clien;
            out = new PrintWriter(clien.getOutputStream(), true);
		System.out.println("esta aqui");
		le = new BufferedReader(new InputStreamReader(System.in));
		start();
	}
	RMI rmiserver;
	/////////////////////////////////////////////////////chamar fun�oes de ler ficheiros
	public void run(){
		
			/////////////////////////////////
			String [] comando = mensagem.split(",");
			for(int i = 0;i<comando.length;i++){
				if(comando[i].contains("login")){
					String[] username = comando[i+1].split(": ");
					String username2 = username[1];
					String[] password = comando[i+2].split(": ");
					String password2 = password[1];
					
					
					resposta = (Response) new Login("login",username2,password2).execute(rmiserver);

					//a fun��o no rmi vai verificar se est� bem e devolve um array de string com os comandos de resposta
					

					//vou considerar que a funcao de login da um value que diz se e admin ou false
					String verifica = resposta.values[0];
					System.out.println(verifica);
					//String admin = resposta.values[1]; //true e admin //false nao e
					String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
					if(resposta.values.length>1){
						comandoresposta+="\n"+resposta.values[1];
					}
					
					System.out.println(comandoresposta);
					out.println(comandoresposta);
					System.out.println("Bem-vindo! Insira um comando:\n");
					String admin="dontgiveafuck";
					
					

					
				
					
					
					//////////////////////////////////////////////////////////////////////////+////////////////////
					
				}
				if(comando[i].contains("register")){
					System.out.println("entrou aqui - register");
					String[] username = comando[i+1].split(": ");
					String username2 = username[1];
					String[] password = comando[i+2].split(": ");
					String password2 = password[1];
					
					resposta = (Response) new Registar("register",username2,password2).execute(rmiserver);
					 
					System.out.println("despois da gestao");

					//System.out.println("ok: eheh"+resposta.values[0]);
					String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
					System.out.println("aquele lindo comando:"+comandoresposta);
					out.println(comandoresposta);
					
					//System.out.println(comandoresposta);
					//String admin = resposta.values[1];
					System.out.println("Bem-vindo! Insira um comando:\n");
					
					
					
					
				
					}
				
				if(comando[i].contains("admin")){
					String[] username = comando[i+1].split(": ");
					String username2 = username[1];
					String[] password = comando[i+2].split(": ");
					String password2 = password[1];
					
					
					resposta = (Response) new RegistarAdmin("admin",username2,password2).execute(rmiserver);

					//a fun��o no rmi vai verificar se est� bem e devolve um array de string com os comandos de resposta
					

					//vou considerar que a funcao de login da um value que diz se e admin ou false
					String verifica = resposta.values[0];
					System.out.println(verifica);
					//String admin = resposta.values[1]; //true e admin //false nao e
					String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
					System.out.println(comandoresposta);
					out.println(comandoresposta);
					System.out.println("Bem-vindo! Insira um comando:\n");
					
					
					
				}
				
			
			
			
			
			
			}/*//apagar uma destas chavetas
					

					if (comando[i].contains("create_auction")){
						System.out.println("entrou no create auction");
						//codigo artigo
						String[] code = comando[i+1].split(": ");
						String codigo = code[1];
						//titulo
						String[] title = comando[i+2].split(": ");
						String titulo = title[1];
						//description
						String[] description = comando[i+3].split(": ");
						String descricao = description[1];
				
						
						//data
						String[]deadline = comando[i+4].split(": ");
						String[] data = deadline[1].split(" ");
						String[] anodiames = data[0].split("-");
						String[] horadata = data[1].split("-");
						int ano = Integer.parseInt(anodiames[0]);
						int mes = Integer.parseInt(anodiames[1]);
						int dia = Integer.parseInt(anodiames[2]);
						int hora = Integer.parseInt(horadata[0]);
						int minuto = Integer.parseInt(horadata[1]);
						
						//detalhe
						String[] detalhes = comando[i+5].split(": ");
						String detalhe = description[1];
						
						
						Random rn = new Random();
						
						//id
						int idpedido = rn.nextInt(100000000); //verificar nos ficheiros se ja nao existe um com este id
						

						resposta = (Response) new CriarLeilao("create_auction",username2,idpedido,codigo,titulo,descricao,detalhe, ano, mes, dia, hora ,minuto).execute(rmiserver);  //resposta � o comando

						String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						
						
						}
					
					
					if (comando[i].contains("search_auction")){
						//codigo artigo
						String[] code = comando[i+1].split(": ");
						String codigo = code[1];
						

						resposta = (Response) new SearchLeilao("search_auction",codigo).execute(rmiserver);  //resposta � o comando


						int cnt = Integer.parseInt(resposta.values[0]);
						String comandoresposta = "type: "+resposta.type+", "+"items_count: "+resposta.values[0]+", ";
						int aux = 0;
						for(int j=cnt;j>0;j--){
							comandoresposta+="items_"+(Integer.parseInt(resposta.values[0])-cnt)+"_id: ";
							aux++;
							comandoresposta+=resposta.values[aux]+", "+"items_";
							aux++;
							comandoresposta+=Integer.parseInt(resposta.values[0])-cnt+"_code: "+resposta.values[aux]+", "+"items_";
							if(j==1){
								aux++;
								comandoresposta+=Integer.parseInt(resposta.values[0])-cnt+"_title: "+resposta.values[aux];
							}
							else{
								aux++;
								comandoresposta+=Integer.parseInt(resposta.values[0])-cnt+"_title: "+resposta.values[aux++]+", ";
							}
							
							
						}
						//System.out.println(comandoresposta);
						System.out.println(comandoresposta);
						
						
					}
					
					if (comando[i].contains("detail_auction")){
						//id leilao
						String[] idp = comando[i+1].split(": ");
						int id = Integer.parseInt(idp[1]);
						

						resposta = (Response) new SearchLeilaoDetalhe("detail_auction",id).execute(rmiserver);  //resposta � o comando

						
						
						String comandoresposta = "type: "+resposta.type+", "+"title: "+resposta.values[1]+", "+"description: "+resposta.values[2]+", "+"deadline: "+resposta.values[3]+"-"+resposta.values[4]+"-"+resposta.values[5]+" "+resposta.values[6]+"-"+resposta.values[7]+", "+"message_count: "+resposta.values[8];
						int aux = 7;
						System.out.println("tem estas mensagens"+resposta.values[8]);
						if(Integer.parseInt(resposta.values[8])!=0){
							
							int cnt = Integer.parseInt(resposta.values[8]);
							
							
							for(int j=cnt;j>0;j--){
									aux++;
									comandoresposta+=", messages_"+(Integer.parseInt(resposta.values[8])-cnt)+"_user:"+resposta.values[aux];
									aux++;
									comandoresposta+=", messages_"+(Integer.parseInt(resposta.values[8])-cnt)+"_text:"+resposta.values[aux];
							}
							}
							aux++;
							comandoresposta+=", bids_count: "+resposta.values[aux];
							//System.out.println(comandoresposta);
							System.out.println(comandoresposta);
							
							
						}
						
						
					
					if (comando[i].contains("my_auctions")){
						

						resposta = (Response) new SearchLeilaoUser("my_auctions",username2).execute(rmiserver);  //resposta � o comando
						
						////////////////////DEBUGG////////////////////
						for(int j=0;j<resposta.values.length;j++){
							System.out.println(resposta.values[j]);
						}
						
						

						String comandoresposta = "type: "+resposta.type+", items_count: "+resposta.values[0];
						int cnt = Integer.parseInt(resposta.values[0]);
						int aux = 0;
						for(int j=cnt;j>0;j--){
							aux++;
							comandoresposta+=", items_"+(Integer.parseInt(resposta.values[0])-cnt)+"_id: "+resposta.values[aux];
							aux++;
							comandoresposta+=", items_"+(Integer.parseInt(resposta.values[0])-cnt)+"_code: "+resposta.values[aux];
							aux++;
							comandoresposta+=", items_"+(Integer.parseInt(resposta.values[0])-cnt)+"_title: "+resposta.values[aux];
							
						}
						System.out.println(comandoresposta);
						
						
						
						
						
					}
					if (comando[i].contains("bid")){
						String[] idl = comando[i+1].split(": ");
						int id = Integer.parseInt(idl[1]);
						String[] amount = comando[i+2].split(": ");
						int money = Integer.parseInt(amount[1]);
						

						resposta = (Response)new LicitarLeilao("bid",id,money,username2).execute(rmiserver);  //resposta � o comando

						String comandoresposta = "type: "+resposta.type+", ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						
						
						if(resposta.values[1]=="true"){
							//receber notificacao
							//String notificacao = n o t i f i c a t i o n _ b i d , id : 101 , user : pi e r r e , amount : 5
							
						}
						
						
					}
					if (comando[i].contains("edit_auction")){
						
						////////////////////DEBUGG////////////////////
						for(int j=0;j<comando.length;j++){
							System.out.println("comando:"+comando);
						}
						
						
						String[] idl = comando[i+1].split(": ");
						int id = Integer.parseInt(idl[1]);
						
						//data
						String[]deadline = comando[i+4].split(": ");
						String[] data = deadline[1].split(" ");
						String[] anodiames = data[0].split("-");
						String[] horadata = data[1].split("-");
						int ano = Integer.parseInt(anodiames[0]);
						int mes = Integer.parseInt(anodiames[1]);
						int dia = Integer.parseInt(anodiames[2]);
						int hora = Integer.parseInt(horadata[0]);
						int minuto = Integer.parseInt(horadata[1]);
						
						

						resposta = (Response) new EditarLeilao("edit_auction",id, ano, mes, dia, hora, minuto).execute(rmiserver);  //resposta � o comando


						String comandoresposta = "type: "+resposta.type+", ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						

						
					}

					if (comando[i].contains("message")){
						
						String[] idl = comando[i+1].split(": ");
						int id = Integer.parseInt(idl[1]);
						
						String[] texto = comando[i+1].split(": ");
						String text = texto[1];
						
						

						resposta = (Response) new EscreverMural("message", id, text, username2).execute(rmiserver);  //resposta � o comando

						String comandoresposta = "type: "+resposta.type+", ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						
						//todos os que escreveram devem receber isto; ->depende de como se fizer a funcao no rmi
						//type : not i f i c a t ion_me s s age , id : 101 , user : pi e r r e , t e x t :alguma edi t o r a em e s p e c i a l ?
						
						
						
						
						
					}
					if (comando[i].contains("online_users")){
						
						resposta = (Response) new ListarUsersOnline("online_users").execute(rmiserver);  //resposta � o comando


						String comandoresposta = "type: "+resposta.type+", users_count: "+resposta.values[0];
						
						System.out.println("n users online:"+resposta.values[0]);
						
						int cnt = Integer.parseInt(resposta.values[0]);
						int aux = 0;
						for(int j=cnt;j>0;j--){
							aux++;
							comandoresposta+=", users_"+(Integer.parseInt(resposta.values[0])-cnt)+"_username: "+resposta.values[aux];
						}
						System.out.println(comandoresposta);
						
						

						
					}

					
					
				}
			*/
					
					
					
					//////////////////////////////////////////////////////////////////////////*////////////////////
					
		}
				
				
				
				
			
			
			
			 
		
			
		
		
		
	
	
	
	
	
	
	public void after(String username2, String admin) throws NumberFormatException, IOException{
		Response resposta;
		
		
		String [] comando = mensagem.split(",");
			System.out.println("Insira um comando:\n");
			
			
			/*
			if(admin=="true"){
				int op23 = Integer.parseInt(op2);
				System.out.println("Bem-vindo Mr. Admin!");
				System.out.println("\n1- Cancelar Leilao\n2- Banir um Utilizador\n3- Consultar Estatisticas\n4- Testar Servidor TCP\n ");
				switch(op23){
					case 1:
						System.out.println("Insira o id do Leilao a cancelar:\n");
						int id = Integer.parseInt(le.readLine());
						

						resposta = (Response) new CancelarLeilao(id).execute(rmiserver); //resposta � o comando

						
						String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
						//System.out.println(comandoresposta);
						
						System.out.println(comandoresposta);
						
						
					case 2:
						System.out.println("Insira o nome do Utilizador a banir:\n");
						String user = le.readLine();
						

						resposta = (Response) new BanUser(user).execute(rmiserver); //resposta � o comando
						
						String comandoresposta2 = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
						//System.out.println(comandoresposta2);
						System.out.println(comandoresposta2);
						
						
					case 3:

						resposta = (Response) new ListarEstatisticas().execute(rmiserver); //resposta � o comando

					

						//String comandoresposta2 = "type: "+resposta.type+", "+""
						//System.out.println(comandoresposta); //DEPENDE DE COMO SAO ESCRITAS AS ESTATISTICAS PARA A LISTA
						
						
						
					
					case 4:
						resposta = (Response) new TestarServidorTCP().execute(rmiserver); //AINDA NAO SEI COMO VOU FAZER
						
						
						
				}
				
				
			}*/
			
			
			
			
	}

	

		
	 
}
