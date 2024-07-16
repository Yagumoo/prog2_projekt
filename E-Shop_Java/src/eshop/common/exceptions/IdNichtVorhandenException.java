package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn eine ID verwendet wird, die nicht vorhanden ist.
 *
 * <p>Die Klasse {@code IdNichtVorhandenException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn eine angegebene ID nicht existiert.</p>
 *
 * @see java.lang.Exception
 */
public class IdNichtVorhandenException extends Exception {

    private int id;
    /**
     * Konstruktor, der eine Fehlermeldung mit der nicht vorhandenen ID erstellt.
     *
     * @param id die ID, die nicht vorhanden ist
     */
    public IdNichtVorhandenException(int id){
        super("Die ID " +id+ " existiert nicht");
        this.id = id;
    }

    public IdNichtVorhandenException(){
        super("Die ID existiert nicht");
    }
    /**
     * Gibt die ID zurück, die die Ausnahme ausgelöst hat.
     *
     * @return die nicht vorhandene ID
     */
    public int getId() {
        return id;
    }

    public IdNichtVorhandenException(){
        super("Die ID existiert nicht");
    }
}
