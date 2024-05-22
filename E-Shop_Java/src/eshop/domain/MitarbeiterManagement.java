package eshop.domain;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import eshop.domain.ArtikelManagement;
import eshop.enitities.Kunde;
import eshop.enitities.Mitarbeiter;
import eshop.enitities.Person;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class MitarbeiterManagement {
    //Mitarbeiter erstellen
    //KundenListeaufrufen
    private Map<Integer, Person> mitarbeiterListe = new HashMap<>();
    private Person eingeloggterMitarbeiter;

    public MitarbeiterManagement() {
        try {
            addMitarbeiter("Johnny", "Sims", "sins.honny@gmail.com", "Sins", "12345", 1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password, int id) throws DoppelteIdException {
        if(sucheMitarbeiter(id)){
            throw new DoppelteIdException(id);
        }else{
            Person mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, password, id);
            mitarbeiterListe.put(id, mitarbeiter);
        }
    }

    public boolean loginMitarbeiter(String usernameOrEmail, String password) {
        // Überprüfung der Mitarbeiter-Anmeldeinformationen
        for (Map.Entry<Integer, Person> entry : mitarbeiterListe.entrySet()) {
            Person mitarbeiter = entry.getValue();
            if (mitarbeiter.getUsername().equals(usernameOrEmail) || mitarbeiter.getEmail().equals(usernameOrEmail)) {
                if (mitarbeiter.checkPasswort(password)) {
                    setEingeloggteMitarbeiter(mitarbeiter);
                    return true;
                }
            }
        }
        // Ungültige Anmeldeinformationen
        return false;
    }

    public  void setEingeloggteMitarbeiter(Person mitarbeiter) {
        this.eingeloggterMitarbeiter = mitarbeiter;
    }

    public Person getEingeloggterMitarbeiter() {
        return eingeloggterMitarbeiter;
    }

    public  boolean sucheMitarbeiter(int id){
        return  mitarbeiterListe.containsKey(id);
    }

    public Map<Integer, Person> gibAlleMitarbeiter() {

        return mitarbeiterListe;
    }
}
