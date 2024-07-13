package eshop.common.enitities;

public class Mitarbeiter extends Person{
    /**
     * @param vorname ist der Vorname vom Mitarbeiter
     * @param nachname ist der Nachname vom Mitarbeiter
     * @param email ist die E-mail vom Mitarbeiter
     * @param username ist der Benutzername vom Mitarbeiter
     * @param password ist das Password vom Mitarbeiter
     * */
    public Mitarbeiter(String vorname, String nachname, String email, String username, String password) {
        super(vorname, nachname, email, username, password);

    }

    public Mitarbeiter(String vorname, String nachname, String email, String username, String password, int id) {
        super(vorname, nachname, email, username, password, id);
    }

}
