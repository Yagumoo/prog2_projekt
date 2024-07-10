package eshop.domain.exceptions;

public class FilterException extends Exception {
    public FilterException(String fehlenderFilter) {
        super("Mindestens ein Filter muss gesetzt sein: " + fehlenderFilter);
    }
}
