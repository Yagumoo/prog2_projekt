package eshop.ui.cui;

import eshop.domain.ArtikelManagement;
import eshop.domain.E_Shop;
import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Warenkorb;
import eshop.enitities.Mitarbeiter;
import eshop.enitities.Person;

import java.util.Map;
import java.util.HashMap;
import java.util.*;
import java.util.List;

public class EShopCUI {
    /**
     *
     */

    private Person eingeloggtePerson = null;

    private void KundeOderMitarbeiter(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Sind Sie ein Kunde 'K' oder ein Mitarbeiter 'M'?");
        String input = scan.next();
        if(input.equalsIgnoreCase("k")){
            System.out.println("Willkommen Kunde");
            System.out.println("___________________________");
            System.out.println("Haben Sie bereits ein Konto? (Y/N)");
            printArrow(); String input2 = scan.next();
            if(input2.equalsIgnoreCase("N")){
                System.out.println("Bitte Registrieren Sie sich");

                kundeRegistrieren(scan);
            }else{
                System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
                printArrow(); String usernameOrEmail = scan.next();
                System.out.println("Bitte geben Sie ihr Passwort ein: ");
                printArrow(); String password = scan.next();

                eingeloggtePerson = eShop.loginKunde(usernameOrEmail, password);
                if(eingeloggtePerson != null){
                    do{
                        KundenSeite();
                        System.out.println("Noch was? (y/n)");
                        printArrow(); input = scan.next();
                        if(input.equalsIgnoreCase("n")){
                                break;
                        }
                    } while (true);

                }else{
                    System.out.println("Ungültiges Passwort oder Benutzername! Bitte erneut versuchen!");
                }
            }

        } else if(input.equalsIgnoreCase("M")){
            System.out.println("Willkommen Arbeiter!");
            System.out.println("___________________________");

            System.out.println("Bitter geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
            printArrow(); String usernameOrEmail = scan.next();
            System.out.println("Bitte geben Sie ihr Passwort ein: ");
            printArrow(); String password = scan.next();

            if(eShop.loginMitarbeiter(usernameOrEmail, password)){

                do{
                    MitarbeiterSeite();
                    System.out.println("Noch was? (y/n)");
                    printArrow(); input = scan.next();
                    if(input.equalsIgnoreCase("n")){
                        break;
                    }
                } while (true);

            }else{
                System.out.println("Ungültiges Passwort oder Benutzername! Bitte erneut versuchen!");
            }

        } else{
            System.out.println("Falsche Eingabe");
        }
    }



    private void MitarbeiterSeite(){
        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel einfügen \n3: Artikel löschen \n4: Artikel ändern \n5: Kunden Liste ausgeben \n6: Angestellte Mitarbeiter ausgeben \n7: Mitarbeiter registrieren \n8: Beenden");
        Scanner scan = new Scanner(System.in);
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                ListeVonArtikel();
                break;
            case 2:
                artikelHinzufugen(scan);
                break;
            case 3:
                System.out.println("Welchen Artikel willst du löschen?");
                break;
            case 4:
                System.out.println("Welchen Artikel willst du ändern?");
                bestandAeundern(scan);
                break;
            case 5:
                System.out.println("Registrierte Kunden ausgeben:");
                ListeVonKunden();
                break;
            case  6:
                System.out.println("Angestellte Mitarbeiter ausgeben:");
                ListeVonMitarbeiter();
                break;
            case 7:
                System.out.println("Bitte registrieren Sie den neuen Angestellten:");
                mitarbeiterRegistrieren(scan);
                break;
            case 8:
                System.out.println("Sie sind gefeuert");
                break;
        }
    }

    private void KundenSeite(){
        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel ausgeben lassen \n2: Warenkorb oeffnen");
        Scanner scan = new Scanner(System.in);
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                ListeVonArtikel();
                break;
            case 2:
                System.out.println("Warenkorb oeffnen:");
                    Warenkorb();
                break;
        }
    }

    private void Warenkorb(){

        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel im Warenkorb ausgeben lassen \n2: Artikel in Warenkorb einfügen \n" +
                            "3: Menge von einem Artikel im Warenkorb aendern \n4: Warenkorb leeren \n" +
                            "5: Alle Artikel aus dem Warenkorb kaufen \n6: Bestimmten Artikel aus dem Warenkorb entfernen \n" +
                            "7: Beenden");
        Scanner scan = new Scanner(System.in);
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                //ListeVonArtikel();
                System.out.println("________________________________________________________________");
                ListeVonWarenkorb();
                break;
            case 2:
                System.out.println("Welchen Artikel moechten Sie hinzufuegen?:");
                artikelInWarenkorb(scan);
                break;
            case 3:
                System.out.println("Von welchem Artikel moechten Sie die Menge aendern?:");
                bestandImWarenkorbAendern(scan);
                break;
            case 4:
                System.out.println("Moechten Sie den Warenkorb wirklich leeren? (Y/N)");
                warenkorbLeeren(scan);
                break;
            case 5:
                System.out.println("Moechten Sie alle Artikel im Warenkorb kaufen?");
                warenkorbKaufen();
                break;
            case 6:
                artikelAusWarenkorbEntfernen(scan);
                System.out.println("Waehlen Sie den Artikel den Sie entfernen moechten");
                break;
            case 7:
                System.out.println("Beenden (y/n)");
                break;
        }
    }

    private E_Shop eShop;

    public EShopCUI() {

        this.eShop = new E_Shop();
    }

    private void ListeVonArtikel() {
        Map<Integer, Artikel> artikel = eShop.gibAlleArtikel();
        artikel.forEach((arikelnummer, artikelbezeichnung)-> {
            System.out.println("Gesamt Preis: "+ artikelbezeichnung);
        });
    }

    private  void ListeVonMitarbeiter(){
        Map<Integer, Person> mitarbeiter = eShop.gibAlleMitarbeiter();
        mitarbeiter.forEach((mitarbeiterId, mitarbeiterDaten)-> {
            System.out.println(mitarbeiterDaten.toString());
        });
    }

    private  void ListeVonKunden(){
        Map<Integer, Kunde> kunden = eShop.gibAlleKunden();
        kunden.forEach((kundeId, kundeDaten)-> {
            System.out.println(kundeDaten.toString());
        });
    }

    private void ListeVonWarenkorb(){
        System.out.println(eShop.printWarenkorbRechnung());
        System.out.println("Gesamt Preis: "+ eShop.gesamtPreis());

    }

    private void artikelHinzufugen(Scanner scan){
        System.out.println("Bitte Artikelnummer einfügen:");
        printArrow();
        int artikelnummer = scan.nextInt();


        System.out.println("Bitte Artikelbezeichnung einfügen:");
        printArrow();
        String artikelbezeichnung = scan.next();


        System.out.println("Bitte Artikelbestand einfügen:");
        printArrow();
        int artikelbestand = scan.nextInt();


        System.out.println("Bitte Artikelpreis einfügen:");
        while(!scan.hasNextDouble()){
            System.out.println("Ungultige Eingabe. Bitte nochmal versuchen");
            printArrow();
            scan.next();
        }
        printArrow();
        double artikelPreis = scan.nextDouble();


        try{
            eShop.addArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
            System.out.println("Artikel wurde hinzugefügt");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void mitarbeiterRegistrieren(Scanner scan){
        System.out.println("Bitte Vornamen einfügen:");
        printArrow();
        String vorname = scan.next();


        System.out.println("Bitte Nachnamen einfügen:");
        printArrow();
        String nachname = scan.next();


        System.out.println("Bitte eMail erstellen:");
        printArrow();
        String email = scan.next();


        System.out.println("Bitte username erstellen:");
        printArrow();
        String username = scan.next();


        System.out.println("Bitte Passwort erstellen:");
        printArrow();
        String passwort = scan.next();


        System.out.println("Bitte ID einfügen:");
        printArrow();
        int id = scan.nextInt();

        try {
            eShop.addMitarbeiter(vorname, nachname, email, username, passwort, id);
            System.out.println("Neuer Mitarbeiter wurde registrert");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void kundeRegistrieren(Scanner scan){
        System.out.println("Bitte Vornamen einfügen:");
        printArrow();
        String vorname = scan.next();


        System.out.println("Bitte Nachnamen einfügen:");
        printArrow();
        String nachname = scan.next();


        System.out.println("Bitte eMail erstellen:");
        printArrow();
        String email = scan.next();


        System.out.println("Bitte username erstellen:");
        printArrow();
        String username = scan.next();


        System.out.println("Bitte Passwort erstellen:");
        printArrow();
        String passwort = scan.next();


        System.out.println("Bitte ID einfügen:");
        printArrow();
        int id = scan.nextInt();


        System.out.println("Bitte Ort einfügen:");
        printArrow();
        String ort = scan.next();


        System.out.println("Bitte PLZ einfügen:");
        printArrow();
        int plz = scan.nextInt();


        System.out.println("Bitte Strassennamen einfügen:");
        printArrow();
        String strasse = scan.next();


        System.out.println("Bitte Strassennummer einfügen:");
        printArrow();
        int strassenNummer = scan.nextInt();



        try{
            eShop.addKunde(vorname, nachname, email, username, passwort, id, ort, plz, strasse, strassenNummer);
            System.out.println("Sie haben sich als Kunden registriert");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void bestandAeundern(Scanner scan){
        System.out.println("Bitte Artikelnummer einfügen:");
        printArrow();
        int artikelnummer = scan.nextInt();
        System.out.println("Bitte neuen Artikelbestand einfügen:");
        printArrow();
        int neuerBestand = scan.nextInt();

        boolean erfolgreichGeaendert = eShop.aendereArtikelBestand(artikelnummer, neuerBestand);

        if (erfolgreichGeaendert){
            System.out.println("Artikel wurde erfolgreich geändert");
        } else{
            System.out.println("Artikel konnte nicht gefunden werden");
        }

    }
    private void printArrow() {
        System.out.print("--> ");
    }

    private void artikelInWarenkorb(Scanner scan){
        scan = new Scanner(System.in);

        System.out.println("Bitte geben Sie die Artikelnummer ein:");
        printArrow();
        int artikelnummer = scan.nextInt();


        System.out.println("Bitte geben Sie die Menge ein:");
        printArrow();
        int menge = scan.nextInt();

        // Suche Artikel mit der angegebenen Artikelnummer
        Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);
        if (artikel == null) {
            System.out.println("Artikel mit der angegebenen Artikelnummer nicht gefunden.");
            return; // Beendet Methode, wenn der Artikel nicht gefunden wurde
        }

        // Fügen Artikel dem Warenkorb hinzu
        eShop.artikelInWarenkorbHinzufuegen1(artikel, menge);

        System.out.println("Artikel erfolgreich hinzugefügt.");
    }

    private void warenkorbKaufen(){
        Scanner scanner = new Scanner(System.in);

        // Kauf bestätigen
        System.out.println("Möchten Sie den Warenkorb kaufen? (Y/N)");
        printArrow();
        String antwort = scanner.nextLine();

        if (antwort.equalsIgnoreCase("Y")) {
            // Artikel im Warenkorb kaufen
            System.out.print("Gesamt Preis: "); ListeVonWarenkorb();
            eShop.warenkorbKaufen();
            System.out.println("Der Kauf wurde erfolgreich abgeschlossen.");


            // Warenkorb leeren
            eShop.warenkorbLeeren();
        } else {
            System.out.println("Der Kauf wurde abgebrochen.");
        }
    }

    private  void warenkorbLeeren(Scanner scan){
        scan = new Scanner(System.in);
        String scaner = scan.next();
        if(scaner.equalsIgnoreCase("Y")){
            eShop.warenkorbLeeren();
        }
    }

    //Funktioniert semi
    private void artikelAusWarenkorbEntfernen(Scanner scan){
        System.out.println("Bitte geben Sie die Artikelnummer ein:");
        printArrow();
        int artikelnummer = scan.nextInt();


        Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);

        if (artikel != null) {
            eShop.artikelImWarenkorbEntfernen(artikel);
        } else {
            System.out.println("Artikel nicht gefunden.");
        }
    }

    //Geht noch nicht
    public void bestandImWarenkorbAendern(Scanner scan) {
        System.out.println("Bitte Artikelnummer einfügen:");
        int artikelnummer = scan.nextInt();


        System.out.println("Bitte neuen Artikelbestand einfügen:");
        int neuerBestand = scan.nextInt();


        Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);

        if (artikel != null) {
            eShop.bestandImWarenkorbAendern(artikel, neuerBestand);
            System.out.println("Artikel wurde erfolgreich geändert");
        } else {
            System.out.println("Artikel konnte nicht gefunden werden");
        }
    }


    public static void main(String[] args) {
        EShopCUI eShopCUI = new EShopCUI();
        Scanner scanner = new Scanner(System.in);

        do {
            //eShopCUI.start();
            eShopCUI.KundeOderMitarbeiter();
            System.out.println("Möchten Sie das Programm beenden? y/n");
            System.out.print("--> "); String input = scanner.next();
            if(input.equalsIgnoreCase("y")){
                break;
            }
        } while (true);
        scanner.close();
    }
}