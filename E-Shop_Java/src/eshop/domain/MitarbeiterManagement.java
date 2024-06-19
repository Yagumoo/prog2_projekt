package eshop.domain;

import eshop.domain.exceptions.*;
import eshop.enitities.Mitarbeiter;
import eshop.enitities.Person;

import java.util.Map;
import java.util.HashMap;

import eshop.persistence.filePersistenceManager;

public class MitarbeiterManagement {
    private filePersistenceManager fpm;
    //Mitarbeiter erstellen
    //KundenListeaufrufen
    private Map<Integer, Mitarbeiter> mitarbeiterListe = new HashMap<>();
    private Person eingeloggterMitarbeiter;
    //private filePersistenceManager fpm = new filePersistenceManager();

    public MitarbeiterManagement(filePersistenceManager fpm) {

        try {
            this.fpm = fpm;
            mitarbeiterListe = fpm.loadMitarbeiterListe("mitarbeiter.txt");


            if(mitarbeiterListe.isEmpty()){
                addMitarbeiter("Johnny", "Sims", "sins.honny@gmail.com", "Sins", "12345");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password) throws DoppelteIdException {
        Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, password);
        int id = mitarbeiter.getId();

        if (sucheMitarbeiter(id)) {
            throw new DoppelteIdException(id);
        } else {
            mitarbeiterListe.put(id, mitarbeiter);
        }
    }

    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException {
        try {
            // Überprüfung der Mitarbeiter-Anmeldeinformationen
            for (Map.Entry<Integer, Mitarbeiter> entry : mitarbeiterListe.entrySet()) {
                Mitarbeiter mitarbeiter = entry.getValue();
                if (mitarbeiter.getUsername().equals(usernameOrEmail) || mitarbeiter.getEmail().equals(usernameOrEmail)) {
                    if (mitarbeiter.checkPasswort(password)) {
                        setEingeloggteMitarbeiter(mitarbeiter);
                        return mitarbeiter;
                    }
                }
            }

            // Ungültige Anmeldeinformationen
            throw new LoginException();
        } catch (LoginException e) {
            // Logge die Ausnahme oder handle sie auf andere Weise
            throw e; // Falls eine Weiterverarbeitung erforderlich ist
        }
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
