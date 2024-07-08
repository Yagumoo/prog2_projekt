package eshop.ui.gui;

import eshop.domain.E_Shop;

import javax.swing.*;
import java.awt.*;

public class KundenSeite extends JPanel {

    private E_Shop eShop;

    public KundenSeite(E_Shop eShop) {
        this.eShop = eShop;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(123, 50, 250));

        Mitarbeiterseite();
    }

    private void Mitarbeiterseite() {
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

        JPanel panelCenter = new JPanel();

        this.add(panelNord, BorderLayout.NORTH);
        this.add(panelEast, BorderLayout.EAST);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.add(panelWestup, BorderLayout.WEST);
        this.add(panelCenter, BorderLayout.CENTER);

        //Logout
        //Logout im Süden
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);

        logoutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new LoginOptionenGUI(eShop));
            this.setVisible(false); // Schließt das aktuelle Fenster
        });
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Mann.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/ui/gui/Icon/Mann.png");
            return null;
        }
    }
}
