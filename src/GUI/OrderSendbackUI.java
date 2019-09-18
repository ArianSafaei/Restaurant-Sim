package GUI;

import Model.Order;
import Model.OrderManager;
import Model.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Interface for sending orders back.

public class OrderSendbackUI {
  private JPanel mainPanel;
  private JButton sendBackButton;
  private JTextField cancelReasonField; // Field to enter reason of send-back.

  /**
   * Create this UI, passing in the Server object and the order being sent back.
   *
   * @param x x co-ordinate of the UI display.
   * @param y y co-ordinate of the UI display.
   * @param server The server handling the order.
   * @param orderHandled The order being handled.
   */
  public OrderSendbackUI(int x, int y, Server server, int orderHandled) {
    JFrame frame = new JFrame("Enter Order Number");

    setup();
    frame.getContentPane().add(mainPanel);
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.pack();
    mainPanel.setVisible(true);

    // pass both coordinates as -1 if want center (for first window)
    if (x == -1 && y == -1) {
      // to center window on screen
      frame.setLocationRelativeTo(null);
    } else {
      frame.setLocation(x, y);
    }
    frame.getContentPane().setBackground(Color.getHSBColor(0, 201, 28));
    frame.setAlwaysOnTop(true);
    frame.setVisible(true);
    frame.setResizable(false);

    sendBackButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String cancelReason = cancelReasonField.getText();

            try {
              Order order = OrderManager.getOrderManager().getOrderByID(orderHandled);

              // handled unsuccessful send-back due to incorrect order status
              if (!(OrderManager.getOrderManager()
                  .processReturnOrder(orderHandled, server, cancelReason))) {
                JOptionPane.showMessageDialog(
                    null,
                    order.toString()
                        + " cannot be sent back before it is filled. It is currently "
                        + order.getStatus()
                        + ".");
              }
              frame.dispose();
            } catch (Exception notInt) {
              JOptionPane.showMessageDialog(null, "Please enter a valid order number.");
            }
          }
        });
  }

  public void setup() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(0, 0));
    mainPanel.setMinimumSize(new Dimension(350, 200));
    mainPanel.setOpaque(true);
    mainPanel.setPreferredSize(new Dimension(350, 200));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridBagLayout());
    panel2.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel2, BorderLayout.WEST);
    final JLabel label1 = new JLabel();
    label1.setText("Reason for Sendback ");
    GridBagConstraints gbc;
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    panel2.add(label1, gbc);
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
    mainPanel.add(panel4, BorderLayout.SOUTH);
    sendBackButton = new JButton();
    sendBackButton.setBackground(ColorUtils.buttonColor);
    sendBackButton.setText("Confirm Sendback");
    panel4.add(sendBackButton);
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridBagLayout());
    panel5.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel5, BorderLayout.CENTER);
    cancelReasonField = new JTextField();
    cancelReasonField.setBackground(ColorUtils.fieldColor);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
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
    mainPanel.add(panel7, BorderLayout.EAST);
  }
}
