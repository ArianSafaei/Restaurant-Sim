package GUI;

import Model.Employee;
import Model.Inventory;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window which allows for employees to receive shipment of ingredients and
 * add them to the inventory stock.
 */
public class ShipmentConfirmationUI extends JFrame {

  private JPanel contentPane; // The JPanel of this window.
  private JTextField txtIngredient; // Textfield to store the ingredient name.
  private JTextField txtAmount; // Textfield to store how much of an ingredient is being added.

  /**
   * Create the frame.
   *
   * @param employee The employee which opens this window to confirm shipment.
   */
  public ShipmentConfirmationUI(Employee employee) {
    setTitle("Confirm Shipment of Ingredient");
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setBounds(100, 100, 500, 200);
    contentPane = new JPanel();
    contentPane.setBackground(ColorUtils.backgroundColor);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);

    JLabel lblIngredientName = new JLabel("Ingredient Name:");
    lblIngredientName.setFont(new Font("Calibri", Font.BOLD, 18));

    JLabel lblAmount = new JLabel("Amount:");
    lblAmount.setFont(new Font("Calibri", Font.BOLD, 18));

    txtIngredient = new JTextField();
    txtIngredient.setColumns(10);

    txtAmount = new JTextField();
    txtAmount.setColumns(10);

    ImageIcon okIcon = new ImageIcon("src/GUI/Pictures/ok-icon.png");

    JButton btnConfirm = new JButton("Confirm");
    btnConfirm.setIcon(okIcon);
    btnConfirm.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (txtIngredient.getText().equals("")) {
              JOptionPane.showMessageDialog(null, "No ingredient has been entered.");
              return;
            }
            try {
              int amountReceived = Integer.parseInt(txtAmount.getText());
              String ingredientReceived = txtIngredient.getText();
              if (employee.updateInventory(
                      Inventory.getInventory(), ingredientReceived, amountReceived)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Shipment of "
                                + amountReceived
                                + "x "
                                + ingredientReceived
                                + " has been received.");
                dispose();
              }
            } catch (NumberFormatException e1) {
              JOptionPane.showMessageDialog(null, "Not a valid integer amount.");
            }
          }
        });

    Image returnIcon = new ImageIcon("src/GUI/Pictures/undo.png").getImage();

    ImageIcon returnIconSmall =
        new ImageIcon(returnIcon.getScaledInstance(48, 48, Image.SCALE_SMOOTH));

    JButton btnCancel = new JButton("Cancel");
    btnCancel.setIcon(returnIconSmall);
    btnCancel.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        });
    GroupLayout gl_contentPane = new GroupLayout(contentPane);
    gl_contentPane.setHorizontalGroup(
        gl_contentPane
            .createParallelGroup(Alignment.LEADING)
            .addGroup(
                gl_contentPane
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        gl_contentPane
                            .createParallelGroup(Alignment.CENTER)
                            .addGroup(
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addComponent(lblIngredientName)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(
                                        txtIngredient,
                                        GroupLayout.DEFAULT_SIZE,
                                        311,
                                        Short.MAX_VALUE))
                            .addGroup(
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addComponent(
                                        lblAmount,
                                        GroupLayout.PREFERRED_SIZE,
                                        132,
                                        GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(
                                        txtAmount,
                                        GroupLayout.PREFERRED_SIZE,
                                        311,
                                        Short.MAX_VALUE))
                            .addGroup(
                                Alignment.TRAILING,
                                gl_contentPane
                                    .createSequentialGroup()
                                    .addComponent(btnCancel)
                                    .addPreferredGap(
                                        ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                                    .addComponent(btnConfirm)))
                    .addContainerGap()));
    gl_contentPane.setVerticalGroup(
        gl_contentPane
            .createParallelGroup(Alignment.LEADING)
            .addGroup(
                gl_contentPane
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        gl_contentPane
                            .createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblIngredientName)
                            .addComponent(
                                txtIngredient,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(
                        gl_contentPane
                            .createParallelGroup(Alignment.BASELINE)
                            .addComponent(
                                lblAmount,
                                GroupLayout.PREFERRED_SIZE,
                                23,
                                GroupLayout.PREFERRED_SIZE)
                            .addComponent(
                                txtAmount,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                    .addGroup(
                        gl_contentPane
                            .createParallelGroup(Alignment.BASELINE)
                            .addComponent(btnConfirm)
                            .addComponent(btnCancel))
                    .addContainerGap()));
    contentPane.setLayout(gl_contentPane);
  }
}
