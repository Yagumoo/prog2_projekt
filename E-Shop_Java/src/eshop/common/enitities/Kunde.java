package eshop.common.enitities;

/**
 * Repräsentiert einen Kunden im System.
 *
 * <p>Die Klasse {@code Kunde} erweitert die {@code Person}-Klasse um zusätzliche Attribute für die Adresse des Kunden.</p>
 */
public class Kunde extends Person {

    private int plz;
    private String ort;
    private String strasse;
    private int strassenNummer;
    /**
     * Konstruktor für die {@code Kunde}-Klasse.
     *
     * <p>Initialisiert einen neuen Kunden mit den angegebenen persönlichen Daten und Adressinformationen.</p>
     *
     * @param vorname        der Vorname des Kunden
     * @param nachname       der Nachname des Kunden
     * @param email          die E-Mail-Adresse des Kunden
     * @param username       der Benutzername des Kunden
     * @param password       das Passwort des Kunden
     * @param ort            der Ort der Adresse des Kunden
     * @param plz            die Postleitzahl der Adresse des Kunden
     * @param strasse        die Straße der Adresse des Kunden
     * @param strassenNummer die Hausnummer der Adresse des Kunden
     */
    public Kunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) {
        super(vorname, nachname, email, username, password);
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.strassenNummer = strassenNummer;
    }
    /**
     * Konstruktor für die {@code Kunde}-Klasse.
     *
     * <p>Initialisiert einen neuen Kunden mit den angegebenen persönlichen Daten, einer ID und Adressinformationen.</p>
     *
     * @param vorname        der Vorname des Kunden
     * @param nachname       der Nachname des Kunden
     * @param email          die E-Mail-Adresse des Kunden
     * @param username       der Benutzername des Kunden
     * @param password       das Passwort des Kunden
     * @param id             die eindeutige ID des Kunden
     * @param ort            der Ort der Adresse des Kunden
     * @param plz            die Postleitzahl der Adresse des Kunden
     * @param strasse        die Straße der Adresse des Kunden
     * @param strassenNummer die Hausnummer der Adresse des Kunden
     */
    public Kunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) {
        super(vorname, nachname, email, username, password, id);
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.strassenNummer = strassenNummer;
    }


    /**
     * Gibt die Postleitzahl des Kunden zurück.
     *
     * <p>Diese Methode gibt die Postleitzahl der Adresse des Kunden zurück.</p>
     *
     * @return die Postleitzahl des Kunden
     */
    public int getPlz() {

        return plz;
    }
    /**
     * Setzt die Postleitzahl des Kunden.
     *
     * <p>Diese Methode aktualisiert die Postleitzahl der Adresse des Kunden.</p>
     *
     * @param plz die neue Postleitzahl des Kunden
     */
    public void setPlz(int plz) {

        this.plz = plz;
    }

    /**
     * Gibt den Ort des Kunden zurück.
     *
     * <p>Diese Methode gibt den Ort der Adresse des Kunden zurück.</p>
     *
     * @return der Ort des Kunden
     */
    public String getOrt() {

        return ort;
    }
    /**
     * Setzt den Ort des Kunden.
     *
     * <p>Diese Methode aktualisiert den Ort der Adresse des Kunden.</p>
     *
     * @param ort der neue Ort des Kunden
     */
    public void setOrt(String ort) {

        this.ort = ort;
    }

    /**
     * Gibt die Straße der Adresse des Kunden zurück.
     *
     * <p>Diese Methode gibt die Straße der Adresse des Kunden zurück.</p>
     *
     * @return die Straße der Adresse des Kunden
     */
    public String getStrasse() {

        return strasse;
    }
    /**
     * Setzt die Straße der Adresse des Kunden.
     *
     * <p>Diese Methode aktualisiert die Straße der Adresse des Kunden.</p>
     *
     * @param strasse die neue Straße der Adresse des Kunden
     */
    public void setStrasse(String strasse) {

        this.strasse = strasse;
    }

    /**
     * Gibt die Hausnummer der Adresse des Kunden zurück.
     *
     * <p>Diese Methode gibt die Hausnummer der Adresse des Kunden zurück.</p>
     *
     * @return die Hausnummer der Adresse des Kunden
     */
    public int getStrassenNummer() {
        return strassenNummer;
    }
    /**
     * Setzt die Hausnummer der Adresse des Kunden.
     *
     * <p>Diese Methode aktualisiert die Hausnummer der Adresse des Kunden.</p>
     *
     * @param strassenNummer die neue Hausnummer der Adresse des Kunden
     */
    public void setStrassenNummer(int strassenNummer) {
        this.strassenNummer = strassenNummer;
    }
    /**
     * Gibt eine textuelle Darstellung des Kunden zurück.
     *
     * <p>Diese Methode gibt eine {@code String}-Repräsentation des Kunden zurück, die die persönlichen Daten sowie die Adresse des Kunden enthält.</p>
     *
     * @return eine {@code String}-Darstellung des Kunden
     */
    @Override
    public String toString() {
        return super.toString() + " |Addresse: " + ort + " " + plz + " " + strasse + " " + strassenNummer;
    }
}