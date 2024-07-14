package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.FilterException;
import eshop.common.exceptions.WertNichtGefundenException;
import eshop.common.enitities.Ereignis;
import eshop.common.enitities.Mitarbeiter;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class EreignisListeGUI extends JPanel {

    private final Eshopclientsite eShopclientsite;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable artikelTabelle;
    private DefaultTableModel tableModel;

    public EreignisListeGUI(Eshopclientsite eShopclientsite, Mitarbeiter eingeloggterMitarbeiter) {
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
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShopclientsite));
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
        List<Ereignis> ereignisListe = eShopclientsite.getEreignisListe();

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
