package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.common.exceptions.DoppelteIdException;
import eshop.common.exceptions.EmailExistiertException;
import eshop.common.exceptions.FalscheEingabeException;
import eshop.common.exceptions.UsernameExistiertException;

import javax.swing.*;
import java.awt.*;
/**
 * Die Klasse {@code RegistrierenKundeGUI} stellt das Registrierungsfenster für neue Kunden im E-Shop bereit.
 *
 * <p>Dieses Fenster ermöglicht es neuen Kunden, ein Konto zu erstellen, indem sie ihre persönlichen Informationen wie
 * Name, Adresse und E-Mail-Adresse angeben. Die Registrierung erfolgt über ein Formular, das die erforderlichen Felder
 * für die Anmeldung enthält.</p>
 *
 * <p>Nach erfolgreicher Registrierung wird der Kunde zur Login-Seite weitergeleitet, um sich mit den neuen Anmeldeinformationen
 * einzuloggen. Bei einem Fehler wird eine Fehlermeldung angezeigt.</p>
 *
 * <p>Das Fenster enthält folgende GUI-Komponenten:</p>
 * <ul>
 *     <li>Textfelder für Name, Adresse, E-Mail-Adresse und Passwort.</li>
 *     <li>Buttons zum Registrieren und Zurückkehren zur Login-Seite.</li>
 * </ul>
 *
 * @see Eshopclientsite
 * @see LoginKundeGUI
 */
public class RegistrierenKundeGUI extends JFrame {

    boolean loginErfolgreich = false;
    private Eshopclientsite eShopclientsite;
    /**
     * Konstruktor, der das Registrierungsfenster für neue Kunden initialisiert.
     *
     * <p>Dieser Konstruktor setzt den Fenstertitel, die Standard-Schließoperation und die Größe des Fensters.
     * Außerdem wird das Layout des Fensters mit einem {@link GridLayout} erstellt und die kundeRegistrieren Methode
     * aufgerufen, um die Registrierungs-Komponenten zu initialisieren.</p>
     *
     * @param eShopclientsite Das E-Shop-Client-Objekt, das die Kommunikation mit dem Server verwaltet.
     */
    public RegistrierenKundeGUI(Eshopclientsite eShopclientsite) {
        this.eShopclientsite = eShopclientsite;
        this.setTitle("Registrieren");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 500);
        this.setLayout(new GridLayout(10, 2, 10, 10));

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

        kundeRegistrieren();
    }
    /**
     * Initialisiert die GUI-Komponenten für das Registrierungsfenster der Kunden.
     *
     * <p>Diese Methode erstellt und positioniert die GUI-Elemente, einschließlich Labels, Textfelder und Buttons,
     * und definiert die entsprechenden ActionListenerfür die Interaktionen der Benutzer.</p>
     *
     * <p>Beim Klicken auf den Registrieren-Button wird versucht, den neuen Kunden mit den eingegebenen Informationen beim E-Shop zu registrieren.
     * Bei Erfolg wird das Registrierungsfenster geschlossen und die Login-Seite geöffnet. Bei einem Fehler wird eine
     * Fehlermeldung angezeigt.</p>
     *
     * Beim Klicken auf den Zurück-Button wird der Benutzer zur Login-Seite weitergeleitet.</p>
     */
    private void kundeRegistrieren() {

        JLabel vornameLabel = new JLabel("Vorname:");
        JTextField vornameField = new JTextField();

        JLabel nachnameLabel = new JLabel("Nachname:");
        JTextField nachnameField = new JTextField();

        JLabel emailLabel = new JLabel("E-Mail:");
        JTextField emailField = new JTextField();

        JLabel usernameLabel = new JLabel("Benutzername:");
        JTextField usernameField = new JTextField();

        JLabel passwortLabel = new JLabel("Passwort:");
        JPasswordField passwortField = new JPasswordField();

        JLabel ortLabel = new JLabel("Ort:");
        JTextField ortField = new JTextField();

        JLabel plzLabel = new JLabel("PLZ:");
        JTextField plzField = new JTextField();

        JLabel strasseLabel = new JLabel("Straße:");
        JTextField strasseField = new JTextField();

        JLabel strassenNummerLabel = new JLabel("Straßennummer:");
        JTextField strassenNummerField = new JTextField();

        JButton registerButton = new JButton("Registrieren");

        JButton zurückButton = new JButton("Zurück");

        this.add(vornameLabel);
        this.add(vornameField);

        this.add(nachnameLabel);
        this.add(nachnameField);

        this.add(emailLabel);
        this.add(emailField);

        this.add(usernameLabel);
        this.add(usernameField);

        this.add(passwortLabel);
        this.add(passwortField);

        this.add(ortLabel);
        this.add(ortField);

        this.add(plzLabel);
        this.add(plzField);

        this.add(strasseLabel);
        this.add(strasseField);

        this.add(strassenNummerLabel);
        this.add(strassenNummerField);

        this.add(zurückButton);
        this.add(registerButton);

        registerButton.addActionListener(e -> {
            try {
                String vorname = vornameField.getText();
                String nachname = nachnameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String passwort = new String(passwortField.getPassword());
                String ort = ortField.getText();
                String plz = plzField.getText();
                String strasse = strasseField.getText();
                String strassenNummer = strassenNummerField.getText();

                if(vorname.isEmpty() || nachname.isEmpty() || ort.isEmpty() || plz.isEmpty() || strasse.isEmpty() || strassenNummer.isEmpty() || passwort.isEmpty() || email.isEmpty() || username.isEmpty()) {
                    throw new FalscheEingabeException();
                }

                // Hier wird die Methode addKunde aufgerufen
                eShopclientsite.addKunde(vorname, nachname, email, username, passwort, ort, Integer.parseInt(plz), strasse, Integer.parseInt(strassenNummer));
                JOptionPane.showMessageDialog(null, "Registrierung erfolgreich!");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new LoginKundeGUI(eShopclientsite);
                    }
                });
                this.dispose();
            } catch (DoppelteIdException | UsernameExistiertException | EmailExistiertException ex) {
                JOptionPane.showMessageDialog(null, "Benutzername oder E-Mail bereits registriert!", "Fehler", JOptionPane.ERROR_MESSAGE);
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
                    new LoginKundeGUI(eShopclientsite);
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
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/client/gui/Icon/Dj.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Könnte Pfard nicht finden: " + "eshop/client/gui/Icon/Dj.png");
            return null;
        }
    }

}


