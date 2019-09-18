package Controller;

import GUI.ManagerUI;
import GUI.UndeliveredOrdersUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Undelivered button in manager
 */
public class ActionListenerForUndelivered implements ActionListener {

    private ManagerUI managerUI;

    /**
     * Constructor for this class
     * @param managerUI The managerUI window that this button is on
     */
    public ActionListenerForUndelivered(ManagerUI managerUI) {
        this.managerUI = managerUI;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        UndeliveredOrdersUI undeliveredOrdersUI = new UndeliveredOrdersUI(managerUI.getManager());
        undeliveredOrdersUI.setVisible(true);
    }
}
