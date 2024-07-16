package eshop.client.starten;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.gui.KundenFenster.LoginKundeGUI;
import eshop.client.gui.MitarbeiterFenster.LoginMitarbeiterGUI;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
/**
 * GUI-Klasse für die Login-Optionen des E-Shop-Clients.
 *
 * <p>
 * Diese Klasse zeigt die Login-Optionen für Kunden und Mitarbeiter an und ermöglicht es, das Programm zu beenden.
 * </p>
 */
public class LoginOptionenGUI extends JFrame{
    private Eshopclientsite eShopclientsite;

    /**
     * Konstruktor für die LoginOptionenGUI.
     *
     * @param eShop Eshopclientsite-Instanz für die Kommunikation mit dem Server.
     */
    public LoginOptionenGUI(Eshopclientsite eShop) {
        this.eShopclientsite = eShop;
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

    /**
     * Erstellt und zeigt die Login-Optionen für den Benutzer an.
     * Es gibt drei Schaltflächen: eine für den Zugang als Kunde, eine für den Zugang als Mitarbeiter
     * und eine zum Beenden der Anwendung. Bei einem Klick auf eine der Schaltflächen wird das
     * aktuelle Fenster geschlossen und die entsprechende Login-GUI geöffnet.
     */
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
                    new LoginKundeGUI(eShopclientsite);
                }
            });
            this.dispose();
        });

        mitarbeiterButton.addActionListener(e -> {
            //Öffent die Mitarbeiter Login-GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginMitarbeiterGUI(eShopclientsite);
                }
            });
            this.dispose();
        });

    }

    /**
     * Lädt ein {@link ImageIcon} aus dem Ressourcenordner der Anwendung.
     *
     * Versucht, das Bild "Mann.png" aus dem Pfad "eshop/client/gui/Icon/" zu laden.
     * Gibt ein {@link ImageIcon} zurück, das das Bild darstellt, oder `null`, wenn die Datei nicht gefunden wurde.
     *
     * @return Ein {@link ImageIcon} Objekt für das Bild, oder `null` bei Fehler.
     */
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
