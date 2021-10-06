/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.IOpstiDomenskiObjekat;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public abstract class OpstaSistemskaOperacija {
    
    public synchronized ServerskiOdgovor izvrsiOperaciju(IOpstiDomenskiObjekat odo){
        ServerskiOdgovor so = new ServerskiOdgovor();
        
        try {
            DBBroker.getInstance().ucitajDrajver();
            DBBroker.getInstance().otvoriKonekciju();
            proveriPreduslove(odo); 
            so = izvrsiKonkretnuOperaciju(odo);
            DBBroker.getInstance().commit();
        } catch (Exception ex) {
            Logger.getLogger(OpstaSistemskaOperacija.class.getName()).log(Level.SEVERE, null, ex);
            try {
                DBBroker.getInstance().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OpstaSistemskaOperacija.class.getName()).log(Level.SEVERE, null, ex1);
            }
            so.setUspesno(false);
            so.setPoruka(ex.getMessage());
        }finally{
            try {
                DBBroker.getInstance().zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(OpstaSistemskaOperacija.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return so;
    }

    protected abstract void proveriPreduslove(IOpstiDomenskiObjekat odo) throws Exception;

    protected abstract ServerskiOdgovor izvrsiKonkretnuOperaciju(IOpstiDomenskiObjekat odo) throws Exception;
}
