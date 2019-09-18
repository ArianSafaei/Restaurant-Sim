package Controller;

import GUI.InformationPrintout;
import GUI.ManagerUI;
import Model.Inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Inventory printout button in manager
 */
public class ActionListenerForInventory implements ActionListener {

    private ManagerUI managerUI;

    /**
     * Constructor for this class
     * @param managerUI The managerUI window that this button is on
     */
    public ActionListenerForInventory(ManagerUI managerUI) {
        this.managerUI = managerUI;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        InformationPrintout frame = new InformationPrintout("Inventory", Inventory.getInventory().toString());
        frame.setVisible(true);
    }
}
