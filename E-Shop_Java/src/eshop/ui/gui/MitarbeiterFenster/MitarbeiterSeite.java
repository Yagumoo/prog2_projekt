package eshop.ui.gui.MitarbeiterFenster;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.DoppelteIdException;
import eshop.domain.exceptions.KeinMassengutException;
import eshop.domain.exceptions.MinusZahlException;
import eshop.enitities.Artikel;
import eshop.enitities.MassengutArtikel;
import eshop.enitities.Mitarbeiter;
import eshop.ui.gui.LoginOptionenGUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class MitarbeiterSeite extends JPanel {

    private final E_Shop eShop;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable artikelTabelle;
    private DefaultTableModel tableModel;

    public MitarbeiterSeite(E_Shop eShop, Mitarbeiter eingeloggterMitarbeiter) {
        this.eShop = eShop;
        this.eingeloggterMitarbeiter = eingeloggterMitarbeiter;
        this.setBackground(new Color(123, 50, 250));
        this.setLayout(new BorderLayout());

        // Load image icon (if needed)
        ImageIcon image = loadImageIcon();
        if (image != null) {
            // Note: You cannot set an icon for a JPanel, only for a JFrame
        }
        //Mitarbeiter Textfelder erstellen
        mitarbeiterSeite();
        //Tabelle erstellen
        initializeTable();


    }

    private void initializeTable() {
        String[] spaltenNamen = {"Artikelnummer", "Bezeichnung", "Bestand", "Preis", "Verpackungsgröße"};
        tableModel = new DefaultTableModel(spaltenNamen, 0);
        artikelTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(artikelTabelle);
        this.add(scrollPane, BorderLayout.CENTER);

        updateTabelle();  // Initiales Update der Tabelle
    }

    private void mitarbeiterSeite() {
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

        JPanel panelCenter = new JPanel(new BorderLayout());

        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelEast, BorderLayout.EAST);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWestup, BorderLayout.WEST);
        this.add(panelCenter, BorderLayout.CENTER);

        // Search area in the north
        JLabel suchLabel = new JLabel("Suchbegriff: ");
        JTextField suchFeld = new JTextField(15);
        JButton suchButton = new JButton("Suchen");

        panelNord.add(suchLabel);
        panelNord.add(suchFeld);
        panelNord.add(suchButton);

        // Add an article to the inventory
        JLabel überschriftLabel = new JLabel("Artikel zum Lager hinzufügen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 0;
        panelWestup.add(überschriftLabel, westUpgbc);

        JLabel nummerLabel = new JLabel("ID");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 1;
        panelWestup.add(nummerLabel, westUpgbc);

        JTextField nummerFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 1;
        panelWestup.add(nummerFeld, westUpgbc);

        JLabel bezeichnungLabel = new JLabel("Bezeichnung");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 2;
        panelWestup.add(bezeichnungLabel, westUpgbc);

        JTextField bezeichnungFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 2;
        panelWestup.add(bezeichnungFeld, westUpgbc);

        JLabel bestandLabel = new JLabel("Menge");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 3;
        panelWestup.add(bestandLabel, westUpgbc);

        JTextField bestandFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 3;
        panelWestup.add(bestandFeld, westUpgbc);

        JLabel preisLabel = new JLabel("Preis");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 4;
        panelWestup.add(preisLabel, westUpgbc);

        JTextField preisFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 4;
        panelWestup.add(preisFeld, westUpgbc);

        JLabel fürpaketgrößeLabel = new JLabel("Nur Massengut");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 5;
        panelWestup.add(fürpaketgrößeLabel, westUpgbc);

        JLabel paketgrößeLabel = new JLabel("Paketgröße");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 6;
        panelWestup.add(paketgrößeLabel, westUpgbc);

        JTextField paketgrößeFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 6;
        panelWestup.add(paketgrößeFeld, westUpgbc);

        JButton hinzufügenButton = new JButton("Hinzufügen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 7;
        panelWestup.add(hinzufügenButton, westUpgbc);

        JButton entfernenButton = new JButton("Entfernen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 8;
        panelWestup.add(entfernenButton, westUpgbc);

        // Changing article inventory
        // Also in the west below adding articles
        JLabel nummer2Label = new JLabel("ID:");
        westDowngbc.gridx = 0;
        westDowngbc.gridy = 0;
        panelWestdown.add(nummer2Label, westDowngbc);

        JTextField nummer2Feld = new JTextField(15);
        westDowngbc.gridx = 1;
        westDowngbc.gridy = 0;
        panelWestdown.add(nummer2Feld, westDowngbc);

        JLabel bestand2Label = new JLabel("Bestand:");
        westDowngbc.gridx = 0;
        westDowngbc.gridy = 1;
        JTextField bestand2 = new JTextField(15);

        // Logout button in the south
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
            // Close the current window
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        hinzufügenButton.addActionListener(e -> {
            try {
                String ID = nummerFeld.getText();
                String Bezeichnung = bezeichnungFeld.getText();
                String Menge = bestandFeld.getText();
                String Preis = preisFeld.getText();
                String Paketgröße = paketgrößeFeld.getText();

                int id = Integer.parseInt(ID);
                int menge = Integer.parseInt(Menge);
                double preis = Double.parseDouble(Preis);

                Artikel artikel;
                if (!Paketgröße.isEmpty()) {
                    int paketgröße = Integer.parseInt(Paketgröße);
                    artikel = new MassengutArtikel(id, Bezeichnung, menge, preis, paketgröße);
                } else {
                    artikel = new Artikel(id, Bezeichnung, menge, preis);
                }

                eShop.addArtikel(eingeloggterMitarbeiter, artikel);
                updateTabelle();  // Aktualisieren Sie die Tabelle nach dem Hinzufügen eines Artikels


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Zahlen für die Artikelnummer, Menge oder Preis ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (DoppelteIdException | MinusZahlException | KeinMassengutException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void updateTabelle() {
        tableModel.setRowCount(0); // Bestehende Daten löschen
        Map<Integer, Artikel> artikelMap = eShop.gibAlleArtikel();
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

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Mann.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/ui/gui/Icon/Mann.png");
            return null;
        }
    }
}





