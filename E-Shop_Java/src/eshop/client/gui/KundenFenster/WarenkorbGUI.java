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

public class WarenkorbGUI extends JPanel {

    private Eshopclientsite eShopclientsite;
    private JTable artikelTabelle;
    private Kunde eingelogterKunde;
    private DefaultTableModel tableModel;

    public WarenkorbGUI(Eshopclientsite eShopclientsite, Kunde eingelogterKunde) {
        this.eShopclientsite = eShopclientsite;
        this.eingelogterKunde = eingelogterKunde;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(123, 50, 250));

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

                int nummer = Integer.parseInt(artikelnummerText);
                Artikel artikel = eShopclientsite.sucheArtikelMitNummer(nummer);

                int bestand = Integer.parseInt(mengeText);

                eShopclientsite.bestandImWarenkorbAendern(eingelogterKunde, artikel, bestand);
                updateTabelle();
            } catch (IdNichtVorhandenException | MinusZahlException | IstLeerException | KeinMassengutException |
                     BestandNichtAusreichendException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        kaufButton.addActionListener(e -> {
            try {
                double gesamtRechnungspreis = eShopclientsite.gesamtPreis(eingelogterKunde);
                Rechnung rechnung = eShopclientsite.warenkorbKaufen((Kunde) eingelogterKunde);
                zeigeRechnungPopup(rechnung.getKunde(), rechnung, gesamtRechnungspreis);
            } catch (BestandNichtAusreichendException | IstLeerException | IdNichtVorhandenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            updateTabelle();
        });
        // Artikel aus dem Warenkorb in der Tabelle anzeigen

    }
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

    private void zeigeRechnungPopup(Kunde kunde,Rechnung rechnung, double gesamtRechnungspreis) {
            // Erstellen Sie die Rechnungsdetails
            StringBuilder rechnungsDetails = new StringBuilder();
            rechnungsDetails.append("Rechnung\n");
            rechnungsDetails.append("==============================\n");
            rechnungsDetails.append("Kunde: ").append(kunde.getVorname()).append(" ").append(kunde.getNachname()).append("\n");
            rechnungsDetails.append("Artikel\n");

            // Holen Sie sich die Artikel und deren Menge direkt aus dem Warenkorb
            Map<Artikel, Integer> artikelMap = rechnung.getWarenkorbKopie();

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

