package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.ui.gui.LoginGUI;
import eshop.ui.gui.EShopGUI;
import eshop.enitities.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);//Lambda wurde durch :: ersetzt
    }

}
