package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.client.starten.LoginOptionenGUI;
import eshop.common.enitities.Kunde;

import eshop.common.exceptions.FalscheEingabeException;
import eshop.common.exceptions.LoginException;

import javax.swing.*;
import java.awt.*;
/**
 * Die Klasse {@code LoginKundeGUI} repräsentiert das Login-Fenster für Kunden im E-Shop.
 *
 * <p>Dieses Fenster ermöglicht es Kunden, sich mit ihrem Benutzernamen oder ihrer E-Mail-Adresse sowie einem Passwort anzumelden.</p>
 *
 * <p>Nach erfolgreichem Login wird der Kunde zur Hauptansicht für Kunden im E-Shop weitergeleitet. Bei einem Fehlversuch
 * wird eine Fehlermeldung angezeigt.</p>
 *
 * <p>Das Fenster enthält folgende GUI-Komponenten:</p>
 * <ul>
 *     <li>Ein Textfeld für den Benutzernamen oder die E-Mail-Adresse.</li>
 *     <li>Ein Passwortfeld für die Eingabe des Passworts.</li>
 *     <li>Ein Button zum Einloggen.</li>
 *     <li>Ein Button, um zur vorherigen Ansicht zurückzukehren.</li>
 * </ul>
 *
 * @see Eshopclientsite
 * @see Kunde
 * @see LoginOptionenGUI
 */
public class LoginKundeGUI extends JFrame {

    private Eshopclientsite eShopclientsite;
    private Kunde eingeloggteKunde = null;
    /**
     * Konstruktor, der das Login-Fenster für Kunden initialisiert.
     *
     * <p>Dieser Konstruktor setzt den Fenstertitel, die Standard-Schließoperation und die Größe des Fensters.
     * Außerdem wird das Layout des Fensters mit einer {@link GridBagLayout}-Anordnung erstellt und das
     * showCustomerLogin Methode aufgerufen, um die Login-Komponenten zu initialisieren.</p>
     *
     * @param eShopclientsite Das E-Shop-Client-Objekt, das die Kommunikation mit dem Server verwaltet.
     */
    public LoginKundeGUI(Eshopclientsite eShopclientsite) {
        this.eShopclientsite = eShopclientsite;
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 160);
        this.setLayout(new GridBagLayout());

        // Laden des Bildsymbols
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

        showCustomerLogin();
    }
    /**
     * Initialisiert die GUI-Komponenten für das Login-Fenster der Kunden.
     *
     * <p>Diese Methode erstellt und positioniert die GUI-Elemente, einschließlich Labels, Textfelder und Buttons,
     * und definiert die entsprechenden ActionListener für die Interaktionen der Benutzer.</p>
     *
     * <p>Beim Klicken auf den Einloggen-Button wird versucht, sich mit den eingegebenen Anmeldeinformationen beim E-Shop anzumelden.
     * Bei Erfolg wird das Login-Fenster geschlossen und die Hauptansicht für Kunden geöffnet. Bei einem Fehler wird eine
     * Fehlermeldung angezeigt.</p>
     *
     * Beim Klicken auf den Zurück-Button wird der Benutzer zur Login-Optionen-Seite weitergeleitet.</p>
     */
    public void showCustomerLogin() {
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

        JPasswordField passwortTextfeld = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(passwortTextfeld, gbc);

        JButton loginButton = new JButton("Einloggen");
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(loginButton, gbc);

        JButton registerButton = new JButton("Registrieren");
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(registerButton, gbc);

        JButton zurückButton = new JButton("Zurück");
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(zurückButton, gbc);

        this.setVisible(true);

        // ActionListener für den Einloggen-Button
        loginButton.addActionListener(e -> {
            try {
                if(usernameOrEmailTextfeld.getText().isEmpty() || passwortTextfeld.getText().isEmpty()){
                    throw new FalscheEingabeException();
                }
                String usernameOrEmail = usernameOrEmailTextfeld.getText();
                char[] password = passwortTextfeld.getPassword();
                eingeloggteKunde = eShopclientsite.loginKunde(usernameOrEmail, new String(password));
                // Zeige die Kundenansicht an
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new KundenTabs(eShopclientsite, eingeloggteKunde).setVisible(true);

                    }
                });
                // Schließe das Login-Fenster
                this.dispose();
                // Lösche das Passwort aus dem Speicher
                java.util.Arrays.fill(password, '0');
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }catch (FalscheEingabeException ex) {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Artikelnummer ein", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ActionListener für den Registrieren-Button
        registerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new RegistrierenKundeGUI(eShopclientsite);
                }
            }); // Zeigt das Registrierungsfenster an
            this.dispose(); // Schließt das Login-Fenster
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
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/client/gui/Icon/Dj.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Könnte Pfard nicht finden: " + "eshop/client/gui/Icon/Dj.png");
            return null;
        }
    }
}
