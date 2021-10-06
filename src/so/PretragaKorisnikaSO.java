/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.IOpstiDomenskiObjekat;
import java.util.List;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class PretragaKorisnikaSO extends  OpstaSistemskaOperacija{

    @Override
    public void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {
        
    }

    @Override
    public ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {
        List<IOpstiDomenskiObjekat> rez = DBBroker.getInstance().select(odo);
        ServerskiOdgovor so = new ServerskiOdgovor(rez, "", true);
        return so;
    }
    
}
