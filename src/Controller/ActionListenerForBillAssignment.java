package Controller;

import GUI.AssignOrderToSeatUI;
import GUI.ServerUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for BillAssignment button
 */
public class ActionListenerForBillAssignment implements ActionListener {

  private ServerUI serverUI;
  private int orderHandled;

  /**
   * Constructor for this class
   * @param serverUI The serverUI window that this button is on
   * @param orderHandled to check if order had been handled
   */
  public ActionListenerForBillAssignment(ServerUI serverUI, int orderHandled) {
    this.serverUI = serverUI;
    this.orderHandled = orderHandled;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    JOptionPane.getRootFrame().dispose();

    new AssignOrderToSeatUI(
        serverUI.getLocation().x / 2,
        serverUI.getLocation().y / 2,
        serverUI.getServer(), orderHandled);
  }
}
