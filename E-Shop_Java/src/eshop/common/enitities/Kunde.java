package eshop.common.enitities;

//Hello Leute
public class Kunde extends Person {

    private int plz;
    private String ort;
    private String strasse;
    private int strassenNummer;
    /**
     * @param ort ist der Name der Stadt vom Kunden
     * @param plz ist die PLZ der Stadt
     * @param strasse ist der Strassenname
     * @param strassenNummer ist die Hausnummer
     *
     * */

    public Kunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) {
        super(vorname, nachname, email, username, password);
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.strassenNummer = strassenNummer;
    }

    // Getter und Setter für PLZ
    public int getPlz() {

        return plz;
    }

    public void setPlz(int plz) {

        this.plz = plz;
    }

    // Getter und Setter für Ort
    public String getOrt() {

        return ort;
    }

    public void setOrt(String ort) {

        this.ort = ort;
    }

    // Getter und Setter für Straße
    public String getStrasse() {

        return strasse;
    }

    public void setStrasse(String strasse) {

        this.strasse = strasse;
    }

    // Getter und Setter für Straßennummer
    public int getStrassenNummer() {
        return strassenNummer;
    }

    public void setStrassenNummer(int strassenNummer) {
        this.strassenNummer = strassenNummer;
    }

    @Override
    public String toString() {
        return super.toString() + " |Addresse: " + ort + " " + plz + " " + strasse + " " + strassenNummer;
    }
}