package eshop.ui.cui;

import eshop.domain.ArtikelManagement;
import eshop.domain.E_Shop;
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
    private  void KundeOderMitarbeiter(){
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

                if(eShop.loginKunde(usernameOrEmail, password)){
                    KundenSeite();
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
                MitarbeiterSeite();
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
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel einfügen \n3: Artikel löschen \n4: Artikel ändern \n5: Kunden Liste \n6: Angestellte Mitarbeiter ausgeben \n7: Mitarbeiter registrieren \n8: Beenden");
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
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel in Warenkorb einfügen");
        Scanner scan = new Scanner(System.in);
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                ListeVonArtikel();
                break;
            case 2:
                System.out.println("Warenkorb oeffnen:");
                do{
                    Warenkorb();
                    System.out.println("Noch was? (y/n)");
                    printArrow(); String input = scan.next();
                    if(input.equalsIgnoreCase("n")){
                        break;
                    }
                }while(true);

                break;
        }
    }

    private void Warenkorb(){
        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel in Warenkorb einfügen \n3: Menge von Artikel aendern \n4: Warenkorb leeren \n5: Artikel kaufen \n6: Beenden");
        Scanner scan = new Scanner(System.in);
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                ListeVonArtikel();
                System.out.println("________________________________________________________________");
                //listeVonArtikelImWarenkorb();
                break;
            case 2:
                System.out.println("Welchen Artikel moechten Sie hinzufuegen?:");
                //artikelInWarenkorb(scan);
                break;
            case 3:
                System.out.println("Von welchem Artikel moechten Sie die Menge aendern?:");
                //mengeVonArtikelInWarenkorbAendern(scan);
                break;
            case 4:
                System.out.println("Moechten Sie den Warenkorb wirklich leeren?");
                //warenkorbLeeren(scan);
                break;
            case 5:
                System.out.println("Moechten Sie alle Artikel im Warenkorb kaufen?");
                //warenkorbKaufen();
                break;
            case 6:
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
            System.out.println(artikelbezeichnung);
        });
    }

    private  void ListeVonMitarbeiter(){
        Map<Integer, Person> mitarbeiter = eShop.gibAlleMitarbeiter();
        mitarbeiter.forEach((mitarbeiterId, mitarbeiterDaten)-> {
            System.out.println(mitarbeiterDaten.printDetails());
        });
    }

    private  void ListeVonKunden(){
        Map<Integer, Kunde> kunden = eShop.gibAlleKunden();
        kunden.forEach((kundeId, kundeDaten)-> {
            System.out.println(kundeDaten.printDetails());
        });
    }


    private void artikelHinzufugen(Scanner scan){
        System.out.println("Bitte Artikelnummer einfügen:");
        int artikelnummer = scan.nextInt();
        scan.nextLine();

        System.out.println("Bitte Artikelbezeichnung einfügen:");
        String artikelbezeichnung = scan.next();
        scan.nextLine();

        System.out.println("Bitte Artikelbestand einfügen:");
        int artikelbestand = scan.nextInt();
        scan.nextLine();

        System.out.println("Bitte Artikelpreis einfügen:");
        while(!scan.hasNextDouble()){
            System.out.println("Ungultige Eingabe. Bitte nochmal versuchen");
            scan.next();
        }
        double artikelPreis = scan.nextDouble();
        scan.nextLine();

        eShop.addArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
        System.out.println("Artikel wurde hinzugefügt");
    }

    private void mitarbeiterRegistrieren(Scanner scan){
        System.out.println("Bitte Vornamen einfügen:");
        String vorname = scan.next();
        scan.nextLine();

        System.out.println("Bitte Nachnamen einfügen:");
        String nachname = scan.next();
        scan.nextLine();

        System.out.println("Bitte eMail erstellen:");
        String email = scan.next();
        scan.nextLine();

        System.out.println("Bitte username erstellen:");
        String username = scan.next();
        scan.nextLine();

        System.out.println("Bitte Passwort erstellen:");
        String passwort = scan.next();
        scan.nextLine();

        System.out.println("Bitte ID einfügen:");
        int id = scan.nextInt();
        scan.nextLine();

        try {
            eShop.addMitarbeiter(vorname, nachname, email, username, passwort, id);
            System.out.println("Neuer Mitarbeiter wurde registrert");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void kundeRegistrieren(Scanner scan){
        System.out.println("Bitte Vornamen einfügen:");
        printArrow(); String vorname = scan.next();
        scan.nextLine();

        System.out.println("Bitte Nachnamen einfügen:");
        printArrow(); String nachname = scan.next();
        scan.nextLine();

        System.out.println("Bitte eMail erstellen:");
        printArrow(); String email = scan.next();
        scan.nextLine();

        System.out.println("Bitte username erstellen:");
        printArrow(); String username = scan.next();
        scan.nextLine();

        System.out.println("Bitte Passwort erstellen:");
        printArrow(); String passwort = scan.next();
        scan.nextLine();

        System.out.println("Bitte ID einfügen:");
        printArrow(); int id = scan.nextInt();
        scan.nextLine();

        System.out.println("Bitte Ort einfügen:");
        printArrow(); String ort = scan.next();
        scan.nextLine();

        System.out.println("Bitte PLZ einfügen:");
        printArrow(); int plz = scan.nextInt();
        scan.nextLine();

        System.out.println("Bitte Strassennamen einfügen:");
        printArrow(); String strasse = scan.next();
        scan.nextLine();

        System.out.println("Bitte Strassennummer einfügen:");
        printArrow(); int strassenNummer = scan.nextInt();
        scan.nextLine();


        eShop.addKunde(vorname, nachname, email, username, passwort, id, ort, plz, strasse, strassenNummer);
        System.out.println("Sie haben sich als Kunden registriert");
    }

    private void bestandAeundern(Scanner scan){
        System.out.println("Bitte Artikelnummer einfügen:");
        int artikelnummer = scan.nextInt();
        System.out.println("Bitte neuen Artikelbestand einfügen:");
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


