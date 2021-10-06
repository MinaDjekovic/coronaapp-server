/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.IOpstiDomenskiObjekat;
import domen.Korisnik;
import domen.Pol;
import domen.Stanje;
import java.util.List;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class RegistracijaSO extends  OpstaSistemskaOperacija{

    @Override
    public void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {
Korisnik korisnik = (Korisnik)odo;
        Korisnik zaPretragu = new Korisnik(-1, null, null, korisnik.getKorisnickoIme(), null, Pol.muski, null, Stanje.pozitivan, null, true);
        List<IOpstiDomenskiObjekat> saUsername = DBBroker.getInstance().select(zaPretragu);
        if(saUsername.isEmpty() == false)
            throw new Exception("Već postoji korisnik sa unetim korisničkim imenom. ");
    }

    @Override
    public ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {
        DBBroker.getInstance().insert(odo);
        int korisnikID = DBBroker.getInstance().max(odo);
        ((Korisnik)odo).setKorisnikID(korisnikID);        
        ServerskiOdgovor so = new ServerskiOdgovor(odo, "Korisnik je uspešno registrovan. ", true);
        return so;
    }
    
}
