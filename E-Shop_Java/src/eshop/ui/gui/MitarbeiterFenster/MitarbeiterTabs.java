package eshop.ui.gui.MitarbeiterFenster;

import eshop.domain.E_Shop;
import eshop.enitities.Mitarbeiter;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MitarbeiterTabs extends JFrame {

    public MitarbeiterTabs(E_Shop eShop, Mitarbeiter eingeloggteMitarbeiter)  {
        this.setTitle("E-Shop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820, 620);
        this.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Erstellen und Hinzufügen der Tabs
        MitarbeiterSeite mitarbeiterSeite = new MitarbeiterSeite(eShop, eingeloggteMitarbeiter);
        ArtikelEntfernen artikelEntfernen = new ArtikelEntfernen(eShop, eingeloggteMitarbeiter);
        ArtikelBestandÄndern artikelBestandÄndern = new ArtikelBestandÄndern(eShop, eingeloggteMitarbeiter);
        MitarbeiterRegistrieren mitarbeiterRegistrieren = new MitarbeiterRegistrieren(eShop, eingeloggteMitarbeiter);
        EreignisListeGUI ereignisListeGUI = new EreignisListeGUI(eShop, eingeloggteMitarbeiter);

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
