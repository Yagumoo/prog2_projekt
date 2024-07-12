package eshop.ui.cui;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.*;
import eshop.enitities.*;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class EShopCUI {
    /**
     *
     */

    private final E_Shop eShop;
    private Person eingeloggtePerson = null;
    Scanner scan = new Scanner(System.in);

    public EShopCUI() {
        this.eShop = new E_Shop();
    }

    public E_Shop getEShop() {
        return eShop;
    }

    private void KundeOderMitarbeiter() {

        try {
            System.out.println("Sind Sie ein Kunde 'K' oder ein Mitarbeiter 'M'? \n Programm beenden mit B \n");
            String input = getStringInput();

            if(input.equalsIgnoreCase("k")){

                System.out.println("Willkommen Kunde");
                System.out.println("___________________________");
                System.out.println("Haben Sie bereits ein Konto? (Y/N)");
                printArrow();
                String input2 = getStringInput();
                if(input2.equalsIgnoreCase("N")){
                    System.out.println("Bitte Registrieren Sie sich");
                    kundeRegistrieren();
                }else{
                    kundeLogin();
                }

            } else if(input.equalsIgnoreCase("M")) {
                System.out.println("Willkommen Arbeiter!");
                System.out.println("___________________________");

                MitarbeiterLogin();

            } else if(input.equalsIgnoreCase("B")){
                System.out.println("Programm wird beendet und Daten werden gespeichert...");
                System.out.println("Speichern der Listen beim Beenden...");
                System.exit(0); //Loest Shutdownhook aus
                //eShop.speicherAlleListen();
                System.out.println("Speichern abgeschlossen.");

            }
        } catch (FalscheEingabeException e) {
            System.err.println(e.getMessage());
        }
    }

    private void MitarbeiterSeite(){
        System.out.println("""
        
        1: Artikel ausgeben lassen\s
        2: Artikel einfügen\s
        3: Einen Artikel löschen\s
        4: Artikelbestand verändern\s
        5: Liste von Kunden ausgeben lassen\s
        6: Liste von Mitarbeitern ausgeben lassen\s
        7: Neuen Mitarbeiter registrieren\s
        8: Liste von Ereignissen Ausgeben lassen \s
        9: Zurück zum Login""");
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                listeVonArtikel();
                break;
            case 2:
                artikelHinzufugen();
                break;
            case 3:
                loescheArtikel();
                break;
            case 4:
                System.out.println("Welchen Artikelbestand willst du ändern?");
                bestandAendern();
                break;
            case 5:
                System.out.println("Registrierte Kunden ausgeben:");
                listeVonKunden();
                break;
            case  6:
                System.out.println("Angestellte Mitarbeiter ausgeben:");
                listeVonMitarbeiter();
                break;
            case 7:
                System.out.println("Bitte registrieren Sie den neuen Angestellten:");
                mitarbeiterRegistrieren();
                break;
            case 8:
                ereignisListeAusgeben();
                break;
            case 9:
                try {
                    KundeOderMitarbeiter();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
        }
    }

    private void KundenSeite(){
        System.out.println("""

                1: Artikel ausgeben lassen\s
                2: Artikel im Warenkorb ausgeben lassen\s
                3: Artikel in Warenkorb einfügen\s
                4: Menge von einem Artikel im Warenkorb aendern\s
                5: Warenkorb leeren\s
                6: Alle Artikel aus dem Warenkorb kaufen\s
                7: Bestimmten Artikel aus dem Warenkorb entfernen\s
                8: Zurueck zum Login""");
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                listeVonArtikel();
                break;
            case 2:
                System.out.println("Alle Artikel:");
                //ListeVonArtikel();
                System.out.println("________________________________________________________________");
                listeVonWarenkorb(eingeloggtePerson);
                break;
            case 3:
                System.out.println("Welchen Artikel moechten Sie hinzufuegen?:");
                artikelInWarenkorb();
                break;
            case 4:
                System.out.println("Von welchem Artikel moechten Sie die Menge aendern?:");
                bestandImWarenkorbAendern();
                break;
            case 5:
                eShop.warenkorbLeeren(eingeloggtePerson);
                System.out.println("Der Warenkorb wurde geleert!");
                System.out.println();
                break;
            case 6:
                warenkorbKaufen();
                break;
            case 7:
                artikelAusWarenkorbEntfernen();
                System.out.println("Waehlen Sie den Artikel den Sie entfernen moechten");
                break;
            case 8:
                try {
                    KundeOderMitarbeiter();
                }catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                break;
            case 9:
                break;
        }
    }

    public void listeVonArtikel() {
        Map<Integer, Artikel> artikel = eShop.gibAlleArtikel();
        artikel.forEach((arikelnummer, artikelbezeichnung)-> {
            System.out.println(artikelbezeichnung);
        });
    }

    public void listeVonMitarbeiter(){
        Map<Integer, Mitarbeiter> mitarbeiter = eShop.gibAlleMitarbeiter();
        mitarbeiter.forEach((mitarbeiterId, mitarbeiterDaten)-> {
            System.out.println(mitarbeiterDaten.toString());
        });
    }

    public void listeVonKunden(){
        Map<Integer, Kunde> kunden = eShop.gibAlleKunden();
        kunden.forEach((kundeId, kundeDaten)-> {
            System.out.println(kundeDaten.toString());
        });
    }

    public void ereignisListeAusgeben(){
        for (Ereignis ereignisListe : eShop.getEreignisListe()){
            System.out.println(ereignisListe);
        }
    }

    public void listeVonWarenkorb(Person kunde){
        try {
            System.out.println(eShop.printWarenkorbArtikel(kunde));
            System.out.println("Gesamt Preis: "+ eShop.gesamtPreis(kunde));
        } catch (IstLeerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void artikelHinzufugen() {
        try {
            int massengutAnzahl = 1;

            System.out.println("Bitte Artikelnummer einfügen:");
            printArrow();
            int artikelnummer = getIntInput();

            System.out.println("Bitte Artikelbezeichnung einfügen:");
            printArrow();
            String artikelbezeichnung = getStringInput();
            scan.nextLine();


            System.out.println("Moechten sie einen normalen Artikel oder ein Massengut hinzufügen?\nM = Massgut / N = Normal");
            printArrow();
            String artikelTyp = getStringInput();

            if (artikelTyp.equalsIgnoreCase("M")) {
                System.out.println("Bitte die anzahl an in dem der Artikel verkauft werden soll: ");
                printArrow();
                massengutAnzahl = getIntInput();
            }

            System.out.println("Bitte Artikelbestand einfügen:");
            printArrow();
            int artikelbestand = getIntInput();

            System.out.println("Bitte Artikelpreis einfügen:");
            printArrow();
            double artikelPreis = getDoubleInput();

            Artikel artikel;
            if (artikelTyp.equalsIgnoreCase("M")) {
                artikel = new MassengutArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis, massengutAnzahl);
            } else {
                artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
            }
            eShop.addArtikel(eingeloggtePerson, artikel);

        } catch (FalscheEingabeException | MinusZahlException | KeinMassengutException e){
            System.err.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void loescheArtikel(){
        try {
            System.out.println("Welchen Artikel moechtest du loeschen? Bitte gebe die Artikelnummer ein:");
            printArrow();
            int artikelnummer = getIntInput();

            eShop.loescheArtikel(eingeloggtePerson, artikelnummer);
            System.out.println("Artikel mit der Nummer " + artikelnummer + " wurde erfolgreich gelöscht.");
        } catch (FalscheEingabeException | IdNichtVorhandenException e){
            System.err.println(e.getMessage());
        }
    }

    private void mitarbeiterRegistrieren(){
        try {
            System.out.println("Bitte Vornamen einfügen:");
            printArrow();
            String vorname = getStringInput();


            System.out.println("Bitte Nachnamen einfügen:");
            printArrow();
            String nachname = getStringInput();


            System.out.println("Bitte eMail erstellen:");
            printArrow();
            String email = getStringInput();


            System.out.println("Bitte username erstellen:");
            printArrow();
            String username = getStringInput();


            System.out.println("Bitte Passwort erstellen:");
            printArrow();
            String passwort = getStringInput();


            eShop.addMitarbeiter(eingeloggtePerson, vorname, nachname, email, username, passwort);
            System.out.println("Neuer Mitarbeiter wurde registrert");

        }catch (FalscheEingabeException | UsernameExistiertException | EmailExistiertException e){
            System.err.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void kundeRegistrieren(){

        try {
            System.out.println("Bitte Vornamen einfügen:");
            printArrow();
            String vorname = getStringInput();

            System.out.println("Bitte Nachnamen einfügen:");
            printArrow();
            String nachname = getStringInput();

            System.out.println("Bitte eMail erstellen:");
            printArrow();
            String email = getStringInput();

            System.out.println("Bitte username erstellen:");
            printArrow();
            String username = getStringInput();

            System.out.println("Bitte Passwort erstellen:");
            printArrow();
            String passwort = getStringInput();

            System.out.println("Bitte Ort einfügen:");
            printArrow();
            String ort = getStringInput();

            System.out.println("Bitte PLZ einfügen:");
            printArrow();
            int plz = getIntInput();

            System.out.println("Bitte Strassennamen einfügen:");
            printArrow();
            String strasse = getStringInput();

            System.out.println("Bitte Strassennummer einfügen:");
            printArrow();
            int strassenNummer = getIntInput();

            eShop.addKunde(vorname, nachname, email, username, passwort,ort, plz, strasse, strassenNummer);
            System.out.println("Sie haben sich als Kunden registriert");

        }catch (FalscheEingabeException | UsernameExistiertException | EmailExistiertException e){
            System.err.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void kundeLogin(){
        try {
            System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
            printArrow(); String usernameOrEmail = getStringInput();
            System.out.println("Bitte geben Sie ihr Passwort ein: ");
            printArrow(); String password = getStringInput();

            eingeloggtePerson = eShop.loginKunde(usernameOrEmail, password);

            do{
                KundenSeite();
            } while (true);

        }catch (FalscheEingabeException e){
            System.err.println(e.getMessage());
        } catch (LoginException e){
            System.out.println(e.getMessage());
            kundeLogin();
        }
    }

    private void MitarbeiterLogin(){
        try {
            System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
            printArrow(); String usernameOrEmail = getStringInput();
            System.out.println("Bitte geben Sie ihr Passwort ein: ");
            printArrow(); String password = getStringInput();

            eingeloggtePerson = eShop.loginMitarbeiter(usernameOrEmail, password);

            do{
                MitarbeiterSeite();
            } while (true);

        }catch (FalscheEingabeException e){
            System.err.println(e.getMessage());
        } catch (LoginException e){
            System.err.println(e.getMessage());
            MitarbeiterLogin();
        }
    }

    private void bestandAendern() {
        try {
            // Eingabe der Artikelnummer
            System.out.println("Bitte geben Sie die Artikelnummer ein:");
            printArrow();
            int artikelnummer = getIntInput();

            // Eingabe des neuen Bestands
            System.out.println("Bitte geben Sie den neuen Artikelbestand ein:");
            printArrow();
            int neuerBestand = getIntInput();

            // Änderung des Bestands aufrufen (ohne Überprüfung auf Massengutartikel hier in der CUI)
            eShop.aendereArtikelBestand(eingeloggtePerson, artikelnummer, neuerBestand);
            System.out.println("Artikelbestand erfolgreich geändert.");

        } catch (FalscheEingabeException | IdNichtVorhandenException | KeinMassengutException | MinusZahlException e) {
            System.err.println("Fehler beim Ändern des Artikelbestands: " + e.getMessage());
        }
    }
    private void artikelInWarenkorb() {
        try {
            System.out.println("Bitte geben Sie die Artikelnummer ein:");
            printArrow();
            int artikelnummer = getIntInput();

            System.out.println("Bitte geben Sie die Menge ein:");
            printArrow();
            int menge = getIntInput();


            // Fügen Artikel dem Warenkorb hinzu
            eShop.artikelInWarenkorbHinzufügen(eingeloggtePerson, artikelnummer, menge);
            System.out.println("Artikel erfolgreich hinzugefügt.");

        } catch (FalscheEingabeException | KeinMassengutException |IdNichtVorhandenException | MinusZahlException |BestandNichtAusreichendException e){
            System.err.println(e.getMessage());
        }
    }

    private void warenkorbKaufen(){
        try {
            // Artikel im Warenkorb kaufen
            //eShop.ListeVonWarenkorb();
            Rechnung rechnung = eShop.warenkorbKaufen((Kunde) eingeloggtePerson);

            System.out.println(rechnung);

            System.out.println("Der Kauf wurde erfolgreich abgeschlossen.");
            // Warenkorb leeren
            // eShop.warenkorbLeeren();

        } catch (BestandNichtAusreichendException | IstLeerException e){
            System.err.println(e.getMessage());
        }

    }

    //Funktioniert semi
    private void artikelAusWarenkorbEntfernen(){
        try {
            System.out.println("Bitte geben Sie die Artikelnummer ein:");
            printArrow();
            int artikelnummer = getIntInput();

            Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);

            eShop.artikelImWarenkorbEntfernen(eingeloggtePerson, artikel);

        }catch (FalscheEingabeException | IdNichtVorhandenException | ArtikelExisitiertNichtException e){
            System.err.println(e.getMessage());
        }
    }

    //Geht noch nicht
    public void bestandImWarenkorbAendern() {
        try {
            System.out.println("Bitte Artikelnummer einfügen:");
            int artikelnummer = getIntInput();

            System.out.println("Bitte neuen Artikelbestand einfügen:");
            int neuerBestand = getIntInput();

            Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);
            eShop.bestandImWarenkorbAendern(eingeloggtePerson, artikel, neuerBestand);
            System.out.println("Artikel wurde erfolgreich geaendert!");
        }catch (FalscheEingabeException  | IdNichtVorhandenException | BestandNichtAusreichendException | KeinMassengutException | MinusZahlException |IstLeerException e){
            System.err.println(e.getMessage());
        }
    }

    private int getIntInput() throws FalscheEingabeException {
        try {
            return scan.nextInt();
        } catch (InputMismatchException e) {
            String gegebeneEingabe = scan.next(); // Eingabe löschen
            throw new FalscheEingabeException("Falsche Eingabe", "int", gegebeneEingabe);
        }
    }

    private String getStringInput() throws FalscheEingabeException {
        try {
            return scan.next();
        } catch (InputMismatchException e) {
            String gegebeneEingabe = scan.nextLine(); // Eingabe löschen
            throw new FalscheEingabeException("Falsche Eingabe", "String", gegebeneEingabe);
        }
    }

    private double getDoubleInput() throws FalscheEingabeException {
        try {
            return scan.nextDouble();
        } catch (InputMismatchException e) {
            String gegebeneEingabe = scan.next(); // Eingabe löschen
            throw new FalscheEingabeException("Falsche Eingabe", "double", gegebeneEingabe);
        }
    }

    private void printArrow() {
        System.out.print("--> ");
    }

    public static void main(String[] args) {
        EShopCUI eShopCUI = new EShopCUI();

        do {
            //eShopCUI.start();
            try {
                eShopCUI.KundeOderMitarbeiter();
            }catch (Exception e){
                e.getStackTrace();
            }
        } while (true);

    }
}