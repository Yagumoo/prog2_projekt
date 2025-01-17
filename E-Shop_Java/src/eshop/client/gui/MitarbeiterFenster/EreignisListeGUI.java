package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.FilterException;
import eshop.common.exceptions.IdNichtVorhandenException;
import eshop.common.exceptions.WertNichtGefundenException;
import eshop.common.enitities.Ereignis;
import eshop.common.enitities.Mitarbeiter;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
/**
 * Ein Panel für die Benutzeroberfläche zur Anzeige von Ereignissen für einen Mitarbeiter.
 *
 * Diese Klasse stellt die Benutzeroberfläche bereit, auf der Mitarbeiter eine Liste von Ereignissen aus dem E-Shop einsehen können.
 * Die Ansicht umfasst eine Tabelle zur Darstellung von Ereignisinformationen und eine Möglichkeit, zur Mitarbeiterseite zurückzukehren.
 *
 * @see Eshopclientsite
 * @see Mitarbeiter
 */
public class EreignisListeGUI extends JPanel {

    private final Eshopclientsite eShopclientsite;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable artikelTabelle;
    private DefaultTableModel tableModel;
    /**
     * Konstruktor für das {@link EreignisListeGUI} Panel.
     *
     * Initialisiert die Benutzeroberfläche, einschließlich des Layouts, der Tabelle zur Anzeige der Ereignisse und der Schaltflächen.
     *
     * @param eShopclientsite Die Client-Seite des E-Shop, die die Verbindung zur Server-Seite herstellt.
     * @param eingeloggterMitarbeiter Der derzeit eingeloggte Mitarbeiter, der die Ereignisliste einsehen kann.
     */
    public EreignisListeGUI(Eshopclientsite eShopclientsite, Mitarbeiter eingeloggterMitarbeiter) {
        this.eShopclientsite = eShopclientsite;
        this.eingeloggterMitarbeiter = eingeloggterMitarbeiter;
        this.setBackground(new Color(123, 50, 250));
        this.setLayout(new BorderLayout());

        ImageIcon image = loadImageIcon();
        if (image != null) {
        }
        //Mitarbeiter Textfelder erstellen
        mitarbeiterSeite();
        //Tabelle erstellen
        initializeTable();
    }
    /**
     * Initialisiert die Tabelle zur Anzeige der Artikelinformationen.
     * Erstellt die Spaltennamen, initialisiert das TableModel und fügt die Tabelle
     * zum Panel hinzu. Führt anschließend ein initiales Update der Tabelle durch.
     */
    private void initializeTable() {
        String[] spaltenNamen = {"Datum", "Artikel", "Anzahl", "Kunde Oder Mitarbeiter", "Ereignistyp"};
        tableModel = new DefaultTableModel(spaltenNamen, 0);
        artikelTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(artikelTabelle);
        this.add(scrollPane, BorderLayout.CENTER);

        updateTabelle();  // Initiales Update der Tabelle
    }
    /**
     * Initialisiert die Benutzeroberfläche für die Mitarbeiterseite.
     * Erstellt verschiedene Panels und fügt UI-Komponenten wie Labels, Buttons und Textfelder hinzu.
     * Fügt ActionListener zu den Buttons hinzu, um entsprechende Aktionen auszuführen.
     */
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
            } catch (FilterException | WertNichtGefundenException | IdNichtVorhandenException ex) {
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
            } catch (FilterException | WertNichtGefundenException | IdNichtVorhandenException ex) {
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
            } catch (FilterException | WertNichtGefundenException | IdNichtVorhandenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Filter Error", JOptionPane.ERROR_MESSAGE);
            }
        });



    }
    /**
     * Aktualisiert die Tabelle mit den aktuellen Ereignisinformationen ohne Filter.
     *
     * Diese Methode ruft die überladene Methode updateTabelle auf, ohne Filterparameter
     * zu setzen. Sie aktualisiert die Tabelle, um alle Ereignisse anzuzeigen, die keine speziellen Filter erfordern.
     *
     * Falls ein Fehler auftritt, der durch {@link WertNichtGefundenException} gekennzeichnet ist, wird der Fehler hier nicht behandelt,
     * da keine Filter angewendet werden und folglich keine Fehler auftreten sollten.
     */
    public void updateTabelle() {
        try {
            updateTabelle(null, null, null);  // Keine Filter anwenden
        } catch (WertNichtGefundenException | IdNichtVorhandenException ex){
            //kann leer bleieben, eil hier kein Filter angewendet wird
        }

    }
    /**
     * Aktualisiert die Tabelle mit den aktuellen Ereignisinformationen unter Anwendung von optionalen Filtern.
     *
     * Diese Methode leert zunächst die bestehende Tabelle und ruft dann die vollständige Liste der Ereignisse von der E-Shop-Client-Seite ab.
     * Anschließend wird die Liste nach Datum aufsteigend sortiert und basierend auf den angegebenen Filterparametern gefiltert.
     * Die gefilterten Ereignisse werden in die Tabelle eingefügt. Wenn keine Ereignisse den Filterkriterien entsprechen, wird eine
     * {@link WertNichtGefundenException} ausgelöst.
     *
     * <p>Die Filterparameter sind optional:
     * <ul>
     *     <li><b>artikelFilter:</b> Filter für die Artikelbeschreibung der Ereignisse. Wenn {@code null} oder leer, wird dieser Filter ignoriert.</li>
     *     <li><b>usernameFilter:</b> Filter für den Username des Kunden oder Mitarbeiters, der mit dem Ereignis verbunden ist. Wenn {@code null} oder leer, wird dieser Filter ignoriert.</li>
     *     <li><b>typFilter:</b> Filter für den Typ des Ereignisses. Wenn {@code null} oder leer, wird dieser Filter ignoriert.</li>
     * </ul>
     * </p>
     *
     * <p>Die Methode sortiert die Ereignisse nach Datum in aufsteigender Reihenfolge und wendet die Filter auf die Ereignisse an:
     * <ul>
     *     <li>Artikelbeschreibung muss mit {@code artikelFilter} übereinstimmen, wenn dieser Filter gesetzt ist.</li>
     *     <li>Der Username des Kunden oder Mitarbeiters muss mit {@code usernameFilter} übereinstimmen, wenn dieser Filter gesetzt ist.</li>
     *     <li>Der Typ des Ereignisses muss mit {@code typFilter} übereinstimmen, wenn dieser Filter gesetzt ist.</li>
     * </ul>
     * </p>
     *
     * @param artikelFilter Der Filter für die Artikelbeschreibung der Ereignisse, kann {@code null} oder leer sein, wenn kein Filter angewendet wird.
     * @param usernameFilter Der Filter für den Username des Kunden oder Mitarbeiters, kann {@code null} oder leer sein, wenn kein Filter angewendet wird.
     * @param typFilter Der Filter für den Typ der Ereignisse, kann {@code null} oder leer sein, wenn kein Filter angewendet wird.
     *
     * @throws WertNichtGefundenException Wenn keine Ereignisse den Filterkriterien entsprechen. Dies tritt auf, wenn keine Ereignisse die angegebenen Filterbedingungen erfüllen.
     */
    public void updateTabelle(String artikelFilter, String usernameFilter, String typFilter) throws WertNichtGefundenException, IdNichtVorhandenException {

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

    /**
     * Lädt ein {@link ImageIcon} aus dem Ressourcenordner der Anwendung.
     *
     * Versucht, das Bild "Mann.png" aus dem Pfad "eshop/client/gui/Icon/" zu laden.
     * Gibt ein {@link ImageIcon} zurück, das das Bild darstellt, oder `null`, wenn die Datei nicht gefunden wurde.
     *
     * @return Ein {@link ImageIcon} Objekt für das Bild, oder `null` bei Fehler.
     */
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
