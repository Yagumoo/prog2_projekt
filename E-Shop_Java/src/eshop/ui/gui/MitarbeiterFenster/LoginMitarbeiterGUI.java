package eshop.ui.gui.MitarbeiterFenster;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.LoginException;
import eshop.enitities.Person;
import eshop.ui.gui.LoginOptionenGUI;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;

public class LoginMitarbeiterGUI extends JFrame {

    boolean loginErfolgreich = false;
    private E_Shop eShop;
    private Person eingeloggtePerson = null;

    public LoginMitarbeiterGUI( E_Shop eShop) {
        this.eShop = eShop;
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
                eingeloggtePerson = eShop.loginMitarbeiter(usernameOrEmailTextfeld.getText(), passwortTextfeld.getText());
                //showCustomerPage();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new MitarbeiterSeite(eShop);
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
                    new LoginOptionenGUI(eShop);
                }
            });
            this.dispose();
        });

        this.setVisible(true);
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Bock.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Könnte Pfard nicht finden: " + "eshop/ui/gui/Icon/Bock.png");
            return null;
        }
    }

}
