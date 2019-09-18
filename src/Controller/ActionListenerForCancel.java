package Controller;

import GUI.CancelOrderUI;
import GUI.ServerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Cancel button in server
 */
public class ActionListenerForCancel implements ActionListener {

  private ServerUI serverUI;

  /**
   * Constructor for this class
   * @param serverUI The serverUI window that this button is on
   */
  public ActionListenerForCancel(ServerUI serverUI) {
    this.serverUI = serverUI;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    new CancelOrderUI(
        serverUI.getLocation().x / 2,
        serverUI.getLocation().y / 2,
        serverUI.getServer());
  }
}
