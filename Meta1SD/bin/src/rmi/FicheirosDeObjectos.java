/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;
import java.io.*;


/**
 *
 * @author utilizador
 */
public class FicheirosDeObjectos {
    private ObjectInputStream iS;
    private ObjectOutputStream oS;
    

public boolean abreLeitura(String ficheiro){
try{
    File  file = new File(ficheiro);
    if(file.exists()){
       iS = new ObjectInputStream(new FileInputStream(ficheiro));
        return true; 
    }
    else{
        file.createNewFile();
        oS = new ObjectOutputStream(new FileOutputStream(ficheiro));
        return true;
    }
    
}
catch(IOException e){
    return false;
    }

}


public boolean abreEscrita(String ficheiro) throws IOException{
    try{
        File file = new File(ficheiro);
        if(file.exists()){
            oS = new ObjectOutputStream(new FileOutputStream(ficheiro));
            return true;
        }
        else{
            
            file.createNewFile();
            oS = new ObjectOutputStream(new FileOutputStream(ficheiro));
            return true;
        }
    }catch(IOException e){
        return false;
    }

}


public Object leObjecto()throws IOException, ClassNotFoundException{
    return iS.readObject();
}

public void escreveObjecto(Object o) throws IOException{
	System.out.println("entrou na func de escrver");
    oS.writeObject(o);
    System.out.println(o);
    System.out.println("escreveu");
}

public void fechaLeitura() throws IOException
{
    iS.close();
}

public void fechaEscrita() throws IOException{
    oS.close();
}

 
}
