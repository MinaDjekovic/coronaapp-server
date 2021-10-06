/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.IOpstiDomenskiObjekat;
import domen.Stanje;
import domen.StatusTesta;
import domen.Test;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.PCRTestNit;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class AzurirajSO extends OpstaSistemskaOperacija {

    @Override
    protected void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {

    }

    @Override
    protected ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {
        DBBroker.getInstance().update(odo);
        return null;        
    }

}
