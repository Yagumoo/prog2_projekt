package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.exceptions.FalscheEingabeException;
import eshop.common.exceptions.LoginException;
import eshop.common.enitities.Mitarbeiter;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
/**
 * Eine GUI für das Login von Mitarbeitern in den E-Shop-Client.
 *
 * Diese Klasse stellt das Login-Fenster bereit, über das sich Mitarbeiter in das System einloggen können.
 * Sie bietet ein Interface zur Eingabe von Benutzername und Passwort und versucht, den Mitarbeiter beim E-Shop-Server zu authentifizieren.
 *
 * Die Klasse erbt von {@link JFrame} und enthält ein Login-Formular sowie eine Methode zum Laden eines Icons für das Fenster.
 *
 * @see Eshopclientsite
 * @see Mitarbeiter
 */
public class LoginMitarbeiterGUI extends JFrame {

    boolean loginErfolgreich = false;
    private Eshopclientsite eShopclientsite;
    private Mitarbeiter eingeloggteMitarbeiter = null;
    /**
     * Konstruktor für das {@link LoginMitarbeiterGUI} Fenster.
     *
     * Initialisiert das Fenster für das Login von Mitarbeitern. Legt den Fenstertitel, die Standard-Schließoperation, die Größe und das Layout fest.
     * Lädt das Icon für das Fenster und zeigt das Mitarbeiter-Login-Formular an.
     *
     * @param eShopclientsite Die Client-Seite des E-Shop, die die Verbindung zur Server-Seite herstellt.
     */
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
    /**
     * Zeigt das Login-Formular für Mitarbeiter an.
     *
     * Diese Methode konfiguriert das Layout und die Benutzeroberfläche für das Mitarbeiter-Login-Formular.
     * Sie erstellt Eingabefelder für den Benutzernamen oder die Email-Adresse sowie das Passwort und fügt zwei Schaltflächen hinzu:
     * einen für den Login-Vorgang und einen für das Zurückkehren zum vorherigen Menü.
     * Bei erfolgreichem Login wird ein neues Fenster mit den Mitarbeiter-Tabs geöffnet, und das aktuelle Fenster wird geschlossen.
     * Bei einem fehlgeschlagenen Login-Versuch wird eine Fehlermeldung angezeigt.
     * Beim Klicken auf die Zurück-Schaltfläche wird zur vorherigen Login-Optionen-Seite gewechselt und das aktuelle Fenster geschlossen.
     *
     * <p>Das Layout verwendet {@link GridBagLayout} für die Positionierung der Komponenten. Die {@link GridBagConstraints} werden verwendet, um
     * die Komponenten mit Abständen zu platzieren und ihre Größe zu bestimmen.</p>
     *
     * <p>Die Methode behandelt zwei wichtige Aktionen:
     * <ul>
     *     <li><b>Login:</b> Versucht, den Mitarbeiter mit den angegebenen Anmeldedaten zu authentifizieren. Bei Erfolg wird die
     *         {@link MitarbeiterTabs} Seite geöffnet, andernfalls wird eine Fehlermeldung angezeigt.</li>
     *     <li><b>Zurück:</b> Wechselt zur {@link LoginOptionenGUI} Seite, die dem Benutzer alternative Login-Optionen bietet.</li>
     * </ul>
     * </p>
     */
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
                if(usernameOrEmailTextfeld.getText().isEmpty() || passwortTextfeld.getText().isEmpty()){
                    throw new FalscheEingabeException();
                }

                eingeloggteMitarbeiter = eShopclientsite.loginMitarbeiter(usernameOrEmailTextfeld.getText(), passwortTextfeld.getText());
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new MitarbeiterTabs(eShopclientsite, eingeloggteMitarbeiter);
                    }
                });
                this.dispose();
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }catch (FalscheEingabeException ex) {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Artikelnummer ein", "Fehler", JOptionPane.ERROR_MESSAGE);
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
    /**
     * Lädt ein {@link ImageIcon} aus dem Ressourcenordner der Anwendung.
     *
     * Versucht, das Bild "Mann.png" aus dem Pfad "eshop/client/gui/Icon/" zu laden.
     * Gibt ein {@link ImageIcon} zurück, das das Bild darstellt, oder `null`, wenn die Datei nicht gefunden wurde.
     *
     * @return Ein {@link ImageIcon} Objekt für das Bild, oder `null` bei Fehler.
     */
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