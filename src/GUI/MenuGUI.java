package GUI;

import Controller.MenuController;
import Model.Menu;
import Model.MenuItem;
import Model.Server;
import Model.Table;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.GridLayout;

/**
This windows displays all the menuItems
A server places an order by selecting an item through the MenuGUI
 */
public class MenuGUI extends JFrame {

    /**
    Using GridLayout, the frame is divided into 3 columns
    and a dynamic number of rows depending on the number of menu items

    Each element of the grid is further divided into 2 columns
    The left column contains the picture
    The right column contains information about the menuItem and a button to order
     */
    public MenuGUI(Menu menu, Server server, Table table) {
        super("Menu");

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1280, 720);

        JPanel container1 = new JPanel();
        container1.setLayout(new GridLayout(menu.getMenuItems().size() / 3 + 1, 3));
        container1.setBackground(ColorUtils.backgroundColor);

        JScrollPane scrollPane =
                new JScrollPane(
                        container1,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane);

        for (MenuItem menuItem : menu.getMenuItems()) {
            Icon icon = new ImageIcon(menuItem.getIcon());
            JLabel jLabel1 = new JLabel(icon);

            JLabel jLabel2 = new JLabel(menuItem.getName(), SwingConstants.CENTER);
            jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            JLabel jLabel3 = new JLabel(menuItem.getPriceInString(), SwingConstants.CENTER);
            jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton jButton = new JButton("Order");
            jButton.addActionListener(new MenuController.OrderListener(menuItem, server, table));
            jButton.setContentAreaFilled(false);
            jButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JPanel container2 = new JPanel();
            container2.setLayout(new GridLayout(1, 2));
            container2.setBorder(new EmptyBorder(64, 16, 64, 16));
            container2.setBackground(ColorUtils.backgroundColor);
            JPanel container3 = new JPanel();
            container3.setLayout(new GridLayout(3, 1));
            container3.setBackground(ColorUtils.backgroundColor);

            container2.add(jLabel1);

            container3.add(jLabel2);
            container3.add(jLabel3);
            container3.add(jButton);

            container2.add(container3);

            container1.add(container2);
        }
        setVisible(true);
    }

}

