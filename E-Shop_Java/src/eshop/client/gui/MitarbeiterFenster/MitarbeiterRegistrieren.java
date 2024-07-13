package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.DoppelteIdException;
import eshop.common.exceptions.EmailExistiertException;
import eshop.common.exceptions.UsernameExistiertException;
import eshop.server.domain.E_Shop;
import eshop.common.enitities.Mitarbeiter;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class MitarbeiterRegistrieren extends JPanel {

    private final Eshopclientsite eShop;
    private Mitarbeiter eingeloggterMitarbeiter;
    private JTable mitarbeiterTabelle;
    private DefaultTableModel tableModel;

    public MitarbeiterRegistrieren(Eshopclientsite eShop, Mitarbeiter eingeloggterMitarbeiter) {
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
        String[] spaltenNamen = {"Vorname", "Nachname", "E-mail", "Username", "Mitarbeiter-ID"};
        tableModel = new DefaultTableModel(spaltenNamen, 0);
        mitarbeiterTabelle = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(mitarbeiterTabelle);
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
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
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

                eShop.addMitarbeiter(eingeloggterMitarbeiter, Vorname, Nachname, Email, Username, Passwort);

                updateTabelle();


            } catch (DoppelteIdException | UsernameExistiertException | EmailExistiertException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void updateTabelle() {
        tableModel.setRowCount(0); // Bestehende Daten löschen
        Map<Integer, Mitarbeiter> artikelMap = eShop.gibAlleMitarbeiter();
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
