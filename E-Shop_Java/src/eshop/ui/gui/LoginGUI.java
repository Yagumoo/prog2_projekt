package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.enitities.*;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    boolean loginErfolgreich = false;
    private E_Shop eShop;

    public LoginGUI() {
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

        loginFenster();
    }

    public void loginFenster() {

        JPanel panelN = new JPanel();
        JPanel panelS = new JPanel();
        JPanel panelC = new JPanel();
        //TODO: Gird verwenden evtl
        //panelC.setLayout(new GridLayout(2, 2));

        JTextField usernameOrEmailTextfeld = new JTextField(20);
        JTextField passwortTextfeld = new JTextField(20);
        JButton loginButton = new JButton("Einloggen");
        JLabel usernameOrEmailLabel = new JLabel("Benutzername oder Email:");
        JLabel passwortLabel = new JLabel("Passwort:                               ");

        //Die Labels Hinzufügen
        panelN.add(usernameOrEmailLabel);
        panelC.add(passwortLabel);

        //Die beiden textfelder und der button dadrunter
        panelN.add(usernameOrEmailTextfeld);
        panelC.add(passwortTextfeld);
        panelS.add(loginButton);



        this.add(panelN, BorderLayout.NORTH);
        this.add(panelS, BorderLayout.SOUTH);
        this.add(panelC, BorderLayout.CENTER);

        this.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hier das EShopGUI-Fenster öffnen
                SwingUtilities.invokeLater(EShopGUI::new);
                dispose(); // Schließt das LoginGUI-Fenster
            }
        });

    }

    public boolean loginerfolgreich() {
        //TODO: login überarbeiten

        return loginErfolgreich;
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
