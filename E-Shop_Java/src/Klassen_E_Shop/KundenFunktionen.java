package Klassen_E_Shop;

import Server_E_Shop.Kunde;
import java.util.ArrayList;
import java.util.Scanner;


public class KundenFunktionen {
    private ArrayList<Kunde> kundenListe;

    public KundenFunktionen() {
        this.kundenListe = new ArrayList<>();
    }

    // Methode zum Speichern von Kundeninformationen
    public void kundenInformationenSpeichern(Kunde kunde) {
        kundenListe.add(kunde);
    }

    // Methode zum Einloggen mit Username oder Email + Passwort
    // Methode zum Einloggen mit Username oder Email + Passwort
    public boolean login(String benutzernameOderEmail, String passwort) {
        for (Kunde kundeElement : kundenListe) {
            if ((kundeElement.getEmail().equals(benutzernameOderEmail) || kundeElement.getUsername().equals(benutzernameOderEmail))
                    && kundeElement.getPassword().equals(passwort)) {
                System.out.println("Erfolgreich eingeloggt als: " + kundeElement.getVorname() + " " + kundeElement.getNachname());
                return true;
            }
        }
        System.out.println("Fehler beim Einloggen. Überprüfen Sie Ihre Login-Daten.");
        return false;
    }

    // Methode zum Registrieren eines neuen Kunden
    public void registrieren(String vorname, String nachname, String email, String username, String passwort, String ort, int plz, String strasse, int strassenNummer) {
        int id = kundenListe.size() + 1; // Automatische ID-Erhöhung
        Kunde neuerKunde = new Kunde(vorname, nachname, email, username, passwort, id, ort, plz, strasse, strassenNummer);
        kundenListe.add(neuerKunde);
        System.out.println("Neuer Kunde registriert: " + vorname + " " + nachname);
    }

    // Methode zum Anmelden mit gültiger Email
    public void anmeldenMitEmail(String email, String passwort) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getEmail().equals(email) && kunde.getPassword().equals(passwort)) {
                System.out.println("Erfolgreich angemeldet als: " + kunde.getVorname() + " " + kunde.getNachname());
                return;
            }
        }
        System.out.println("Fehler beim Anmelden. Überprüfen Sie Ihre Email und Passwort.");
    }

    // Methode zum Erstellen eines Passworts
    public String passwortErstellen() {
        // Hier kann Logik zur Generierung eines Passworts implementiert werden
        return "NeuesPasswort123"; // Beispielhaftes generiertes Passwort
    }

    // Methode zum Erstellen eines Benutzernamens
    public String benutzernameErstellen(String vorname, String nachname) {
        // Hier kann Logik zur Generierung eines Benutzernamens implementiert werden
        return (vorname.toLowerCase() + "." + nachname.toLowerCase()).replace(" ", ""); // Beispielhafte Benutzername-Erstellung
    }

    // Methode zum Aufrufen von Kundendaten
    public void kundenDatenAufrufen(int kundenID) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getId() == kundenID) {
                kunde.printDetails();
                return;
            }
        }
        System.out.println("Kunde mit ID " + kundenID + " nicht gefunden.");
    }

    // Methode zum Speichern von Kundendaten (Email, Username, Name, Adresse, ID)
    public void kundenDatenSpeichern(String email, String username, String vorname, String nachname, String ort, int plz, String strasse, int strassenNummer) {
        int id = kundenListe.size() + 1; // Automatische ID-Erhöhung
        Kunde neuerKunde = new Kunde(vorname, nachname, email, username, "", id, ort, plz, strasse, strassenNummer);
        kundenListe.add(neuerKunde);
        System.out.println("Kundendaten gespeichert für: " + vorname + " " + nachname);
    }

    // Getter für die Kundenliste
    public ArrayList<Kunde> getKundenListe() {
        return kundenListe;
    }

    public Kunde findCustomerByUsernameOrEmail(String usernameOrEmail) {
        for (Kunde kunde : kundenListe) {
            if (kunde.getUsername().equals(usernameOrEmail) || kunde.getEmail().equals(usernameOrEmail)) {
                return kunde;
            }
        }
        return null; // Wenn kein Kunde mit dem angegebenen Benutzernamen oder der angegebenen E-Mail gefunden wurde
    }


}







