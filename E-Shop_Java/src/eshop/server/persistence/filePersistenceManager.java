package eshop.server.persistence;

import eshop.common.enitities.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;



public class filePersistenceManager {

    /*
    private String Ereignisdatei = "Ereignisdatei";
    private String Mitarbeiterdatei = "Mitarbeiterdatei";
    private String Kundendatei = "Kundenmdatei";
    private String Artikeldatei = "Artikeldatei";
    */

    private BufferedReader reader = null;
    private PrintWriter writer = null;

    //Erstellen eines Readers damit man überhaupt nh datei lesen kann
    public void zumLesen(String datei) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(datei));
    }
    //Erstellen eines Writers damit man überhaupt nh datei schreiben kann
    public void zumSchreiben(String datei) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
    }


    //Schließen der Datei
    public boolean close() {
        boolean erfolgreich = true;

        if (writer != null) {
            writer.close();
        }

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                erfolgreich = false;
            }
        }

        return erfolgreich;
    }

    //Liest eine zeile
    private String liesZeile() throws IOException {
        if (reader != null)
            return reader.readLine();
        else
            return "";
    }
    //Schreibt eine Zeile
    private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
    }

    public Map<Integer, Artikel> ladeArtikelListe(String datei) throws IOException {
        Map<Integer, Artikel> artikelListe = new HashMap<>();
        zumLesen(datei);
        try {
            String line;
            while ((line = liesZeile()) != null) {
                String[] parts = line.split(",");
                int artikelnummer = Integer.parseInt(parts[0]);
                String artikelbezeichnung = parts[1];
                int artikelbestand = Integer.parseInt(parts[2]);
                double artikelPreis = Double.parseDouble(parts[3]);
                Artikel artikel;
                if (parts.length == 5) {
                    int anzahlMassengut = Integer.parseInt(parts[4]);
                    artikel = new MassengutArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis, anzahlMassengut);
                } else {
                    artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
                }
                artikelListe.put(artikelnummer, artikel);
            }
        } finally {
            close();
        }
        return artikelListe;
    }


    public void speicherArtikelListe(String datei, Map<Integer, Artikel> artikelListe) throws IOException {
        zumSchreiben(datei);
        try {
            for (Artikel artikel : artikelListe.values()) {
                String daten;
                if (artikel instanceof MassengutArtikel) {
                    MassengutArtikel massengutArtikel = (MassengutArtikel) artikel;
                    daten = artikel.getArtikelnummer() + "," + artikel.getArtikelbezeichnung() + "," + artikel.getArtikelbestand() + "," + artikel.getArtikelPreis() + "," + massengutArtikel.getAnzahlMassengut();
                } else {
                    daten = artikel.getArtikelnummer() + "," + artikel.getArtikelbezeichnung() + "," + artikel.getArtikelbestand() + "," + artikel.getArtikelPreis();
                }
                schreibeZeile(daten);
            }
        } finally {
            close();
        }
    }


    public Map<Integer, Kunde> ladeKundenListe(String datei) throws IOException {
        Map<Integer, Kunde> kundenListe = new HashMap<>();
        zumLesen(datei);
        try {
            String line;
            while ((line = liesZeile()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[5]);
                Kunde kunde = new Kunde(
                        parts[0], // vorname
                        parts[1], // nachname
                        parts[2], // email
                        parts[3], // username
                        parts[4], // password
                        id,       // id
                        parts[6], // ort
                        Integer.parseInt(parts[7]), // plz
                        parts[8], // strasse
                        Integer.parseInt(parts[9]) // strassenNummer
                );
                kundenListe.put(id, kunde);
            }
        } finally {
            close();
        }
        return kundenListe;
    }

    public void speicherKundenListe(String datei, Map<Integer, Kunde> kundenListe) throws IOException {
        zumSchreiben(datei);
        try {
            for (Kunde kunde : kundenListe.values()) {
                String daten = String.join(",",
                        kunde.getVorname(),
                        kunde.getNachname(),
                        kunde.getEmail(),
                        kunde.getUsername(),
                        kunde.getPassword(),
                        String.valueOf(kunde.getId()),
                        kunde.getOrt(),
                        String.valueOf(kunde.getPlz()),
                        kunde.getStrasse(),
                        String.valueOf(kunde.getStrassenNummer())
                );
                schreibeZeile(daten);
            }
        } finally {
            close();
        }
    }

    public Map<Integer, Mitarbeiter> ladeMitarbeiterListe(String datei) throws IOException {
        Map<Integer, Mitarbeiter> mitarbeiterListe = new HashMap<>();
        zumLesen(datei);
        try {
            String line;
            while ((line = liesZeile()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[5]);
                Mitarbeiter mitarbeiter = new Mitarbeiter(
                        parts[0], // vorname
                        parts[1], // nachname
                        parts[2], // email
                        parts[3], // username
                        parts[4], // password
                        id        // id
                        );
                mitarbeiterListe.put(id, mitarbeiter);
            }
        } finally {
            close();
        }
        return mitarbeiterListe;
    }

    public void speicherMitarbeiterListe(String datei, Map<Integer, Mitarbeiter> mitarbeiterListe) throws IOException {
        zumSchreiben(datei);
        try {
            for (Mitarbeiter mitarbeiter : mitarbeiterListe.values()) {
                String daten = String.join(",",
                        mitarbeiter.getVorname(),
                        mitarbeiter.getNachname(),
                        mitarbeiter.getEmail(),
                        mitarbeiter.getUsername(),
                        mitarbeiter.getPassword(),
                        String.valueOf(mitarbeiter.getId())
                );
                schreibeZeile(daten);
            }
        } finally {
            close();
        }
    }

    public List<Ereignis> ladeEreignisListe(String datei, Map<Integer, Kunde> alleKunden, Map<Integer, Mitarbeiter> alleMitarbeiter) throws IOException, ParseException {
        List<Ereignis> ereignisList = new ArrayList<>();
        zumLesen(datei);
        try {
            String line;
            Person person;
            while ((line = liesZeile()) != null) {
                String[] parts = line.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date datum = sdf.parse(parts[0]);
                String artikelbezeichnung = parts[1];
                int anzahl = Integer.parseInt(parts[2]);
                Ereignis.EreignisTyp typ = Ereignis.EreignisTyp.valueOf(parts[3]);
                boolean istKunde = parts[4].equals("k");
                if (istKunde) {
                    person = alleKunden.get(Integer.parseInt(parts[5]));
                    //System.out.println(("TEST: Kunde mit ID " + parts[5]));
                } else {
                    // Mitarbeiter aus Mitarbeiterliste raussuchen
                    person = alleMitarbeiter.get(Integer.parseInt(parts[5]));
                    //System.out.println(("TEST: Mitarbeiter mit ID " + parts[5]));
                }
                Ereignis ereignis = new Ereignis(datum, artikelbezeichnung, anzahl, person, typ);
                ereignisList.add(ereignis);
            }
        } finally {
            close();
        }
        return ereignisList;
    }

    public void speicherEreignisListe(String datei, List<Ereignis> ereignisList) throws IOException {
        zumSchreiben(datei);
        try {
            for (Ereignis ereignis : ereignisList) {
                boolean istKunde = ereignis.getKundeOderMitarbeiter() instanceof Kunde;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String daten = String.join(",",
                        sdf.format(ereignis.getDatum()),
                        ereignis.getArtikel(),
                        String.valueOf(ereignis.getAnzahl()),
                        ereignis.getTyp().toString(),
                        istKunde ? "k" : "m",
                        ereignis.getKundeOderMitarbeiter() != null ? ereignis.getKundeOderMitarbeiter().getId()+"" : "000"
                );
                schreibeZeile(daten);
            }
        } finally {
            close();
        }
    }
}
