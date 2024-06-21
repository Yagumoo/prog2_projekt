package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.ui.gui.LoginGUI;
import eshop.enitities.*;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;

public class EShopGUI extends JFrame {

    private E_Shop eShop;
    private final Person eingeloggtePerson = null;

    public EShopGUI() {
        eShop = new E_Shop();
        //frame = new JFrame("Wagners E-Shop");
        this.setTitle("Wagners E-Shop");
        this.getContentPane().setBackground(new Color(123,50,250));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 420);
        this.setLayout(new BorderLayout());

        // Load image icon
        ImageIcon image = loadImageIcon();
        if (image != null) {
            this.setIconImage(image.getImage());
        }

        showLoginOptions();
    }

    private void showLoginOptions() {

        JPanel panelN = new JPanel();
        JPanel panelE = new JPanel();
        JPanel panelS = new JPanel();
        JPanel panelW = new JPanel();
        JPanel panelC = new JPanel();

        //panel.setLayout(new GridLayout(3, 1));

        JTextField suchFeld = new JTextField(20);
        panelN.add(suchFeld);

        JButton suchButton = new JButton("Suchen");
        //customerButton.addActionListener(e -> showCustomerLogin());
        panelN.add(suchButton);


        JButton customerButton = new JButton("Customer");
        //customerButton.addActionListener(e -> showCustomerLogin());
        panelC.add(customerButton);

        JButton employeeButton = new JButton("Employee");
        //employeeButton.addActionListener(e -> showEmployeeLogin());
        panelC.add(employeeButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panelS.add(exitButton);

        this.add(panelN, BorderLayout.NORTH);
        this.add(panelE, BorderLayout.EAST);
        this.add(panelS, BorderLayout.SOUTH);
        this.add(panelW, BorderLayout.WEST);
        this.add(panelC, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Icon/Mann.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/ui/gui/Icon/Mann.png");
            return null;
        }
    }
}


