package GUI;

import Controller.ModifyIngredientsController;
import Model.MenuItem;
import Model.Server;
import Model.Table;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
This window displays all the ingredients of a menuItem
A server adds or subtracts ingredients by clicking on add or subtract buttons beside the ingredient's name
A server sends the order to the kitchen by clicking on the confirm button
 */
public class ModifyIngredientsGUI extends JFrame {

    /**
    Using GridLayout, the frame is divided into 3 columns
    and a dynamic number of rows depending on the number of ingredients in the menuItem

    Each element of the grid is further divided into 3 columns,
    for the name and the buttons to add and subtract ingredients
     */
    public ModifyIngredientsGUI(MenuItem menuItem, Server server, Table table) {
        super("Modify Ingredients");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 480);

        JPanel container1 = new JPanel();
        container1.setLayout(new GridLayout(menuItem.getIngredients().size() + 1, 3));
        container1.setBackground(ColorUtils.backgroundColor);

        JScrollPane scrollPane =
                new JScrollPane(
                        container1,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane);

        for (HashMap.Entry<String, Integer> entry : menuItem.getIngredients().entrySet()) {
            String ingredient = entry.getKey();
            Object amount = entry.getValue();

            JLabel jLabel = new JLabel(ingredient + ": " + amount, SwingConstants.CENTER);
            jLabel.setBorder(new EmptyBorder(32, 8, 32, 8));
            jLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

            JButton jButton1 = new JButton("-");
            jButton1.addActionListener(new ModifyIngredientsController.SubtractListener(menuItem, ingredient, jLabel));
            jButton1.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(16, 64, 16, 64), new LineBorder(Color.black)));
            jButton1.setContentAreaFilled(false);
            jButton1.setFont(new Font("Segoe UI", Font.BOLD, 24));

            JButton jButton2 = new JButton("+");
            jButton2.addActionListener(new ModifyIngredientsController.AddListener(menuItem, ingredient, jLabel));
            jButton2.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(16, 64, 16, 64), new LineBorder(Color.black)));
            jButton2.setContentAreaFilled(false);
            jButton2.setFont(new Font("Segoe UI", Font.BOLD, 24));

            container1.add(jButton1);
            container1.add(jLabel);
            container1.add(jButton2);
        }

        JButton jButton = new JButton("Confirm");
        jButton.addActionListener(new ModifyIngredientsController.ConfirmListener(this, menuItem, server, table));
        jButton.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(16, 64, 16, 64), new LineBorder(Color.black)));
        jButton.setContentAreaFilled(false);
        jButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JPanel empty = new JPanel();
        empty.setBackground(ColorUtils.backgroundColor);

        container1.add(empty);
        container1.add(jButton);

        setVisible(true);
    }
}