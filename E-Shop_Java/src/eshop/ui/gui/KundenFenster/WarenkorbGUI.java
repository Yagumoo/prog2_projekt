package eshop.ui.gui.KundenFenster;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.*;
import eshop.enitities.*;
import eshop.domain.*;
import eshop.ui.gui.LoginOptionenGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WarenkorbGUI extends JPanel {

    private E_Shop eShop;
    private JTable artikelTabelle;
    private Person eingelogterKunde;
    private DefaultTableModel tableModel;

    public WarenkorbGUI(E_Shop eShop, Kunde eingelogterKunde) {
        this.eShop = eShop;
        this.eingelogterKunde = eingelogterKunde;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(123, 50, 250));

        initComponents();
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

    private void initComponents() {
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

        JPanel panelCenter = new JPanel(new BorderLayout());

        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelEastup, BorderLayout.EAST);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWestup, BorderLayout.WEST);
        this.add(panelCenter, BorderLayout.CENTER);

        // Nordbereich für Suchbereich
        JLabel suchLabel = new JLabel("Suchbegriff: ");
        JTextField suchFeld = new JTextField(15);
        JButton suchButton = new JButton("Suchen");

        panelNord.add(suchLabel);
        panelNord.add(suchFeld);
        panelNord.add(suchButton);

        //Ostberreicht
        JLabel überschriftLabel2 = new JLabel("Menge von Artikel erhöhen");
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
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
            this.setVisible(false); // Schließt das aktuelle Fenster
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        //Warenkorb leeren
        entfernenButton.addActionListener(e -> {
            eShop.warenkorbLeeren(eingelogterKunde);
            updateTabelle();
        });

        ändernButton.addActionListener(e -> {

            try {
                String artikelnummerText = artikelnummerFeld.getText();
                String mengeText = mengenFeld.getText();

                int nummer = Integer.parseInt(artikelnummerText);
                Artikel artikel = eShop.sucheArtikelMitNummer(nummer);

                int bestand = Integer.parseInt(mengeText);
                eShop.bestandImWarenkorbAendern(eingelogterKunde, artikel, bestand);
                updateTabelle();
            } catch (IdNichtVorhandenException | MinusZahlException | IstLeerException | KeinMassengutException | BestandNichtAusreichendException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        kaufButton.addActionListener(e -> {
            try {
                eShop.warenkorbKaufen(eingelogterKunde);
            } catch (BestandNichtAusreichendException | IstLeerException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            updateTabelle();
        });
        // Artikel aus dem Warenkorb in der Tabelle anzeigen

    }
    public void updateTabelle() {
        try {
            tableModel.setRowCount(0); // Bestehende Daten löschen
            String artikelString = eShop.printWarenkorbArtikel(eingelogterKunde);
            if (artikelString.equals("Person ist kein Kunde")) {
                // Zeige eine Fehlermeldung oder mache nichts
                return;
            }

            // Kundendaten hinzufügen
            if (eingelogterKunde instanceof Kunde kunde) {
                String kundendaten = "Name: " + kunde.getVorname() + " " + kunde.getNachname() + " | Username: " + kunde.getUsername() + " | ID: " + kunde.getId();
                Object[] kundenDaten = {
                        kundendaten,
                        "Adresse: " + kunde.getOrt() + " " + kunde.getPlz() + " " + kunde.getStrasse() + " " + kunde.getStrassenNummer()
                };
                tableModel.addRow(kundenDaten);
            }

            // Parsen des Strings, um Artikelinformationen zu extrahieren
            String[] artikelZeilen = artikelString.split("\n");
            for (String zeile : artikelZeilen) {
                if (zeile.startsWith("Artikelnummer: ")) {
                    // Extrahiere die Artikelinformationen aus der Zeile
                    String[] artikelDaten = zeile.split(" \\| ");
                    String artikelnummer = artikelDaten[0].split(": ")[1];
                    String bezeichnung = artikelDaten[1].split(": ")[1];
                    int menge = Integer.parseInt(artikelDaten[2].split(": ")[1]);
                    double preis = Double.parseDouble(artikelDaten[3].split(": ")[1]);

                    // Fügen Sie die Daten zur Tabelle hinzu
                    Object[] daten = {
                            artikelnummer,
                            bezeichnung,
                            preis,
                            menge
                    };
                    tableModel.addRow(daten);
                }
            }

            // Gesamtpreis hinzufügen
            double gesamtPreis = eShop.gesamtPreis(eingelogterKunde);
            Object[] gesamtPreisDaten = {
                    "Gesamtpreis",
                    gesamtPreis
            };
            tableModel.addRow(gesamtPreisDaten);

        } catch (IstLeerException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




}
