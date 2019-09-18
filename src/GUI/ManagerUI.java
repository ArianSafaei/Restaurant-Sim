package GUI;

import Controller.*;
import Model.Manager;

import javax.swing.*;
import java.awt.*;

public class ManagerUI extends JFrame {
  private JLayeredPane buttonPanel;
  private final int SCREEN_WIDTH = 700;
  private final int SCREEN_HEIGHT = 470;
  private final int height_constant = 90;
  private final int width_constant = 130;
  private Manager manager; // The manager signed in to this window.
  private JButton signoutButton, shipmentButton, undeliveredButton, inventoryButton, paymentButton;

  /**
   * Set up the UI for this manager.
   *
   * @param manager The manager signed into this UI.
   */
  public ManagerUI(Manager manager) {
    this.manager = manager;
    setLayout(new BorderLayout());

    Image managerImage = new ImageIcon("src/GUI/Pictures/Manager-Suit.png").getImage();
    // to scale image to smaller size
    ImageIcon managerSmall =
        new ImageIcon(managerImage.getScaledInstance(240, 330, Image.SCALE_SMOOTH));

    JLabel managerSuit = new JLabel();
    managerSuit.setIcon(managerSmall);
    managerSuit.setHorizontalAlignment(JLabel.CENTER);
    getContentPane().setBackground(ColorUtils.backgroundColor);
    add(managerSuit, BorderLayout.NORTH);

    buttonPanel = new JLayeredPane();

    // Line axis means layout is in a horizontal line
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

    // set up buttons right to left, in desired order
    buttonPanel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    buttonPanel.setAlignmentX(JLayeredPane.RIGHT_ALIGNMENT);
    // border to separate buttons from edge of window
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    // buttonPanel.add(Box.createHorizontalGlue());
    add(buttonPanel, BorderLayout.SOUTH);

    setupSignoutButton();
    // rigid area is to separate buttons
    // must go in between every two buttons created
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    setupReceiveShipmentButton();
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    setupInventoryPrintButton();
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    setupPrintUndeliveredButton();
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    setupdisplayPaymentsButton();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

    setTitle(manager.toString());
  }

  /** Set up sign-out button, and set up ActionListener to handle input. */
  public void setupSignoutButton() {
    ImageIcon signoutImage = new ImageIcon("src/GUI/Pictures/sign-out-icon.png");

    signoutButton = new JButton("Sign out");
    signoutButton.setBounds(
        SCREEN_WIDTH - width_constant,
        SCREEN_HEIGHT - height_constant,
        (int) signoutButton.getPreferredSize().getWidth() + 10,
        (int) signoutButton.getPreferredSize().getHeight() + 10);
    signoutButton.setIcon(signoutImage);

    // to make image in button appear from leftmost positiob
    signoutButton.setMargin(new Insets(0, 0, 0, 0));
    signoutButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    signoutButton.setFocusable(false);
    buttonPanel.add(signoutButton);
    signoutButton.addActionListener(new ActionListenerForSignout(this));
  }

  /** Set up receive shipment button, and set up ActionListener to handle input. */
  public void setupReceiveShipmentButton() {
    ImageIcon shipmentImage = new ImageIcon("src/GUI/Pictures/truck-icon.png");

    shipmentButton = new JButton("Receive Shipment");
    //        shipmentButton.setBounds(SCREEN_WIDTH - width_constant - 180, SCREEN_HEIGHT -
    // height_constant,
    //                (int) shipmentButton.getPreferredSize().getWidth() + 30,
    //                (int) shipmentButton.getPreferredSize().getHeight() + 10);
    shipmentButton.setMaximumSize(
        new Dimension(
            (int) getPreferredSize().getWidth() - 80, (int) getPreferredSize().getHeight() - 360));
    shipmentButton.setPreferredSize(
        new Dimension(
            (int) getPreferredSize().getWidth() - 80, (int) getPreferredSize().getHeight() - 360));
    shipmentButton.setMinimumSize(
        new Dimension(
            (int) getPreferredSize().getWidth() - 80, (int) getPreferredSize().getHeight() - 360));

    shipmentButton.setIcon(shipmentImage);

    // to make image in button appear from leftmost position
    shipmentButton.setMargin(new Insets(0, 0, 0, 0));
    shipmentButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    shipmentButton.setFocusable(false);
    buttonPanel.add(shipmentButton);
    shipmentButton.addActionListener(new ActionListenerForReceiveShipment(this));
  }

  /** Set up button to access undelivered orders, and set up ActionListener to handle input. */
  public void setupPrintUndeliveredButton() {

    undeliveredButton = new JButton("Undelivered Orders");
    undeliveredButton.setBounds(
        SCREEN_WIDTH - width_constant,
        SCREEN_HEIGHT - height_constant,
        (int) undeliveredButton.getPreferredSize().getWidth() + 10,
        (int) undeliveredButton.getPreferredSize().getHeight() + 10);

    // to make image in button appear from leftmost position
    undeliveredButton.setMargin(new Insets(0, 0, 0, 0));
    undeliveredButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    undeliveredButton.setFocusable(false);
    buttonPanel.add(undeliveredButton);
    undeliveredButton.addActionListener(new ActionListenerForUndelivered(this));
  }

  /** Set up button to check inventory, and set up ActionListener to handle input. */
  public void setupInventoryPrintButton() {
    inventoryButton = new JButton("Inventory Printout");

    // to make image in button appear from leftmost position
    inventoryButton.setMargin(new Insets(0, 0, 0, 0));
    inventoryButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    inventoryButton.setFocusable(false);
    buttonPanel.add(inventoryButton);
    inventoryButton.addActionListener(new ActionListenerForInventory(this));
  }

  /** Set up button to check payment record, and set up ActionListener to handle input. */
  public void setupdisplayPaymentsButton() {
    paymentButton = new JButton("Payment Record");

    // to make image in button appear from leftmost position
    paymentButton.setMargin(new Insets(0, 0, 0, 0));
    paymentButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    paymentButton.setFocusable(false);
    buttonPanel.add(paymentButton);
    paymentButton.addActionListener(new ActionListenerForPayments(this));
  }

  public Manager getManager() {
    return manager;
  }
}
