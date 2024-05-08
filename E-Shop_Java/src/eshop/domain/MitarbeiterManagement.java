package eshop.domain;

import eshop.enitities.Artikel;
import eshop.domain.ArtikelManagement;
import eshop.enitities.Person;

import java.util.ArrayList;
import java.util.List;

public class MitarbeiterManagement {
    //Mitarbeiter erstellen
    //KundenListeaufrufen
    private List<Person> mitarbeiterListe = new ArrayList<>();

    public MitarbeiterManagement() {
       addMitarbeiter("Johnny", "Sims", "sins.honny@gmail.com", "Sins", "12345", 1);

    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password, int id) {
        Person mitarbeiter = new Person(vorname, nachname, email, username, password, id);
        mitarbeiterListe.add(mitarbeiter);
    }

    public boolean loginMitarbeiter(String usernameOrEmail, String password) {
        // Überprüfung der Mitarbeiter-Anmeldeinformationen
        for (Person mitarbeiter : mitarbeiterListe) {
            if (mitarbeiter.getUsername().equalsIgnoreCase(usernameOrEmail) || mitarbeiter.getEmail().equalsIgnoreCase(usernameOrEmail)) {
                if (mitarbeiter.checkPasswort(password)) {
                    // Mitarbeiter erfolgreich angemeldet
                    return true;
                }
            }
        }
        // Ungültige Anmeldeinformationen
        return false;
    }





    //public Person getMitarbeiter(int id) {
    //     return mitarbeiterListe.get(id);
    // }
    public List<Person> gibAlleMitarbeiter() {

        return mitarbeiterListe;
    }
}
