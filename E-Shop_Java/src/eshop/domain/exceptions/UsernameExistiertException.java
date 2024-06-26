package eshop.domain.exceptions;

public class UsernameExistiertException extends Exception {

    private String username;

    public UsernameExistiertException(String username) {
        super("Der Username '" + username + "' existiert bereits.");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

