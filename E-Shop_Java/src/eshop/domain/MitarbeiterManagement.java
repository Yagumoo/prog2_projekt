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

import eshop.persistence.filePersistenceManager;

public class MitarbeiterManagement {
    //Mitarbeiter erstellen
    //KundenListeaufrufen
    private Map<Integer, Mitarbeiter> mitarbeiterListe = new HashMap<>();
    private Person eingeloggterMitarbeiter;
    private filePersistenceManager fpm = new filePersistenceManager();

    public MitarbeiterManagement() {

        try {
            mitarbeiterListe = fpm.loadMitarbeiterListe("mitarbeiter.txt");


            if(mitarbeiterListe.isEmpty()){
                addMitarbeiter("Johnny", "Sims", "sins.honny@gmail.com", "Sins", "12345", 1);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password, int id) throws DoppelteIdException {
        if(sucheMitarbeiter(id)){
            throw new DoppelteIdException(id);
        }else{
            Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, password, id);
            mitarbeiterListe.put(id, mitarbeiter);

            try{
                fpm.saveMitarbeiterListe("mitarbeiter.txt", mitarbeiterListe);
            }
            catch(Exception e){
                System.err.println("Fehler beim Speichern der Kunden-Liste:" + e.getMessage());
            }

        }
    }

    public boolean loginMitarbeiter(String usernameOrEmail, String password) {
        // Überprüfung der Mitarbeiter-Anmeldeinformationen
        for (Map.Entry<Integer, Mitarbeiter> entry : mitarbeiterListe.entrySet()) {
            Mitarbeiter mitarbeiter = entry.getValue();
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

    public Map<Integer, Mitarbeiter> gibAlleMitarbeiter() {

        return mitarbeiterListe;
    }
}
