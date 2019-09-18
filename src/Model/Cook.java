/*
The Cook class. Cooks are responsible for confirming that an order has been seen and is being prepared, and
confirming that an order has been completed.
 */
package Model;

import java.util.Map;
import java.util.logging.Level;

public class Cook extends Employee {
  private boolean isOccupied; // Whether this cook is currently preparing an order

  /**
   * Initialize an unoccupied Cook with this name, ID & password.
   *
   * @param name The cook's name.
   * @param id The cook's ID.
   * @param password The cook's password.
   */
  public Cook(String name, String id, String password) {
    super(name, id, password);
    isOccupied = false;
  }

  /**
   * If currently occupied, then register that this order is currently being prepared. Changes order
   * status to seen, broadcasting an update. Changes cook's status to occupied. Return true iff
   * successful.
   *
   * @param order The order being prepared.
   * @return Whether this action was successful.
   */
  public boolean prepareOrder(Order order) {
    if (!(isOccupied)) {
      order.updateStatus("seen");
      isOccupied = true;
      logger.log(
          Level.INFO, "Cook " + getID() + " is preparing order " + order.getOrderNumber() + ".");
      return true;
    }
    logger.log(
        Level.WARNING,
        "Cook "
            + getID()
            + " is occupied and unable to prepare order "
            + order.getOrderNumber()
            + ".");
    return false;
  }

  /**
   * if there are sufficient ingredients in this inventory to complete this order, register that
   * this order has be completed using ingredients from this inventory, and update the order's
   * status to 'filled', broadcasting an update. Also subtracts the ingredients required for this
   * order from the inventory, and sets the cook's status to unoccupied. Return true iff successful.
   *
   * @param order The order to be completed.
   * @param inventory The inventory whose ingredients are used to complete this order.
   * @return Whether this action was successful.
   */
  public boolean completeOrder(Order order, Inventory inventory) {
    MenuItem item = order.getMenuItem();
    Map<String, Integer> ingredientsNeeded = item.getIngredients();

    if (isOccupied) {
      for (Map.Entry<String, Integer> entry : ingredientsNeeded.entrySet()) {
        inventory.subtractIngredientStock(entry.getKey(), entry.getValue());
      }
      order.updateStatus("filled");
      isOccupied = false;
      logger.log(
          Level.INFO, "Cook " + getID() + " has completed order " + order.getOrderNumber() + ".");
      return true;
    }
    logger.log(
        Level.WARNING,
        "Cook " + getID() + " is unable to complete order " + order.getOrderNumber() + ".");
    return false;
  }

  /**
   * Return a string representation of this cook, with their role, name and id.
   *
   * @return the string representation of this cook.
   */
  public String toString() {
    return "Cook " + getName() + ", " + getID();
  }
}
