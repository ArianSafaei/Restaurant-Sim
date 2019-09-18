package Controller;

import GUI.CookUI;
import Model.Inventory;
import Model.Order;
import Model.OrderManager;
import Model.OrderNotFoundException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Finish button in CookUI
 * Responsible for finishing orders that have already been started by cook
 */
public class ActionListenerForFinish implements ActionListener {

  private CookUI cookUI;

  /**
   * Constructor for this class
   * @param cookUI The cookUI window that this button is on
   */
  public ActionListenerForFinish(CookUI cookUI) {
    this.cookUI = cookUI;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    try {
      int orderToBeProcessed = Integer.parseInt(cookUI.getOrderNumberField().getText());
      Order order = OrderManager.getOrderManager().getOrderByID(orderToBeProcessed);
      if (OrderManager.getOrderManager()
          .processCompleteOrder(
              orderToBeProcessed, cookUI.getCook(), Inventory.getInventory())) {
        // re-enables button since cook has finished order
        if (!cookUI.getStartButton().isEnabled()) {
          cookUI.getStartButton().setText("Start Order");
          cookUI.getStartButton().setEnabled(true);
          cookUI.getOrderNumberField().setEnabled(true);
          cookUI.getFinishButton().setEnabled(false);
          cookUI.getOrderNumberField().setText("");
        }
      } else {
        JOptionPane.showMessageDialog(
            null,
            order.toString()
                + " cannot be completed before it is seen. It is currently "
                + order.getStatus()
                + ".");
      }
    } catch (OrderNotFoundException e1) {
      JOptionPane.showMessageDialog(null, "The specified order does not exist.");
    } catch (NumberFormatException e2) {
      JOptionPane.showMessageDialog(null, "Not a valid number.");
    }
  }
}
