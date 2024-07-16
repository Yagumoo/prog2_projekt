package eshop.common.enitities;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
/**
 * Diese Klasse repräsentiert ein Ereignis im System, das einen bestimmten Typ und zusätzliche Informationen zu einem Artikel enthält.
 *
 * <p>Die Klasse {@code Ereignis} dient dazu, Ereignisse wie die Erstellung eines neuen Artikels, Käufe, Erhöhungen oder Reduzierungen im Bestand zu verwalten.</p>
 *
 * <p>Jedes Ereignis hat ein Datum, eine Artikelbezeichnung, eine Menge, eine referenzierte Person (Kunde oder Mitarbeiter) und einen Ereignistyp.</p>
 */
public class Ereignis {
    /**
     * Diese Enum beschreibt die verschiedenen Typen von Ereignissen, die im System auftreten können.
     *
     * <p>Die Enum {@code EreignisTyp} definiert die folgenden Typen von Ereignissen:</p>
     * <ul>
     *   <li>{@code NEU} - Ein neues Ereignis, z.B. die Einführung eines neuen Artikels.</li>
     *   <li>{@code KAUF} - Ein Kaufereignis, z.B. wenn ein Artikel verkauft wird.</li>
     *   <li>{@code ERHOEHUNG} - Ein Ereignis, das eine Erhöhung des Bestands darstellt, z.B. bei einer Nachlieferung.</li>
     *   <li>{@code REDUZIERUNG} - Ein Ereignis, das eine Reduzierung des Bestands darstellt, z.B. durch einen Verkauf.</li>
     * </ul>
     */
    public enum EreignisTyp {
        NEU, KAUF, ERHOEHUNG, REDUZIERUNG
    }

    private LocalDate datum;
    private String artikelbezeichnung; // Artikel
    private int anzahl;
    private Person kundeOderMitarbeiter;
    private EreignisTyp typ;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
//    private Person betroffenePerson;
    /**
     * Konstruktor für die {@code Ereignis}-Klasse.
     *
     * <p>Initialisiert ein neues Ereignis mit dem angegebenen Datum, der Artikelbezeichnung, der Menge, der betroffenen Person und dem Ereignistyp.</p>
     *
     * @param datum das Datum des Ereignisses
     * @param artikelbezeichnung die Bezeichnung des Artikels, auf den sich das Ereignis bezieht
     * @param anzahl die Anzahl der betroffenen Artikel
     * @param kundeOderMitarbeiter die Person, die mit dem Ereignis in Verbindung steht (entweder ein Kunde oder ein Mitarbeiter)
     * @param typ der Typ des Ereignisses (z.B. NEU, KAUF, ERHOEHUNG, REDUZIERUNG)
     */
    public Ereignis(LocalDate datum, String artikelbezeichnung, int anzahl, Person kundeOderMitarbeiter, EreignisTyp typ){
        this.datum = datum;
        this.artikelbezeichnung = artikelbezeichnung;
        this. anzahl = anzahl;
        this.kundeOderMitarbeiter = kundeOderMitarbeiter;
        this.typ = typ;
    }
    /**
     * Gibt das Datum des Ereignisses zurück.
     *
     * <p>Diese Methode gibt das {@code LocalDate}-Objekt zurück, das das Datum repräsentiert, an dem das Ereignis stattgefunden hat.</p>
     *
     * @return das Datum des Ereignisses
     */
    public LocalDate getDatum() {
        return datum;
    }
    /**
     * Gibt die Bezeichnung des Artikels zurück.
     *
     * <p>Diese Methode gibt den Namen des Artikels zurück, der mit dem Ereignis in Verbindung steht.</p>
     *
     * @return die Bezeichnung des Artikels
     */
    public String getArtikel() {
        return artikelbezeichnung;
    }
    /**
     * Setzt die Bezeichnung des Artikels auf den Namen des angegebenen {@code Artikel}-Objekts.
     *
     * <p>Diese Methode aktualisiert die Bezeichnung des Artikels auf den Namen des übergebenen {@code Artikel}-Objekts.</p>
     *
     * @param artikelbezeichnung das {@code Artikel}-Objekt, dessen Bezeichnung als neuer Name des Artikels verwendet wird
     */
    public void setArtikel(Artikel artikelbezeichnung) {
        this.artikelbezeichnung = artikelbezeichnung.getArtikelbezeichnung();
    }
    /**
     * Gibt die Person zurück, die mit dem Ereignis in Verbindung steht.
     *
     * <p>Diese Methode gibt das {@code Person}-Objekt zurück, das den Kunden oder Mitarbeiter repräsentiert, der mit dem Ereignis verknüpft ist.</p>
     *
     * @return die Person, die mit dem Ereignis in Verbindung steht
     */
    public Person getKundeOderMitarbeiter() {
        return kundeOderMitarbeiter;
    }
    /**
     * Gibt die Anzahl der betroffenen Artikel zurück.
     *
     * <p>Diese Methode gibt die Menge der Artikel zurück, die durch das Ereignis beeinflusst wurden.</p>
     *
     * @return die Anzahl der betroffenen Artikel
     */
    public int getAnzahl() {
        return anzahl;
    }
    /**
     * Gibt den Typ des Ereignisses zurück.
     *
     * <p>Diese Methode gibt den {@code EreignisTyp} zurück, der den Typ des Ereignisses beschreibt (z.B. NEU, KAUF, ERHOEHUNG, REDUZIERUNG).</p>
     *
     * @return der Typ des Ereignisses
     */
    public EreignisTyp getTyp() {
        return typ;
    }
    /**
     * Setzt die Anzahl der betroffenen Artikel.
     *
     * <p>Diese Methode aktualisiert die Menge der Artikel, die durch das Ereignis beeinflusst werden.</p>
     *
     * @param anzahl die neue Anzahl der betroffenen Artikel
     */
    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }
    /**
     * Gibt das Datum des Ereignisses als formatierte Zeichenkette zurück.
     *
     * <p>Diese Methode formatiert das Datum des Ereignisses gemäß dem definierten {@code DateTimeFormatter} und gibt es als {@code String} zurück.</p>
     *
     * @return das formatierte Datum des Ereignisses als {@code String}
     */
    public String simpleDatum() {
        return dateFormat.format(datum);
    }
    /**
     * Gibt eine textuelle Darstellung des Ereignisses zurück.
     *
     * <p>Diese Methode gibt eine {@code String}-Repräsentation des Ereignisses zurück, die das Datum, die Artikelbezeichnung, die Anzahl, die Person und den Typ des Ereignisses enthält.</p>
     *
     * @return eine {@code String}-Darstellung des Ereignisses
     */
    @Override
    public String toString() {
        return "Datum: " + simpleDatum() +
                ", Artikel: " + artikelbezeichnung +
                ", Anzahl: " + anzahl +
                ", Person: " + kundeOderMitarbeiter +
                ", Typ: " + typ;
    }
}