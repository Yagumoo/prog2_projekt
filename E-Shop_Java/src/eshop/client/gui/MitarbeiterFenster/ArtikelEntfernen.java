package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.FalscheEingabeException;
import eshop.common.exceptions.IdNichtVorhandenException;
import eshop.common.enitities.Artikel;
import eshop.common.enitities.MassengutArtikel;
import eshop.common.enitities.Mitarbeiter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Map;

public class ArtikelEntfernen extends JPanel {

    private final Eshopclientsite eShopclientsite;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable artikelTabelle;
    private DefaultTableModel tableModel;

    public ArtikelEntfernen(Eshopclientsite eShopclientsite, Mitarbeiter eingeloggterMitarbeiter) {
        this.eShopclientsite = eShopclientsite;
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
        JPanel panelWest = new JPanel(new GridBagLayout());
        GridBagConstraints westgbc = new GridBagConstraints();
        westgbc.insets = new Insets(10, 10, 10, 10);
        westgbc.fill = GridBagConstraints.VERTICAL;

        JPanel panelCenter = new JPanel(new BorderLayout());

        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelEast, BorderLayout.EAST);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWest, BorderLayout.WEST);
        this.add(panelCenter, BorderLayout.CENTER);

        // Search area in the north
        JLabel suchLabel = new JLabel("Sortieren nach: ");
        JButton sortByNumberButton = new JButton("Artikelnummer");
        JButton sortByNameButton = new JButton("Bezeichnung");

        panelNord.add(suchLabel);
        panelNord.add(sortByNumberButton);
        panelNord.add(sortByNameButton);

        // Add an article to the inventory
        JLabel nummerLabel = new JLabel("Artikelnummer:");
        westgbc.gridx = 0;
        westgbc.gridy = 0;
        panelWest.add(nummerLabel, westgbc);

        JTextField nummerFeld = new JTextField(5);
        westgbc.gridx = 1;
        westgbc.gridy = 0;
        panelWest.add(nummerFeld, westgbc);

        JButton entfernenButton = new JButton("Entfernen");
        westgbc.gridx = 0;
        westgbc.gridy = 1;
        panelWest.add(entfernenButton, westgbc);


        // Logout button in the south
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            eingeloggterMitarbeiter = null;
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShopclientsite));
            // Close the current window
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        entfernenButton.addActionListener(e -> {
            try {
                String ID = nummerFeld.getText();
                if(ID.isEmpty()){
                    throw new FalscheEingabeException();
                }
                int artikelnummer = Integer.parseInt(ID);
                eShopclientsite.loescheArtikel(eingeloggterMitarbeiter, artikelnummer);
                updateTabelle();
            } catch (IdNichtVorhandenException ex) {
                //throw new RuntimeException(ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (FalscheEingabeException ex) {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Artikelnummer ein", "Fehler", JOptionPane.ERROR_MESSAGE);
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
