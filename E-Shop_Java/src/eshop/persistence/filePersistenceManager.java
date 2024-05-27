package eshop.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;

import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Mitarbeiter;
import eshop.enitities.Ereignis;
import eshop.enitities.Warenkorb;


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
        boolean success = true;

        if (writer != null) {
            writer.close();
        }

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            }
        }

        return success;
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

    public Map<Integer, Artikel> loadArtikelListe(String datei) throws IOException {
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
                Artikel artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
                artikelListe.put(artikelnummer, artikel);
            }
        } finally {
            close();
        }
        return artikelListe;
    }

    public void saveArtikelListe(String datei, Map<Integer, Artikel> artikelListe) throws IOException {
        zumSchreiben(datei);
        try {
            for (Artikel artikel : artikelListe.values()) {
                String daten = artikel.getArtikelnummer() + "," + artikel.getArtikelbezeichnung() + "," + artikel.getArtikelbestand() + "," + artikel.getArtikelPreis();
                schreibeZeile(daten);
            }
        } finally {
            close();
        }
    }

    public Map<Integer, Kunde> loadKundenListe(String datei) throws IOException {
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
                        id, // id
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

    public void saveKundenListe(String datei, Map<Integer, Kunde> kundenListe) throws IOException {
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

    public Map<Integer, Mitarbeiter> loadMitarbeiterListe(String datei) throws IOException {
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
                        id // id
                        );
                mitarbeiterListe.put(id, mitarbeiter);
            }
        } finally {
            close();
        }
        return mitarbeiterListe;
    }

    public void saveMitarbeiterListe(String datei, Map<Integer, Mitarbeiter> mitarbeiterListe) throws IOException {
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

}
