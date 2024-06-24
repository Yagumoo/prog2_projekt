package eshop.ui.gui;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
public class LoginOptionenGUI extends JFrame{

    public LoginOptionenGUI() {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 160);
        this.setLayout(new BorderLayout());
        showLoginOptions();

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }
    }

    private void showLoginOptions() {
        this.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton kundeButton = new JButton("Kunde");
        panel.add(kundeButton);

        JButton mitarbeiterButton = new JButton("Mitarbeiter");
        panel.add(mitarbeiterButton);

        JButton exitButton = new JButton("Beenden");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        this.setVisible(true);

        kundeButton.addActionListener(e -> {
            //Öffent die Kunden Login-GUI
            SwingUtilities.invokeLater(LoginKundeGUI::new);
            this.dispose();
        });

        mitarbeiterButton.addActionListener(e -> {
            //Öffent die Mitarbeiter Login-GUI
            SwingUtilities.invokeLater(LoginMitarbeiterGUI::new);

            this.dispose();
        });

    }


    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Macker.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/ui/gui/Icon/Macker.png");
            return null;
        }
    }
}
