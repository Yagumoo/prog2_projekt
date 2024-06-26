package eshop.ui.gui;

import eshop.domain.E_Shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import eshop.domain.exceptions.*;
import java.awt.event.ActionListener;

public class RegistrierenKundeGUI extends JFrame {

    boolean loginErfolgreich = false;
    private E_Shop eShop;

    public RegistrierenKundeGUI(E_Shop eShop) {
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

        this.add(new JLabel());
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
            } catch (DoppelteIdException ex) {
                JOptionPane.showMessageDialog(null, "Benutzername oder E-Mail bereits registriert!", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.setVisible(true);
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Dj.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Könnte Pfard nicht finden: " + "eshop/ui/gui/Icon/Dj.png");
            return null;
        }
    }

}


