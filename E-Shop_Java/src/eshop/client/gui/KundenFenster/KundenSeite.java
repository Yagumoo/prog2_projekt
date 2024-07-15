package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.BestandNichtAusreichendException;
import eshop.common.exceptions.IdNichtVorhandenException;
import eshop.common.exceptions.KeinMassengutException;
import eshop.common.exceptions.MinusZahlException;
import eshop.common.enitities.Artikel;
import eshop.common.enitities.Kunde;
import eshop.common.enitities.MassengutArtikel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class KundenSeite extends JPanel {

    private Eshopclientsite eShopclientsite;
    private JTable artikelTabelle;
    private Kunde eingelogterKunde;
    private DefaultTableModel tableModel;

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

    private void initializeTable() {
        String[] spaltenNamen = {"Artikelnummer", "Bezeichnung", "Preis", "Verpackungsgröße"};
        tableModel = new DefaultTableModel(spaltenNamen, 0);
        artikelTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(artikelTabelle);
        this.add(scrollPane, BorderLayout.CENTER);

        updateTabelle();  // Initiales Update der Tabelle
    }

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

        JButton entfernenButton = new JButton("Entfernen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 8;
        panelWestup.add(entfernenButton, westUpgbc);

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

                int nummer = Integer.parseInt(nummerText);
                int bestand = Integer.parseInt(bestandText);

                eShopclientsite.artikelInWarenkorbHinzufügen(eingelogterKunde, nummer, bestand);
                updateTabelle();  // Tabelle aktualisieren

            } catch (IdNichtVorhandenException | BestandNichtAusreichendException | KeinMassengutException | MinusZahlException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Zahlen für die Artikelnummer und Menge ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        entfernenButton.addActionListener(e -> {
            //eShopclientsite.warenkorbLeeren(eingelogterKunde);
            updateTabelle();  // Tabelle aktualisieren
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


    //Artikelliste asugeben lassen
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
