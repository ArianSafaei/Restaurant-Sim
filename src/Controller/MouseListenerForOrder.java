package Controller;

import GUI.MenuGUI;
import GUI.ServerUI;
import Model.Menu;
import Model.OrderManager;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Mouse listener class for Take order button in server which appears after clicking on a table.
 */
public class MouseListenerForOrder implements MouseListener {
    private ServerUI serverUI;

    /**
     * Constructor for this class
     * @param serverUI The serverUI window that this button is on
     */
    public MouseListenerForOrder(ServerUI serverUI) {
        this.serverUI = serverUI;
    }

    //to highlight table as mouse goes over it
    public void mouseEntered(MouseEvent e) {

    }

    //unhighlight table as mouse leaves
    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        JOptionPane.getRootFrame().dispose();
        if (OrderManager.getOrderManager().hasOrdersReadyForDelivery()) {
            JOptionPane.showMessageDialog(null,
                    "Server cannot take an order because there is an order that is ready to deliver.");
        } else {
            MenuGUI menuGUI = new MenuGUI(new Menu(), serverUI.getServer(), serverUI.getCurrentTable());
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }
}
