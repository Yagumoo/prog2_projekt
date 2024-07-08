package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.enitities.*;

import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import java.awt.*;

public class MitarbeiterSeite extends JFrame{

    private final E_Shop eShop;

    public MitarbeiterSeite(E_Shop eShop) {
        this.eShop = eShop;
        //JFrame frame = new JFrame("Wagners E-Shop");
        this.setTitle("E-Shop");
        this.getContentPane().setBackground(new Color(123,50,250));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820, 620);
        this.setLayout(new BorderLayout());

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

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
        //this.add(panelWestdown, BorderLayout.WEST);
        this.add(panelCenter, BorderLayout.CENTER);


        //alles über Artikelhinzufügen

        //Im Norden der Suchbereich
        JLabel suchLabel= new JLabel("Suchbegriff: ");
        JTextField suchFeld = new JTextField(15);
        JButton suchButton= new JButton("Suchen");

        panelNord.add(suchLabel);
        panelNord.add(suchFeld);
        panelNord.add(suchButton);

        //Im Westen einen Artikel Hinzufügen
        JLabel überschriftLabel = new JLabel("Artikel zum Lager hinzufügen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 0;
        panelWestup.add(überschriftLabel, westUpgbc);

        JLabel nummerLabel = new JLabel("ID");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 1;
        panelWestup.add(nummerLabel, westUpgbc);

        JTextField nummerFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 1;
        panelWestup.add(nummerFeld, westUpgbc);

        JLabel bezeichnungLabel = new JLabel("Bezeichnung");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 2;
        panelWestup.add(bezeichnungLabel, westUpgbc);

        JTextField bezeichnungFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 2;
        panelWestup.add(bezeichnungFeld, westUpgbc);


        JLabel bestandLabel = new JLabel("Menge");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 3;
        panelWestup.add(bestandLabel, westUpgbc);

        JTextField bestandFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 3;
        panelWestup.add(bestandFeld, westUpgbc);

        JLabel preisLabel = new JLabel("Preis");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 4;
        panelWestup.add(preisLabel, westUpgbc);

        JTextField preisFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 4;
        panelWestup.add(preisFeld, westUpgbc);

        JLabel fürpaketgrößeLabel = new JLabel("Nur Massengut");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 5;
        panelWestup.add(fürpaketgrößeLabel, westUpgbc);

        JLabel paketgrößeLabel = new JLabel("Paketgröße");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 6;
        panelWestup.add(paketgrößeLabel, westUpgbc);

        JTextField paketgrößeFeld = new JTextField(15);
        westUpgbc.gridx = 1;
        westUpgbc.gridy = 6;
        panelWestup.add(paketgrößeFeld, westUpgbc);

        JButton hinzufügenButton = new JButton("Hinzufügen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 7;
        panelWestup.add(hinzufügenButton, westUpgbc);

        JButton entfernenButton = new JButton("Entfernen");
        westUpgbc.gridx = 0;
        westUpgbc.gridy = 8;
        panelWestup.add(entfernenButton, westUpgbc);


        //Artikelbestand ändern
        //Auch im Westen unter Artikelhinzufügen

        JLabel nummer2Label = new JLabel("ID:");
        westDowngbc.gridx = 0;
        westDowngbc.gridy = 0;
        panelWestdown.add(nummer2Label, westDowngbc);

        JTextField nummer2Feld = new JTextField(15);
        westDowngbc.gridx = 1;
        westDowngbc.gridy = 0;
        panelWestdown.add(nummer2Feld, westDowngbc);


        JLabel bestand2Label = new JLabel("Bestand:");
        westDowngbc.gridx = 0;
        westDowngbc.gridy = 1;
        JTextField bestand2 = new JTextField(15);

        //Artikel Ausgeben lassen
        //Liste von Kunden und Mitarbeiter ausgeben lassen
        //Neuen Mitarbeiter regestieren
        //Liste von Ereignissen ausgeben lassen
        //Bestand ausgeben mit historie
        //Alles mit Buttons im Osten

        JButton artikelAusgebenButton = new JButton("Artikel ausgeben");
        JButton BestandAusgebenButton = new JButton("Bestand ausgeben");
        JButton ListeKundeButton = new JButton("Kundenliste ausgeben");
        JButton ListeMitarbeiterButton = new JButton("Mitarbeiterliste ausgeben");
        JButton EreignisseAusgebenButton = new JButton("Ergenissliste ausgeben");
        JButton regestrierenButton = new JButton("Mitarbeiter regestrieren");


        //Logout
        //Logout im SÜden
        JButton logoutButton = new JButton("logout");
        panelSouth.add(logoutButton);


        logoutButton.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginOptionenGUI(eShop);
                }
            }); // Zeigt das Registrierungsfenster an
            this.dispose(); // Schließt das Login-Fenster
        });

        this.setVisible(true);

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
