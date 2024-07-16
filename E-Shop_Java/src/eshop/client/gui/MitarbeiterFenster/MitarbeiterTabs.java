package eshop.client.gui.MitarbeiterFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.server.domain.E_Shop;
import eshop.common.enitities.Mitarbeiter;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
/**
 * Hauptfenster für die Mitarbeiter-Seite im E-Shop-Client, das verschiedene Verwaltungsfunktionen bereitstellt.
 *
 * Diese Klasse stellt das Hauptfenster für Mitarbeiter zur Verfügung, das mehrere Tabs für verschiedene Verwaltungsaufgaben enthält.
 * Jeder Tab bietet eine unterschiedliche Funktionalität im Rahmen der Mitarbeiteraufgaben im E-Shop-System.
 *
 * Die Klasse erbt von {@link JFrame} und verwendet ein {@link BorderLayout}, um den {@link JTabbedPane} im Zentrum des Fensters anzuzeigen.
 *
 * @see Eshopclientsite
 * @see Mitarbeiter
 * @see MitarbeiterSeite
 * @see ArtikelEntfernen
 * @see ArtikelBestandÄndern
 * @see MitarbeiterRegistrieren
 * @see EreignisListeGUI
 */
public class MitarbeiterTabs extends JFrame {

    Eshopclientsite eShopclientsite;
    /**
     * Konstruktor für das {@link MitarbeiterTabs} Fenster.
     *
     * Initialisiert das Hauptfenster für die Mitarbeiter mit mehreren Tabs für unterschiedliche Verwaltungsfunktionen.
     * Setzt den Fenstertitel, die Größe und das Layout des Fensters. Erzeugt und fügt die verschiedenen Tabs zum {@link JTabbedPane} hinzu,
     * um die Benutzeroberfläche für die Verwaltung von Artikeln, Mitarbeitern und Ereignissen zu organisieren.
     *
     * <p>Die Tabs sind wie folgt konfiguriert:</p>
     * <ul>
     *     <li><b>Artikel hinzufügen:</b> Zeigt die {@link MitarbeiterSeite} zur Verwaltung und Hinzufügung von Artikeln.</li>
     *     <li><b>Artikel entfernen:</b> Zeigt die {@link ArtikelEntfernen} Seite zur Entfernung von Artikeln.</li>
     *     <li><b>Artikelbestand verändern:</b> Zeigt die {@link ArtikelBestandÄndern} Seite zur Veränderung des Artikelbestands.</li>
     *     <li><b>Mitarbeiter registrieren:</b> Zeigt die {@link MitarbeiterRegistrieren} Seite zur Registrierung neuer Mitarbeiter.</li>
     *     <li><b>Ereignisliste:</b> Zeigt die {@link EreignisListeGUI} Seite zur Anzeige und Filterung von Ereignissen.</li>
     * </ul>
     *
     * <p>Ein {@link ChangeListener} wird hinzugefügt, um die Tabellen in den verschiedenen Tabs bei einem Wechsel zwischen den Tabs zu aktualisieren.</p>
     *
     * @param eShopclientsite Die Client-Seite des E-Shop, die die Verbindung zur Server-Seite herstellt.
     * @param eingeloggteMitarbeiter Der derzeit eingeloggte Mitarbeiter, der die Verwaltungsvorgänge durchführt.
     */
    public MitarbeiterTabs(Eshopclientsite eShopclientsite, Mitarbeiter eingeloggteMitarbeiter)  {
        this.eShopclientsite = eShopclientsite;
        this.setTitle("E-Shop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820, 620);
        this.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Erstellen und Hinzufügen der Tabs
        MitarbeiterSeite mitarbeiterSeite = new MitarbeiterSeite(eShopclientsite, eingeloggteMitarbeiter);
        ArtikelEntfernen artikelEntfernen = new ArtikelEntfernen(eShopclientsite, eingeloggteMitarbeiter);
        ArtikelBestandÄndern artikelBestandÄndern = new ArtikelBestandÄndern(eShopclientsite, eingeloggteMitarbeiter);
        MitarbeiterRegistrieren mitarbeiterRegistrieren = new MitarbeiterRegistrieren(eShopclientsite, eingeloggteMitarbeiter);
        EreignisListeGUI ereignisListeGUI = new EreignisListeGUI(eShopclientsite, eingeloggteMitarbeiter);

        tabbedPane.addTab("Artikel hinzufügen", mitarbeiterSeite);
        tabbedPane.addTab("Artikel entfernen", artikelEntfernen);
        tabbedPane.addTab("Artikelbestand verändern", artikelBestandÄndern);
        tabbedPane.addTab("Mitarbeiter registrieren", mitarbeiterRegistrieren);
        tabbedPane.addTab("Ereignisliste", ereignisListeGUI);

        // Hinzufügen des ChangeListeners
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                if (index == 0) {
                    mitarbeiterSeite.updateTabelle(); // Tabelle in Artikel hinzufügen Tab aktualisieren
                } else if (index == 1) {
                    artikelEntfernen.updateTabelle(); // Tabelle in Artikel entfernen Tab aktualisieren
                } else if (index == 2) {
                    artikelBestandÄndern.updateTabelle(); // Tabelle in Artikelbestand verändern Tab aktualisieren
                } else if (index == 3) {
                    mitarbeiterRegistrieren.updateTabelle(); // Tabelle in Artikelbestand verändern Tab aktualisieren
                } else if (index == 4) {
                    ereignisListeGUI.updateTabelle(); // Tabelle in Artikelbestand verändern Tab aktualisieren
                }
            }
        });

        // Fügen Sie hier weitere Tabs hinzu, wenn benötigt.
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

}
