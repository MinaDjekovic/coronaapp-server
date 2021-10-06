package niti;

import forma.ServerskaForma;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mina
 */
public class PokreniServerNit extends Thread{
    ServerskaForma sf;

    public PokreniServerNit(ServerskaForma sf) {
        this.sf = sf;
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            sf.serverJePokrenut();
            while (true) {                
                Socket s = ss.accept();
                System.out.println("Prihvatio klijenta");
                ObradaZahtevaNit ozn = new ObradaZahtevaNit(s);
                ozn.start();
            }
        } catch (IOException ex) {
            sf.serverNijePokrenut();
            Logger.getLogger(PokreniServerNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
