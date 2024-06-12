package eshop.domain;
import eshop.enitities.Ereignis;
import eshop.enitities.Kunde;
import eshop.enitities.Mitarbeiter;
import eshop.enitities.Person;
import eshop.persistence.filePersistenceManager;

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

            ereignisListe = fpm.loadEreignisListe("ereignis.txt", alleKunden, alleMitarbeiter);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public List<Ereignis> getEreignisse(){
        return  ereignisListe;
    }

    public void addEreignis(/*Person person,*/ Ereignis ereignis){
//        ereignis.setBetroffenePerson(person);
        ereignisListe.add(ereignis);
    }
}
