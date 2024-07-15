package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.*;
import eshop.common.enitities.Mitarbeiter;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
/**
 * Eine GUI für die Registrierung neuer Mitarbeiter im E-Shop-Client.
 *
 * Diese Klasse stellt eine Benutzeroberfläche zur Verfügung, die es Administratoren ermöglicht, neue Mitarbeiter im System zu registrieren.
 * Die GUI enthält ein Formular zur Eingabe der Mitarbeiterdaten sowie eine Tabelle, um die Liste der registrierten Mitarbeiter anzuzeigen.
 *
 * Die Klasse erbt von {@link JPanel} und organisiert die Benutzeroberfläche mit einem {@link BorderLayout}.
 *
 * @see Eshopclientsite
 * @see Mitarbeiter
 */
public class MitarbeiterRegistrieren extends JPanel {

    private final Eshopclientsite eShopclientsite;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable mitarbeiterTabelle;
    private DefaultTableModel tableModel;
    /**
     * Konstruktor für das {@link MitarbeiterRegistrieren} Panel.
     *
     * Initialisiert das Panel für die Registrierung neuer Mitarbeiter. Setzt den Hintergrund, das Layout und lädt optional ein Bild-Icon.
     * Erstellt das Formular für die Eingabe der Mitarbeiterdaten und initialisiert die Tabelle zur Anzeige der registrierten Mitarbeiter.
     *
     * @param eShopclientsite Die Client-Seite des E-Shop, die die Verbindung zur Server-Seite herstellt.
     * @param eingeloggterMitarbeiter Der derzeit eingeloggte Mitarbeiter, der die Registrierung durchführt.
     */
    public MitarbeiterRegistrieren(Eshopclientsite eShopclientsite, Mitarbeiter eingeloggterMitarbeiter) {
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
    /**
     * Initialisiert die Tabelle zur Anzeige der Artikelinformationen.
     * Erstellt die Spaltennamen, initialisiert das TableModel und fügt die Tabelle
     * zum Panel hinzu. Führt anschließend ein initiales Update der Tabelle durch.
     */
    private void initializeTable() {
        String[] spaltenNamen = {"Vorname", "Nachname", "E-mail", "Username", "Mitarbeiter-ID"};
        tableModel = new DefaultTableModel(spaltenNamen, 0);
        mitarbeiterTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(mitarbeiterTabelle);
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


        // Add an article to the inventory
        JLabel überschriftLabel = new JLabel("Neuen Mitarbeiter Registrieren");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 0;
        panelWestup.add(überschriftLabel, westUpgbc);

        JLabel vornameLabel = new JLabel("Vorname");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 1;
        panelWestup.add(vornameLabel, westUpgbc);

        JTextField vornameFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 1;
        panelWestup.add(vornameFeld, westUpgbc);

        JLabel nachnameLabel = new JLabel("Nachname");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 2;
        panelWestup.add(nachnameLabel, westUpgbc);

        JTextField nachnameFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 2;
        panelWestup.add(nachnameFeld, westUpgbc);

        JLabel emailLabel = new JLabel("Email");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 3;
        panelWestup.add(emailLabel, westUpgbc);

        JTextField emailFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 3;
        panelWestup.add(emailFeld, westUpgbc);

        JLabel usernameLabel = new JLabel("Username");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 4;
        panelWestup.add(usernameLabel, westUpgbc);

        JTextField usernameFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 4;
        panelWestup.add(usernameFeld, westUpgbc);

        JLabel passwortLabel = new JLabel("Passwort");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 5;
        panelWestup.add(passwortLabel, westUpgbc);

        JTextField passwortFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 5;
        panelWestup.add(passwortFeld, westUpgbc);


        JButton hinzufügenButton = new JButton("Registrieren");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 7;
        panelWestup.add(hinzufügenButton, westUpgbc);

        // Logout button in the south
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            eingeloggterMitarbeiter = null;
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShopclientsite));
            // Close the current window
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        hinzufügenButton.addActionListener(e -> {
            try {
                String Vorname = vornameFeld.getText();
                String Nachname = nachnameFeld.getText();
                String Email = emailFeld.getText();
                String Username = usernameFeld.getText();
                String Passwort = passwortFeld.getText();

                if(Vorname.isEmpty() || Nachname.isEmpty() || Email.isEmpty() || Username.isEmpty() || Passwort.isEmpty()){
                    throw new FalscheEingabeException();
                }

                eShopclientsite.addMitarbeiter(eingeloggterMitarbeiter, Vorname, Nachname, Email, Username, Passwort);

                updateTabelle();

            } catch (DoppelteIdException | UsernameExistiertException | EmailExistiertException | IdNichtVorhandenException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (FalscheEingabeException ex) {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Artikelnummer ein", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    /**
     * Aktualisiert die Tabelle mit allen Artikeln aus dem eShopclientsite.
     */
    public void updateTabelle() {
        tableModel.setRowCount(0); // Bestehende Daten löschen
        Map<Integer, Mitarbeiter> artikelMap = eShopclientsite.gibAlleMitarbeiter();
        for (Map.Entry<Integer, Mitarbeiter> eintrag : artikelMap.entrySet()) {
            Mitarbeiter mitarbeiter = eintrag.getValue();
            if (mitarbeiter != null) {
                Object[] daten = {
                        mitarbeiter.getVorname(),
                        mitarbeiter.getNachname(),
                        mitarbeiter.getEmail(),
                        mitarbeiter.getUsername(),
                        mitarbeiter.getId()
                };
                tableModel.addRow(daten);
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
