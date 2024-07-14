package eshop.server.domain;
import eshop.common.enitities.Ereignis;
import eshop.common.enitities.Kunde;
import eshop.common.enitities.Mitarbeiter;
import eshop.server.persistence.filePersistenceManager;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EreignisManagement {

    private filePersistenceManager fpm;// = new filePersistenceManager();
    private List<Ereignis> ereignisListe = new ArrayList<>();
    Date aktuellesDatum = new Date();

    public  EreignisManagement(filePersistenceManager fpm, Map<Integer, Kunde> alleKunden, Map<Integer, Mitarbeiter> alleMitarbeiter){
        //ereignisListe.add(new Ereignis(aktuellesDatum, "Initialisierung", 0, new Person("System", 0)));
        try {
            this.fpm = fpm;
            ereignisListe = fpm.ladeEreignisListe("ereignis.txt", alleKunden, alleMitarbeiter);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public List<Ereignis> getEreignisse(){
        return  ereignisListe;
    }

    public void addEreignis(Ereignis ereignis){
        ereignisListe.add(ereignis);
    }

    //TODO: 30 Tage aufgabe
}
