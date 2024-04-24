package eshop.ui.cui;

import eshop.domain.E_Shop;
import eshop.enitities.Artikel;

import java.util.*;
import java.util.List;

public class EShopCUI {

    private  void KundeOderMitarbeiter(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Sind Sie ein Kunde 'K' oder ein Mitarbeiter 'M'?");
        String input = scan.next();
        if(input.equalsIgnoreCase("k")){
            System.out.println("Willkommen Kunde");
            KundenSeite();
        } else if(input.equalsIgnoreCase("M")){
            System.out.println("Willkommen Arbeiter!");
            MitarbeiterSeite();
        } else{
            System.out.println("Falsche Eingabe");
        }
    }



    private void MitarbeiterSeite(){
        System.out.println("Was willst du machen? b Artikel ausgeben lassen");
        Scanner scan = new Scanner(System.in);
        String eingabe = scan.next();

        switch(eingabe) {
            case "B":
                System.out.println("Alle Artikel:");
                start();
                break;
            case "A":
                break;
        }
    }

    private void KundenSeite(){

    }

    private E_Shop eShop;

    public EShopCUI() {

        this.eShop = new E_Shop();
    }

    private void start() {
        List<Artikel> artikel = eShop.gibAlleArtikel();
        artikelAusgeben(artikel);
    }

    public void artikelAusgeben(List<Artikel> artikelListe) {
        for (Artikel artikel : artikelListe) {
            System.out.println(artikel.toString());
        }
    }

    public static void main(String[] args) {
        EShopCUI eShopCUI = new EShopCUI();
        eShopCUI.start();
        eShopCUI.KundeOderMitarbeiter();
    }

}


