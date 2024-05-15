package eshop.domain;


import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Person;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class KundenManagement {
    //Warenkorb öffnen
    private Map<Integer, Kunde> kundenListe = new HashMap<>();
    private Kunde eingeloggterKunde;

    public KundenManagement() {
        try{
            addKunde("Hannah", "Lotus", "Hannah@gmail.com", "H4n", "1234", 1, "Hamburg", 27754, "Feldweg", 69);
            addKunde("Dima", "Lotik", "Dima@gmail.com", "D1m", "1234", 2, "Hamburg", 27754, "Feldweg", 69);
            addKunde("Hans", "Lotus", "Hans@gmail.com", "H4n5", "1234", 60, "Hamburg", 27554, "Feldstr", 123);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) throws DoppelteIdException {
        if(sucheKunde(id)){
            throw new DoppelteIdException(id);
        }else{
            Kunde kunde = new Kunde(vorname, nachname, email, username, password, id, ort, plz, strasse, strassenNummer);
            kundenListe.put(id, kunde);
        }

    }

    public Kunde loginkunde(String usernameOrEmail, String password) {
        // Überprüfung der Mitarbeiter-Anmeldeinformationen
        for (Map.Entry<Integer, Kunde> entry : kundenListe.entrySet()) {
            Kunde kunde = entry.getValue();
            if (kunde.getUsername().equalsIgnoreCase(usernameOrEmail) || kunde.getEmail().equalsIgnoreCase(usernameOrEmail)) {
                if (kunde.checkPasswort(password)) {
                    // Mitarbeiter erfolgreich angemeldet
                    setEingeloggterKunde(kunde);
                    return kunde;
                }
            }
        }
        // Ungültige Anmeldeinformationen
        return null;
    }

    public  void setEingeloggterKunde(Kunde kunde) {
        this.eingeloggterKunde = kunde;
    }

    public Kunde getEingeloggterKunde() {
        return eingeloggterKunde;
    }

    public Map<Integer, Kunde> gibAlleKunden() {

        return kundenListe;
    }

    public  boolean sucheKunde(int id){
        return  kundenListe.containsKey(id);
    }

    public Kunde gibKundePerId(int id) {
        return kundenListe.get(id);
    }
}