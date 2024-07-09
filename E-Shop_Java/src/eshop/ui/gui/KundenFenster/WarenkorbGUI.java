package eshop.ui.gui.KundenFenster;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.IstLeerException;
import eshop.enitities.Artikel;
import eshop.enitities.Person;
import eshop.enitities.Kunde;
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

    public WarenkorbGUI(E_Shop eShop) {
        this.eShop = eShop;
        this.eingelogterKunde = eingelogterKunde;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(123, 50, 250));

        initComponents();
        artikelTabelle();
    }

    private void initComponents() {
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

        // Nordbereich für Suchbereich
        JLabel suchLabel = new JLabel("Suchbegriff: ");
        JTextField suchFeld = new JTextField(15);
        JButton suchButton = new JButton("Suchen");

        panelNord.add(suchLabel);
        panelNord.add(suchFeld);
        panelNord.add(suchButton);

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

        // Logout Button
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
            this.setVisible(false); // Schließt das aktuelle Fenster
        });


        // Artikel aus dem Warenkorb in der Tabelle anzeigen

    }
    private void artikelTabelle() {
        String[] spaltenNamen = {"Artikelnummer", "Bezeichnung", "Menge", "Preis"};
        DefaultTableModel tableModel = new DefaultTableModel(spaltenNamen, 0);
        artikelTabelle = new JTable(tableModel);

        try {
            Map<Artikel, Integer> warenkorbMap = new HashMap<>();
            for (Map.Entry<Artikel, Integer> entry : warenkorbMap.entrySet()) {
                Artikel artikel = entry.getKey();
                int menge = entry.getValue();
                Object[] daten = {
                        artikel.getArtikelnummer(),
                        artikel.getArtikelbezeichnung(),
                        menge,
                        artikel.getArtikelPreis()
                };
                tableModel.addRow(daten);
            }
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(this, "Der eingeloggte Benutzer ist kein Kunde.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPane = new JScrollPane(artikelTabelle);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}
