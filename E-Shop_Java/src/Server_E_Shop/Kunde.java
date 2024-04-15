package Server_E_Shop;
//Hello Leute
public class Kunde extends Person {
    int plz;
    String ort;
    String strasse;
    int strassenNummer;

    public Kunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) {
        super(vorname, nachname, email, username, password, id);
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.strassenNummer = strassenNummer;
    }


}