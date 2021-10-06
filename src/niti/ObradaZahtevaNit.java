/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import domen.Korisnik;
import domen.Test;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacije;
import kontroler.Kontroler;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Mina
 */
public class ObradaZahtevaNit extends Thread {

    Socket s;
    boolean kraj = false;

    public ObradaZahtevaNit(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (!this.isInterrupted() && !kraj) {
            KlijentskiZahtev kz = primiZahtev();
            ServerskiOdgovor so = new ServerskiOdgovor();
            System.out.println("Operacija: " + kz.getOperacija());
            switch (kz.getOperacija()) {
                case Operacije.REGISTRACIJA:
                    Korisnik korisnik = (Korisnik)kz.getParametar();
                    so = Kontroler.getInstance().registracijaKorisnika(korisnik);
                    break;
                case Operacije.PRIJAVA:
                    Korisnik korisnik1 = (Korisnik) kz.getParametar();
                    so = Kontroler.getInstance().prijavaKorisnika(korisnik1);
                    break;
                case Operacije.PRETRAGA_KORISNIKA:
                    Korisnik korisnik2 = (Korisnik) kz.getParametar();
                    so = Kontroler.getInstance().pretragaKorisnika(korisnik2);
                    break;
                case Operacije.ZAKASNILI_PONOVNI_TEST_KORISNICI:
                    so = Kontroler.getInstance().zakasniliPonovniTestKorisnici();
                    break;
                case Operacije.NOVI_POZITIVNI_KORISNICI:
                    so = Kontroler.getInstance().noviPozitivniKorisnici();
                    break;
                case Operacije.SLANJE_TESTA:
                    Test test = (Test) kz.getParametar();
                    so = Kontroler.getInstance().slanjeTesta(test);
                    break;
                case Operacije.PRETRAGA_TESTOVA:
                    Test test2 = (Test)kz.getParametar();
                    so = Kontroler.getInstance().pretragaTestova(test2);
                    break;
                case Operacije.STATISTIKA:
                    so = Kontroler.getInstance().statistika();
                    break;
                default:
                    so.setUspesno(false);
                    so.setPoruka("Operacija nije podrzana");
                    break;
            }
            posaljiOdgovor(so);
        }
        System.out.println("Klijent je otisao sa servera");
    }

    private KlijentskiZahtev primiZahtev() {
        KlijentskiZahtev kz = new KlijentskiZahtev();

        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            kz = (KlijentskiZahtev) ois.readObject();
        } catch (Exception ex) {
            //Logger.getLogger(ObradaZahtevaNit.class.getName()).log(Level.SEVERE, null, ex);
            this.interrupt();
        }

        return kz;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
        } catch (IOException ex) {
            //Logger.getLogger(ObradaZahtevaNit.class.getName()).log(Level.SEVERE, null, ex);
            this.interrupt();
        }

    }

}
