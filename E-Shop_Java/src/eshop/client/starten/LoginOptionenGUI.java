package eshop.client.starten;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.server.domain.E_Shop;
import eshop.client.gui.KundenFenster.LoginKundeGUI;
import eshop.client.gui.MitarbeiterFenster.LoginMitarbeiterGUI;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;

public class LoginOptionenGUI extends JFrame{
    private Eshopclientsite eShop;

    public LoginOptionenGUI(Eshopclientsite eShop) {
        this.eShop = eShop;
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 160);
        this.setLayout(new GridLayout(3, 1));
        showLoginOptions();

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }
    }

    private void showLoginOptions() {

        JButton kundeButton = new JButton("Kunde");
        this.add(kundeButton);

        JButton mitarbeiterButton = new JButton("Mitarbeiter");
        this.add(mitarbeiterButton);

        JButton beendenButton = new JButton("Beenden");
        beendenButton.addActionListener(e -> System.exit(0));
        this.add(beendenButton);

        this.setVisible(true);

        kundeButton.addActionListener(e -> {
            //Öffent die Kunden Login-GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginKundeGUI(eShop);
                }
            });
            this.dispose();
        });

        mitarbeiterButton.addActionListener(e -> {
            //Öffent die Mitarbeiter Login-GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginMitarbeiterGUI(eShop);
                }
            });
            this.dispose();
        });

    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/client/gui/Icon/Macker.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Könnte Pfard nicht finden: " + "eshop/client/gui/Icon/Macker.png");
            return null;
        }
    }
}
