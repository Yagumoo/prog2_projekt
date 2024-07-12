package eshop.common.exceptions;

public class IdNichtVorhandenException extends Exception {

    private int id;
    public IdNichtVorhandenException(int id){
        super("Die ID " +id+ " existiert nicht");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
