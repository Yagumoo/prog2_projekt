package eshop.client.gui.KundenFenster;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.common.enitities.Kunde;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
/**
 * Die Klasse {@code KundenTabs} repräsentiert das Hauptfenster für Kunden im E-Shop.
 *
 * Diese Klasse erstellt ein Fenster, das verschiedene Tabs für Kundenaktionen bereitstellt. Die Tabs enthalten
 * Funktionalitäten zum Hinzufügen von Artikeln zum Warenkorb und zur Verwaltung des Warenkorbs.
 * Es wird ein {@link JTabbedPane} verwendet, um zwischen diesen Funktionen zu navigieren.
 *
 * <p>Die Tabs in diesem Fenster sind:</p>
 * <ul>
 *     <li><b>Artikel in Warenkorb Hinzufügen:</b> Ermöglicht dem Kunden, Artikel aus dem Katalog in den Warenkorb zu legen.</li>
 *     <li><b>Warenkorb:</b> Zeigt den aktuellen Inhalt des Warenkorbs und bietet Optionen zur Bearbeitung des Warenkorbs.</li>
 * </ul>
 *
 * <p>Die Klasse reagiert auf Tab-Wechsel-Ereignisse, um sicherzustellen, dass die Tabellen in den einzelnen Tabs
 * immer mit aktuellen Daten aus dem {@code Eshopclientsite} aktualisiert werden.</p>
 *
 * @see KundenSeite
 * @see WarenkorbGUI
 */
public class KundenTabs extends JFrame {

    private Eshopclientsite eShopclientsite;
    private Kunde aktuellerKunde;
    /**
     * Erstellt ein neues {@code KundenTabs} Fenster für den angegebenen E-Shop-Client und Kunden.
     *
     * <p>Der Konstruktor richtet die Größe, das Layout und die Tabs für das Hauptfenster der Kundenansicht ein.</p>
     *
     * @param eShopclientsite Das E-Shop-Client-Objekt, das die Kommunikation mit dem Server verwaltet.
     * @param eingeloggteKunde Der Kunde, der in diesem Fenster eingeloggt ist und dessen Informationen angezeigt werden.
     */
    public KundenTabs(Eshopclientsite eShopclientsite, Kunde eingeloggteKunde) {
        this.eShopclientsite = eShopclientsite;
        this.setTitle("E-Shop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820, 620);
        this.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Erstellen und Hinzufügen der Tabs
        KundenSeite kundenSeite = new KundenSeite(eShopclientsite, eingeloggteKunde);
        WarenkorbGUI warenkorbGUI = new WarenkorbGUI(eShopclientsite, eingeloggteKunde);

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
