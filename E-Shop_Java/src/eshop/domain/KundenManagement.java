package eshop.domain;


import eshop.enitities.Artikel;
import eshop.enitities.Kunde;

import java.util.ArrayList;
import java.util.List;

public class KundenManagement {
    //Warenkorb öffnen
    private List<Kunde> kundenListe = new ArrayList<>();

    public KundenManagement() {
        addKunde("Hannah", "Lotus", "Hannah@gmail.com", "H4n", "1234", 1, "Hamburg", 27754, "Feldweg", 69);
        addKunde("Dima", "Lotik", "Dim@gmail.com", "D1m", "1234", 2, "Hamburg", 27754, "Feldweg", 69);
        addKunde("Hans", "Lotus", "Hans@gmail.com", "H4n5", "1234", 60, "Hamburg", 27554, "Feldstr", 123);

    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) {
        Kunde kunde = new Kunde(vorname, nachname, email, username, password, id, ort, plz, strasse, strassenNummer);
        kundenListe.add(kunde);
    }

    public List<Kunde> gibAlleKunden() {

        return kundenListe;
    }
}