package eshop.enitities;
import java.util.Date;

public class Ereignis {
    private Date datum;
    private String artikel;
    private int anzahl;
    private String beteiligtePerson;

    public Ereignis(Date datum, String artikel, int anzahl, String beteiligtePerson){
        this.datum = datum;
        this.artikel = artikel;
        this. anzahl = anzahl;
        this. beteiligtePerson = beteiligtePerson;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public String getBeteiligtePerson() {
        return beteiligtePerson;
    }

    public void setBeteiligtePerson(String beteiligtePerson) {
        this.beteiligtePerson = beteiligtePerson;
    }

    @Override
    public String toString() {
        return "Datum: " + datum +
                ", Artikel: " + artikel +
                ", Anzahl: " + anzahl +
                ", Beteiligte Person: " + beteiligtePerson;
    }
}

/*
public void einlagern(Artikel artikel, int anzahl, Person mitarbeiter) {
        // Prüfen, ob der Artikel bereits in der Liste vorhanden ist
        if (artikelListe.containsKey(artikel.getArtikelnummer())) {
            // Artikel vorhanden, Bestand erhöhen
            Artikel vorhandenerArtikel = artikelListe.get(artikel.getArtikelnummer());
            vorhandenerArtikel.setArtikelbestand(vorhandenerArtikel.getArtikelbestand() + anzahl);
        } else {
            // Artikel nicht vorhanden, neuen Artikel hinzufügen
            artikel.setArtikelbestand(anzahl);
            artikelListe.put(artikel.getArtikelnummer(), artikel);
        }

        // Ereignis festhalten
        LagerEreignis ereignis = new LagerEreignis(new Date(), artikel, anzahl, mitarbeiter);
        // Hier könnten Sie das Ereignis speichern, z. B. in einer Liste oder Datenbank
    }

    public void auslagern(Artikel artikel, int anzahl, Person mitarbeiter) {
        // Prüfen, ob genügend Bestand vorhanden ist
        if (artikelListe.containsKey(artikel.getArtikelnummer())) {
            Artikel vorhandenerArtikel = artikelListe.get(artikel.getArtikelnummer());
            int bestand = vorhandenerArtikel.getArtikelbestand();
            if (bestand >= anzahl) {
                // Ausreichend Bestand vorhanden, Bestand reduzieren
                vorhandenerArtikel.setArtikelbestand(bestand - anzahl);
            } else {
                // Nicht genügend Bestand vorhanden
                System.out.println("Nicht genügend Bestand für " + artikel.getArtikelbezeichnung() + " vorhanden.");
                return;
            }
        } else {
            // Artikel nicht gefunden
            System.out.println("Artikel " + artikel.getArtikelbezeichnung() + " nicht gefunden.");
            return;
        }

        // Ereignis festhalten
        LagerEreignis ereignis = new LagerEreignis(new Date(), artikel, anzahl, mitarbeiter);
        // Hier könnten Sie das Ereignis speichern, z. B. in einer Liste oder Datenbank
    }



public class EShopCUI {
    // ...

    private void artikelHinzufugen(Scanner scan) {
        // Artikel hinzufügen Logik
        // ...

        // Einlagerungsereignis festhalten
        artikelManagement.einlagern(artikel, anzahl, beteiligtePerson);
    }

    private void artikelAusWarenkorbEntfernen(Scanner scan) {
        // Artikel aus Warenkorb entfernen Logik
        // ...

        // Auslagerungsereignis festhalten
        artikelManagement.auslagern(artikel, anzahl, beteiligtePerson);
    }

    // ...
}

 */
