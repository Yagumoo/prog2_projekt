package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.enitities.Artikel;
import eshop.common.enitities.Kunde;
import eshop.common.enitities.Rechnung;
import eshop.common.exceptions.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
/**
 * Die Klasse {@code WarenkorbGUI} stellt die Benutzeroberfläche für die Verwaltung des Warenkorbs eines Kunden im E-Shop bereit.
 *
 * <p>Diese Klasse zeigt den aktuellen Inhalt des Warenkorbs an und ermöglicht dem Kunden, die Menge von Artikeln zu ändern,
 * Artikel aus dem Warenkorb zu entfernen und den Kauf abzuschließen.</p>
 *
 * <p>Das Fenster enthält folgende GUI-Komponenten:</p>
 * <ul>
 *     <li>Eine Tabelle zur Anzeige der Artikel im Warenkorb.</li>
 *     <li>Buttons zum Bearbeiten des Warenkorbs, einschließlich zum Entfernen von Artikeln und zum Abschluss des Kaufs.</li>
 * </ul>
 *
 * @see Eshopclientsite
 * @see Kunde
 * @see KundenTabs
 */
public class WarenkorbGUI extends JPanel {

    private Eshopclientsite eShopclientsite;
    private JTable artikelTabelle;
    private Kunde eingelogterKunde;
    private DefaultTableModel tableModel;
    /**
     * Konstruktor, der die Benutzeroberfläche für die Warenkorb-Verwaltung erstellt.
     *
     * <p>Dieser Konstruktor initialisiert das Layout und die GUI-Komponenten des Panels, einschließlich der Tabelle für
     * die Anzeige des Warenkorbs und der Buttons zum Entfernen von Artikeln und zum Abschließen des Kaufs.</p>
     *
     * @param eShopclientsite Das E-Shop-Client-Objekt, das die Kommunikation mit dem Server verwaltet.
     * @param eingelogterKunde Der Kunde, der in diesem Fenster eingeloggt ist und dessen Warenkorb angezeigt wird.
     */
    public WarenkorbGUI(Eshopclientsite eShopclientsite, Kunde eingelogterKunde) {
        this.eShopclientsite = eShopclientsite;
        this.eingelogterKunde = eingelogterKunde;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(123, 50, 250));

        kundenseite();
        initializeTable();
    }
    /**
     * Initialisiert die Tabelle zur Anzeige der Artikel im Warenkorb des Kunden.
     *
     * <p>Diese Methode erstellt die Tabelle und das Tabellenmodell für die Anzeige der Artikel im Warenkorb. Die Tabelle zeigt
     * Informationen wie Artikelnummer, Bezeichnung und Menge der Artikel an.</p>
     */
    private void initializeTable() {
        String[] spaltenNamen = {"Artikelnummer", "Bezeichnung", "Preis", "Verpackungsgröße"};
        tableModel = new DefaultTableModel(spaltenNamen, 0);
        artikelTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(artikelTabelle);
        this.add(scrollPane, BorderLayout.CENTER);

        updateTabelle();  // Initiales Update der Tabelle
    }
    /**
     * Initialisiert die Benutzeroberfläche für die Verwaltung des Warenkorbs eines Kunden.
     *
     * <p>Diese Methode richtet die verschiedenen GUI-Komponenten ein, die der Kunde benötigt, um den Warenkorb zu verwalten.</p>
     *
     * <ul>
     *     <li>Im Norden der Benutzeroberfläche gibt es ein Panel für die Titel und eventuell für andere Nord-Elemente.</li>
     *     <li>Im Osten der Benutzeroberfläche gibt es ein Panel für die Eingabe der Artikelnummer und der neuen Menge sowie für den "Ändern"-Button.</li>
     *     <li>Im Westen der Benutzeroberfläche gibt es ein Panel für die Anzeige der Artikel im Warenkorb sowie für die "Entfernen"- und "Kaufen"-Buttons.</li>
     *     <li>Im Süden der Benutzeroberfläche gibt es einen "Logout"-Button.</li>
     * </ul>
     *
     * <p>Zusätzlich definiert diese Methode die Aktionen für die Buttons:</p>
     * <ul>
     *     <li>Der "Ändern"-Button aktualisiert die Menge eines Artikels im Warenkorb.</li>
     *     <li>Der "Entfernen"-Button leert den gesamten Warenkorb des Kunden.</li>
     *     <li>Der "Kaufen"-Button schließt den Kauf des Warenkorbs ab und zeigt die Rechnung an.</li>
     *     <li>Der "Logout"-Button loggt den Kunden aus und öffnet das Login-Fenster neu.</li>
     * </ul>
     */
    private void kundenseite() {
        JPanel panelNord = new JPanel(new FlowLayout());
        JPanel panelEastup = new JPanel(new GridBagLayout());
        JPanel panelSouth = new JPanel();
        JPanel panelWestup = new JPanel(new GridBagLayout());
        GridBagConstraints westUpgbc = new GridBagConstraints();
        westUpgbc.insets = new Insets(10, 10, 10, 10);
        westUpgbc.fill = GridBagConstraints.VERTICAL;

        GridBagConstraints eastUpgbc = new GridBagConstraints();
        eastUpgbc.insets = new Insets(10, 10, 10, 10);
        eastUpgbc.fill = GridBagConstraints.VERTICAL;

        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelEastup, BorderLayout.EAST);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWestup, BorderLayout.WEST);


        //Ostberreicht
        JLabel überschriftLabel2 = new JLabel("Menge von Artikel verändern");
        eastUpgbc.gridx = 0;
        eastUpgbc.gridy = 0;
        panelEastup.add(überschriftLabel2, eastUpgbc);

        JLabel artikelnummerFeldTitel = new JLabel("Artikelnummer");
        eastUpgbc.gridx = 0;
        eastUpgbc.gridy = 1;
        panelEastup.add(artikelnummerFeldTitel, eastUpgbc);

        JTextField artikelnummerFeld = new JTextField(5);
        eastUpgbc.gridx = 1;
        eastUpgbc.gridy = 1;
        panelEastup.add(artikelnummerFeld, eastUpgbc);

        JLabel mengenFeldTitel = new JLabel("Neue Menge");
        eastUpgbc.gridx = 0;
        eastUpgbc.gridy = 2;
        panelEastup.add(mengenFeldTitel, eastUpgbc);

        JTextField mengenFeld = new JTextField(5);
        eastUpgbc.gridx = 1;
        eastUpgbc.gridy = 2;
        panelEastup.add(mengenFeld, eastUpgbc);

        // Button zum Entfernen des Artikels aus dem Warenkorb
        JButton ändernButton = new JButton("Ändern");
        eastUpgbc.gridx = 0;
        eastUpgbc.gridy = 3;
        panelEastup.add(ändernButton, eastUpgbc);

        // Westbereich für Artikel hinzufügen und entfernen
        JLabel überschriftLabel = new JLabel("Artikel im Warenkorb");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 0;
        panelWestup.add(überschriftLabel, westUpgbc);

        // Button zum Entfernen des Artikels aus dem Warenkorb
        JButton entfernenButton = new JButton("Entfernen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 1;
        panelWestup.add(entfernenButton, westUpgbc);

        JButton kaufButton = new JButton("Kaufen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 2;
        panelWestup.add(kaufButton, westUpgbc);

        // Logout Button
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            eingelogterKunde = null;
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShopclientsite));
            this.setVisible(false); // Schließt das aktuelle Fenster
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        //Warenkorb leeren
        entfernenButton.addActionListener(e -> {
            eShopclientsite.warenkorbLeeren(eingelogterKunde);
            updateTabelle();
        });

        ändernButton.addActionListener(e -> {

            try {
                String artikelnummerText = artikelnummerFeld.getText();
                String mengeText = mengenFeld.getText();

                if (artikelnummerText.isEmpty() || mengeText.isEmpty()) {
                    throw new FalscheEingabeException();
                }

                int nummer = Integer.parseInt(artikelnummerText);
                Artikel artikel = eShopclientsite.sucheArtikelMitNummer(nummer);

                int bestand = Integer.parseInt(mengeText);

                eShopclientsite.bestandImWarenkorbAendern(eingelogterKunde, artikel, bestand);
                updateTabelle();
            } catch (IdNichtVorhandenException | MinusZahlException | IstLeerException | KeinMassengutException | BestandNichtAusreichendException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FalscheEingabeException ex) {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Artikelnummer ein", "Fehler", JOptionPane.ERROR_MESSAGE);
            }

        });

        kaufButton.addActionListener(e -> {
            try {
                double gesamtRechnungspreis = eShopclientsite.gesamtPreis(eingelogterKunde);
                Rechnung rechnung = eShopclientsite.warenkorbKaufen((Kunde) eingelogterKunde);
                zeigeRechnungPopup(rechnung.getKunde(), rechnung, gesamtRechnungspreis);
            } catch (BestandNichtAusreichendException | IstLeerException | IdNichtVorhandenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Artikelnummer ein", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            updateTabelle();
        });
        // Artikel aus dem Warenkorb in der Tabelle anzeigen

    }
    /**
     * Aktualisiert die Tabelle mit allen Artikeln aus dem eShopclientsite.
     */
    public void updateTabelle() {
        try {
            tableModel.setRowCount(0); // Bestehende Daten löschen

            // Kundendaten hinzufügen
            String kundendaten = "Name: " + eingelogterKunde.getVorname() + " " + eingelogterKunde.getNachname() + "|  Username: " + eingelogterKunde.getUsername() + " | ID: " + eingelogterKunde.getId();
            Object[] kundenDaten = {
                    kundendaten,
                    "Adresse: " + eingelogterKunde.getOrt() + " " + eingelogterKunde.getPlz() + " " + eingelogterKunde.getStrasse() + " " + eingelogterKunde.getStrassenNummer()
            };
            tableModel.addRow(kundenDaten);

            // Artikelinformationen abrufen
            Map<Artikel, Integer> artikelMap = eShopclientsite.gibWarenkorbArtikel(eingelogterKunde);
            for (Map.Entry<Artikel, Integer> eintrag : artikelMap.entrySet()) {
                Artikel artikel = eintrag.getKey();
                int menge = eintrag.getValue();

                Object[] daten = {
                        artikel.getArtikelnummer(),
                        artikel.getArtikelbezeichnung(),
                        artikel.getArtikelPreis(),
                        menge
                };
                tableModel.addRow(daten);
            }

            // Gesamtpreis hinzufügen
            double gesamtPreis = eShopclientsite.gesamtPreis(eingelogterKunde);
            Object[] gesamtPreisDaten = {
                    "Gesamtpreis",
                    gesamtPreis
            };
            tableModel.addRow(gesamtPreisDaten);

        } catch (IstLeerException | IdNichtVorhandenException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Zeigt ein Popup-Fenster mit den Rechnungsdetails für den Einkauf des Kunden an.
     *
     * <p>Diese Methode erstellt eine detaillierte Rechnung, die den Namen des Kunden, die Artikel im Warenkorb mit deren Menge und Einzelpreis sowie den Gesamtbetrag der Rechnung enthält.
     * Das Popup-Fenster informiert den Kunden über den Abschluss des Einkaufs und enthält alle relevanten Rechnungsinformationen.</p>
     *
     * @param kunde Der Kunde, für den die Rechnung erstellt wurde. Dieser Parameter wird verwendet, um den Namen des Kunden in der Rechnung anzuzeigen.
     * @param rechnung Die Rechnung, die die Details der gekauften Artikel enthält. Dieser Parameter wird verwendet, um die Artikel im Warenkorb und das Datum der Rechnung abzurufen.
     * @param gesamtRechnungspreis Der Gesamtbetrag der Rechnung. Dieser Parameter gibt den Endpreis für die gekauften Artikel an und wird in der Rechnung angezeigt.
     */
    private void zeigeRechnungPopup(Kunde kunde,Rechnung rechnung, double gesamtRechnungspreis) {
            // Erstellen Sie die Rechnungsdetails
            StringBuilder rechnungsDetails = new StringBuilder();
            rechnungsDetails.append("Rechnung\n");
            rechnungsDetails.append("==============================\n");
            rechnungsDetails.append("Kunde: ").append(kunde.getVorname()).append(" ").append(kunde.getNachname()).append("\n");
            rechnungsDetails.append("Artikel\n");

            // Holen Sie sich die Artikel und deren Menge direkt aus dem Warenkorb
            Map<Artikel, Integer> artikelMap = rechnung.getWarenkorbKopieMap();

            if (artikelMap == null || artikelMap.isEmpty()) {
                // Falls der Warenkorb leer ist, informieren wir den Nutzer
                rechnungsDetails.append("Ihr Warenkorb ist leer.\n");
            } else {
                // Iteriere über die Artikel und deren Menge im Warenkorb
                for (Map.Entry<Artikel, Integer> eintrag : artikelMap.entrySet()) {
                    Artikel artikel = eintrag.getKey();
                    int menge = eintrag.getValue();
                    double preis = artikel.getArtikelPreis();

                    rechnungsDetails.append(artikel.getArtikelbezeichnung())
                            .append(" - Menge: ").append(menge)
                            .append(" - Einzelpreis: ").append(preis)
                            .append("\n");
                }

                // Gesamtpreis der Rechnung
                rechnungsDetails.append("\nGesamtbetrag: ").append( gesamtRechnungspreis).append(" Euro\n");
            }

            rechnungsDetails.append("==============================\n");
            rechnungsDetails.append("Datum: "+ rechnung.getDatum() +"\n");
            //Date datumJetzt = new Date();
            //rechnungsDetails.append(datumJetzt.getDay()+ "-"+datumJetzt.getMonth()+"-"+datumJetzt.getYear()+"\n");
            rechnungsDetails.append("Vielen Dank für Ihren Einkauf!");
            // Zeigen Sie die Rechnung in einem Popup an
            JOptionPane.showMessageDialog(this, rechnungsDetails.toString(), "Rechnung", JOptionPane.INFORMATION_MESSAGE);
    }
}

