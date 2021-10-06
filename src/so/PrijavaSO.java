/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.IOpstiDomenskiObjekat;
import domen.Korisnik;
import java.util.List;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class PrijavaSO extends  OpstaSistemskaOperacija{

    @Override
    public void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {
        
    }

    @Override
    public ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {
        List<IOpstiDomenskiObjekat> rez = DBBroker.getInstance().select(odo);
        if(rez.isEmpty()){
            throw new Exception("Ne postoji korisnik sa unetim kredencijalima. ");
        }
        ServerskiOdgovor so = new ServerskiOdgovor(rez.get(0), "Korisnik je uspešno prijavljen na sistem. ", true);
        return so;
    }
    
}
