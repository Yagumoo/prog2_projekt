package eshop.server.domain;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Verwaltet die Warenkörbe der Kunden und die zugehörigen Rechnungen.
 *
 * Diese Klasse ist verantwortlich für die Verwaltung der Warenkörbe, einschließlich der Hinzufügung von Artikeln,
 * der Berechnung des Gesamtpreises und des Leeren des Warenkorbs. Außerdem stellt sie Methoden bereit, um
 * den Warenkorb eines Kunden zu verwalten und eine Rechnung zu erstellen.
 */
public class WarenkorbManagement {
    private Map<Artikel, Integer> warenkorbMap;
    private Map<Kunde, Warenkorb> warenkorbVonKunde;
    private Rechnung rechnung;

    /**
     * Konstruktor, der das WarenkorbManagement initialisiert.
     *
     * Erzeugt eine leere Map für die Warenkörbe der Kunden.
     */
    public WarenkorbManagement(){
        this.warenkorbVonKunde = new HashMap<>();
    }

    /**
     * Leert den Warenkorb eines Kunden.
     *
     * Diese Methode ruft die Methode {@link Warenkorb#warenkorbLeeren()} auf, um alle Artikel aus dem Warenkorb
     * des angegebenen Kunden zu entfernen. Der Warenkorb wird auf seinen ursprünglichen Zustand zurückgesetzt.
     *
     * @param kunde Der Kunde, dessen Warenkorb geleert werden soll.
     *
     * @throws NullPointerException Wenn der übergebene Kunde {@code null} ist.
     * @throws IstLeerException Wenn der Kunde keinen Warenkorb hat oder der Warenkorb bereits leer ist.
     */
    public void warenkorbLeeren(Kunde kunde){
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        warenkorb.warenkorbLeeren();
    }

    /**
     * Fügt einen Artikel mit einer bestimmten Menge zum Warenkorb eines Kunden hinzu.
     *
     * Überprüft, ob der Artikel Massengut ist und ob die Menge den Anforderungen für Massengutartikel entspricht.
     * Außerdem wird überprüft, ob die angegebene Menge gültig ist und ob genügend Bestand für den Artikel vorhanden ist.
     * Wenn der Artikel bereits im Warenkorb vorhanden ist, wird die Menge des Artikels entsprechend angepasst.
     *
     * @param kunde Der Kunde, dessen Warenkorb aktualisiert werden soll.
     * @param artikel Der Artikel, der dem Warenkorb hinzugefügt werden soll.
     * @param menge Die Menge des Artikels, die dem Warenkorb hinzugefügt werden soll.
     *
     * @throws MinusZahlException Wenn die angegebene Menge kleiner oder gleich null ist.
     * @throws KeinMassengutException Wenn der Artikel ein Massengutartikel ist und die Menge nicht durch die erforderliche Menge teilbar ist.
     * @throws BestandNichtAusreichendException Wenn die angegebene Menge den verfügbaren Bestand des Artikels überschreitet.
     */
    public void artikelInWarenkorbHinzufuegen(Kunde kunde, Artikel artikel,int menge) throws MinusZahlException, KeinMassengutException, BestandNichtAusreichendException {
        //Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        if (artikel instanceof MassengutArtikel massengutArtikel) {
            int massengutAnzahl = massengutArtikel.getAnzahlMassengut();
            if (menge % massengutAnzahl != 0) {
                throw new KeinMassengutException(massengutAnzahl);
            }
        }

        if(menge <=0 ){
            throw new MinusZahlException();
        }

        if(menge > artikel.getArtikelbestand() ){
            throw new BestandNichtAusreichendException(artikel, artikel.getArtikelbestand());
        }

        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        //Wenn Artikel schon im Warenkorb ist, wird die neue Menge dazu addiert
        if(warenkorb.getWarenkorbMap().containsKey(artikel)){
            int bestehendeMenge = warenkorb.getWarenkorbMap().get(artikel);
            menge += bestehendeMenge;
        }
        warenkorb.artikelHinzufuegen(artikel, menge);

    }

    /**
     * Entfernt einen Artikel aus dem Warenkorb eines Kunden.
     *
     * Überprüft, ob der Artikel im Warenkorb des Kunden vorhanden ist. Wenn der Artikel nicht vorhanden ist,
     * wird eine Ausnahme ausgelöst. Andernfalls wird der Artikel aus dem Warenkorb entfernt.
     *
     * @param kunde Der Kunde, dessen Warenkorb aktualisiert werden soll.
     * @param artikel Der Artikel, der aus dem Warenkorb entfernt werden soll.
     *
     * @throws ArtikelExisitiertNichtException Wenn der Artikel nicht im Warenkorb des Kunden vorhanden ist.
     */
    public void entferneArtikelAusWarenkorb(Kunde kunde, Artikel artikel) throws ArtikelExisitiertNichtException {
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);

        if(!warenkorb.getWarenkorbMap().containsKey(artikel)){
            throw new ArtikelExisitiertNichtException(artikel.getArtikelbezeichnung());
        }
        warenkorb.artikelEntfernen(artikel);
    }

    /**
     * Gibt den Warenkorb eines bestimmten Kunden zurück.
     *
     * Überprüft, ob der Warenkorb des Kunden vorhanden ist. Wenn der Warenkorb leer ist, wird eine Ausnahme ausgelöst.
     * Andernfalls wird der Warenkorb des Kunden zurückgegeben.
     *
     * @param kunde Der Kunde, dessen Warenkorb abgerufen werden soll.
     * @return Der Warenkorb des angegebenen Kunden.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer oder nicht vorhanden ist.
     */
    public Warenkorb getWarenkorb(Kunde kunde) throws IstLeerException {
        if(warenkorbVonKunde == null){
            throw new IstLeerException();
        }
        return warenkorbVonKunde.get(kunde);
    }

    /**
     * Erstellt einen neuen Warenkorb für einen bestimmten Kunden und fügt ihn dem Warenkorb-Management hinzu.
     *
     * Wenn der Kunde noch keinen Warenkorb hat, wird ein neuer Warenkorb erstellt und dem Kunden zugeordnet.
     *
     * @param kunde Der Kunde, für den ein neuer Warenkorb erstellt werden soll.
     */
    public void warenkorbHinzufuegen(Kunde kunde){
        Warenkorb warenkorb = new Warenkorb();
        warenkorbVonKunde.put(kunde, warenkorb);
    }


    /**
     * Gibt den Warenkorb eines Kunden zurück, der nicht leer ist.
     *
     * Überprüft, ob der Warenkorb des Kunden Artikel enthält. Wenn der Warenkorb leer ist, wird eine Ausnahme ausgelöst.
     * Andernfalls wird der Warenkorb des Kunden zurückgegeben.
     *
     * @param kunde Der Kunde, dessen Warenkorb abgerufen werden soll.
     * @return Der Warenkorb des angegebenen Kunden, wenn dieser nicht leer ist.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer ist.
     */
    public Warenkorb getWarenkorbKaufen(Person kunde) throws IstLeerException {
        Warenkorb wk = warenkorbVonKunde.get(kunde);
        Map<Artikel, Integer> wkMap = wk.getWarenkorbMap();
        if(wkMap.isEmpty()){
            throw new IstLeerException();
        }
        return warenkorbVonKunde.get(kunde);
    }
}