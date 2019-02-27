package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.Random;

import rmi.BanUser;
import rmi.CancelarLeilao;
import rmi.CriarLeilao;
import rmi.EditarLeilao;
import rmi.EscreverMural;
import rmi.LicitarLeilao;
import rmi.ListarEstatisticas;
import rmi.ListarLicitacoesUser;
import rmi.ListarUsersOnline;
import rmi.Listarversoesantigas;
import rmi.Login;
import rmi.RMI;
import rmi.RMIServer;
import rmi.Registar;
import rmi.RegistarAdmin;
import rmi.Response;
import rmi.SearchLeilao;
import rmi.SearchLeilaoDetalhe;
import rmi.SearchLeilaoUser;
import rmi.TopTenLeiloesCriados;
import rmi.TopTenLeiloesGanhos;

public class MenuAdmin extends Thread {
    String mensagem;
    String username2;
    Response resposta;
    Socket clien;
    PrintWriter out;

    public Response getResposta() {
        return resposta;
    }
	public BufferedReader le;
	//public TCPserver tcp;
	
	public MenuAdmin(String mensagem ,Socket clien, String username) throws IOException, NotBoundException{
			
            this.mensagem = mensagem;
            this.clien = clien;
            this.username2=username;
            out = new PrintWriter(clien.getOutputStream(), true);
		System.out.println("esta aqui");
		le = new BufferedReader(new InputStreamReader(System.in));
		start();
	}
	RMI rmiserver;
	
	
	public void run(){
		
		System.out.println("MENU_ADMIN");
				String [] comando = mensagem.split(",");
				for(int i = 0;i<comando.length;i++){
					if (comando[i].contains("cancel")){
						
						
						String[] id = comando[i+1].split(": ");
						String id2 = id[1];
						int id3 = Integer.parseInt(id2);
						resposta = (Response) new CancelarLeilao(id3).execute(rmiserver); //resposta ï¿½ o comando

						String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						
					}
					if (comando[i].contains("statics")){
						//////////////////////////////
						resposta = (Response) new TopTenLeiloesGanhos().execute(rmiserver);
						
						String comandoresposta = "type: statics_toptenwon, ok:"+resposta.values[0]+", "+resposta.values[1]+", "+resposta.values[2]+", "+resposta.values[3]+", "+resposta.values[4]+", "+resposta.values[5]+", "+resposta.values[6]+", "+resposta.values[7]+", "+resposta.values[8]+", "+resposta.values[9];
						out.println(comandoresposta);
						

						resposta = (Response) new TopTenLeiloesCriados().execute(rmiserver);
						
						String comandoresposta2 = "type: statics_toptencreated, ok:"+resposta.values[0]+", "+resposta.values[1]+", "+resposta.values[2]+", "+resposta.values[3]+", "+resposta.values[4]+", "+resposta.values[5]+", "+resposta.values[6]+", "+resposta.values[7]+", "+resposta.values[8]+", "+resposta.values[9];
						out.println(comandoresposta2);
						
						resposta = (Response) new ListarEstatisticas().execute(rmiserver);
						
						String comandoresposta3 = "type: statics_last10, ok:"+resposta.values[0]+", "+resposta.values[1]+", "+resposta.values[2]+", "+resposta.values[3]+", "+resposta.values[4]+", "+resposta.values[5]+", "+resposta.values[6]+", "+resposta.values[7]+", "+resposta.values[8]+", "+resposta.values[9];
						out.println(comandoresposta3);
						
						
					}
					
					if (comando[i].contains("ban_user")){
						
						System.out.print("BEM VINDO ADMIN BORA EXPULSAR PESSOAS");
						String[] id = comando[i+1].split(": ");
						String id2 = id[1];
						
						resposta = (Response) new BanUser(id2).execute(rmiserver);
						String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						
						
					}
					
					/*if (comando[i].contains("test_tcp")){
						
						
					}*/

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
						
						if(verificaData(ano,mes,dia,hora,minuto)==false){
							out.println("type: create_auction, ok: false \n (date is invalid)");
							
						}
						else{
							//detalhe
							/*
							String[] detalhes = comando[i+5].split(": ");
							String detalhe = description[1];*/
							
							
							Random rn = new Random();
							
							//id
							int idpedido = rn.nextInt(100000000); //verificar nos ficheiros se ja nao existe um com este id
							String[] amount = comando[i+5].split(": ");
							int amounty = Integer.parseInt(amount[1]);

							resposta = (Response) new CriarLeilao("create_auction",username2,idpedido,codigo,titulo,descricao, ano, mes, dia, hora ,minuto, amounty).execute(rmiserver);  //resposta ï¿½ o comando

							String comandoresposta = "type: "+resposta.type+", "+"ok: "+resposta.values[0];
							System.out.println(comandoresposta);
							out.println(comandoresposta);
							
							
							
							}
						
						
						}
					
					if (comando[i].contains("edit_auction")){
						
						System.out.println("numero de cenas no comando:"+comando.length);
						for(int j=0;j<comando.length;j++){
							System.out.println(comando[j]);
							
						}
						
						String[] idl = comando[i+1].split(": ");
						int id = Integer.parseInt(idl[1]);
						
						//data
						String[]deadline = comando[i+2].split(": ");
						String[] data = deadline[1].split(" ");
						String[] anodiames = data[0].split("-");
						String[] horadata = data[1].split("-");
						int ano = Integer.parseInt(anodiames[0]);
						int mes = Integer.parseInt(anodiames[1]);
						int dia = Integer.parseInt(anodiames[2]);
						int hora = Integer.parseInt(horadata[0]);
						int minuto = Integer.parseInt(horadata[1]);
						
						String[]titulo2 = comando[i+3].split(": ");
						String titulo = titulo2[1];
						
						
						System.out.print("ano:"+ano+"mes:"+mes+"dia:"+dia+"hora:"+hora+"minuto:"+minuto+"\n");
						
						
						System.out.print("titulo:"+titulo);
						
						String[]descricao2 = comando[i+4].split(": ");
						String descricao = descricao2[1];
						
						System.out.print("descricao:"+descricao);
						
						String[]amount2 = comando[i+5].split(": ");
						int amount = Integer.parseInt(amount2[1]);
						
						System.out.println("amount:"+amount);
						
						
						resposta = (Response) new EditarLeilao("edit_auction",id, ano, mes, dia, hora, minuto, titulo, descricao, amount).execute(rmiserver);  //resposta ï¿½ o comando


						String comandoresposta = "type: "+resposta.type+", ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						out.println(comandoresposta);

						
					}


					
					
					if (comando[i].contains("search_auction")){
						//codigo artigo
						String[] code = comando[i+1].split(": ");
						String codigo = code[1];
						

						resposta = (Response) new SearchLeilao("search_auction",codigo).execute(rmiserver);  //resposta ï¿½ o comando


						int cnt = Integer.parseInt(resposta.values[0]);
						String comandoresposta = "type: "+resposta.type+", "+"items_count: "+resposta.values[0]+", ";
						int aux = 0;
						for(int j=cnt;j>0;j--){
							comandoresposta+="items_";
							comandoresposta+=j-Integer.parseInt(resposta.values[0])+"_id: ";
							aux++;
							comandoresposta+=resposta.values[aux]+", "+"items_";
							aux++;
							comandoresposta+=j-Integer.parseInt(resposta.values[0])+"_code: "+resposta.values[aux]+", "+"items_";
							if(j==1){
								aux++;
								comandoresposta+=j-Integer.parseInt(resposta.values[0])+"_title: "+resposta.values[aux];
							}
							else{
								aux++;
								comandoresposta+=j-Integer.parseInt(resposta.values[0])+"_title: "+resposta.values[aux]+", ";
							}
							
							
						}
						//System.out.println(comandoresposta);
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						
					}
					
					if (comando[i].contains("detail_auction")){
						//id leilao
						String[] idp = comando[i+1].split(": ");
						int id = Integer.parseInt(idp[1]);
						

						resposta = (Response) new SearchLeilaoDetalhe("detail_auction",id).execute(rmiserver);  //resposta ï¿½ o comando
						
						////////////////////////DEBUG
						for(int j=0;j<resposta.values.length;j++){
							System.out.print("RESPOSTAPUTA"+"--------->"+resposta.values[j]);
							
						}
						
						
						
						String comandoresposta = "type: "+resposta.type+", "+"title: "+resposta.values[1]+", "+"description: "+resposta.values[2]+", "+"deadline: "+resposta.values[3]+"-"+resposta.values[4]+"-"+resposta.values[5]+" "+resposta.values[6]+"-"+resposta.values[7]+", "+"message_count: "+resposta.values[8];
						int aux = 8;
						System.out.println("tem estas mensagens"+resposta.values[8]);
						if(Integer.parseInt(resposta.values[8])!=0){
							
							int cnt = Integer.parseInt(resposta.values[8]);
							
							if(cnt>0){
							for(int j=cnt;j>0;j--){
									aux++;
									comandoresposta+=", messages_"+(Integer.parseInt(resposta.values[8])-j)+"_user:"+resposta.values[aux];
									aux++;
									comandoresposta+=", messages_"+(Integer.parseInt(resposta.values[8])-j)+"_text:"+resposta.values[aux];
							}
							}}
						
						
							//LICITAÇÕES
							aux++;
							comandoresposta+=", bids_count: "+resposta.values[aux];
							int save = Integer.parseInt(resposta.values[aux]);
							int cnt2 = Integer.parseInt(resposta.values[aux]);
							int varia = cnt2;
							if(cnt2>0){
								for(int j=cnt2;j>0;j--){
									aux++;
									comandoresposta+=", bid_"+(save-j)+"_user:"+resposta.values[aux];
									aux++;
									comandoresposta+=", bid_"+(save-j)+"_amount:"+resposta.values[aux];
									
								}
								
							}
							
							//System.out.println(comandoresposta);
							System.out.println(comandoresposta);
							out.println(comandoresposta);
							
						}
						
						
					
					if (comando[i].contains("my_auctions")){
						

						resposta = (Response) new SearchLeilaoUser("my_auctions",username2).execute(rmiserver);  //resposta ï¿½ o comando
						
						////////////////////DEBUGG////////////////////
						for(int j=0;j<resposta.values.length;j++){
							System.out.println(resposta.values[j]);
						}
						
						
						////////////////////////////////////////mudar resposta
						String comandoresposta = "type: "+resposta.type+", items_count: "+resposta.values[0];
						int cnt = Integer.parseInt(resposta.values[0]);
						int aux = 0;
						for(int j=cnt;j>0;j--){
							aux++;
							comandoresposta+=", items_"+(Integer.parseInt(resposta.values[0])-j)+"_id: "+resposta.values[aux];
							aux++;
							comandoresposta+=", items_"+(Integer.parseInt(resposta.values[0])-j)+"_code: "+resposta.values[aux];
							aux++;
							comandoresposta+=", items_"+(Integer.parseInt(resposta.values[0])-j)+"_title: "+resposta.values[aux];
							
						}
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						
						
						
						
					}
					if (comando[i].contains("bid")){
						System.out.print("Entrou no bid");
						String[] idl = comando[i+1].split(": ");
						int id = Integer.parseInt(idl[1]);
						String[] amount = comando[i+2].split(": ");
						int money = Integer.parseInt(amount[1]);
						

						resposta = (Response)new LicitarLeilao("bid",id,money,username2).execute(rmiserver);  //resposta ï¿½ o comando

						String comandoresposta = "type: "+resposta.type+", ok: "+resposta.values[0];
						System.out.println("licitou"+comandoresposta);
						out.println(comandoresposta);
						/*
						if(resposta.values[1]=="true"){
							//receber notificacao
							//String notificacao = n o t i f i c a t i o n _ b i d , id : 101 , user : pi e r r e , amount : 5
							
						}*/
						
						resposta = (Response) new EscreverMural("message", id, ""+money, username2).execute(rmiserver);  //resposta ï¿½ o comando

						String comandorespostaa = "type: notification_bid"+", ok: "+resposta.values[0];
						System.out.println(comandorespostaa);
						//out.println(comandoresposta);
						
						
					}
					

					if (comando[i].contains("message")){
						
						String[] idl = comando[i+1].split(": ");
						int id = Integer.parseInt(idl[1]);
						
						String[] texto = comando[i+2].split(": ");
						////////DEBUG
						for(int j=0;j<texto.length;j++){
							System.out.println("Mensagem"+texto[j]+"\n");
						}
						String text = texto[1];
						System.out.print("mensagem guardada"+text);
						
						

						resposta = (Response) new EscreverMural("message", id, text, username2).execute(rmiserver);  //resposta ï¿½ o comando

						String comandoresposta = "type: "+resposta.type+", ok: "+resposta.values[0];
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						//todos os que escreveram devem receber isto; ->depende de como se fizer a funcao no rmi
						//type : not i f i c a t ion_me s s age , id : 101 , user : pi e r r e , t e x t :alguma edi t o r a em e s p e c i a l ?
						
						
						
						
						
					}
					if (comando[i].contains("online_users")){
						
						resposta = (Response) new ListarUsersOnline("online_users").execute(rmiserver);  //resposta ï¿½ o comando


						String comandoresposta = "type: "+resposta.type+", users_count: "+resposta.values[0];
						
						System.out.println("n users online:"+resposta.values[0]);
						
						int cnt = Integer.parseInt(resposta.values[0]);
						int aux = 0;
						for(int j=cnt;j>0;j--){
							aux++;
							comandoresposta+=", users_"+(Integer.parseInt(resposta.values[0])-j)+"_username: "+resposta.values[aux];
						}
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						

						
					}
					
					if (comando[i].contains("older_versions")){
						String[] idl = comando[i+1].split(": ");
						int id = Integer.parseInt(idl[1]);
						resposta = (Response) new Listarversoesantigas("older_versions",id).execute(rmiserver);
						String comandoresposta = "";
						int aux=0;
						
							comandoresposta += "code: "+resposta.values[0]+", ";
							comandoresposta+= "title: "+resposta.values[1]+", ";
							comandoresposta+= "description: "+resposta.values[2]+", ";
							comandoresposta+= "date: "+resposta.values[3];
							
						
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						
						
					}
					if (comando[i].contains("my_lici")){
						String[] username = comando[i+1].split(": ");
						String username2 = username[1];
						resposta = (Response) new ListarLicitacoesUser("my_bids",username2).execute(rmiserver);
						String comandoresposta = "type: my_bids, bids_count: "+resposta.values[0];
						int cnt = Integer.parseInt(resposta.values[0]);
						int aux =0;
						for(int j=1;j<=cnt;j++){
							aux++;
							comandoresposta += ", id: "+resposta.values[aux];
							aux++;
							comandoresposta+=", valor: "+resposta.values[aux];
						}
						System.out.println(comandoresposta);
						out.println(comandoresposta);
						
					}
					
					
					if (comando[i].contains("win10"))
					{
						//////////////////////////////
						resposta = (Response) new TopTenLeiloesGanhos().execute(rmiserver);
						
						String comandoresposta = "type: statics_toptenwon, ok: ";
						
						for (int j = 0; j < resposta.values.length; j++)
						{
							comandoresposta = comandoresposta + resposta.values[j] + ", ";
							
						}
						
						out.println(comandoresposta);
					}
					if (comando[i].contains("created10"))
					{
						resposta = (Response) new TopTenLeiloesCriados().execute(rmiserver);
						String comandoresposta2 = "type: statics_toptencreated, ok: ";
						
						for (int j = 0; j < resposta.values.length; j++)
						{
							comandoresposta2 = comandoresposta2 + resposta.values[j] + ", ";
							
						}
						
						out.println(comandoresposta2);
					}
					if (comando[i].contains("last10"))
					{
						resposta = (Response) new ListarEstatisticas().execute(rmiserver);
						
						String comandoresposta3 = "type: statics_last10, ok: ";
						
						for (int j = 0; j < resposta.values.length; j++)
						{
							comandoresposta3 = comandoresposta3 + resposta.values[j];
							
						}
						
						out.println(comandoresposta3);						
						
						/*
						String comandoresposta3 = "type: statics_last10, ok:"+resposta.values[0]+", "+resposta.values[1]+", "+resposta.values[2]+", "+resposta.values[3]+", "+resposta.values[4]+", "+resposta.values[5]+", "+resposta.values[6]+", "+resposta.values[7]+", "+resposta.values[8]+", "+resposta.values[9];
						out.println(comandoresposta3);
						*/
						
					}
					
					
					
					///////////////////////////////////////////////////////////////////
					
					


					
					////////////////////////////////////////////////////////////////
					
					
					
					
					
			
				}
	}
		
		

		    public boolean verificaData(int ano, int mes, int dia, int hora, int minuto){
		    
		    if(ano>=2016){
		        if(mes==1|| mes==3||mes==5||mes==7||mes==8||mes==10||mes==12){
		            if(dia<=31 && dia>0){
		                
		                return true;
		            }
		            else{
		                
		                return false;
		            } 
		        }
		        else if(mes==4||mes==6||mes==9||mes==11){
		            if(dia<=30 && dia>0){
		                
		                return true;
		            }
		            else{
		               
		                return false;
		            } 
		            
		        }
		        else if(mes==2){
		            if(dia<=29 && dia>0){
		               
		                return true;
		            }
		            else
		               
		            return false;
		        }
		        else{
		       
		        return false;
		    }
		        
		    }
		    else{
		       
		        return false;
		    }
		
		    }

			
	
	}
		
	
	
	
