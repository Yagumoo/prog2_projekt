package eshop.client.starten;

import eshop.client.clientServerVerbindung.Eshopclientsite;

import javax.swing.*;


public class MainGUI {
    private Eshopclientsite eShopclientsite  = new Eshopclientsite("localhost", 1028);

    public static void main(String[] args) {
        MainGUI mainGUI = new MainGUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginOptionenGUI(mainGUI.eShopclientsite);
            }
        });
    }



}