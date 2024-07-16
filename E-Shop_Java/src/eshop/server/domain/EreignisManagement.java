package eshop.server.domain;
import eshop.common.enitities.Ereignis;
import eshop.common.enitities.Kunde;
import eshop.common.enitities.Mitarbeiter;
import eshop.server.persistence.filePersistenceManager;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Verwaltet eine Liste von Ereignissen und ermöglicht das Laden und Speichern von Ereignisdaten.
 *
 * Diese Klasse ist verantwortlich für das Verwalten von Ereignissen innerhalb des E-Shops. Sie lädt Ereignisse aus einer Datei, speichert Ereignisse
 * und verwaltet eine Liste von {@link Ereignis}-Objekten. Die Klasse verwendet eine Instanz von {@link filePersistenceManager} für das Speichern und
 * Laden von Ereignisdaten.
 */
public class EreignisManagement {

    private filePersistenceManager fpm;// = new filePersistenceManager();
    private List<Ereignis> ereignisListe = new ArrayList<>();
    Date aktuellesDatum = new Date();

    /**
     * Konstruktor für die Klasse {@link EreignisManagement}.
     *
     * Initialisiert die Ereignisverwaltung mit dem angegebenen {@link filePersistenceManager}, der Liste aller Kunden und Mitarbeiter.
     *
     * Die Methode lädt die Ereignisliste aus der Datei "ereignis.txt" mithilfe des {@link filePersistenceManager} und fügt die geladenen Ereignisse
     * zur {@link #ereignisListe} hinzu. Bei einem Fehler während des Ladens wird die Fehlermeldung auf der Konsole ausgegeben.
     *
     * @param fpm Die Instanz von {@link filePersistenceManager}, die für das Laden und Speichern der Ereignisdaten verwendet wird.
     * @param alleKunden Eine Map aller Kunden, die im System existieren, die für das Zuordnen von Kunden in der Ereignisliste verwendet wird.
     * @param alleMitarbeiter Eine Map aller Mitarbeiter, die im System existieren, die für das Zuordnen von Mitarbeitern in der Ereignisliste verwendet wird.
     */
    public  EreignisManagement(filePersistenceManager fpm, Map<Integer, Kunde> alleKunden, Map<Integer, Mitarbeiter> alleMitarbeiter){
        //ereignisListe.add(new Ereignis(aktuellesDatum, "Initialisierung", 0, new Person("System", 0)));
        try {
            this.fpm = fpm;
            ereignisListe = fpm.ladeEreignisListe("ereignis.txt", alleKunden, alleMitarbeiter);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Gibt die Liste aller Ereignisse zurück.
     *
     * Diese Methode gibt die aktuelle Liste von {@link Ereignis}-Objekten zurück, die von der {@link #ereignisListe} verwaltet wird.
     *
     * @return Eine Liste von {@link Ereignis}-Objekten, die alle Ereignisse enthält.
     */
    public List<Ereignis> getEreignisse(){
        return  ereignisListe;
    }

    /**
     * Fügt ein neues Ereignis zur Ereignisliste hinzu.
     *
     * Diese Methode fügt ein neues {@link Ereignis}-Objekt zur {@link #ereignisListe} hinzu. Diese Methode speichert das Ereignis jedoch nicht automatisch
     * in der Datei. Erst beim beenden des Programms/Servers
     *
     * @param ereignis Das hinzuzufügende {@link Ereignis}-Objekt.
     */
    public void addEreignis(Ereignis ereignis){
        ereignisListe.add(ereignis);
    }

    //TODO: 30 Tage aufgabe
}
