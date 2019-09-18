package Controller;

import GUI.OrderSendbackUI;
import GUI.ServerUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Sendback button in server that shows up after
 * deliver button pressed and order number entered
 */
public class ActionListenerForSendback implements ActionListener {

  private ServerUI serverUI;
  private int orderHandled;

  /**
   * Constructor for this class
   * @param serverUI The serverUI window that this button is on
   * @param orderHandled to check if order had been handled
   */
  public ActionListenerForSendback(ServerUI serverUI, int orderHandled) {
    this.serverUI = serverUI;
    this.orderHandled = orderHandled;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    new OrderSendbackUI(
        serverUI.getLocation().x / 2,
        serverUI.getLocation().y / 2,
        serverUI.getServer(), orderHandled);
    JOptionPane.getRootFrame().dispose();

  }
}
