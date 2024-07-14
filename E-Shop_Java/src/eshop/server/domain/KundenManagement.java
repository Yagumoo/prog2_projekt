package eshop.server.domain;


import eshop.common.enitities.Mitarbeiter;
import eshop.common.exceptions.*;
import eshop.common.enitities.Kunde;

import java.util.Map;
import java.util.HashMap;

import eshop.server.persistence.filePersistenceManager;

public class KundenManagement {
    //Warenkorb öffnen
    private Map<Integer, Kunde> kundenListe = new HashMap<>();
    private Kunde eingeloggterKunde;
    private filePersistenceManager fpm;// = new filePersistenceManager();

    public KundenManagement(filePersistenceManager fpm) {
        try{
            this.fpm = fpm;

            kundenListe = fpm.ladeKundenListe("kunden.txt");
            if(kundenListe.isEmpty()){
                addKunde("Hannah", "Lotus", "Hannah@gmail.com", "H4n", "1234", "Hamburg", 27754, "Feldweg", 69);
                addKunde("Dima", "Lotik", "Dima@gmail.com", "D1m", "1234", "Hamburg", 27754, "Feldweg", 69);
                addKunde("Hans", "Lotus", "Hans@gmail.com", "H4n5", "1234", "Hamburg", 27554, "Feldstr", 123);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {

        // Überprüfung, ob der Username bereits existiert
        for (Kunde kunde : kundenListe.values()) {
            if (kunde.getUsername().equals(username)) {
                throw new UsernameExistiertException(username);
            }
        }

        for(Kunde kunde : kundenListe.values()){
            if(kunde.getEmail().equals(email)){
                throw new EmailExistiertException(email);
            }
        }

        Kunde kunde = new Kunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
        int id = kunde.getId();

        if (sucheKunde(id)) {
            throw new DoppelteIdException(id);
        } else {
            kundenListe.put(id, kunde);
        }
    }

    public Kunde loginkunde(String usernameOrEmail, String password) throws LoginException {
        // Überprüfung der Mitarbeiter-Anmeldeinformationen
        for (Map.Entry<Integer, Kunde> entry : kundenListe.entrySet()) {
            Kunde kunde = entry.getValue();
            if (kunde.getUsername().equals(usernameOrEmail) || kunde.getEmail().equals(usernameOrEmail)) {
                if (kunde.checkPasswort(password)) {
                    // Kunde erfolgreich angemeldet
                    setEingeloggterKunde(kunde);
                    return kunde;
                }
            }
        }

        // Ungültige Anmeldeinformationen
        throw new LoginException();

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

    public Kunde sucheKundePerId(int id) throws IdNichtVorhandenException{
        if(!kundenListe.containsKey(id)){
            throw new IdNichtVorhandenException(id);
        } else {
            return kundenListe.get(id);
        }
    }

    public boolean sucheKunde(int id){
        return  kundenListe.containsKey(id);
    }

}