package eshop.ui;


import eshop.domain.E_Shop;
import eshop.enitities.Mitarbeiter;
import eshop.ui.gui.EShopGUI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            E_Shop eShop = new E_Shop(); // Assuming you have a default constructor for E_Shop
            new EShopGUI(eShop);
        });
    }
}


