package GUI;

import Controller.ActionListenerForBillAssignment;
import Controller.ActionListenerForSendback;
import Model.OrderManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Options interface for order delivery attempts.

public class OrderDeliveryOptionsUI {
  private JPanel mainPanel;
  private JButton orderEnterButton;
  private JTextField orderNumberField; // The field to enter order number
  private int orderHandled; // The order being handled.
  private ServerUI serverUI; // The UI of the server which opened this interface.

  /**
   * Create this UI, passing in the ServerUI which accessed this interface.
   *
   * @param x x co-ordinate of the UI display.
   * @param y y co-ordinate of the UI display.
   * @param serverUI The server interface which accessed this interface.
   */
  public OrderDeliveryOptionsUI(int x, int y, ServerUI serverUI) {
    this.serverUI = serverUI;
    JFrame frame = new JFrame("Delivery Options");

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

    orderEnterButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              orderHandled = Integer.parseInt(orderNumberField.getText());

              // Check if order entered exists within system.
              if (OrderManager.getOrderManager().orderExistsInSystem(orderHandled)) {
                frame.dispose();
                setupDeliverOptionPane();
              } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid order number.");
              }
            } catch (NumberFormatException e1) {
              JOptionPane.showMessageDialog(null, "Not a valid integer.");
            }
          }
        });
  }

  /**
   * Set up this UI.
   */
  public void setup() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(0, 0));
    mainPanel.setMinimumSize(new Dimension(327, 200));
    mainPanel.setOpaque(true);
    mainPanel.setPreferredSize(new Dimension(327, 200));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridBagLayout());
    panel2.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel2, BorderLayout.WEST);
    final JLabel label1 = new JLabel();
    label1.setText("Order Number: ");
    GridBagConstraints gbc;
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
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
    orderEnterButton = new JButton();
    orderEnterButton.setBackground(ColorUtils.buttonColor);
    orderEnterButton.setText("Ok");
    panel4.add(orderEnterButton);
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridBagLayout());
    panel5.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel5, BorderLayout.CENTER);
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

  public JButton setupConfirmButton() {
    ImageIcon okIcon = new ImageIcon("src/GUI/Pictures/ok-icon.png");

    JButton confirmButton = new JButton("Confirm");
    confirmButton.setIcon(okIcon);

    // to make image in button appear from leftmost position
    confirmButton.setMargin(new Insets(0, 0, 0, 0));
    confirmButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    confirmButton.setFocusable(false);

    confirmButton.addActionListener(new ActionListenerForBillAssignment(serverUI, orderHandled));
    return confirmButton;
  }

  public JButton setupSendbackButton() {
    Image returnIcon = new ImageIcon("src/GUI/Pictures/undo.png").getImage();

    ImageIcon returnIconSmall =
        new ImageIcon(returnIcon.getScaledInstance(48, 48, Image.SCALE_SMOOTH));

    JButton sendbackButton = new JButton("Send Back");
    sendbackButton.setIcon(returnIconSmall);

    // to make image in button appear from leftmost position
    sendbackButton.setMargin(new Insets(0, 0, 0, 0));
    sendbackButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    sendbackButton.setFocusable(false);

    sendbackButton.addActionListener(new ActionListenerForSendback(serverUI, orderHandled));
    JOptionPane.getRootFrame().dispose();
    return sendbackButton;
  }

  public void setupDeliverOptionPane() {
    JButton confirmButton = setupConfirmButton();
    JButton sendBack = setupSendbackButton();

    // WARNING Changes background colour of any new windows created
    UIManager.put("OptionPane.background", ColorUtils.backgroundColor);
    UIManager.put("Panel.background", ColorUtils.backgroundColor);

    ImageIcon logo = new ImageIcon("src/GUI/Pictures/Fruits and Vegetables small.png");

    JButton[] buttons = {confirmButton, sendBack};
    JOptionPane.showOptionDialog(
        null,
        "Choose an action",
        "Options",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        logo,
        buttons,
        buttons[0]);
  }
}
