package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.LoginException;
import eshop.common.enitities.Mitarbeiter;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;

public class LoginMitarbeiterGUI extends JFrame {

    boolean loginErfolgreich = false;
    private Eshopclientsite eShopclientsite;
    private Mitarbeiter eingeloggteMitarbeiter = null;

    public LoginMitarbeiterGUI(Eshopclientsite eShopclientsite) {
        this.eShopclientsite = eShopclientsite;
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 160);
        this.setLayout(new GridBagLayout());

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

        showMitarbeiterLogin();
    }

    public void showMitarbeiterLogin() {
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

        JTextField passwortTextfeld = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(passwortTextfeld, gbc);

        JButton loginButton = new JButton("Einloggen");
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(loginButton, gbc);

        JButton zurückButton = new JButton("Zurück");
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(zurückButton, gbc);


        // ActionListener für den Einloggen-Button
        loginButton.addActionListener(e -> {
            try {
                eingeloggteMitarbeiter = eShopclientsite.loginMitarbeiter(usernameOrEmailTextfeld.getText(), passwortTextfeld.getText());
                //showCustomerPage();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new MitarbeiterTabs(eShopclientsite, eingeloggteMitarbeiter).setVisible(true);
                    }
                });
                this.dispose();
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        zurückButton.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginOptionenGUI(eShopclientsite);
                }
            });
            this.dispose();
        });

        this.setVisible(true);
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/client/gui/Icon/Bock.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Könnte Pfard nicht finden: " + "eshop/client/gui/Icon/Bock.png");
            return null;
        }
        }

}