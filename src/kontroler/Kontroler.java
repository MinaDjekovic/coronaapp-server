/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;


import domen.IOpstiDomenskiObjekat;
import domen.Korisnik;
import domen.Statistika;
import domen.Test;
import domen.TipTesta;
import so.BrziTestSO;
import so.NoviPozitivniKorisniciSO;
import so.OpstaSistemskaOperacija;
import so.PCRTestSO;
import so.PretragaKorisnikaSO;
import so.PretragaTestovaSO;
import so.PrijavaSO;
import so.RegistracijaSO;
import so.StatistikaSO;
import so.TestSamoproceneSO;
import so.ZakasniliPonovniTestKorisniciSO;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class Kontroler {
    private static Kontroler instance;

    private Kontroler() {
    }

    public static Kontroler getInstance() {
        if(instance == null)
            instance = new Kontroler();
        return instance;
    }

    public ServerskiOdgovor registracijaKorisnika(IOpstiDomenskiObjekat odo ) {
        OpstaSistemskaOperacija oso = new RegistracijaSO();
        return oso.izvrsiOperaciju(odo);
    }

    public ServerskiOdgovor prijavaKorisnika(IOpstiDomenskiObjekat odo) {
        OpstaSistemskaOperacija oso = new PrijavaSO();
        return oso.izvrsiOperaciju(odo);
    }

    public ServerskiOdgovor pretragaKorisnika(IOpstiDomenskiObjekat odo) {
        OpstaSistemskaOperacija oso = new PretragaKorisnikaSO();
        return oso.izvrsiOperaciju(odo);
    }

    public ServerskiOdgovor zakasniliPonovniTestKorisnici() {
        IOpstiDomenskiObjekat odo = new Korisnik();
        OpstaSistemskaOperacija oso = new ZakasniliPonovniTestKorisniciSO();
        return oso.izvrsiOperaciju(odo);
    }

    public ServerskiOdgovor noviPozitivniKorisnici() {
        IOpstiDomenskiObjekat odo = new Korisnik();
        OpstaSistemskaOperacija oso = new NoviPozitivniKorisniciSO();
        return oso.izvrsiOperaciju(odo);
    }

    public ServerskiOdgovor slanjeTesta(IOpstiDomenskiObjekat odo) {
        Test test = (Test)odo;
        OpstaSistemskaOperacija oso = null;
        switch(test.getTip()){            
            case samoprocena:
                oso = new TestSamoproceneSO();
            break;
            case brzi:
                oso = new BrziTestSO();
                break;
            case PCR:
                oso = new PCRTestSO();
                break;            
        }
        if(oso == null){
            return new ServerskiOdgovor(null, "Tip testa nije podrzan", false);
        }
        return oso.izvrsiOperaciju(odo);
    }

    public ServerskiOdgovor pretragaTestova(IOpstiDomenskiObjekat odo) {
        OpstaSistemskaOperacija oso = new PretragaTestovaSO();
        return oso.izvrsiOperaciju(odo);
    }

    public ServerskiOdgovor statistika() {
        OpstaSistemskaOperacija oso= new StatistikaSO();
        return oso.izvrsiOperaciju(new Statistika());
    }

    
    
    
    
    
}
