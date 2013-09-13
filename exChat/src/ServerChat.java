package com.wizards.exChat;

import java.net.*;
import java.io.*;
import java.util.*;

/**
*Classe Sever per una chat. Il server accetta le connessioni, inizializza il nuovo utente
*e delega le gestione dei messaggi al Manager
*@author Luca Colangelo
*/

public class ServerChat{
  
  public static void main(String argv[]){
  
    //inizializzo tutto ci� che mi occorre
    int            port   = 7181; 
    ServerSocket   server = null;
    Socket         socket = null;
    Manager        manager = new Manager(); // Thread che gestisce i messaggi
    
   
       
	
    //creo il server specificando la porta
    try{
      server = new ServerSocket(port);
      System.out.println("Server creato.");
      System.out.println("In attesa di connessioni...");
    }catch(IOException e){
      System.out.println("ERROR : "+e.getMessage());
    }

    manager.start();//Lancio il Thread manager
    
    //Genero il loop che  accetta le connessioni mantenendo il server sempre in ascolto
    try{ 
      do{	
	  socket = server.accept(); //per ogni connessione genero un socket 
            
          //creo un'istanza dell'utente inizializzando i suoi attributi e i thread associati
          Utente utente = new Utente();
          utente.cSocket = socket;
          UtenteListener utenteListener = new UtenteListener(utente,manager);
          UtenteSender utenteSender = new UtenteSender(utente, manager);
          utente.cUtenteListener = utenteListener;
          utente.cUtenteSender = utenteSender;
       
          //lancio i thread associati al client
          utenteListener.start();
          utenteSender.start();
        
          //aggiungo l'utente alla lista nel manager
          manager.addUtente(utente);   
          
       
          
      }while(true);	  
    }catch(IOException ioe){
	  System.out.println(ioe.getMessage());
    } 
   }  
  
}












