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

    public RegistrierenKundeGUI() {
        eShop = new E_Shop();
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 160);
        this.setLayout(new BorderLayout());

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

        kundeRegistrieren();
    }

    private void kundeRegistrieren() {
        JFrame registrierFenster = new JFrame("Registrieren");
        registrierFenster.setSize(400, 500);
        registrierFenster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registrierFenster.setLayout(new GridLayout(10, 2, 10, 10));

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

        registrierFenster.add(vornameLabel);
        registrierFenster.add(vornameField);

        registrierFenster.add(nachnameLabel);
        registrierFenster.add(nachnameField);

        registrierFenster.add(emailLabel);
        registrierFenster.add(emailField);

        registrierFenster.add(usernameLabel);
        registrierFenster.add(usernameField);

        registrierFenster.add(passwortLabel);
        registrierFenster.add(passwortField);

        registrierFenster.add(ortLabel);
        registrierFenster.add(ortField);

        registrierFenster.add(plzLabel);
        registrierFenster.add(plzField);

        registrierFenster.add(strasseLabel);
        registrierFenster.add(strasseField);

        registrierFenster.add(strassenNummerLabel);
        registrierFenster.add(strassenNummerField);

        registrierFenster.add(new JLabel());
        registrierFenster.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    registrierFenster.dispose();
                } catch (DoppelteIdException ex) {
                    JOptionPane.showMessageDialog(null, "Benutzername oder E-Mail bereits registriert!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registrierFenster.setVisible(true);
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Dj.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/ui/gui/Icon/Dj.png");
            return null;
        }
    }

}


