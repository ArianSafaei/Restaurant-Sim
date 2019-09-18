package Controller;

import GUI.ServerUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Mouse listener class for all table buttons in server.
 * Used mouse listener so can make use of hovering mouse over table images.
 */
public class MouseListenerForTables implements MouseListener {
  private int tableID;
  private ServerUI serverUI;

  /**
   * Constructor for this class
   * @param serverUI The serverUI window that this button is on
   * @param index the index (Table number) of the table mouse is on
   */
  public MouseListenerForTables(ServerUI serverUI, int index) {
    this.serverUI = serverUI;
    this.tableID = index;
  }

  // to highlight table as mouse goes over it
  public void mouseEntered(MouseEvent e) {
    serverUI.getTableButtons()[tableID].setEnabled(false);
  }

  // unhighlight table as mouse leaves
  public void mouseExited(MouseEvent e) {
    serverUI.getTableButtons()[tableID].setEnabled(true);
  }

  public void mouseClicked(MouseEvent e) {
    serverUI.setCurrentTableID(tableID);
    serverUI.setupOptionPane();
  }

  public void mouseReleased(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {}
}
