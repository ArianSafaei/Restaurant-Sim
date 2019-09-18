package Controller;

import GUI.ModifyIngredientsGUI;
import Model.Inventory;
import Model.MenuItem;
import Model.Server;
import Model.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Contains action listeners for all buttons required in ModifyIngredientsUI
 */
public class ModifyIngredientsController {
  public static class SubtractListener implements ActionListener {
    private MenuItem menuItem;
    private String ingredient;
    private JLabel jLabel;

    public SubtractListener(MenuItem menuItem, String ingredient, JLabel jLabel) {
      this.menuItem = menuItem;
      this.ingredient = ingredient;
      this.jLabel = jLabel;
    }

    /**
     * add a unit to the ingredient
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
      menuItem.modifyIngredient(ingredient, -1);
      jLabel.setText(ingredient + ": " + menuItem.getIngredients().get(ingredient));
    }
  }

  public static class AddListener implements ActionListener {
    private MenuItem menuItem;
    private String ingredient;
    private JLabel jLabel;

    public AddListener(MenuItem menuItem, String ingredient, JLabel jLabel) {
      this.menuItem = menuItem;
      this.ingredient = ingredient;
      this.jLabel = jLabel;
    }

    /**
     * subtract a unit from the ingredient
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
      menuItem.modifyIngredient(ingredient, 1);
      jLabel.setText(ingredient + ": " + menuItem.getIngredients().get(ingredient));
    }
  }

  public static class ConfirmListener implements ActionListener {
    private ModifyIngredientsGUI modifyIngredientsGUI;
    private MenuItem menuItem;
    private Server server;
    private Table table;

    public ConfirmListener(ModifyIngredientsGUI modifyIngredientsGUI, MenuItem menuItem, Server server, Table table) {
      this.modifyIngredientsGUI = modifyIngredientsGUI;
      this.menuItem = menuItem;
      this.server = server;
      this.table = table;
    }

    /**
     * let the server create the order
     * @param actionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
      if (!(server.createOrder(menuItem, table, Inventory.getInventory()))) {
        JOptionPane.showMessageDialog(
                null, "Cannot place this order due to insufficient ingredients.");
      }
      modifyIngredientsGUI.dispose();
    }
  }
}
