package eshop.ui.cui;

import eshop.domain.E_Shop;
<<<<<<< Updated upstream
import eshop.domain.exceptions.BestandNichtAusreichendException;
import eshop.domain.exceptions.LoginException;
=======
import eshop.domain.exceptions.*;

>>>>>>> Stashed changes
import eshop.enitities.*;

import java.util.Map;
import java.util.Scanner;

public class EShopCUI {
    /**
     *
     */

    private Person eingeloggtePerson = null;
    Scanner scan = new Scanner(System.in);

    private void KundeOderMitarbeiter() throws LoginException, FalscheEingabeException {
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
<<<<<<< Updated upstream

        if(input.equalsIgnoreCase("k")){
            System.out.println("Willkommen Kunde");
            System.out.println("___________________________");
            System.out.println("Haben Sie bereits ein Konto? (Y/N)");
            printArrow();
            String input2 = scan.next();
            if(input2.equalsIgnoreCase("N")){
                System.out.println("Bitte Registrieren Sie sich");
                kundeRegistrieren(scan);
            }else{
                kundeLogin(scan);
            }


        } else if(input.equalsIgnoreCase("M")) {
            System.out.println("Willkommen Arbeiter!");
            System.out.println("___________________________");

            System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
            printArrow(); String usernameOrEmail = scan.next();
            System.out.println("Bitte geben Sie ihr Passwort ein: ");
            printArrow(); String password = scan.next();

            try {
                if (eShop.loginMitarbeiter(usernameOrEmail, password)) {

                    do {
                        MitarbeiterSeite();
                        System.out.println("Zurueck zum Menue? (y/n)");
                        printArrow(); input = scan.next();
                        if (input.equalsIgnoreCase("n")) {
                            break;
                        }
                    } while (true);

=======
        try {
            if(input.equalsIgnoreCase("k")){
                System.out.println("Willkommen Kunde");
                System.out.println("___________________________");
                System.out.println("Haben Sie bereits ein Konto? (Y/N)");
                printArrow();
                input = scan.next();
                if(input.equalsIgnoreCase("N")){
                    System.out.println("Bitte Registrieren Sie sich");
                    kundeRegistrieren(scan);
                }else if(input.equalsIgnoreCase("Y")){
                    kundeLogin(scan);
                } else {
                    throw new FalscheEingabeException("Ungueltige Eingabe " + input + " ist keine gueltige Auswahl");
>>>>>>> Stashed changes
                }
            } else if(input.equalsIgnoreCase("M")) {
                MitarbeiterLogin();
            } else {
                throw new FalscheEingabeException("Ungueltige Eingabe " + input + " ist keine gueltige Auswahl");
            }

        }catch (FalscheEingabeException e){
            System.err.println(e.getMessage());
        }
        System.out.println("Sind Sie ein Kunde 'K' oder ein Mitarbeiter 'M'?");



    }



    private void MitarbeiterSeite(){
        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel einfügen \n3: Artikel löschen \n4: Artikel ändern \n5: Kunden Liste ausgeben \n6: Angestellte Mitarbeiter ausgeben \n7: Mitarbeiter registrieren \n8: Ereignisliste Ausgeben lassen \n9: Beenden");
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
                EreignisListeAusgeben();
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
        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel im Warenkorb ausgeben lassen \n3: Artikel in Warenkorb einfügen \n4: Menge von einem Artikel im Warenkorb aendern \n5: Warenkorb leeren \n6: Alle Artikel aus dem Warenkorb kaufen \n7: Bestimmten Artikel aus dem Warenkorb entfernen \n8: Beenden");
        Scanner scan = new Scanner(System.in);
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                ListeVonArtikel();
                break;
            case 2:
                System.out.println("Alle Artikel:");
                //ListeVonArtikel();
                System.out.println("________________________________________________________________");
                ListeVonWarenkorb();
                break;
            case 3:
                System.out.println("Welchen Artikel moechten Sie hinzufuegen?:");
                artikelInWarenkorb(scan);
                break;
            case 4:
                System.out.println("Von welchem Artikel moechten Sie die Menge aendern?:");
                bestandImWarenkorbAendern(scan);
                break;
            case 5:
                System.out.println("Moechten Sie den Warenkorb wirklich leeren? (Y/N)");
                warenkorbLeeren(scan);
                break;
            case 6:
                System.out.println("Moechten Sie alle Artikel im Warenkorb kaufen?");
                warenkorbKaufen();
                break;
            case 7:
                artikelAusWarenkorbEntfernen(scan);
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
        Map<Integer, Mitarbeiter> mitarbeiter = eShop.gibAlleMitarbeiter();
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
    //Umschreiben
    private void ListeVonWarenkorb(){
        System.out.println(eShop.printWarenkorbArtikel());
        System.out.println("Gesamt Preis: "+ eShop.gesamtPreis());

    }

    private void EreignisListeAusgeben(){

        for (Ereignis ereignisListe : eShop.getEreignisListe()){
            System.out.println(ereignisListe);
        }

    }

    private void artikelHinzufugen(Scanner scan) {

        int massengutAnzahl = 1;

        System.out.println("Bitte Artikelnummer einfügen:");
        printArrow();
        int artikelnummer = scan.nextInt();


        System.out.println("Bitte Artikelbezeichnung einfügen:");
        printArrow();
        String artikelbezeichnung = scan.next();

        System.out.println("Moechten sie einen normalen Artikel oder ein Massengut hinzufügen?\nM = Massgut / N = Normal");
        printArrow();
        String artikelTyp = scan.next();

        if (artikelTyp.equalsIgnoreCase("M")) {
            System.out.println("Bitte die anzahl an in dem der Artikel verkauft werden soll: ");
            printArrow();
            massengutAnzahl = scan.nextInt();
        }

        System.out.println("Bitte Artikelbestand einfügen:");
        printArrow();
        int artikelbestand = scan.nextInt();


        System.out.println("Bitte Artikelpreis einfügen:");
        while (!scan.hasNextDouble()) {
            System.out.println("Ungultige Eingabe. Bitte nochmal versuchen");
            printArrow();
            scan.next();
        }
        printArrow();
        double artikelPreis = scan.nextDouble();

        try {
            Artikel artikel;
            if (artikelTyp.equalsIgnoreCase("M")) {
                artikel = new MassengutArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis, massengutAnzahl);
            } else {
                artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
            }
            eShop.addArtikel(artikel);
        } catch (Exception e) {
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

        try {
            eShop.addMitarbeiter(vorname, nachname, email, username, passwort);
            System.out.println("Neuer Mitarbeiter wurde registriert");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void MitarbeiterLogin(){
        String input;
        System.out.println("Willkommen Arbeiter!");
        System.out.println("___________________________");

        System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
        printArrow(); String usernameOrEmail = scan.next();
        System.out.println("Bitte geben Sie ihr Passwort ein: ");
        printArrow(); String password = scan.next();
        mitarbeiterRegistrieren(scan);

        try {
            if (eShop.loginMitarbeiter(usernameOrEmail, password)) {

                do {
                    MitarbeiterSeite();
                    System.out.println("Zurueck zum Menue? (y/n)");
                    printArrow(); input = scan.next();
                    if (input.equalsIgnoreCase("n")) {
                        break;
                    }
                } while (true);

            }
        } catch (LoginException e) {

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
            eShop.addKunde(vorname, nachname, email, username, passwort,ort, plz, strasse, strassenNummer);
            System.out.println("Sie haben sich als Kunden registriert");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void kundeLogin(Scanner scan){
        System.out.println("Bitte geben Sie Ihre E-mail oder Ihren Benutzernamen ein: ");
        printArrow(); String usernameOrEmail = scan.next();
        System.out.println("Bitte geben Sie ihr Passwort ein: ");
        printArrow(); String password = scan.next();

        try {
            eingeloggtePerson = eShop.loginKunde(usernameOrEmail, password);

            do{
                KundenSeite();
                System.out.println("Zurueck zum Menue? (y/n)");
                printArrow(); String input = scan.next();
                if(input.equalsIgnoreCase("n")){
                    break;
                }
            } while (true);

        }catch (Exception e){
            System.out.println(e.getMessage());
            kundeLogin(scan);
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

    private void artikelInWarenkorb(){
        scan = new Scanner(System.in);

        System.out.println("Bitte geben Sie die Artikelnummer ein:");
        printArrow();
        int artikelnummer = scan.nextInt();


        System.out.println("Bitte geben Sie die Menge ein:");
        printArrow();
        int menge = scan.nextInt();

        // Fügen Artikel dem Warenkorb hinzu
        eShop.artikelInWarenkorbHinzufuegen1((Kunde) eingeloggtePerson, artikelnummer, menge);

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
            try {
                eShop.warenkorbKaufen();
            } catch (BestandNichtAusreichendException e){
                System.out.println(e.getMessage());
            }

            System.out.println("Der Kauf wurde erfolgreich abgeschlossen.");


            // Warenkorb leeren
           // eShop.warenkorbLeeren();
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
            try {
                eShopCUI.KundeOderMitarbeiter();
            }catch (Exception e){
                e.getStackTrace();
            }
            System.out.println("Möchten Sie das Programm beenden? y/n");
            System.out.print("--> "); String input = scanner.next();
            if(input.equalsIgnoreCase("y")){
                break;
            }
        } while (true);
        scanner.close();
    }
}