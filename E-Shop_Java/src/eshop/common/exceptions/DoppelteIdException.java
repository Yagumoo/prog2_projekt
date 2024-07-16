package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn versucht wird, eine ID zu verwenden, die bereits vergeben ist.
 *
 * <p>Die Klasse {@code DoppelteIdException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn eine ID doppelt vergeben wird.</p>
 *
 * @see java.lang.Exception
 */

public class DoppelteIdException extends Exception {
    /**
     * Konstruktor, der eine Fehlermeldung mit der doppelten ID erstellt.
     *
     * @param id die ID, die bereits vergeben ist
     */
    public DoppelteIdException(int id) {
        super("Die ID " + id + " ist bereits vergeben");
    }
}