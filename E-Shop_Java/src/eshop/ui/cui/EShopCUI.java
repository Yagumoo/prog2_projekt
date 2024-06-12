package eshop.ui.cui;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.*;
import eshop.enitities.*;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.List;


public class EShopCUI {
    /**
     *
     */

    private E_Shop eShop;

    public EShopCUI() {
        this.eShop = new E_Shop();
    }

    public E_Shop getEShop() {
        return eShop;
    }

    private Person eingeloggtePerson = null;
    Scanner scan = new Scanner(System.in);

    private void KundeOderMitarbeiter() {

        try {
            System.out.println("Sind Sie ein Kunde 'K' oder ein Mitarbeiter 'M'? Programm beenden mit B");
            String input = getStringInput("String");

            if(input.equalsIgnoreCase("k")){

                System.out.println("Willkommen Kunde");
                System.out.println("___________________________");
                System.out.println("Haben Sie bereits ein Konto? (Y/N)");
                printArrow();
                String input2 = getStringInput("String");
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
                System.exit(0); //Loest Shutdownhook aus
            }
        } catch (FalscheEingabeException e) {
            System.err.println(e);
        }
    }

    private void MitarbeiterSeite(){
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel einfügen \n3: Einen Artikel löschen \n4: Artikelbestand verändern \n5: Liste von Kunden ausgeben lassen \n6: Liste von Mitarbeitern ausgeben lassen \n7: Neuen Mitarbeiter registrieren \n8: Liste von Ereignissen Ausgeben lassen  \n9: Zurueck zum Login");
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                eShop.ListeVonArtikel();
                break;
            case 2:
                artikelHinzufugen();
                break;
            case 3:
                loescheArtikel();
                break;
            case 4:
                System.out.println("Welchen Artikelbestand willst du ändern?");
                bestandAeundern();
                break;
            case 5:
                System.out.println("Registrierte Kunden ausgeben:");
                eShop.ListeVonKunden();
                break;
            case  6:
                System.out.println("Angestellte Mitarbeiter ausgeben:");
                eShop.ListeVonMitarbeiter();
                break;
            case 7:
                System.out.println("Bitte registrieren Sie den neuen Angestellten:");
                mitarbeiterRegistrieren();
                break;
            case 8:
                eShop.EreignisListeAusgeben();
                break;
            case 9:
                try {
                    KundeOderMitarbeiter();
                }catch (Exception e) {
                    e.getMessage();
                }
                break;
        }
    }

    private void KundenSeite(){
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel im Warenkorb ausgeben lassen \n3: Artikel in Warenkorb einfügen \n4: Menge von einem Artikel im Warenkorb aendern \n5: Warenkorb leeren \n6: Alle Artikel aus dem Warenkorb kaufen \n7: Bestimmten Artikel aus dem Warenkorb entfernen \n8: Zurueck zum Login");
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                eShop.ListeVonArtikel();
                break;
            case 2:
                System.out.println("Alle Artikel:");
                //ListeVonArtikel();
                System.out.println("________________________________________________________________");
                eShop.ListeVonWarenkorb();
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
                eShop.warenkorbLeeren();
                System.out.println("Der Warenkorb wurde geleert!");
                System.out.println();
                break;
            case 6:
                System.out.println("Moechten Sie alle Artikel im Warenkorb kaufen?");
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
                    e.getMessage();
                }
                break;
            case 9:
                break;
        }
    }
    private void artikelHinzufugen() {
        try {
            int massengutAnzahl = 1;

            System.out.println("Bitte Artikelnummer einfügen:");
            printArrow();
            int artikelnummer = getIntInput("int");

            System.out.println("Bitte Artikelbezeichnung einfügen:");
            printArrow();
            String artikelbezeichnung = getStringInput("String");
            scan.nextLine();


            System.out.println("Moechten sie einen normalen Artikel oder ein Massengut hinzufügen?\nM = Massgut / N = Normal");
            printArrow();
            String artikelTyp = getStringInput("String");

            if (artikelTyp.equalsIgnoreCase("M")) {
                System.out.println("Bitte die anzahl an in dem der Artikel verkauft werden soll: ");
                printArrow();
                massengutAnzahl = getIntInput("int");
            }

            System.out.println("Bitte Artikelbestand einfügen:");
            printArrow();
            int artikelbestand = getIntInput("int");

            if(artikelTyp.equalsIgnoreCase("M") && artikelbestand % massengutAnzahl != 0){
                throw new KeinMassengutException(massengutAnzahl);
            }

            System.out.println("Bitte Artikelpreis einfügen:");
            printArrow();
            double artikelPreis = getDoubleInput("double");

            Artikel artikel;
            if (artikelTyp.equalsIgnoreCase("M")) {
                artikel = new MassengutArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis, massengutAnzahl);
            } else {
                artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
            }
            eShop.addArtikel(artikel);

        } catch (FalscheEingabeException e){
            System.err.println(e);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void loescheArtikel(){
        try {
            System.out.println("Welchen Artikel moechtest du loeschen? Bitte gebe die Artikelnummer ein:");
            printArrow();
            int artikelnummer = getIntInput("int");

            eShop.loescheArtikel(artikelnummer);
            System.out.println("Artikel mit der Nummer " + artikelnummer + " wurde erfolgreich gelöscht.");
        } catch (FalscheEingabeException | IdNichtVorhandenException e){
            System.err.println(e.getMessage());
        }
    }

    private void mitarbeiterRegistrieren(){
        try {
            System.out.println("Bitte Vornamen einfügen:");
            printArrow();
            String vorname = getStringInput("String");


            System.out.println("Bitte Nachnamen einfügen:");
            printArrow();
            String nachname = getStringInput("String");


            System.out.println("Bitte eMail erstellen:");
            printArrow();
            String email = getStringInput("String");


            System.out.println("Bitte username erstellen:");
            printArrow();
            String username = getStringInput("String");


            System.out.println("Bitte Passwort erstellen:");
            printArrow();
            String passwort = getStringInput("String");


            eShop.addMitarbeiter(vorname, nachname, email, username, passwort);
            System.out.println("Neuer Mitarbeiter wurde registrert");

        }catch (FalscheEingabeException e){
            System.err.println(e);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void kundeRegistrieren(){

        try {
            System.out.println("Bitte Vornamen einfügen:");
            printArrow();
            String vorname = getStringInput("String");

            System.out.println("Bitte Nachnamen einfügen:");
            printArrow();
            String nachname = getStringInput("String");

            System.out.println("Bitte eMail erstellen:");
            printArrow();
            String email = getStringInput("String");

            System.out.println("Bitte username erstellen:");
            printArrow();
            String username = getStringInput("String");

            System.out.println("Bitte Passwort erstellen:");
            printArrow();
            String passwort = getStringInput("String");

            System.out.println("Bitte Ort einfügen:");
            printArrow();
            String ort = getStringInput("String");

            System.out.println("Bitte PLZ einfügen:");
            printArrow();
            int plz = getIntInput("int");

            System.out.println("Bitte Strassennamen einfügen:");
            printArrow();
            String strasse = getStringInput("String");

            System.out.println("Bitte Strassennummer einfügen:");
            printArrow();
            int strassenNummer = getIntInput("int");

            eShop.addKunde(vorname, nachname, email, username, passwort,ort, plz, strasse, strassenNummer);
            System.out.println("Sie haben sich als Kunden registriert");
            KundenSeite();

        }catch (FalscheEingabeException e){
            System.err.println(e);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void kundeLogin(){
        try {
            System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
            printArrow(); String usernameOrEmail = getStringInput("String");
            System.out.println("Bitte geben Sie ihr Passwort ein: ");
            printArrow(); String password = getStringInput("String");

            eingeloggtePerson = eShop.loginKunde(usernameOrEmail, password);

            do{
                KundenSeite();
            } while (true);

        }catch (FalscheEingabeException e){
            System.err.println(e);
        } catch (LoginException e){
            System.out.println(e.getMessage());
            kundeLogin();
        }
    }

    private void MitarbeiterLogin(){
        try {
            System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
            printArrow(); String usernameOrEmail = getStringInput("String");
            System.out.println("Bitte geben Sie ihr Passwort ein: ");
            printArrow(); String password = getStringInput("String");

            eingeloggtePerson = eShop.loginMitarbeiter(usernameOrEmail, password);

            do{
                MitarbeiterSeite();
            } while (true);

        }catch (FalscheEingabeException e){
            System.err.println(e);
        } catch (LoginException e){
            System.out.println(e.getMessage());
            MitarbeiterLogin();
        }
    }

    private void bestandAeundern(){
        try {
            System.out.println("Bitte Artikelnummer einfügen:");
            printArrow();
            int artikelnummer = getIntInput("int");
            System.out.println("Bitte neuen Artikelbestand einfügen:");
            printArrow();
            int neuerBestand = getIntInput("int");
            eShop.aendereArtikelBestand(artikelnummer, neuerBestand);
        } catch (FalscheEingabeException e){
            System.err.println(e);
        }

    }
    private void artikelInWarenkorb() {
        try {
            System.out.println("Bitte geben Sie die Artikelnummer ein:");
            printArrow();
            int artikelnummer = getIntInput("int");

            System.out.println("Bitte geben Sie die Menge ein:");
            printArrow();
            int menge = getIntInput("int");

            Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);

            if (artikel instanceof MassengutArtikel) {
                MassengutArtikel massengutArtikel = (MassengutArtikel) artikel;
                int massengutAnzahl = massengutArtikel.getAnzahlMassengut();
                if (menge % massengutAnzahl != 0) {
                    throw new KeinMassengutException(massengutAnzahl);
                }
            }
            // Fügen Artikel dem Warenkorb hinzu
            eShop.artikelInWarenkorbHinzufuegen((Kunde) eingeloggtePerson, artikelnummer, menge);

        } catch (FalscheEingabeException e){
            System.err.println(e);
        } catch (KeinMassengutException e){
            System.err.println(e.getMessage());
        }
    }

    private void warenkorbKaufen(){

        try {

            // Artikel im Warenkorb kaufen
            System.out.print("Gesamt Preis: "); eShop.ListeVonWarenkorb();

            eShop.warenkorbKaufen();

            System.out.println("Der Kauf wurde erfolgreich abgeschlossen.");
            // Warenkorb leeren
            // eShop.warenkorbLeeren();

        } catch (BestandNichtAusreichendException e){
            System.err.println(e.getMessage());
        }

    }


    //Funktioniert semi
    private void artikelAusWarenkorbEntfernen(){
        try {
            System.out.println("Bitte geben Sie die Artikelnummer ein:");
            printArrow();
            int artikelnummer = getIntInput("int");


            Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);

            if (artikel != null) {
                eShop.artikelImWarenkorbEntfernen(artikel);
            } else {
                System.out.println("Artikel nicht gefunden.");
            }
        }catch (FalscheEingabeException e){
            System.err.println(e);
        }

    }

    //Geht noch nicht
    public void bestandImWarenkorbAendern() {
        try {
            System.out.println("Bitte Artikelnummer einfügen:");
            int artikelnummer = getIntInput("int");

            System.out.println("Bitte neuen Artikelbestand einfügen:");
            int neuerBestand = getIntInput("int");

            Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);
            eShop.bestandImWarenkorbAendern(artikel, neuerBestand);
        }catch (FalscheEingabeException e){
            System.err.println(e);
        }
    }

    private int getIntInput(String expectedType) throws FalscheEingabeException {
        try {
            return scan.nextInt();
        } catch (InputMismatchException e) {
            String gegebeneEingabe = scan.next(); // Eingabe löschen
            throw new FalscheEingabeException("Falsche Eingabe", expectedType, gegebeneEingabe);
        }
    }

    private String getStringInput(String expectedType) throws FalscheEingabeException {
        try {
            return scan.next();
        } catch (InputMismatchException e) {
            String gegebeneEingabe = scan.nextLine(); // Eingabe löschen
            throw new FalscheEingabeException("Falsche Eingabe", expectedType, gegebeneEingabe);
        }
    }

    private double getDoubleInput(String expectedType) throws FalscheEingabeException {
        try {
            return scan.nextDouble();
        } catch (InputMismatchException e) {
            String gegebeneEingabe = scan.next(); // Eingabe löschen
            throw new FalscheEingabeException("Falsche Eingabe", expectedType, gegebeneEingabe);
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