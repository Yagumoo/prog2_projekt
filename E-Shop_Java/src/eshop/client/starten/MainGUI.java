package eshop.client.starten;

import eshop.server.domain.E_Shop;

import javax.swing.*;


public class MainGUI {
    private E_Shop eShop = new E_Shop();

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(LoginOptionenGUI::new);//Lambda wurde durch :: ersetzt

        MainGUI mainGUI = new MainGUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginOptionenGUI(mainGUI.eShop);
            }
        });
    }

}
