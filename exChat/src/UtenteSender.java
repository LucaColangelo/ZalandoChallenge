package com.wizards.exChat;

import java.net.*;
import java.io.*;
import java.util.*;

/*
*Classe che invia i messaggi 
*@author Luca Colangelo
*/

public class UtenteSender extends Thread{

  Vector<String> messaggi = new Vector<String>(); //coda dei messaggi
  Manager manager;
  Utente utente;
  OutputStream       os  = null;
  OutputStreamWriter osw = null;
  BufferedWriter     bw  = null;

  //costruttore
  public UtenteSender(Utente utente,Manager manager) throws IOException{
    this.utente = utente;
    this.manager = manager;
    Socket socket = utente.cSocket;
    os = socket.getOutputStream(); //catena di costruttori             	 
    osw = new OutputStreamWriter(os);
    bw  = new BufferedWriter(osw);    
  }

  /*Metodo che aggiunge un messaggio alla coda notificandolo a messaggioInCoda
  *@param String. 
  */
  public synchronized void addMessaggio(String messaggio){
    messaggi.add(messaggio);
    notify();
  }
  
  /*Metodo che restituisce e cancella i messaggi dalla coda.
  * Se la coda e' vuota aspetta la notifica di addMessaggio.
  */
  private synchronized String messaggioInCoda() throws InterruptedException{
    while(0 == messaggi.size()){ //attende la notifica di addMessaggio
      wait();
    }
    String m = messaggi.get(0); //memorizzo il messaggio
    messaggi.removeElementAt(0);//lo elimino
    return m;
  }

  /*Metodo che invia il messaggio.
  *@param String.
  */
  public void inviaMessaggio(String messaggio){
    try{
      bw.write(messaggio + "\n");
      bw.flush();
    }catch(IOException ioe){
       System.out.println("Errore : " + ioe.getMessage());
    }  
  }
  
  /*Metodo run che legge i messaggi dalla coda e li invia al socket*/
  public void run(){
    try{
      while(true){
        String m = messaggioInCoda();
        inviaMessaggio(m);
      }
    }catch(Exception e){
       System.out.println("Error : "+ e.getMessage());   
    }
  }
  
}
