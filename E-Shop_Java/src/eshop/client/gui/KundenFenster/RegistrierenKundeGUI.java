package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.DoppelteIdException;
import eshop.common.exceptions.EmailExistiertException;
import eshop.common.exceptions.UsernameExistiertException;
import eshop.server.domain.E_Shop;

import javax.swing.*;
import java.awt.*;

public class RegistrierenKundeGUI extends JFrame {

    boolean loginErfolgreich = false;
    private Eshopclientsite eShop;

    public RegistrierenKundeGUI(Eshopclientsite eShop) {
        this.eShop = eShop;
        this.setTitle("Registrieren");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 500);
        this.setLayout(new GridLayout(10, 2, 10, 10));

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

        kundeRegistrieren();
    }

    private void kundeRegistrieren() {

        JLabel vornameLabel = new JLabel("Vorname:");
        JTextField vornameField = new JTextField();

        JLabel nachnameLabel = new JLabel("Nachname:");
        JTextField nachnameField = new JTextField();

        JLabel emailLabel = new JLabel("E-Mail:");
        JTextField emailField = new JTextField();

        JLabel usernameLabel = new JLabel("Benutzername:");
        JTextField usernameField = new JTextField();

        JLabel passwortLabel = new JLabel("Passwort:");
        JPasswordField passwortField = new JPasswordField();

        JLabel ortLabel = new JLabel("Ort:");
        JTextField ortField = new JTextField();

        JLabel plzLabel = new JLabel("PLZ:");
        JTextField plzField = new JTextField();

        JLabel strasseLabel = new JLabel("Straße:");
        JTextField strasseField = new JTextField();

        JLabel strassenNummerLabel = new JLabel("Straßennummer:");
        JTextField strassenNummerField = new JTextField();

        JButton registerButton = new JButton("Registrieren");

        JButton zurückButton = new JButton("Zurück");

        this.add(vornameLabel);
        this.add(vornameField);

        this.add(nachnameLabel);
        this.add(nachnameField);

        this.add(emailLabel);
        this.add(emailField);

        this.add(usernameLabel);
        this.add(usernameField);

        this.add(passwortLabel);
        this.add(passwortField);

        this.add(ortLabel);
        this.add(ortField);

        this.add(plzLabel);
        this.add(plzField);

        this.add(strasseLabel);
        this.add(strasseField);

        this.add(strassenNummerLabel);
        this.add(strassenNummerField);

        this.add(zurückButton);
        this.add(registerButton);

        registerButton.addActionListener(e -> {
            String vorname = vornameField.getText();
            String nachname = nachnameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String passwort = new String(passwortField.getPassword());
            String ort = ortField.getText();
            String plz = plzField.getText();
            String strasse = strasseField.getText();
            String strassenNummer = strassenNummerField.getText();

                try {
                // Hier wird die Methode addKunde aufgerufen
                eShop.addKunde(vorname, nachname, email, username, passwort, ort, Integer.parseInt(plz), strasse, Integer.parseInt(strassenNummer));
                JOptionPane.showMessageDialog(null, "Registrierung erfolgreich!");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new LoginKundeGUI(eShop);
                    }
                });
                this.dispose();
            } catch (DoppelteIdException | UsernameExistiertException | EmailExistiertException ex) {
                JOptionPane.showMessageDialog(null, "Benutzername oder E-Mail bereits registriert!", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        zurückButton.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginKundeGUI(eShop);
                }
            });
            this.dispose();
        });

        this.setVisible(true);
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/client/gui/Icon/Dj.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Könnte Pfard nicht finden: " + "eshop/client/gui/Icon/Dj.png");
            return null;
        }
    }

}

