package com.wizards.exChat;

import java.net.*;
import java.io.*;
import java.util.*;
 
/*
*Classe che invia messaggi al server
*@author Luca Colangelo
*/


public class Sender extends Thread{

   private BufferedWriter bw;
   
   //Costruttore che prende il BufferedWriter dal client
   public Sender(BufferedWriter bw){
     this.bw = bw;
    }
 
    /*metodo run che legge dall'input e invia al server*/
    public void run(){
     try {
       BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
       while (true) {
         String m = in.readLine();
         bw.write(m + "\n");
         bw.flush();
       }
     }catch(IOException ioe) {
       System.out.println("Error : "+ ioe.getMessage());
     }
    }

}

