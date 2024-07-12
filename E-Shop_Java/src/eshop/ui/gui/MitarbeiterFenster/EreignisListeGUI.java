package eshop.ui.gui.MitarbeiterFenster;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.*;
import eshop.enitities.Artikel;
import eshop.enitities.Ereignis;
import eshop.enitities.MassengutArtikel;
import eshop.enitities.Mitarbeiter;
import eshop.ui.gui.LoginOptionenGUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EreignisListeGUI extends JPanel {

    private final E_Shop eShop;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable artikelTabelle;
    private DefaultTableModel tableModel;

    public EreignisListeGUI(E_Shop eShop, Mitarbeiter eingeloggterMitarbeiter) {
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
        String[] spaltenNamen = {"Datum", "Artikel", "Anzahl", "Kunde Oder Mitarbeiter", "Ereignistyp"};
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
        JLabel überschriftLabel = new JLabel("Sortieren nach");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 0;
        panelWestup.add(überschriftLabel, westUpgbc);

        JButton datumButton = new JButton("Datum");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 1;
        panelWestup.add(datumButton, westUpgbc);

        JLabel überschriftLabel2 = new JLabel("Filtern nach");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 2;
        panelWestup.add(überschriftLabel2, westUpgbc);

        JButton artikelButton = new JButton("Artikel");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 3;
        panelWestup.add(artikelButton, westUpgbc);

        JButton personButton = new JButton("Person");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 4;
        panelWestup.add(personButton, westUpgbc);

        JButton typButton = new JButton("Ereignistyp");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 5;
        panelWestup.add(typButton, westUpgbc);

        // Logout button in the south
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            eingeloggterMitarbeiter = null;
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
            // Close the current window
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        datumButton.addActionListener(e -> {
            // Update table with sorted list by date
            updateTabelle();  // Keine Filter, nur nach Datum sortieren
        });

        artikelButton.addActionListener(e -> {
            try {
                String artikelFilter = JOptionPane.showInputDialog(this, "Artikelbezeichnung eingeben:");
                if (artikelFilter == null || artikelFilter.trim().isEmpty()) {
                    throw new FilterException("Artikelbezeichnung darf nicht leer sein.");
                }
                updateTabelle(artikelFilter, null, null);  // Filter by article
            } catch (FilterException | WertNichtGefundenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Filter Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        personButton.addActionListener(e -> {
            try {
                String usernameFilter = JOptionPane.showInputDialog(this, "Username der Person eingeben:");
                if (usernameFilter == null || usernameFilter.trim().isEmpty()) {
                    throw new FilterException("Bitte einen gültigen Username eingeben.");
                }
                updateTabelle(null, usernameFilter, null);  // Filter by username
            } catch (FilterException | WertNichtGefundenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Filter Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        typButton.addActionListener(e -> {
            try {
                String typFilter = JOptionPane.showInputDialog(this, "Ereignistyp eingeben:");
                if (typFilter == null || typFilter.trim().isEmpty()) {
                    throw new FilterException("Ereignistyp darf nicht leer sein.");
                }
                updateTabelle(null, null, typFilter);  // Filter by event type
            } catch (FilterException | WertNichtGefundenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Filter Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        suchButton.addActionListener(e -> {
            try {
                String suchBegriff = suchFeld.getText();
                if (suchBegriff == null || suchBegriff.trim().isEmpty()) {
                    throw new FilterException("Suchbegriff darf nicht leer sein.");
                }
                updateTabelle(suchBegriff, null, null);  // Search by general term (acts as a filter for article name)
            } catch (FilterException | WertNichtGefundenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Filter Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public void updateTabelle() {
        try {
            updateTabelle(null, null, null);  // Keine Filter anwenden
        } catch (WertNichtGefundenException ex){
            //kann leer bleieben, eil hier kein Filter angewendet wird
        }

    }

    public void updateTabelle(String artikelFilter, String usernameFilter, String typFilter) throws WertNichtGefundenException {

        tableModel.setRowCount(0); // Bestehende Daten löschen

        // Ereignisliste abrufen und sortieren
        List<Ereignis> ereignisListe = eShop.getEreignisListe();

        // Sortieren nach Datum (aufsteigend)
        ereignisListe.sort(Comparator.comparing(Ereignis::getDatum));

        // Filtern der Ereignisse basierend auf den Filterparametern
        List<Ereignis> gefilterteEreignisse = ereignisListe.stream()
                .filter(ereignis -> (artikelFilter == null || artikelFilter.isEmpty() || ereignis.getArtikel().equalsIgnoreCase(artikelFilter)) &&
                        (usernameFilter == null || usernameFilter.isEmpty() || (ereignis.getKundeOderMitarbeiter() != null && ereignis.getKundeOderMitarbeiter().vergleich(usernameFilter))) &&
                        (typFilter == null || typFilter.isEmpty() || ereignis.getTyp().toString().equalsIgnoreCase(typFilter)))
                .toList();

        if(gefilterteEreignisse.isEmpty()){
            throw new WertNichtGefundenException("Kein Ereignis gefunden, das den Filterkriterien entspricht. Bitte die exakte Artikelbeschreibung, den Username oder Ereignistyp angeben.");
        }

        // Daten zur Tabelle hinzufügen
        for (Ereignis ereignis : gefilterteEreignisse) {
            if (ereignis != null) {
                Object[] ereignisDaten = {
                        ereignis.simpleDatum(),
                        ereignis.getArtikel(),
                        ereignis.getAnzahl(),
                        ereignis.getKundeOderMitarbeiter(),
                        ereignis.getTyp()
                };
                tableModel.addRow(ereignisDaten);
            }
        }
    }

    // Überprüfen, welcher Filter fehlt und den entsprechenden Filternamen zurückgeben
    private String checkMissingFilters(String artikelFilter, String usernameFilter, String typFilter) {
        if (artikelFilter == null && usernameFilter == null && typFilter == null) {
            return "Artikel, Person oder Ereignistyp";
        } else if (artikelFilter == null && usernameFilter == null) {
            return "Artikel oder Person";
        } else if (artikelFilter == null && typFilter == null) {
            return "Artikel oder Ereignistyp";
        } else if (usernameFilter == null && typFilter == null) {
            return "Person oder Ereignistyp";
        } else if (artikelFilter == null) {
            return "Artikel";
        } else if (usernameFilter == null) {
            return "Person";
        } else if (typFilter == null) {
            return "Ereignistyp";
        }
        return null;
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
