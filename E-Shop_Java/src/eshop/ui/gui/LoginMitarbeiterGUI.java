package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.domain.exceptions.LoginException;
import eshop.enitities.Person;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginMitarbeiterGUI extends JFrame {

    boolean loginErfolgreich = false;
    private E_Shop eShop;
    private Person eingeloggtePerson = null;

    public LoginMitarbeiterGUI() {
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

        showMitarbeiterLogin();
    }

    public void showMitarbeiterLogin() {
        this.getContentPane().removeAll();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameOrEmailLabel = new JLabel("Benutzername oder Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameOrEmailLabel, gbc);

        JTextField usernameOrEmailTextfeld = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameOrEmailTextfeld, gbc);

        JLabel passwortLabel = new JLabel("Passwort:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwortLabel, gbc);

        JTextField passwortTextfeld = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwortTextfeld, gbc);

        JButton loginButton = new JButton("Einloggen");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);


        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        this.setVisible(true);

        // ActionListener für den Einloggen-Button
        loginButton.addActionListener(e -> {
            try {
                eingeloggtePerson = eShop.loginMitarbeiter(usernameOrEmailTextfeld.getText(), passwortTextfeld.getText());
                //showCustomerPage();
                SwingUtilities.invokeLater(EShopGUI::new);
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Bock.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/ui/gui/Icon/Bock.png");
            return null;
        }
    }

}
