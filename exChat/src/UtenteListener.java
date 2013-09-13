package com.wizards.exChat;

import java.net.*;
import java.io.*;
import java.util.*;

/*
*Classe che legge i messaggi e li inoltra al manager
*@author Luca Colangelo
*/

public class UtenteListener extends Thread{
  
  Manager manager;
  Utente utente;
  InputStream  is       = null;
  InputStreamReader isr = null;
  BufferedReader br     = null;

  //costruttore
  public UtenteListener(Utente utente,Manager manager) throws IOException {
    this.utente = utente;
    this.manager = manager;
    Socket socket = utente.cSocket;
    this.is = socket.getInputStream();
    this.isr = new InputStreamReader(is);
    this.br = new BufferedReader(isr); 
  }
  
  /*Metodo run che legge i messaggi e li passa al gestore messaggi del Manager*/
  public void run(){
    try{
      while(true){
        String m = br.readLine();//leggo il messaggio
        if(null == m){
          System.out.println("Errore");
          break ;
        }
        manager.gestoreMessaggio(utente,m);//lo passo al gestore messaggi del Manager
      }

    }catch(IOException ioe){
      System.out.println("Errore : " + ioe.getMessage());
    }
  }
 

}
