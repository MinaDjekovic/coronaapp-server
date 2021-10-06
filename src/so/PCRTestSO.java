/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.IOpstiDomenskiObjekat;
import domen.StatusTesta;
import domen.Test;
import domen.TipTesta;
import java.util.List;
import niti.PCRTestNit;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class PCRTestSO extends OpstaSistemskaOperacija {

    @Override
    public void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception {
        Test test = (Test) odo;
        Test zaPretragu = new Test(-1, TipTesta.PCR, test.getDatum(), false, null, test.getKorisnik());
        List<IOpstiDomenskiObjekat> pcrIstogDana = DBBroker.getInstance().select(zaPretragu);
        if (pcrIstogDana.isEmpty() == false) {
            throw new Exception("Nije moguće raditi više od jednog PCR testa u istom danu. ");
        }
    }

    @Override
    public ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception {
        Test test = (Test) odo;
        test.setStatus(StatusTesta.na_cekanju);
        DBBroker.getInstance().insert(test);
        int testID = DBBroker.getInstance().max(odo);
        test.setTestID(testID);        
        ServerskiOdgovor so = new ServerskiOdgovor(test, "", true);
        new PCRTestNit(test).start();
        return so;
    }

}
