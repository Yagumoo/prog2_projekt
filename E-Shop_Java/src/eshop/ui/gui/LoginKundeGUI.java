package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.enitities.Person;

import eshop.domain.exceptions.LoginException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginKundeGUI extends JFrame {

    private E_Shop eShop;
    private Person eingeloggtePerson = null;

    public LoginKundeGUI(E_Shop eShop) {
        this.eShop = eShop;
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 160);
        this.setLayout(new GridBagLayout());

        // Laden des Bildsymbols
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

        showCustomerLogin();
    }

    public void showCustomerLogin() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameOrEmailLabel = new JLabel("Benutzername oder Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(usernameOrEmailLabel, gbc);

        JTextField usernameOrEmailTextfeld = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(usernameOrEmailTextfeld, gbc);

        JLabel passwortLabel = new JLabel("Passwort:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(passwortLabel, gbc);

        JPasswordField passwortTextfeld = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(passwortTextfeld, gbc);

        JButton loginButton = new JButton("Einloggen");
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(loginButton, gbc);

        JButton registerButton = new JButton("Registrieren");
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(registerButton, gbc);

        JButton zurückButton = new JButton("Zurück");
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(zurückButton, gbc);

        this.setVisible(true);

        // ActionListener für den Einloggen-Button
        loginButton.addActionListener(e -> {
            try {
                String usernameOrEmail = usernameOrEmailTextfeld.getText();
                char[] password = passwortTextfeld.getPassword();
                eingeloggtePerson = eShop.loginKunde(usernameOrEmail, new String(password));
                // Zeige die Kundenansicht an
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new EShopGUI(eShop);
                    }
                });
                // Schließe das Login-Fenster
                this.dispose();
                // Lösche das Passwort aus dem Speicher
                java.util.Arrays.fill(password, '0');
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ActionListener für den Registrieren-Button
        registerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new RegistrierenKundeGUI(eShop);
                }
            }); // Zeigt das Registrierungsfenster an
            this.dispose(); // Schließt das Login-Fenster
        });

        zurückButton.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginOptionenGUI(eShop);
                }
            });
            this.dispose();
        });

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
