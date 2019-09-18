package Controller;

import GUI.CookUI;
import GUI.ManagerUI;
import GUI.ServerUI;
import GUI.ShipmentConfirmationUI;
import Model.Employee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for ReceiveShipment button in managerUI, serverUI, and cookUI
 */
public class ActionListenerForReceiveShipment implements ActionListener {

    private Employee occupation;

    /**
     * Constructor for this class
     * @param employeeWindow The UI window corresponding to their occupation that this button is on
     */
    public ActionListenerForReceiveShipment(JFrame employeeWindow) {
        if(employeeWindow instanceof ServerUI){
            ServerUI serverUI = (ServerUI) employeeWindow;
            occupation = serverUI.getServer();
        } else if(employeeWindow instanceof CookUI){
            CookUI cookUI = (CookUI) employeeWindow;
            occupation = cookUI.getCook();
        } else if(employeeWindow instanceof ManagerUI){
            ManagerUI managerUI = (ManagerUI) employeeWindow;
            occupation = managerUI.getManager();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ShipmentConfirmationUI frame = new ShipmentConfirmationUI(occupation);
        frame.setVisible(true);

    }
}


