package GUI;

import Controller.*;
import Model.Bill;
import Model.Server;
import Model.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/*
The ServerUI class. ServerUI displays all tables in the restaurant and is used to perform actions
related to order placement and delivery.

 */
public class ServerUI extends JFrame {

  private JLayeredPane panel;
  private JLabel floor; // Background of the UI.
  private final int SCREEN_WIDTH = 800;
  private final int SCREEN_HEIGHT = 550;
  private final int height_constant = 90;
  private final int width_constant = 130;
  private Server server; // The server signed in to this UI.
  private List<Table> tables; // The list of tables appearing on the UI.
  private JLabel[] tableButtons; // Buttons corresponding to list of tables.
  private JButton signoutButton, shipmentButton, cancelButton, deliverButton;
  private int currentTableID; // ID of the table being highlighted.

  /**
   * Initialize a server UI with this list of tables for this server.
   *
   * @param tables The list of tables that are displayed/can be interacted with.
   * @param server The server using this UI.
   */
  public ServerUI(List<Table> tables, Server server) {
    this.tables = tables;
    this.server = server;
    setLayout(null);
    tableButtons = new JLabel[tables.size()];

    panel = new JLayeredPane();
    panel.setLayout(null);
    panel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    add(panel);

    ImageIcon floorImage = new ImageIcon("src/GUI/Pictures/Tiled_wood_floor.png");

    Image table_small = new ImageIcon("src/GUI/Pictures/Table small.png").getImage();
    Image table_disabled = new ImageIcon("src/GUI/Pictures/Table disabled.png").getImage();

    // to scale image to smaller size
    ImageIcon table = new ImageIcon(table_small.getScaledInstance(150, 135, Image.SCALE_SMOOTH));
    ImageIcon table_disabled_small =
        new ImageIcon(table_disabled.getScaledInstance(150, 135, Image.SCALE_SMOOTH));

    floor = new JLabel();
    floor.setIcon(floorImage);
    floor.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    panel.add(floor, new Integer(0));
    int vertical_scaler = 0;
    int horizontal_scaler = 0;

    // Set up table display
    for (int i = 0; i < tables.size(); i++) {
      tableButtons[i] = new JLabel();
      tableButtons[i].setIcon(table);

      tableButtons[i].setDisabledIcon(table_disabled_small);

      tableButtons[i].setText(String.valueOf(i + 1));
      tableButtons[i].setFont(new Font("Arial", Font.BOLD, 14));

      // to account for how much extra the auto JLabel.TOP puts the text on each image
      tableButtons[i].setIconTextGap(-20);

      tableButtons[i].setHorizontalTextPosition(JLabel.CENTER);
      tableButtons[i].setVerticalTextPosition(JLabel.TOP);

      if ((i % 5 == 0) && (i != 0)) {
        horizontal_scaler = 0;
        vertical_scaler += 1;
      }
      tableButtons[i].setBounds(
          10 + horizontal_scaler * 153,
          30 + vertical_scaler * 140,
          table.getIconWidth(),
          table.getIconHeight());
      panel.add(tableButtons[i], new Integer(i + 1));
      horizontal_scaler += 1;
      tableButtons[i].addMouseListener(new MouseListenerForTables(this, i));
    }

    setupSignoutButton(tables.size());
    setupReceiveShipmentButton(tables.size());
    setupDeliverOrderButton(tables.size());
    setupCancelOrderButton(tables.size());

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

    setTitle(server.toString());
  }

  /**
   * Set up button for order cancellation, and add ActionListener to handle input.
   *
   * @param numTables Number of tables displayed, used for positioning.
   */
  public void setupCancelOrderButton(int numTables) {
    ImageIcon cancelImage = new ImageIcon("src/GUI/Pictures/cancel-icon.png");

    cancelButton = new JButton("Cancel Order");
    cancelButton.setBounds(
        SCREEN_WIDTH - width_constant - 510,
        SCREEN_HEIGHT - height_constant,
        (int) cancelButton.getPreferredSize().getWidth() + 30,
        (int) cancelButton.getPreferredSize().getHeight() + 10);
    cancelButton.setIcon(cancelImage);

    // to make image in button appear from leftmost positiob
    cancelButton.setMargin(new Insets(0, 0, 0, 0));
    cancelButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    cancelButton.setFocusable(false);
    panel.add(cancelButton, new Integer(numTables + 3));
    cancelButton.addActionListener(new ActionListenerForCancel(this));
  }

  /**
   * Set up button for order delivery, and add ActionListener to handle input.
   *
   * @param numTables Number of tables displayed, used for positioning.
   */
  public void setupDeliverOrderButton(int numTables) {
    ImageIcon foodDomeImage = new ImageIcon("src/GUI/Pictures/food-dome-icon.png");

    deliverButton = new JButton("Deliver Next Order");
    deliverButton.setBounds(
        SCREEN_WIDTH - width_constant - 360,
        SCREEN_HEIGHT - height_constant,
        (int) deliverButton.getPreferredSize().getWidth() + 30,
        (int) deliverButton.getPreferredSize().getHeight() + 10);
    deliverButton.setIcon(foodDomeImage);

    // to make image in button appear from leftmost positiob
    deliverButton.setMargin(new Insets(0, 0, 0, 0));
    deliverButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    deliverButton.setFocusable(false);
    panel.add(deliverButton, new Integer(numTables + 2));
    deliverButton.addActionListener(new ActionListenerForDeliver(this));
  }

  /**
   * Set up button to receive shipments, and add ActionListener to handle input.
   *
   * @param numTables Number of tables displayed, used for positioning.
   */
  public void setupReceiveShipmentButton(int numTables) {
    ImageIcon shipmentImage = new ImageIcon("src/GUI/Pictures/truck-icon.png");

    shipmentButton = new JButton("Receive Shipment");
    shipmentButton.setBounds(
        SCREEN_WIDTH - width_constant - 180,
        SCREEN_HEIGHT - height_constant,
        (int) shipmentButton.getPreferredSize().getWidth() + 30,
        (int) shipmentButton.getPreferredSize().getHeight() + 10);
    shipmentButton.setIcon(shipmentImage);

    // to make image in button appear from leftmost position
    shipmentButton.setMargin(new Insets(0, 0, 0, 0));
    shipmentButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    shipmentButton.setFocusable(false);
    panel.add(shipmentButton, new Integer(numTables + 2));
    shipmentButton.addActionListener(new ActionListenerForReceiveShipment(this));
  }

  /**
   * Set up button to sign out, and add ActionListener to handle input.
   *
   * @param numTables Number of tables displayed, used for positioning.
   */
  public void setupSignoutButton(int numTables) {
    ImageIcon signoutImage = new ImageIcon("src/GUI/Pictures/sign-out-icon.png");

    signoutButton = new JButton("Sign out");
    signoutButton.setBounds(
        SCREEN_WIDTH - width_constant,
        SCREEN_HEIGHT - height_constant,
        (int) signoutButton.getPreferredSize().getWidth() + 10,
        (int) signoutButton.getPreferredSize().getHeight() + 10);
    signoutButton.setIcon(signoutImage);

    // to make image in button appear from leftmost position
    signoutButton.setMargin(new Insets(0, 0, 0, 0));
    signoutButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    signoutButton.setFocusable(false);
    panel.add(signoutButton, new Integer(numTables + 1));
    signoutButton.addActionListener(new ActionListenerForSignout(this));
  }

  /** Set up button to print one bill, and add ActionListener to handle input. */
  public JButton setupReceiptButton() {
    ImageIcon receiptIcon = new ImageIcon("src/GUI/Pictures/Cash-register-icon-small.png");
    JButton receiptButton = new JButton("Print Bill");
    receiptButton.setIcon(receiptIcon);

    // to make image in button appear from leftmost position
    receiptButton.setMargin(new Insets(0, 0, 0, 0));
    receiptButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    receiptButton.setFocusable(false);
    receiptButton.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            JOptionPane.getRootFrame().dispose();
            if (tables.get(currentTableID).getDeliveredOrders().size() == 0) {
              JOptionPane.showMessageDialog(null, "There are no orders for this table.");
            } else if (tables.get(currentTableID).isReceiptWindowOpen()) {
              JOptionPane.showMessageDialog(null,
                      "There is currently a receipt window open for this table which needs to be dealt with.");
            } else {
              Receipt frame =
                      new Receipt(
                              server.printOneBillForTable(tables.get(currentTableID)),
                              tables.get(currentTableID),
                              server);
              frame.setVisible(true);
            }
          }
        });
    return receiptButton;
  }

  /**
   * Set up button to print one bill for each customer at a table, and add ActionListener to handle
   * input.
   */
  public JButton setupSplitBillButton() {
    ImageIcon receiptIcon = new ImageIcon("src/GUI/Pictures/Cash-register-icon-small.png");
    JButton splitBillButton = new JButton("Split Bill");
    splitBillButton.setIcon(receiptIcon);

    // to make image in button appear from leftmost position
    splitBillButton.setMargin(new Insets(0, 0, 0, 0));
    splitBillButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    splitBillButton.setFocusable(false);
    splitBillButton.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            JOptionPane.getRootFrame().dispose();
            if (tables.get(currentTableID).getDeliveredOrders().size() == 0) {
              JOptionPane.showMessageDialog(null, "There are no orders for this table.");
            } else if (tables.get(currentTableID).isReceiptWindowOpen()) {
              JOptionPane.showMessageDialog(null,
                      "There is currently a receipt window open for this table which needs to be dealt with.");
            } else {
              int i = 0;
              for (Bill bill : server.splitBillForTable(tables.get(currentTableID))) {
                Receipt frame = new Receipt(bill, tables.get(currentTableID).getSeats()[i], server);
                frame.setLocation(frame.getX() + (i * 50), frame.getY());
                frame.setVisible(true);
                i++;
              }
            }
          }
        });
    return splitBillButton;
  }

  /** Set up button to take orders, and add ActionListener to handle input. */
  public JButton setupOrderButton() {
    ImageIcon orderIcon = new ImageIcon("src/GUI/Pictures/Order-icon.png");

    JButton orderButton = new JButton("Take Order");
    orderButton.setIcon(orderIcon);

    // to make image in button appear from leftmost position
    orderButton.setMargin(new Insets(0, 0, 0, 0));
    orderButton.setBackground(ColorUtils.buttonColor);

    // to remove blue border from button
    orderButton.setFocusable(false);

    orderButton.addMouseListener(new MouseListenerForOrder(this));
    return orderButton;
  }

  /**
   * Set up option panel which contains the print bill, split bill and take order buttons.
   */
  public void setupOptionPane() {
    JButton receiptButton = setupReceiptButton();
    JButton orderButton = setupOrderButton();
    JButton splitBillButton = setupSplitBillButton();

    // WARNING Changes background colour of any new windows created
    UIManager.put("OptionPane.background", ColorUtils.backgroundColor);
    UIManager.put("Panel.background", ColorUtils.backgroundColor);

    ImageIcon logo = new ImageIcon("src/GUI/Pictures/Fruits and Vegetables small.png");

    JButton[] buttons = {receiptButton, orderButton, splitBillButton};
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

  public Server getServer() {
    return server;
  }

  public JLabel[] getTableButtons() {
    return tableButtons;
  }

  public void setCurrentTableID(int currentTableID) {
    this.currentTableID = currentTableID;
  }

  public Table getCurrentTable() {
    return tables.get(currentTableID);
  }
}
