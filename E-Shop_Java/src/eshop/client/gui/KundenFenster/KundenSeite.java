package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.*;
import eshop.common.enitities.Artikel;
import eshop.common.enitities.Kunde;
import eshop.common.enitities.MassengutArtikel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
/**
 * Das Haupt-Panel für die Kunden-Seite im E-Shop-Client, das die Hauptansicht für Kunden darstellt.
 *
 * Diese Klasse bietet die Benutzeroberfläche für Kunden im E-Shop-System. Sie zeigt eine Tabelle mit Artikeln an, die vom Kunden angesehen und möglicherweise gekauft werden können.
 *
 * Die Klasse erbt von {@link JPanel} und verwendet ein {@link BorderLayout}, um die Benutzeroberfläche zu organisieren.
 * Die Klasse enthält eine Tabelle für die Anzeige von Artikeldaten und stellt eine Methode zum Aktualisieren dieser Tabelle bereit.
 *
 * @see Eshopclientsite
 * @see Kunde
 * @see Artikel
 */
public class KundenSeite extends JPanel {

    private Eshopclientsite eShopclientsite;
    private JTable artikelTabelle;
    private Kunde eingelogterKunde;
    private DefaultTableModel tableModel;
    /**
     * Konstruktor für das {@link KundenSeite} Panel.
     *
     * Initialisiert das Panel für die Kundenansicht im E-Shop. Setzt das Layout, die Hintergrundfarbe und ruft Methoden zur Erstellung der Benutzeroberfläche und der Tabelle auf.
     *
     * <p>Dieser Konstruktor konfiguriert das Panel und erstellt die Benutzeroberfläche, die einen Bereich für die Anzeige von Artikeln enthält. Es wird eine Methode
     * zum Initialisieren der Tabelle sowie zum Laden eines optionalen Icons für die Benutzeroberfläche verwendet.</p>
     *
     * @param eShopclientsite Die Client-Seite des E-Shop, die die Verbindung zur Server-Seite herstellt.
     * @param eingelogterKunde Der aktuell eingeloggte Kunde, der die Artikel im E-Shop ansieht.
     */
    public KundenSeite(Eshopclientsite eShopclientsite, Kunde eingelogterKunde) {
        this.eShopclientsite = eShopclientsite;
        this.eingelogterKunde = eingelogterKunde;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(123, 50, 250));

        // Load image icon (if needed)
        ImageIcon image = loadImageIcon();
        if (image != null) {
            // Note: You cannot set an icon for a JPanel, only for a JFrame
        }

        kundenseite();
        initializeTable();
    }
    /**
     * Initialisiert die Tabelle zur Anzeige der Artikelinformationen.
     * Erstellt die Spaltennamen, initialisiert das TableModel und fügt die Tabelle
     * zum Panel hinzu. Führt anschließend ein initiales Update der Tabelle durch.
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
     * Konfiguriert die Benutzeroberfläche für die Kundenansicht im E-Shop-Client.
     *
     * Diese Methode erstellt und organisiert alle grafischen Benutzeroberflächenkomponenten für die Kundenansicht.
     * Sie richtet Layouts und GUI-Elemente wie Buttons, Labels und Textfelder ein und fügt sie den entsprechenden Panels hinzu.
     *
     * <p>Die Methode baut die Benutzeroberfläche mit den folgenden Komponenten auf:</p>
     *
     * <ul>
     *     <li><b>Panel Nord:</b> Ein Bereich zur Sortierung der Artikel nach Nummer oder Bezeichnung.</li>
     *     <li><b>Panel West (oben):</b> Ein Bereich zum Hinzufügen von Artikeln zum Warenkorb, einschließlich Eingabefelder für Artikelnummer und Menge sowie Buttons zum Hinzufügen oder Entfernen von Artikeln.</li>
     *     <li><b>Panel Süd:</b> Ein Bereich für den Logout-Button, um sich aus dem E-Shop abzumelden.</li>
     *     <li><b>Panel West (unten):</b> (Derzeit nicht verwendet, aber für zukünftige Erweiterungen vorbereitet.)</li>
     *     <li><b>Panel East:</b> (Derzeit nicht verwendet, aber für zukünftige Erweiterungen vorbereitet.)</li>
     * </ul>
     *
     * <p>Die Methode definiert auch ActionListener für die Buttons, um Funktionen wie das Hinzufügen von Artikeln zum Warenkorb, das Entfernen von Artikeln und das Sortieren der Tabelle zu implementieren.</p>
     */
    private void kundenseite() {
        JPanel panelNord = new JPanel(new FlowLayout());
        JPanel panelEast = new JPanel(new GridLayout(6, 1));
        JPanel panelSouth = new JPanel();
        JPanel panelWestup = new JPanel(new GridBagLayout());
        GridBagConstraints westUpgbc = new GridBagConstraints();
        westUpgbc.insets = new Insets(10, 10, 10, 10);
        westUpgbc.fill = GridBagConstraints.VERTICAL;

        JPanel panelWestdown = new JPanel(new GridBagLayout());
        GridBagConstraints westDowngbc = new GridBagConstraints();
        westDowngbc.insets = new Insets(10, 10, 10, 10);
        westDowngbc.fill = GridBagConstraints.VERTICAL;

        //JPanel panelCenter = new JPanel(new BorderLayout());

        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelEast, BorderLayout.EAST);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWestup, BorderLayout.WEST);
        //this.add(panelCenter, BorderLayout.CENTER);

        // Alles über Artikelhinzufügen
        // Im Norden der Suchbereich
        // Search area in the north
        JLabel suchLabel = new JLabel("Sortieren nach: ");
        JButton sortByNumberButton = new JButton("Artikelnummer");
        JButton sortByNameButton = new JButton("Bezeichnung");

        panelNord.add(suchLabel);
        panelNord.add(sortByNumberButton);
        panelNord.add(sortByNameButton);

        // Im Westen einen Artikel Hinzufügen
        JLabel überschriftLabel = new JLabel("Artikel zum Warenkorb hinzufügen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 0;
        panelWestup.add(überschriftLabel, westUpgbc);

        JLabel nummerLabel = new JLabel("Artikelnummer");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 1;
        panelWestup.add(nummerLabel, westUpgbc);

        JTextField nummerFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 1;
        panelWestup.add(nummerFeld, westUpgbc);

        JLabel bestandLabel = new JLabel("Menge");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 3;
        panelWestup.add(bestandLabel, westUpgbc);

        JTextField bestandFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 3;
        panelWestup.add(bestandFeld, westUpgbc);

        JButton hinzufügenButton = new JButton("Hinzufügen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 7;
        panelWestup.add(hinzufügenButton, westUpgbc);

//        JButton entfernenButton = new JButton("Entfernen");
//        westUpgbc.gridx = 0;
//        westUpgbc.gridy = 8;
//        panelWestup.add(entfernenButton, westUpgbc);

        // Logout im Süden
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            eingelogterKunde = null;
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShopclientsite));
            //this.setVisible(false); // Schließt das aktuelle Fenster
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        hinzufügenButton.addActionListener(e -> {
            try {
                String nummerText = nummerFeld.getText();
                String bestandText = bestandFeld.getText();
                if(nummerText.isEmpty() || bestandText.isEmpty()){
                    throw new FalscheEingabeException();
                }
                int idNummer = Integer.parseInt(nummerText);
                Artikel artikel = eShopclientsite.sucheArtikelMitNummer(idNummer);
                int bestand = Integer.parseInt(bestandText);

                eShopclientsite.artikelInWarenkorbHinzufügen(eingelogterKunde, artikel, bestand);
                updateTabelle();  // Tabelle aktualisieren

            } catch (IdNichtVorhandenException | BestandNichtAusreichendException | KeinMassengutException | MinusZahlException | IstLeerException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Zahlen für die Artikelnummer und Menge ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (FalscheEingabeException ex) {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

//        entfernenButton.addActionListener(e -> {
//            //eShopclientsite.warenkorbLeeren(eingelogterKunde);
//            updateTabelle();  // Tabelle aktualisieren
//        });

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
                        artikel.getArtikelPreis(),
                        massengutArtikel.getAnzahlMassengut()  // Hier holen wir die Massengut-Anzahl ab
                };
                tableModel.addRow(daten);
            } else {
                Object[] daten = {
                        artikel.getArtikelnummer(),
                        artikel.getArtikelbezeichnung(),
                        artikel.getArtikelPreis()
                };
                tableModel.addRow(daten);
            }
        }
    }
    /**
     * Aktualisiert die Tabelle und sortiert die Artikel nach ihrer Artikelnummer.
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
                                artikel.getArtikelPreis(),
                                massengutArtikel.getAnzahlMassengut()  // Hier holen wir die Massengut-Anzahl ab
                        };
                        tableModel.addRow(daten);
                    } else {
                        Object[] daten = {
                                artikel.getArtikelnummer(),
                                artikel.getArtikelbezeichnung(),
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
                                artikel.getArtikelPreis(),
                                massengutArtikel.getAnzahlMassengut()  // Hier holen wir die Massengut-Anzahl ab
                        };
                        tableModel.addRow(daten);
                    } else {
                        Object[] daten = {
                                artikel.getArtikelnummer(),
                                artikel.getArtikelbezeichnung(),
                                artikel.getArtikelPreis()
                        };
                        tableModel.addRow(daten);
                    }
                });
    }
    /**
     * Lädt ein Bild-Icon aus den Ressourcen des Projekts.
     *
     * Diese Methode versucht, ein Bild-Icon von einem bestimmten Pfad in den Ressourcen des Projekts zu laden.
     * Wird von der Klasse verwendet, obwohl ein Icon für ein {@link JPanel} nicht direkt gesetzt werden kann.
     *
     * @return Das geladene {@link ImageIcon} oder null, wenn die Ressource nicht gefunden wurde.
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
