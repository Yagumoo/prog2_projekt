package eshop.ui.gui;

import eshop.domain.E_Shop;
import eshop.enitities.*;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;

public class EShopGUI extends JFrame{

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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton customerButton = new JButton("Customer");
        //customerButton.addActionListener(e -> showCustomerLogin());
        panel.add(customerButton);

        JButton employeeButton = new JButton("Employee");
        //employeeButton.addActionListener(e -> showEmployeeLogin());
        panel.add(employeeButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private ImageIcon loadImageIcon() {
        java.net.URL imgURL = getClass().getClassLoader().getResource("eshop/ui/gui/Mann.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "eshop/ui/gui/Mann.png");
            return null;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(EShopGUI::new); //Lambda wurde durch :: ersetzt

    }
}
