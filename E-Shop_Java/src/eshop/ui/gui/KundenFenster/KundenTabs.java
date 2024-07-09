package eshop.ui.gui.KundenFenster;

import eshop.domain.E_Shop;
import eshop.enitities.Mitarbeiter;

import javax.swing.*;
import java.awt.*;

public class KundenTabs extends JFrame {

    private E_Shop eShop;
    private Mitarbeiter aktuellerMitarbeiter;

    public KundenTabs(E_Shop eShop) {
        this.eShop = eShop;
        this.setTitle("E-Shop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820, 620);
        this.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Erstellen und Hinzufügen der Tabs
        KundenSeite kundenSeite = new KundenSeite(eShop);
        WarenkorbGUI warenkorbGUI = new WarenkorbGUI(eShop);

        tabbedPane.addTab("Artikel in Warenkorb Hinzufügen", kundenSeite);
        tabbedPane.addTab("Warenkorb", warenkorbGUI);
        // Fügen Sie hier weitere Tabs hinzu, wenn benötigt.

        this.add(tabbedPane, BorderLayout.CENTER);
        this.setVisible(true);
    }


}
