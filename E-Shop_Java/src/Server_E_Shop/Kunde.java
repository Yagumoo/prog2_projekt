package Server_E_Shop;
//Hello Leute
public class Kunde extends Person {
    int plz;
    String ort;
    String strasse;
    int strassenNummer;
    public Kunde(int id, String name, String username, String password, String ort, int plz, String strasse, int strassenNummer) {
        super(id, name, username, password);
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.strassenNummer = strassenNummer;
    }
}
