package eshop.ui.gui;

import eshop.domain.E_Shop;

import javax.swing.*;


public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginOptionenGUI::new);//Lambda wurde durch :: ersetzt
        //SwingUtilities.invokeLater(RegistrierenKundeGUI::new);//Lambda wurde durch :: ersetzt
    }

}
