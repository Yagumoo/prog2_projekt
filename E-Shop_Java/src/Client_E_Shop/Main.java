package Client_E_Shop;
import Server_E_Shop.Artikel;
import Klassen_E_Shop.ArtikelFunktion;
import Server_E_Shop.Kunde;
import Klassen_E_Shop.KundenFunktionen;
import java.util.Scanner;

import java.util.ArrayList;


public class Main{
    public static void main(String[] args) {
        /*
        ArtikelFunktion af = new ArtikelFunktion();
        //Artikel a = new Artikel();
        af.addArtikel(5, "Energy", 20);
        Artikel leer = (Artikel) af.artikelListe.get(0);
        Kunde kunde = new Kunde("Max", "Mustermann", "lol@lol.de", "lol", "lol", 1, "Musterstadt", 12 , "Musterstrasse", 123);
        kunde.printDetails();
        leer.printDetails();

         */

        KundenFunktionen kundenFunktionen = new KundenFunktionen();
        Scanner scanner = new Scanner(System.in);

        boolean loggedIn = false;
        Kunde loggedInKunde = null;

        while (!loggedIn) {
            System.out.println("Möchten Sie sich anmelden oder einen neuen Account erstellen?");
            System.out.println("1. Anmelden");
            System.out.println("2. Neuen Account erstellen");
            System.out.print("Wählen Sie eine Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    loggedInKunde = login(kundenFunktionen, scanner);
                    if (loggedInKunde != null) {
                        loggedIn = true;
                    }
                    break;
                case "2":
                    registerNewCustomer(kundenFunktionen, scanner);
                    break;
                default:
                    System.out.println("Ungültige Option. Bitte wählen Sie erneut.");
                    break;
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("Was möchten Sie tun?");
            System.out.println("1. Neuen Kunden registrieren");
            System.out.println("2. Kundenliste anzeigen");
            System.out.println("3. Abmelden");
            System.out.print("Wählen Sie eine Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    registerNewCustomer(kundenFunktionen, scanner);
                    break;
                case "2":
                    displayCustomerList(kundenFunktionen);
                    break;
                case "3":
                    loggedIn = false;
                    System.out.println("Sie haben sich erfolgreich abgemeldet.");
                    break;
                default:
                    System.out.println("Ungültige Option. Bitte wählen Sie erneut.");
                    break;
            }
        }

        scanner.close();
    }

    private static Kunde login(KundenFunktionen kundenFunktionen, Scanner scanner) {
        System.out.print("Benutzername oder E-Mail: ");
        String benutzernameOderEmail = scanner.nextLine();
        System.out.print("Passwort: ");
        String passwort = scanner.nextLine();
        boolean loggedIn = kundenFunktionen.login(benutzernameOderEmail, passwort);
        if (loggedIn) {
            System.out.println("Sie haben sich erfolgreich angemeldet.");
            return kundenFunktionen.findCustomerByUsernameOrEmail(benutzernameOderEmail);
        } else {
            System.out.println("Fehler beim Einloggen. Überprüfen Sie Ihre Login-Daten.");
            return null;
        }
    }

    private static void registerNewCustomer(KundenFunktionen kundenFunktionen, Scanner scanner) {
        System.out.print("Vorname: ");
        String vorname = scanner.nextLine();
        System.out.print("Nachname: ");
        String nachname = scanner.nextLine();
        System.out.print("E-Mail: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Passwort: ");
        String passwort = scanner.nextLine();
        System.out.print("Ort: ");
        String ort = scanner.nextLine();
        System.out.print("PLZ: ");
        int plz = Integer.parseInt(scanner.nextLine());
        System.out.print("Straße: ");
        String strasse = scanner.nextLine();
        System.out.print("Straßennummer: ");
        int strassenNummer = Integer.parseInt(scanner.nextLine());
        kundenFunktionen.registrieren(vorname, nachname, email, username, passwort, ort, plz, strasse, strassenNummer);
        System.out.println("Neuer Kunde registriert.");
    }

    private static void displayCustomerList(KundenFunktionen kundenFunktionen) {
        System.out.println("Kundenliste:");
        for (Kunde kunde : kundenFunktionen.getKundenListe()) {
            kunde.printDetails();
        }
    }
}

