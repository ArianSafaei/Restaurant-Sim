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

public class ShipmentReceiver extends JFrame {

  private JPanel contentPane;
  private JTextField txtIngredient;
  private JTextField txtAmount;

  /** Create the frame. */
  public ShipmentReceiver(Employee employee) {
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
    txtIngredient.setBackground(ColorUtils.fieldColor);

    txtAmount = new JTextField();
    txtAmount.setColumns(10);
    txtAmount.setBackground(ColorUtils.fieldColor);

    JButton btnConfirm = new JButton("Confirm Shipment Received");
    btnConfirm.setBackground(ColorUtils.buttonColor);

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

    JButton btnCancel = new JButton("Cancel");
    btnCancel.setBackground(ColorUtils.buttonColor);

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
                            .createParallelGroup(Alignment.LEADING)
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
