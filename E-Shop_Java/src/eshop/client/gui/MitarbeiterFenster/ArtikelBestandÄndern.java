package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.*;
import eshop.common.enitities.Artikel;
import eshop.common.enitities.MassengutArtikel;
import eshop.common.enitities.Mitarbeiter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
/**
 * Die Klasse ArtikelBestandÄndern ist eine Erweiterung von JPanel und ermöglicht
 * das Ändern des Bestands eines Artikels.
 */
public class ArtikelBestandÄndern extends JPanel {
    /**
     * Erstellt eine Instanz von ArtikelBestandÄndern.
     *
     * @param eShopclientsite           Die Eshopclientsite-Instanz
     * @param eingeloggterMitarbeiter   Der eingeloggte Mitarbeiter
     */
    private final Eshopclientsite eShopclientsite;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable artikelTabelle;
    private DefaultTableModel tableModel;
    /**
     * Konstruktor der Klasse ArtikelBestandÄndern.
     *
     * @param eShopclientsite           Die Instanz der Eshopclientsite
     * @param eingeloggterMitarbeiter   Der eingeloggte Mitarbeiter
     */
    public ArtikelBestandÄndern(Eshopclientsite eShopclientsite, Mitarbeiter eingeloggterMitarbeiter) {
        this.eShopclientsite = eShopclientsite;
        this.eingeloggterMitarbeiter = eingeloggterMitarbeiter;
        this.setBackground(new Color(123, 50, 250));
        this.setLayout(new BorderLayout());

        // Tabelle erstellen
        initializeTable();

        // UI-Komponenten erstellen
        mitarbeiterSeite();
    }
    /**
     * Initialisiert die Tabelle zur Anzeige der Artikelinformationen.
     * Erstellt die Spaltennamen, initialisiert das TableModel und fügt die Tabelle
     * zum Panel hinzu. Führt anschließend ein initiales Update der Tabelle durch.
     */
    private void initializeTable() {
        String[] spaltenNamen = {"Artikelnummer", "Bezeichnung", "Bestand", "Preis", "Verpackungsgröße"};
        tableModel = new DefaultTableModel(spaltenNamen, 0);
        artikelTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(artikelTabelle);
        this.add(scrollPane, BorderLayout.CENTER);

        updateTabelle();  // Initiales Update der Tabelle
    }
    /**
     * Initialisiert die Benutzeroberfläche für die Mitarbeiterseite.
     * Erstellt verschiedene Panels und fügt UI-Komponenten wie Labels, Buttons und Textfelder hinzu.
     * Fügt ActionListener zu den Buttons hinzu, um entsprechende Aktionen auszuführen.
     */
    private void mitarbeiterSeite() {
        JPanel panelNord = new JPanel(new FlowLayout());
        JPanel panelSouth = new JPanel();
        JPanel panelWest = new JPanel(new GridBagLayout());
        GridBagConstraints westgbc = new GridBagConstraints();
        westgbc.insets = new Insets(10, 10, 10, 10);
        westgbc.fill = GridBagConstraints.VERTICAL;

        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWest, BorderLayout.WEST);

        // Search area in the north
        JLabel suchLabel = new JLabel("Sortieren nach: ");
        JButton sortByNumberButton = new JButton("Artikelnummer");
        JButton sortByNameButton = new JButton("Bezeichnung");

        panelNord.add(suchLabel);
        panelNord.add(sortByNumberButton);
        panelNord.add(sortByNameButton);

        // Artikelnummer und Bestand Textfelder erstellen
        JLabel nummerLabel = new JLabel("Artikelnummer:");
        westgbc.gridx = 0;
        westgbc.gridy = 0;
        panelWest.add(nummerLabel, westgbc);

        JTextField nummerFeld = new JTextField(5);
        westgbc.gridx = 1;
        westgbc.gridy = 0;
        panelWest.add(nummerFeld, westgbc);

        JLabel bestandLabel = new JLabel("Bestand:");
        westgbc.gridx = 0;
        westgbc.gridy = 1;
        panelWest.add(bestandLabel, westgbc);

        JTextField bestandFeld = new JTextField(5);
        westgbc.gridx = 1;
        westgbc.gridy = 1;
        panelWest.add(bestandFeld, westgbc);

        JButton ändernButton = new JButton("Ändern");
        westgbc.gridx = 0;
        westgbc.gridy = 2;
        westgbc.gridwidth = 2;
        panelWest.add(ändernButton, westgbc);

        // Logout-Button im Süden
        JButton logoutButton = new JButton("Logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            eingeloggterMitarbeiter = null;
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShopclientsite));
            // Schließen des aktuellen Fensters
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        // Button-ActionListener hinzufügen
        ändernButton.addActionListener(e -> {
            try {
                // Holen Sie sich die Textfeld-Werte, wenn der Button geklickt wird
                String artikelnummerText = nummerFeld.getText().trim();
                String neuerbestandText = bestandFeld.getText().trim();

                // Überprüfen, ob die Textfelder leer sind
                if (artikelnummerText.isEmpty() || neuerbestandText.isEmpty()) {
                    throw new FalscheEingabeException();
                }

                int artikelNummer = Integer.parseInt(artikelnummerText);
                int neuerBestand = Integer.parseInt(neuerbestandText);

                // Bestand ändern
                eShopclientsite.aendereArtikelBestand(eingeloggterMitarbeiter, artikelNummer, neuerBestand);

                // Tabelle aktualisieren
                updateTabelle();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Zahlen für die Artikelnummer und den neuen Bestand ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (IdNichtVorhandenException | KeinMassengutException | MinusZahlException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (FalscheEingabeException ex) {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (ArtikelExisitiertNichtException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        sortByNumberButton.addActionListener(e -> {
            // Sortiere die Tabelle nach Artikelnummer
            updateTabelleSortedByNumber();
        });

        sortByNameButton.addActionListener(e -> {
            // Sortiere die Tabelle nach Artikelbezeichnung
            updateTabelleSortedByName();
        });
    }
    /**
     * Aktualisiert die Tabelle mit allen Artikeln aus dem eShopclientsite.
     */
    public void updateTabelle() {
        tableModel.setRowCount(0); // Bestehende Daten löschen
        Map<Integer, Artikel> artikelMap = eShopclientsite.gibAlleArtikel();
        for (Map.Entry<Integer, Artikel> eintrag : artikelMap.entrySet()) {
            Artikel artikel = eintrag.getValue();
            if (artikel instanceof MassengutArtikel massengutArtikel) {
                Object[] daten = {
                        artikel.getArtikelnummer(),
                        artikel.getArtikelbezeichnung(),
                        artikel.getArtikelbestand(),
                        artikel.getArtikelPreis(),
                        massengutArtikel.getAnzahlMassengut()  // Hier holen wir die Massengut-Anzahl ab
                };
                tableModel.addRow(daten);
            } else {
                Object[] daten = {
                        artikel.getArtikelnummer(),
                        artikel.getArtikelbezeichnung(),
                        artikel.getArtikelbestand(),
                        artikel.getArtikelPreis()
                };
                tableModel.addRow(daten);
            }
        }
    }
    /**
     * Aktualisiert die Tabelle und sortiert die Artikel nach der Artikelnummer.
     */
    public void updateTabelleSortedByNumber() {
        tableModel.setRowCount(0); // Bestehende Daten löschen
        Map<Integer, Artikel> artikelMap = eShopclientsite.gibAlleArtikel();
        artikelMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(eintrag -> {
                    Artikel artikel = eintrag.getValue();
                    if (artikel instanceof MassengutArtikel massengutArtikel) {
                        Object[] daten = {
                                artikel.getArtikelnummer(),
                                artikel.getArtikelbezeichnung(),
                                artikel.getArtikelbestand(),
                                artikel.getArtikelPreis(),
                                massengutArtikel.getAnzahlMassengut()  // Hier holen wir die Massengut-Anzahl ab
                        };
                        tableModel.addRow(daten);
                    } else {
                        Object[] daten = {
                                artikel.getArtikelnummer(),
                                artikel.getArtikelbezeichnung(),
                                artikel.getArtikelbestand(),
                                artikel.getArtikelPreis()
                        };
                        tableModel.addRow(daten);
                    }
                });
    }
    /**
     * Aktualisiert die Tabelle und sortiert die Artikel nach der Artikelbezeichnung.
     */
    public void updateTabelleSortedByName() {
        tableModel.setRowCount(0); // Bestehende Daten löschen
        Map<Integer, Artikel> artikelMap = eShopclientsite.gibAlleArtikel();
        artikelMap.entrySet().stream()
                .sorted((a1, a2) -> a1.getValue().getArtikelbezeichnung().compareTo(a2.getValue().getArtikelbezeichnung()))
                .forEach(eintrag -> {
                    Artikel artikel = eintrag.getValue();
                    if (artikel instanceof MassengutArtikel massengutArtikel) {
                        Object[] daten = {
                                artikel.getArtikelnummer(),
                                artikel.getArtikelbezeichnung(),
                                artikel.getArtikelbestand(),
                                artikel.getArtikelPreis(),
                                massengutArtikel.getAnzahlMassengut()  // Hier holen wir die Massengut-Anzahl ab
                        };
                        tableModel.addRow(daten);
                    } else {
                        Object[] daten = {
                                artikel.getArtikelnummer(),
                                artikel.getArtikelbezeichnung(),
                                artikel.getArtikelbestand(),
                                artikel.getArtikelPreis()
                        };
                        tableModel.addRow(daten);
                    }
                });
    }
    /**
     * Lädt ein {@link ImageIcon} aus dem Ressourcenordner der Anwendung.
     *
     * Versucht, das Bild "Mann.png" aus dem Pfad "eshop/client/gui/Icon/" zu laden.
     * Gibt ein {@link ImageIcon} zurück, das das Bild darstellt, oder `null`, wenn die Datei nicht gefunden wurde.
     *
     * @return Ein {@link ImageIcon} Objekt für das Bild, oder `null` bei Fehler.
     */
    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/client/gui/Icon/Mann.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/client/gui/Icon/Mann.png");
            return null;
        }
    }
}
