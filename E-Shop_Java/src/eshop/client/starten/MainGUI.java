package eshop.client.starten;

import eshop.client.clientServerVerbindung.Eshopclientsite;

import javax.swing.*;


public class MainGUI {
    private Eshopclientsite eShop  = new Eshopclientsite("localhost", 1028);

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