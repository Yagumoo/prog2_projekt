package eshop.ui.gui.MitarbeiterFenster;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.IdNichtVorhandenException;
import eshop.enitities.Artikel;
import eshop.enitities.MassengutArtikel;
import eshop.enitities.Mitarbeiter;
import eshop.ui.gui.LoginOptionenGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class ArtikelEntfernen extends JPanel {

    private final E_Shop eShop;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable artikelTabelle;
    private DefaultTableModel tableModel;

    public ArtikelEntfernen(E_Shop eShop, Mitarbeiter eingeloggterMitarbeiter) {
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
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
            // Close the current window
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        entfernenButton.addActionListener(e -> {
            try {
                String ID = nummerFeld.getText();
                int artikelnummer = Integer.parseInt(ID);

                eShop.loescheArtikel(eingeloggterMitarbeiter, artikelnummer);
                updateTabelle();
            } catch (IdNichtVorhandenException ex) {
                //throw new RuntimeException(ex);
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
