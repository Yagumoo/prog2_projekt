package eshop.common.enitities;

import eshop.server.domain.WarenkorbManagement;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

/**
 * Die Klasse {@code Rechnung} repräsentiert eine Rechnung für einen Kunden basierend auf einem {@link Warenkorb} und enthält Informationen wie den Kunden, das Datum und eine Kopie des Warenkorbs.
 *
 * <p>Die Klasse ermöglicht es, Informationen über den Kunden abzurufen, das Datum der Rechnung zu erhalten und eine Kopie des Warenkorbs zu erhalten. Außerdem kann der Gesamtpreis aller Artikel im Warenkorb berechnet werden.</p>
 */
public class Rechnung {
    private Artikel artikel;
    private Warenkorb warenkorb;
    private Kunde kunde;
    private WarenkorbManagement warenkorbManagement;
    private Map<Artikel, Integer> warenkorbKopieMap;
    private LocalDate datumaktuel;

    /**
     * Erstellt eine neue Rechnung für einen Kunden basierend auf einem Warenkorb.
     *
     * <p>Der Konstruktor initialisiert die Rechnung mit dem übergebenen Warenkorb und Kunden, erstellt eine Kopie des Warenkorbs und setzt das aktuelle Datum als Datum der Rechnung.</p>
     *
     * @param warenkorb Der Warenkorb, der die Artikel und deren Mengen enthält, die in der Rechnung aufgeführt sind. Dieser Parameter darf nicht {@code null} sein.
     * @param kunde     Der Kunde, für den die Rechnung erstellt wird. Dieser Parameter darf nicht {@code null} sein.
     *
     */
    public Rechnung(Warenkorb warenkorb, Kunde kunde){
        this.warenkorb = warenkorb;
        this.kunde = kunde;
        this.warenkorbKopieMap = new HashMap<>();
        this.warenkorbKopieMap.putAll(warenkorb.getWarenkorbMap());
        this.datumaktuel = LocalDate.now();
    }

    /**
     * Gibt den Kunden zurück, der mit dieser Rechnung verbunden ist.
     *
     * <p>Diese Methode liefert das {@link Kunde}-Objekt, das den Kunden repräsentiert, für den diese Rechnung ausgestellt wurde.</p>
     *
     * @return Der Kunde, der mit dieser Rechnung verbunden ist.
     */
    public Kunde getKunde(){
        return kunde;
    }
    /**
     * Gibt das Datum der Rechnung im Format "yyyy-MM-dd" zurück.
     *
     * <p>Diese Methode ruft {@link #simpleDatum()} auf, um das Datum im Format "Jahr-Monat-Tag" zu erhalten.</p>
     *
     * @return Das Datum der Rechnung als String im Format "yyyy-MM-dd".
     *
     * @see #simpleDatum()
     */
    public String getDatum() {
        return simpleDatum();
    }

    /**
     * Gibt den Warenkorb zurück, der für diese Rechnung verwendet wurde.
     *
     * <p>Diese Methode liefert das {@link Warenkorb}-Objekt, das die Artikel und deren Mengen enthält, die in der Rechnung enthalten sind.</p>
     *
     * @return Der Warenkorb, der für diese Rechnung verwendet wurde.
     */
    public Warenkorb getWarenkorb() {
        return warenkorb;
    }

    /**
     * Gibt eine Kopie der Map der Artikel und deren Mengen im Warenkorb zurück.
     *
     * <p>Diese Methode liefert eine unveränderliche Kopie der {@code Map}, die die Artikel und deren Mengen im Warenkorb enthält.
     * Änderungen an der zurückgegebenen Map beeinflussen nicht den Zustand des internen {@code warenkorbKopieMap}.</p>
     *
     * @return Eine unveränderliche Kopie der Map mit den Artikeln und deren Mengen im Warenkorb.
     */
    public Map<Artikel, Integer> getWarenkorbKopieMap(){
        return warenkorbKopieMap;
    }

    /**
     * Gibt das aktuelle Datum im Format "yyyy-MM-dd" zurück.
     *
     * <p>Diese Methode formatiert das aktuelle Datum und die Uhrzeit auf das Format "Jahr-Monat-Tag" und gibt es als String zurück.</p>
     *
     * @return Das aktuelle Datum als String im Format "yyyy-MM-dd".
     */
    public String simpleDatum() {
        // Formatierung des Datums und der Uhrzeit
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return datumaktuel.format(formatter);
    }

    /**
     * Gibt eine String-Darstellung der Rechnung zurück.
     *
     * <p>Diese Methode erstellt eine textuelle Darstellung der Rechnung, die den Kunden, das Datum und die Details aller Artikel im Warenkorb anzeigt. Jeder Artikel wird mit seiner Artikelnummer, Bezeichnung, Menge und Preis sowie dem Datum der Rechnung angezeigt.</p>
     *
     * @return Eine String-Darstellung der Rechnung, einschließlich Kundeninformationen, Artikelübersicht und Rechnungsdatum.
     */
    @Override
    public String toString() {
        String rechnung = "Rechnung für Kunde: " + kunde.getVorname() + " " + kunde.getNachname() +"\n" +
                "Wohnort:"+ kunde.getPlz() + " " + kunde.getOrt() + " " + kunde.getStrasse() + " " + kunde.getStrassenNummer() +  "\n\n";

        for (Map.Entry<Artikel, Integer> entry : warenkorbKopieMap.entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            rechnung += "Artikel: \n" + "Artikelnummer: " + artikel.getArtikelnummer() + " | ";
            rechnung += "Bezeichnung: " + artikel.getArtikelbezeichnung() + " | ";
            rechnung += "Menge: " + menge + " | ";
            rechnung += "Preis: " + artikel.getArtikelPreis() + "\n";
            rechnung += "Datum: " + getDatum() + "\n";
        }

        return rechnung;
    }


}
