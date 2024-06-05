package eshop.domain.exceptions;

public class IdNichtVorhandenException extends Exception{
    public IdNichtVorhandenException(int id){
        System.out.println("Die ID " +id+ " exestiert nicht");
    }
}
