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
import domen.TipTesta;
import java.util.List;
import java.util.Random;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class BrziTestSO extends OpstaSistemskaOperacija {

    @Override
    protected void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {
        Test test = (Test) odo;
        Test zaPretragu = new Test(-1, TipTesta.brzi, test.getDatum(), false, null, test.getKorisnik());
        List<IOpstiDomenskiObjekat> brziIstogDana = DBBroker.getInstance().select(zaPretragu);
        if (brziIstogDana.isEmpty() == false) {
            throw new Exception("Nije moguće raditi više od jednog brzog testa u istom danu. ");
        }
    }

    @Override
    protected ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {
        Test test = (Test)odo;
        boolean pozitivan = new Random().nextInt()%2==0;
        test.setPozitivan(pozitivan);
        test.setStatus(StatusTesta.gotov);
        test.getKorisnik().setStanje(pozitivan ? Stanje.pozitivan : Stanje.negativan);
        DBBroker.getInstance().insert(test);
        int testID = DBBroker.getInstance().max(odo);
        test.setTestID(testID);        
        DBBroker.getInstance().update(test.getKorisnik());
        ServerskiOdgovor so = new ServerskiOdgovor(test, "", true);
        return so;
    }

}
