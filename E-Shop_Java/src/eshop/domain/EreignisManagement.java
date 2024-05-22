package eshop.domain;
import eshop.enitities.Ereignis;
import eshop.enitities.Person;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class EreignisManagement {

    private List<Ereignis> ereignisListe = new ArrayList<>();
    Date aktuellesDatum = new Date();

    public  EreignisManagement(){
        //ereignisListe.add(new Ereignis(aktuellesDatum, "Initialisierung", 0, new Person("System", 0)));
    }

    public List<Ereignis> getEreignisse(){
        return  ereignisListe;
    }

    public void addEreignis(Ereignis ereignis){
        ereignisListe.add(ereignis);
    }
}
