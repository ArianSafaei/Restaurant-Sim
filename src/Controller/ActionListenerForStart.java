package Controller;

import GUI.CookUI;
import Model.Order;
import Model.OrderManager;
import Model.OrderNotFoundException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Start button in CookUI
 * Responsible for starting an order after order number has been entered
 */
public class ActionListenerForStart implements ActionListener{
    private CookUI cookUI;

    public ActionListenerForStart(CookUI cookUI) {
        this.cookUI = cookUI;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (cookUI.getStartButton().isEnabled()) {
            try {
                int orderToBeProcessed = Integer.parseInt(cookUI.getOrderNumberField().getText());
                Order order = OrderManager.getOrderManager().getOrderByID(orderToBeProcessed);
                if (OrderManager.getOrderManager().processPrepareOrder(orderToBeProcessed, cookUI.getCook())) {
                    cookUI.disableStartingOrders();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            order.toString()
                                    + " cannot be prepared, it must be the list of placed orders. It is currently "
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
}
