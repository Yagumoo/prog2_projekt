package eshop.enitities;
import eshop.domain.EreignisManagement;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Ereignis {

    public enum EreignisTyp {
        NEU, KAUF, ERHOEHUNG, REDUZIERUNG
    }

    private Date datum;
    private String artikelbezeichnung; // Artikel
    private int anzahl;
    private Person kundeOderMitarbeiter;
    private EreignisTyp typ;
//    private Person betroffenePerson;

    public Ereignis(Date datum, String artikelbezeichnung, int anzahl, Person kundeOderMitarbeiter, EreignisTyp typ){
        this.datum = datum;
        this.artikelbezeichnung = artikelbezeichnung;
        this. anzahl = anzahl;
        this.kundeOderMitarbeiter = kundeOderMitarbeiter;
        this.typ = typ;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getArtikel() {
        return artikelbezeichnung;
    }

    public void setArtikel(Artikel artikelbezeichnung) {
        this.artikelbezeichnung = artikelbezeichnung.getArtikelbezeichnung();
    }

//    public void setBetroffenePerson(Person betroffenePerson){
//        this.betroffenePerson = betroffenePerson;
//    }

    public Person getKundeOderMitarbeiter() {
        return kundeOderMitarbeiter;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public EreignisTyp getTyp() {
        return typ;
    }


    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public String simpleDatum() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(datum);
    }

    @Override
    public String toString() {
        return "Datum: " + simpleDatum() +
                ", Artikel: " + artikelbezeichnung +
                ", Anzahl: " + anzahl +
                ", Person: " + kundeOderMitarbeiter +
                ", Typ: " + typ;
    }

    public Ereignis ereignisSpeicher(Date datum, String artikelbezeichnung, int anzahl, String nachname, int id){
        Ereignis ereignis = new Ereignis(datum, artikelbezeichnung, anzahl,kundeOderMitarbeiter,typ);
        return ereignis;

    }
}