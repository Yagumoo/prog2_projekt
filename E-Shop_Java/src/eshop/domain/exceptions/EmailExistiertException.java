package eshop.domain.exceptions;

public class EmailExistiertException extends Exception {

    private String email;

    public EmailExistiertException(String email) {
        super("Die E-mail '" + email + "' existiert bereits.");
        this.email = email;
    }

    public String getUsername() {
        return email;
    }
}
