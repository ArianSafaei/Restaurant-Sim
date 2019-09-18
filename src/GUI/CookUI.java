package GUI;

import Controller.ActionListenerForFinish;
import Controller.ActionListenerForReceiveShipment;
import Controller.ActionListenerForSignout;
import Controller.ActionListenerForStart;
import Model.Cook;

import javax.swing.*;
import java.awt.*;

public class CookUI extends JFrame {
  private JLayeredPane buttonPanel;
  private final int SCREEN_WIDTH = 600;
  private final int SCREEN_HEIGHT = 470;
  private final int height_constant = 90;
  private final int width_constant = 130;
  private JTextField orderNumberField; // Field to enter order number.
  private JButton signoutButton, shipmentButton, startButton, finishButton;
  private Cook cook; // The Cook signed in to this interface.

  /**
   * Set up the UI for this cook.
   *
   * @param cook The cook signed into this UI.
   */
  public CookUI(Cook cook) {
    this.cook = cook;
    setLayout(new BorderLayout());

    Image cookHatImage = new ImageIcon("src/GUI/Pictures/chefs-hat.png").getImage();
    // to scale image to smaller size
    ImageIcon cook_hat_small =
        new ImageIcon(cookHatImage.getScaledInstance(240, 330, Image.SCALE_SMOOTH));

    JLabel cookHat = new JLabel();
    cookHat.setIcon(cook_hat_small);
    cookHat.setHorizontalAlignment(JLabel.CENTER);
    getContentPane().setBackground(ColorUtils.backgroundColor);
    add(cookHat, BorderLayout.NORTH);

    JLabel text = new JLabel("Order Number");
    add(text, BorderLayout.WEST);

    orderNumberField = new JTextField();
    add(orderNumberField, BorderLayout.CENTER);

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
    setupFinishButton();
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    setupStartButton();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

    setTitle(cook.toString());
  }

  public JTextField getOrderNumberField() {
    return orderNumberField;
  }

  public Cook getCook() {
    return cook;
  }

  public JButton getStartButton() {
    return startButton;
  }

  public JButton getFinishButton() {
    return finishButton;
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

  /** Set-up shipment receiving button, and set up ActionListener to handle input. */
  public void setupReceiveShipmentButton() {
    ImageIcon shipmentImage = new ImageIcon("src/GUI/Pictures/truck-icon.png");

    shipmentButton = new JButton("Receive Shipment");
    shipmentButton.setIcon(shipmentImage);

    // to make image in button appear from leftmost positiob
    shipmentButton.setMargin(new Insets(0, 0, 0, 0));
    shipmentButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    shipmentButton.setFocusable(false);
    buttonPanel.add(shipmentButton);
    shipmentButton.addActionListener(new ActionListenerForReceiveShipment(this));
  }

  /** Set up button for starting orders, and set up ActionListener to handle input. */
  public void setupStartButton() {
    ImageIcon startImage = new ImageIcon("src/GUI/Pictures/oven-icon.png");

    startButton = new JButton("Start Order");
    startButton.setBounds(
        SCREEN_WIDTH - width_constant,
        SCREEN_HEIGHT - height_constant,
        (int) startButton.getPreferredSize().getWidth() + 10,
        (int) startButton.getPreferredSize().getHeight() + 10);
    startButton.setIcon(startImage);

    // to make image in button appear from leftmost positiob
    startButton.setMargin(new Insets(0, 0, 0, 0));
    startButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    startButton.setFocusable(false);
    buttonPanel.add(startButton);
    startButton.addActionListener(new ActionListenerForStart(this));
  }

  /** Set up button for finishing orders, and set up ActionListener to handle input. */
  public void setupFinishButton() {
    Image spoonLarge = new ImageIcon("src/GUI/Pictures/spoon.png").getImage();
    // to scale image to smaller size
    ImageIcon spoonSmall = new ImageIcon(spoonLarge.getScaledInstance(60, 25, Image.SCALE_SMOOTH));

    finishButton = new JButton("Finish Order");
    finishButton.setMaximumSize(
        new Dimension(
            (int) getPreferredSize().getWidth() - 140, (int) getPreferredSize().getHeight() - 375));
    finishButton.setPreferredSize(
        new Dimension(
            (int) getPreferredSize().getWidth() - 140, (int) getPreferredSize().getHeight() - 375));
    finishButton.setMinimumSize(
        new Dimension(
            (int) getPreferredSize().getWidth() - 140, (int) getPreferredSize().getHeight() - 375));
    finishButton.setIcon(spoonSmall);
    // to make image in button appear from leftmost position
    finishButton.setMargin(new Insets(0, 0, 0, 0));
    finishButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    finishButton.setFocusable(false);
    buttonPanel.add(finishButton);
    finishButton.addActionListener(new ActionListenerForFinish(this));
    finishButton.setEnabled(false);
  }

  /**
   * Disable the start order button and change the text display to 'busy'. Used to prevent cooks
   * from starting another order before finishing their current one.
   */
  public void disableStartingOrders() {
    startButton.setText("Busy");
    startButton.setEnabled(false);
    orderNumberField.setEnabled(false);
    finishButton.setEnabled(true);
  }
}
