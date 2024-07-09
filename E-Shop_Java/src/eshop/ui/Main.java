package eshop.ui;


import eshop.domain.E_Shop;
import eshop.ui.gui.KundenFenster.KundenSeite;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            E_Shop eShop = new E_Shop(); // Assuming you have a default constructor for E_Shop
            new KundenSeite(eShop);
        });
    }
}


