package eshop.enitities;

public class Mitarbeiter extends Person{
    private boolean gesperrt;
    /**
     * @param vorname ist der Vorname vom Mitarbeiter
     * @param nachname ist der Nachname vom Mitarbeiter
     * @param email ist die E-mail vom Mitarbeiter
     * @param username ist der Benutzername vom Mitarbeiter
     * @param password ist das Password vom Mitarbeiter
     * */
    public Mitarbeiter(String vorname, String nachname, String email, String username, String password) {
        super(vorname, nachname, email, username, password);
        this.gesperrt = false; // Zu Beginn ist der Mitarbeiter nicht gesperrt
    }

    public boolean isGesperrt() {
        return gesperrt;
    }

    public void setGesperrt(boolean gesperrt) {
        this.gesperrt = gesperrt;
    }

}
