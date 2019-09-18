package GUI;

import Model.OrderManager;
import Model.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Interface to handle order cancellations.

public class CancelOrderUI {
  private JPanel panel1;
  private JButton confirmButton;
  private JTextField orderNumberField; // Field to enter order number.
  private JTextField cancelReasonField; // Field to enter reason for cancellation.

  /**
   * Create this UI, passing in the Server object to handle the order cancellation.
   *
   * @param x x co-ordinate of the UI display.
   * @param y y co-ordinate of the UI display.
   * @param server The server handling the order.
   */
  public CancelOrderUI(int x, int y, Server server) {
    JFrame frame = new JFrame("Enter Order Number");

    setup();
    frame.getContentPane().add(panel1);

    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.pack();
    panel1.setVisible(true);

    // pass both coordinates as -1 if want center (for first window)
    if (x == -1 && y == -1) {
      // to center window on screen
      frame.setLocationRelativeTo(null);
    } else {
      frame.setLocation(x, y);
    }
    frame.getContentPane().setBackground(Color.getHSBColor(0, 201, 28));
    frame.setVisible(true);
    frame.setResizable(false);

    confirmButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String cancelReason = cancelReasonField.getText();

            try {
              int orderNumber = Integer.parseInt(orderNumberField.getText());

              /*tell order managing system to process cancellation. If unsuccessful, means that
              order has already been delivered.
              */
              if (!(OrderManager.getOrderManager()
                  .processCancelOrder(orderNumber, server, cancelReason))) {
                JOptionPane.showMessageDialog(
                    null, "This order may not be cancelled after " + "it has been delivered.");
              }
              frame.dispose();

              // handle invalid input
            } catch (Exception notInt) {
              JOptionPane.showMessageDialog(null, "Please enter a valid order number.");
            }
          }
        });
  }

  public void setup() {
    panel1 = new JPanel();
    panel1.setLayout(new BorderLayout(0, 0));
    panel1.setMinimumSize(new Dimension(355, 200));
    panel1.setOpaque(true);
    panel1.setPreferredSize(new Dimension(355, 200));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridBagLayout());
    panel2.setBackground(ColorUtils.backgroundColor);
    panel1.add(panel2, BorderLayout.WEST);
    final JLabel label1 = new JLabel();
    label1.setText("Order Number ");
    GridBagConstraints gbc;
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    panel2.add(label1, gbc);
    final JLabel label2 = new JLabel();
    label2.setText("Reason for Cancellation ");
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    panel2.add(label2, gbc);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayout(1, 1, -1, -1));
    Font panel3Font = new Font("Bauhaus 93", Font.PLAIN, 16);
    panel3.setFont(panel3Font);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    panel2.add(panel3, gbc);
    final JLabel label3 = new JLabel();
    label3.setBackground(ColorUtils.backgroundColor);
    label3.setIcon(new ImageIcon(getClass().getResource("/GUI/Pictures/Fruits and Vegetables small.png")));
    label3.setOpaque(true);
    label3.setText("");
    panel3.add(label3);
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    panel4.setBackground(ColorUtils.backgroundColor);
    panel1.add(panel4, BorderLayout.SOUTH);
    confirmButton = new JButton();
    confirmButton.setBackground(ColorUtils.buttonColor);
    confirmButton.setText("Confirm Cancellation");
    panel4.add(confirmButton);
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridBagLayout());
    panel5.setBackground(ColorUtils.backgroundColor);
    panel1.add(panel5, BorderLayout.CENTER);
    orderNumberField = new JTextField();
    orderNumberField.setBackground(ColorUtils.fieldColor);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel5.add(orderNumberField, gbc);
    cancelReasonField = new JTextField();
    cancelReasonField.setBackground(ColorUtils.fieldColor);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel5.add(cancelReasonField, gbc);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayout(1, 1, -1, -1));
    panel6.setBackground(ColorUtils.backgroundColor);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    panel5.add(panel6, gbc);
    final JPanel spacer2 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.ipady = 70;
    spacer2.setBackground(ColorUtils.backgroundColor);
    panel5.add(spacer2, gbc);
    final JLabel label4 = new JLabel();
    label4.setBackground(ColorUtils.backgroundColor);
    Font label4Font = new Font("Bauhaus 93", Font.PLAIN, 18);
    label4.setFont(label4Font);
    label4.setOpaque(true);
    label4.setText("Everything Here Restaurant");
    label4.setVisible(true);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel5.add(label4, gbc);
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    panel7.setBackground(ColorUtils.backgroundColor);
    panel1.add(panel7, BorderLayout.EAST);
  }

  public int setOrderNumber() {
    int order_id = -1;
    // must try to see if value in data field is actually an int,
    // otherwise will generate exception when trying to parse int
    try {
      order_id = Integer.parseInt(orderNumberField.getText());
    } catch (Exception notInt) {
      JOptionPane.showMessageDialog(null, "Did not enter valid entry");
    }
    return order_id;
  }
}
