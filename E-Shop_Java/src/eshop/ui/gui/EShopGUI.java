package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.BestandNichtAusreichendException;
import eshop.domain.exceptions.IdNichtVorhandenException;
import eshop.domain.exceptions.KeinMassengutException;
import eshop.domain.exceptions.MinusZahlException;
import eshop.enitities.Artikel;
import eshop.enitities.MassengutArtikel;
import eshop.enitities.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class EShopGUI extends JPanel {

    private E_Shop eShop;
    private JTable artikelTabelle;
    private Person eingelogterKunde;

    public EShopGUI(E_Shop eShop) {
        this.eShop = eShop;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(123, 50, 250));

        Mitarbeiterseite();
        artikelTabelle();
    }

    private void Mitarbeiterseite() {
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

        // Alles über Artikelhinzufügen
        // Im Norden der Suchbereich
        JLabel suchLabel = new JLabel("Suchbegriff: ");
        JTextField suchFeld = new JTextField(15);
        JButton suchButton = new JButton("Suchen");

        panelNord.add(suchLabel);
        panelNord.add(suchFeld);
        panelNord.add(suchButton);

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
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
            this.setVisible(false); // Schließt das aktuelle Fenster
        });

        hinzufügenButton.addActionListener(e -> {
            try {
                String nummerText = nummerFeld.getText();
                String bestandText = bestandFeld.getText();


                int artikelnummer = Integer.parseInt(nummerText);
                int menge = Integer.parseInt(bestandText);



                eShop.artikelInWarenkorbHinzufügen(eingelogterKunde, artikelnummer, menge);
                artikelTabelle();  // Tabelle aktualisieren

            } catch (IdNichtVorhandenException | BestandNichtAusreichendException | KeinMassengutException | MinusZahlException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Zahlen für die Artikelnummer und Menge ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        entfernenButton.addActionListener(e -> {
            eShop.warenkorbLeeren(eingelogterKunde);
            artikelTabelle();  // Tabelle aktualisieren
        });
    }


    //Artikelliste asugeben lassen
    private void artikelTabelle() {
        String[] spaltenNamen = {"Artikelnummer", "Bezeichnung", "Bestand", "Preis", "Verpackungsgröße"};
        DefaultTableModel tableModel = new DefaultTableModel(spaltenNamen, 0);
        artikelTabelle = new JTable(tableModel);


        Map<Integer, Artikel> artikelMap = eShop.gibAlleArtikel();
        for (Map.Entry<Integer, Artikel> eintrag : artikelMap.entrySet()) {
            Artikel artikel = eintrag.getValue();
            if(artikel instanceof MassengutArtikel massengutArtikel){
                Object[] daten = {
                        artikel.getArtikelnummer(),
                        artikel.getArtikelbezeichnung(),
                        artikel.getArtikelbestand(),
                        artikel.getArtikelPreis(),
                        massengutArtikel.getAnzahlMassengut()
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

        JScrollPane scrollPane = new JScrollPane(artikelTabelle);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
