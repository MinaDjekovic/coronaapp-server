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
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class TestSamoproceneSO extends OpstaSistemskaOperacija {

    @Override
    public void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {
        Test test = (Test) odo;
        Test zaPretragu = new Test(-1, TipTesta.samoprocena, test.getDatum(), false, null, test.getKorisnik());
        List<IOpstiDomenskiObjekat> spIstogDana = DBBroker.getInstance().select(zaPretragu);
        if (spIstogDana.isEmpty() == false) {
            throw new Exception("Nije moguće raditi više od jednog testa samoprocene u istom danu. ");
        }
    }

    @Override
    public ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {        
        Test test = (Test)odo;
        test.setStatus(StatusTesta.gotov);
        DBBroker.getInstance().insert(test);
        int testID = DBBroker.getInstance().max(odo);
        test.setTestID(testID);        
        if(test.isPozitivan() == false)
            test.getKorisnik().setStanje(test.getKorisnik().getStanje()==Stanje.pod_nadzorom?Stanje.negativan:Stanje.pod_nadzorom);
        DBBroker.getInstance().update(test.getKorisnik());        
        ServerskiOdgovor so = new ServerskiOdgovor(test, "", true);
        return so;
    }

}
