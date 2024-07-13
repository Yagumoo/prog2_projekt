package eshop.server.domain;

import eshop.common.enitities.Mitarbeiter;
import eshop.common.enitities.Person;

import java.util.Map;
import java.util.HashMap;

import eshop.common.exceptions.*;
import eshop.server.persistence.filePersistenceManager;

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
            mitarbeiterListe = fpm.ladeMitarbeiterListe("mitarbeiter.txt");


            if(mitarbeiterListe.isEmpty()){
                addMitarbeiter("Johnny", "Sims", "sins.honny@gmail.com", "Sins", "12345");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        // Überprüfung, ob der Username bereits existiert
        for (Mitarbeiter mitarbeiter : mitarbeiterListe.values()) {
            if (mitarbeiter.getUsername().equals(username)) {
                throw new UsernameExistiertException(username);
            }
        }

        for(Mitarbeiter mitarbeiter : mitarbeiterListe.values()){
            if(mitarbeiter.getEmail().equals(email)){
                throw new EmailExistiertException(email);
            }
        }

        Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, password);
        int id = mitarbeiter.getId();

        if (sucheMitarbeiter(id)) {
            throw new DoppelteIdException(id);
        } else {
            mitarbeiterListe.put(id, mitarbeiter);
        }
    }

    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException {

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

        throw new LoginException();

    }

    public  void setEingeloggteMitarbeiter(Person mitarbeiter) {
        this.eingeloggterMitarbeiter = mitarbeiter;
    }

    public Mitarbeiter gibMitarbeiterPerID(int id) throws IdNichtVorhandenException{
        if(!mitarbeiterListe.containsKey(id)){
            throw new IdNichtVorhandenException(id);
        } else {
            return mitarbeiterListe.get(id);
        }

    }

    public boolean sucheMitarbeiter(int id){
        return  mitarbeiterListe.containsKey(id);
    }

    public Map<Integer, Mitarbeiter> gibAlleMitarbeiter() {
        return mitarbeiterListe;
    }
}