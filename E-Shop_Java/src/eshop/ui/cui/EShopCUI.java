package eshop.ui.cui;

import eshop.domain.E_Shop;
import eshop.enitities.Artikel;
import eshop.enitities.Mitarbeiter;

import java.util.*;
import java.util.List;

public class EShopCUI {

    private  void KundeOderMitarbeiter(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Sind Sie ein Kunde 'K' oder ein Mitarbeiter 'M'?");
        String input = scan.next();
        if(input.equalsIgnoreCase("k")){
            System.out.println("Willkommen Kunde");
            System.out.println("___________________________");
            KundenSeite();
        } else if(input.equalsIgnoreCase("M")){
            System.out.println("Willkommen Arbeiter!");
            System.out.println("___________________________");
            MitarbeiterSeite();
        } else{
            System.out.println("Falsche Eingabe");
        }
    }



    private void MitarbeiterSeite(){
        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel einfügen \n3: Artikel löschen \n4: Artikel ändern \n5: Beenden ");
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
                System.out.println("Sie sind gefeuert");
                break;
        }
    }

    private void KundenSeite(){
        System.out.println("Was willst du machen?");
        System.out.println();
        System.out.println("1: Artikel ausgeben lassen \n2: Artikel in Warenkorb einfügen \n3: Artikel suchen \n4: Warenkorb öffnen \n5: Beenden");
        Scanner scan = new Scanner(System.in);
        int eingabe = scan.nextInt();

        switch(eingabe) {
            case 1:
                System.out.println("Alle Artikel:");
                ListeVonArtikel();
                break;
            case 2:
                System.out.println("Bitte Artikel zum einfügen auswählen:");
                break;
            case 3:
                System.out.println("Artikelbezeichnung eingeben:");
                break;
            case 4:
                System.out.println("Willkommen im Warenkorb");
                break;
            case 5:
                System.out.println("Was möchten Sie nun tun?");
                break;
        }
    }

    private E_Shop eShop;

    public EShopCUI() {

        this.eShop = new E_Shop();
    }

    private void ListeVonArtikel() {
        List<Artikel> artikel = eShop.gibAlleArtikel();
        artikelAusgeben(artikel);
    }

    public void artikelAusgeben(List<Artikel> artikelListe) {
        for (Artikel artikel : artikelListe) {
            System.out.println(artikel.toString());
        }
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

    private void bestandAeundern(Scanner scan){
        System.out.println("Bitte Artikelnummer einfügen:");
        int artikelnummer = scan.nextInt();
        System.out.println("Bitte neuen Artikelbestand einfügen:");
        int neuerBestand = scan.nextInt();

        eShop.aendereArtikelBestand(artikelnummer, neuerBestand);

        boolean erfolgreichGeaendert = eShop.aendereArtikelBestand(artikelnummer, neuerBestand);

        if (erfolgreichGeaendert){
            System.out.println("Artikel wurde erfolgreich geändert");
        } else{
            System.out.println("Artikel konnte nicht gefunden werden");
        }

    }



    public static void main(String[] args) {
        EShopCUI eShopCUI = new EShopCUI();
        Scanner scanner = new Scanner(System.in);
        do {
            //eShopCUI.start();
            eShopCUI.KundeOderMitarbeiter();
            System.out.println("Möchten Sie das Programm beenden? Ja/Nein");
            String input = scanner.next();
            if(input.equalsIgnoreCase("ja")){
                break;
            }
        } while (true);
        scanner.close();
    }

}


