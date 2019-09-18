package Controller;

import GUI.CookUI;
import GUI.ManagerUI;
import GUI.ServerUI;
import Model.Employee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for common sign out button in managerUI, serverUI, and cookUI
 */
public class ActionListenerForSignout implements ActionListener {

  private JFrame employeeWindow;
  private Employee employee;

  /**
   * Constructor for this class
   * @param employeeWindow The UI window corresponding to their occupation that this button is on
   */
  public ActionListenerForSignout(JFrame employeeWindow) {
    this.employeeWindow = employeeWindow;
    if (employeeWindow instanceof ServerUI) {
      ServerUI serverUI = (ServerUI) employeeWindow;
      employee = serverUI.getServer();
    } else if (employeeWindow instanceof CookUI) {
      CookUI cookUI = (CookUI) employeeWindow;
      employee = cookUI.getCook();
    } else if (employeeWindow instanceof ManagerUI) {
      ManagerUI managerUI = (ManagerUI) employeeWindow;
      employee = managerUI.getManager();
    }
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    employee.setLoggedIn(false);
    JOptionPane.showMessageDialog(null, employee.toString() + " has signed out.");
    employeeWindow.setVisible(false);
    employeeWindow.dispose();
  }
}
