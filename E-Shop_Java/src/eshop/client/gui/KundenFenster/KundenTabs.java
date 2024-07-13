package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.server.domain.E_Shop;
import eshop.common.enitities.Kunde;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class KundenTabs extends JFrame {

    private Eshopclientsite eShop;
    private Kunde aktuellerKunde;

    public KundenTabs(Eshopclientsite eShop, Kunde eingeloggteKunde) {
        this.eShop = eShop;
        this.setTitle("E-Shop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820, 620);
        this.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Erstellen und Hinzufügen der Tabs
        KundenSeite kundenSeite = new KundenSeite(eShop, eingeloggteKunde);
        WarenkorbGUI warenkorbGUI = new WarenkorbGUI(eShop, eingeloggteKunde);

        tabbedPane.addTab("Artikel in Warenkorb Hinzufügen", kundenSeite);
        tabbedPane.addTab("Warenkorb", warenkorbGUI);
        // Fügen Sie hier weitere Tabs hinzu, wenn benötigt.

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                if (index == 0) {
                    kundenSeite.updateTabelle(); // Tabelle in Artikel hinzufügen Tab aktualisieren
                } else if (index == 1) {
                    warenkorbGUI.updateTabelle(); // Tabelle in Artikel entfernen Tab aktualisieren
                }
            }
        });

        this.add(tabbedPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
