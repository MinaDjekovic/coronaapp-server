/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import db.DBBroker;
import domen.Stanje;
import domen.StatusTesta;
import domen.Test;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.OpstaSistemskaOperacija;
import so.AzurirajSO;

/**
 *
 * @author Mina
 */
public class PCRTestNit extends Thread {

    private final Test test;

    public PCRTestNit(Test test) {
        this.test = test;
    }

    @Override
    public void run() {
        try {
            int tMax = 5 * 60 * 1000;
            Random rand = new Random();
            int tUkupno = Math.abs(rand.nextInt()) % (tMax + 1);
            int tFaze = tUkupno / 3;

            this.sleep(tFaze);
            test.setStatus(StatusTesta.poslato);
            OpstaSistemskaOperacija oso = new AzurirajSO();
            oso.izvrsiOperaciju(test);

            this.sleep(tFaze);
            test.setStatus(StatusTesta.u_obradi);
            oso.izvrsiOperaciju(test);

            this.sleep(tFaze);
            test.setStatus(StatusTesta.gotov);
            boolean pozitivan = rand.nextInt() % 2 == 0;
            test.setPozitivan(pozitivan);            
            oso.izvrsiOperaciju(test);            

            test.getKorisnik().setStanje(pozitivan ? Stanje.pozitivan : Stanje.negativan);            
            oso.izvrsiOperaciju(test.getKorisnik());
        } catch (Exception ex) {
            Logger.getLogger(PCRTestNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
