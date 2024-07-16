package eshop.server.persistence;

import eshop.common.enitities.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.ParseException;


/**
 * Die Klasse {@code filePersistenceManager} bietet Funktionen zum Lesen aus und Schreiben in Dateien.
 *
 * <p>Die Klasse ermöglicht das Erstellen eines {@link BufferedReader} zum Lesen von Dateien und eines {@link PrintWriter} zum Schreiben in Dateien.</p>
 * <p>Sie enthält Methoden, um einen Reader und einen Writer für eine angegebene Datei zu erstellen, die für das Einlesen und Ausgeben von Daten verwendet werden können.</p>
 */
public class filePersistenceManager {

    private BufferedReader reader = null;
    private PrintWriter writer = null;
    /**
     * Erstellt einen {@link BufferedReader} zum Lesen der angegebenen Datei.
     *
     * <p>Diese Methode initialisiert ein {@code BufferedReader}-Objekt, das verwendet werden kann, um Daten aus der Datei zu lesen. Wenn der Reader bereits geöffnet ist, wird der vorherige Reader geschlossen und ersetzt.</p>
     *
     * @param datei Der Pfad zur Datei, die gelesen werden soll. Dieser Parameter darf nicht {@code null} sein und muss auf eine existierende Datei verweisen.
     * @throws FileNotFoundException Wenn die angegebene Datei nicht gefunden wird oder nicht geöffnet werden kann.
     * @throws IllegalArgumentException Wenn der Dateipfad {@code null} ist.
     */
    //Erstellen eines Readers damit man überhaupt nh datei lesen kann
    public void zumLesen(String datei) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(datei));
    }
    /**
     * Erstellt einen {@link PrintWriter} zum Schreiben in die angegebene Datei.
     *
     * <p>Diese Methode initialisiert ein {@code PrintWriter}-Objekt, das verwendet werden kann, um Daten in die Datei zu schreiben. Wenn der Writer bereits geöffnet ist, wird der vorherige Writer geschlossen und ersetzt.</p>
     *
     * @param datei Der Pfad zur Datei, in die geschrieben werden soll. Dieser Parameter darf nicht {@code null} sein.
     * @throws IOException Wenn beim Erstellen des Writers ein Fehler auftritt, z.B. wenn die Datei nicht geöffnet werden kann.
     * @throws IllegalArgumentException Wenn der Dateipfad {@code null} ist.
     */
    //Erstellen eines Writers damit man überhaupt nh datei schreiben kann
    public void zumSchreiben(String datei) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
    }


    //Schließen der Datei
    /**
     * Schließt den {@link BufferedReader} und den {@link PrintWriter}, wenn sie geöffnet sind.
     *
     * <p>Diese Methode schließt die offenen Streams und gibt die damit verbundenen Ressourcen frei. Falls der {@code BufferedReader} oder {@code PrintWriter} bereits geschlossen sind, hat der Aufruf der Methode keine Auswirkungen. Bei einem Fehler beim Schließen des {@code BufferedReader} wird dieser Fehler protokolliert, und die Methode gibt {@code false} zurück.</p>
     *
     * @return {@code true}, wenn alle Streams erfolgreich geschlossen wurden, andernfalls {@code false}.
     */
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
    /**
     * Liest eine Zeile aus der Datei, die vom {@link BufferedReader} gelesen wird.
     *
     * <p>Wenn der {@code BufferedReader} geöffnet ist, wird die nächste Zeile aus der Datei gelesen. Ist der {@code BufferedReader} nicht geöffnet, wird eine leere Zeichenkette zurückgegeben.</p>
     *
     * @return Die gelesene Zeile als {@link String}, oder eine leere Zeichenkette, wenn der {@code BufferedReader} nicht geöffnet ist.
     * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    private String liesZeile() throws IOException {
        if (reader != null)
            return reader.readLine();
        else
            return "";
    }
    //Schreibt eine Zeile
    /**
     * Schreibt eine Zeile in die Datei, die vom {@link PrintWriter} geschrieben wird.
     *
     * <p>Wenn der {@code PrintWriter} geöffnet ist, wird die übergebene Zeichenkette in die Datei geschrieben. Andernfalls wird keine Aktion durchgeführt.</p>
     *
     * @param daten Die zu schreibende Zeile als {@link String}.
     */
    private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
    }
    /**
     * Lädt eine Liste von Artikeln aus der angegebenen Datei und gibt sie als {@link Map} zurück.
     *
     * <p>Diese Methode öffnet die angegebene Datei zum Lesen, liest jede Zeile, und erstellt für jede Zeile ein {@link Artikel} oder {@link MassengutArtikel}-Objekt basierend auf den in der Zeile enthaltenen Informationen. Die Artikel werden dann in einer {@link Map} gespeichert, wobei die Artikelnummer als Schlüssel und das Artikelobjekt als Wert verwendet wird.</p>
     *
     * <p>Die Datei muss in folgendem Format vorliegen: "Artikelnummer,Bezeichnung,Bestand,Preis[,AnzahlMassengut]", wobei der letzte Parameter optional ist. Wenn die Zeile 5 Teile enthält, wird ein {@link MassengutArtikel} erstellt, andernfalls ein {@link Artikel}.</p>
     *
     * @param datei Der Pfad zur Datei, die die Artikeldaten enthält.
     * @return Eine {@link Map} mit den geladenen Artikeln, wobei die Schlüssel die Artikelnummern und die Werte die {@link Artikel} oder {@link MassengutArtikel}-Objekte sind.
     * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
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

    /**
     * Speichert eine Liste von Artikeln in der angegebenen Datei.
     *
     * <p>Diese Methode öffnet die angegebene Datei zum Schreiben und speichert die Artikel aus der übergebenen {@link Map}. Jeder Artikel wird als eine durch Kommas getrennte Zeichenkette in die Datei geschrieben. Wenn der Artikel ein {@link MassengutArtikel} ist, wird zusätzlich die Anzahl der Massengut-Einheiten in der Zeile gespeichert.</p>
     *
     * <p>Die Datei wird im folgenden Format erstellt: "Artikelnummer,Bezeichnung,Bestand,Preis[,AnzahlMassengut]", wobei der letzte Parameter optional ist. Wenn der Artikel ein MassengutArtikel ist, wird auch die Anzahl der Massengut-Einheiten angehängt.</p>
     *
     * @param datei Der Pfad zur Datei, in der die Artikeldaten gespeichert werden sollen.
     * @param artikelListe Eine {@link Map} von {@link Artikel} oder {@link MassengutArtikel} Objekten, die gespeichert werden sollen. Die Schlüssel sind die Artikelnummern und die Werte die entsprechenden {@link Artikel} oder {@link MassengutArtikel} Objekte.
     * @throws IOException Wenn ein Fehler beim Schreiben in die Datei auftritt.
     */
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

    /**
     * Lädt eine Liste von Kunden aus der angegebenen Datei.
     *
     * <p>Diese Methode öffnet die angegebene Datei zum Lesen und lädt die Kunden aus der Datei in eine {@link Map}. Jede Zeile in der Datei repräsentiert einen Kunden und wird in ein {@link Kunde} Objekt umgewandelt. Die Datei wird im CSV-Format erwartet, wobei die Daten durch Kommas getrennt sind.</p>
     *
     * <p>Die Datei muss im folgenden Format vorliegen: "Vorname,Nachname,Email,Username,Passwort,ID,Ort,PLZ,Strasse,StrassenNummer".</p>
     *
     * @param datei Der Pfad zur Datei, aus der die Kundendaten geladen werden sollen.
     * @return Eine {@link Map} von {@link Kunde} Objekten, wobei die Schlüssel die Kunden-IDs und die Werte die entsprechenden {@link Kunde} Objekte sind.
     * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
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
    /**
     * Speichert eine Liste von Kunden in die angegebene Datei.
     *
     * <p>Diese Methode öffnet die angegebene Datei zum Schreiben und speichert die Kunden aus der übergebenen {@link Map} in die Datei. Jeder Kunde wird in einer Zeile im CSV-Format gespeichert. Die Datei wird im folgenden Format erstellt oder überschrieben: "Vorname,Nachname,Email,Username,Passwort,ID,Ort,PLZ,Strasse,StrassenNummer".</p>
     *
     * @param datei Der Pfad zur Datei, in die die Kundendaten gespeichert werden sollen.
     * @param kundenListe Eine {@link Map} von {@link Kunde} Objekten, wobei die Schlüssel die Kunden-IDs und die Werte die entsprechenden {@link Kunde} Objekte sind.
     * @throws IOException Wenn ein Fehler beim Schreiben in die Datei auftritt.
     */
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
    /**
     * Lädt eine Liste von Mitarbeitern aus der angegebenen Datei.
     *
     * <p>Diese Methode öffnet die angegebene Datei zum Lesen und lädt die Mitarbeiter-Daten aus der Datei in eine {@link Map}. Jeder Mitarbeiter wird in der Datei im CSV-Format gespeichert, wobei die Datei folgendes Format hat: "Vorname,Nachname,Email,Username,Passwort,ID".</p>
     *
     * @param datei Der Pfad zur Datei, aus der die Mitarbeiterdaten geladen werden sollen.
     * @return Eine {@link Map} von {@link Mitarbeiter} Objekten, wobei die Schlüssel die Mitarbeiter-IDs und die Werte die entsprechenden {@link Mitarbeiter} Objekte sind.
     * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
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
    /**
     * Speichert eine Liste von Mitarbeitern in der angegebenen Datei.
     *
     * <p>Diese Methode öffnet die angegebene Datei zum Schreiben und speichert die Mitarbeiter-Daten im CSV-Format in die Datei. Jeder Mitarbeiter wird in der Datei durch die Werte "Vorname,Nachname,Email,Username,Passwort,ID" repräsentiert.</p>
     *
     * @param datei Der Pfad zur Datei, in der die Mitarbeiterdaten gespeichert werden sollen.
     * @param mitarbeiterListe Eine {@link Map} von {@link Mitarbeiter} Objekten, wobei die Schlüssel die Mitarbeiter-IDs und die Werte die entsprechenden {@link Mitarbeiter} Objekte sind.
     * @throws IOException Wenn ein Fehler beim Schreiben in die Datei auftritt.
     */
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
    /**
     * Lädt eine Liste von Ereignissen aus der angegebenen Datei.
     *
     * <p>Diese Methode öffnet die angegebene Datei zum Lesen und erstellt eine Liste von {@link Ereignis} Objekten basierend auf den in der Datei gespeicherten Daten. Die Datei muss im CSV-Format vorliegen, wobei jede Zeile die folgenden Werte enthält: "Datum,Artikelbezeichnung,Anzahl,EreignisTyp,PersonTyp,PersonID".</p>
     *
     * <p>Der EreignisTyp wird aus einem der vordefinierten Typen in {@link Ereignis.EreignisTyp} entnommen und die Person (Kunde oder Mitarbeiter) wird aus den bereitgestellten Maps {@code alleKunden} und {@code alleMitarbeiter} anhand der ID ermittelt.</p>
     *
     * @param datei Der Pfad zur Datei, aus der die Ereignisdaten geladen werden sollen.
     * @param alleKunden Eine {@link Map} von {@link Kunde} Objekten, wobei die Schlüssel die Kunden-IDs und die Werte die entsprechenden {@link Kunde} Objekte sind.
     * @param alleMitarbeiter Eine {@link Map} von {@link Mitarbeiter} Objekten, wobei die Schlüssel die Mitarbeiter-IDs und die Werte die entsprechenden {@link Mitarbeiter} Objekte sind.
     * @return Eine {@link List} von {@link Ereignis} Objekten, die die aus der Datei geladenen Ereignisse repräsentiert.
     * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     * @throws ParseException Wenn ein Fehler beim Parsen des Datums auftritt.
     */
    public List<Ereignis> ladeEreignisListe(String datei, Map<Integer, Kunde> alleKunden, Map<Integer, Mitarbeiter> alleMitarbeiter) throws IOException, ParseException {
        List<Ereignis> ereignisList = new ArrayList<>();
        zumLesen(datei);
        try {
            String line;
            Person person;
            while ((line = liesZeile()) != null) {
                String[] parts = line.split(",");
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                LocalDate datum = LocalDate.parse(parts[0], dateFormat);
                String artikelbezeichnung = parts[1];
                int anzahl = Integer.parseInt(parts[2]);
                Ereignis.EreignisTyp typ = Ereignis.EreignisTyp.valueOf(parts[3]);
                boolean istKunde = parts[4].equals("k");
                if (istKunde) {
                    person = alleKunden.get(Integer.parseInt(parts[5]));
                } else {
                    // Mitarbeiter aus Mitarbeiterliste raussuchen
                    person = alleMitarbeiter.get(Integer.parseInt(parts[5]));
                }
                Ereignis ereignis = new Ereignis(datum, artikelbezeichnung, anzahl, person, typ);
                ereignisList.add(ereignis);
            }
        }catch (Exception e){
            System.out.println(e);
        } finally {
            close();
        }
        return ereignisList;
    }
    /**
     * Speichert eine Liste von Ereignissen in eine Datei.
     *
     * Diese Methode erstellt einen {@link PrintWriter}, um die Ereignisse in die angegebene Datei zu schreiben.
     * Jedes Ereignis wird in einer Zeile im CSV-Format gespeichert. Die Zeilen enthalten die folgenden Informationen,
     * getrennt durch Kommata: Datum, Artikelbezeichnung, Anzahl, Ereignis-Typ, Kennzeichnung, und ID der beteiligten Person.
     *
     * Die Methode verwendet den {@link DateTimeFormatter} für das Datumsformat und konvertiert die Ereignisse in ein CSV-Format.
     * Am Ende wird der {@link PrintWriter} geschlossen, um sicherzustellen, dass alle Daten in die Datei geschrieben werden.
     *
     * @param datei Der Pfad zur Datei, in der die Ereignisse gespeichert werden sollen.
     * @param ereignisList Die Liste von {@link Ereignis}-Objekten, die gespeichert werden sollen.
     *
     * @throws IOException Wenn beim Schreiben in die Datei ein Fehler auftritt.
     */
    public void speicherEreignisListe(String datei, List<Ereignis> ereignisList) throws IOException {
        zumSchreiben(datei);
        try {
            for (Ereignis ereignis : ereignisList) {
                boolean istKunde = ereignis.getKundeOderMitarbeiter() instanceof Kunde;
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                String daten = String.join(",",
                        dateFormat.format(ereignis.getDatum()),
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
