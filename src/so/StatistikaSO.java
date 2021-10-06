/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.IOpstiDomenskiObjekat;
import domen.Statistika;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class StatistikaSO extends  OpstaSistemskaOperacija{

    @Override
    public void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {
        
    }

    @Override
    public ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {
        Statistika stat = DBBroker.getInstance().statistika();
        return new ServerskiOdgovor(stat, "", true);
    }
    
}
