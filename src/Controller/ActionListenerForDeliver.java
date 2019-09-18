package Controller;

import GUI.OrderDeliveryOptionsUI;
import GUI.ServerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Deliver button in server
 */
public class ActionListenerForDeliver implements ActionListener {

    private ServerUI serverUI;

    /**
     * Constructor for this class
     * @param serverUI The serverUI window that this button is on
     */
    public ActionListenerForDeliver(ServerUI serverUI) {
        this.serverUI = serverUI;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new OrderDeliveryOptionsUI(serverUI.getLocation().x / 2,
                serverUI.getLocation().y / 2, serverUI);
    }
}
