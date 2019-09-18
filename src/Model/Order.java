/*
The Order class. An order contains its order number and the number of the table it belongs to. An order contains
exactly one menuItem, which may be modified with extra/less ingredients. An order has a status, which must be one
of 'unseen', 'seen', 'filled', 'delivered' or 'cancelled'.
 */
package Model;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Order extends Observable {

  private static int numOrders = 0; // Total number of orders.
  private int orderNumber; // The order's identification number.
  private Table table; // The number of the table this order belongs to.
  private MenuItem menuItem; // The item contained in the order.
  private String
      status; // The status of the order. One of 'unseen', 'seen', 'filled', 'delivered', or
  // 'cancelled'.
  private String cancelledReason; // The reason why the order was cancelled.
  private Logger logger = Log.getLogger(this.getClass().getName());

  /**
   * Instantiates an order with this menuItem for this table.
   *
   * @param menuItem The menuItem represented by this order.
   * @param table The table this order belongs to.
   */
  public Order(MenuItem menuItem, Table table) {
    orderNumber = numOrders;
    numOrders++;
    this.table = table;
    this.menuItem = menuItem;
  }

  /**
   * Static factory method for Order. Returns a new order with this menuItem for this table.
   *
   * @param item The menuItem represented by this order.
   * @param table The table this order belongs to.
   * @return The order with this menuItem for this table.
   */
  public static Order getNewOrder(MenuItem item, Table table) {
    return new Order(item, table);
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public String getStatus() {
    return status;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public Table getTable() {
    return table;
  }

  public void setCancelledReason(String cancelledReason) {
    this.cancelledReason = cancelledReason;
  }

  public String getCancelledReason() {
    return cancelledReason;
  }

  /**
   * Returns the price of this order.
   *
   * @return the price of this order.
   */
  public int calculatePrice() {
    return menuItem.getPrice();
  }

  /**
   * Updates the status of this order to newStatus, and notify all of its observers.
   *
   * @param newStatus The new status of this order.
   */
  public void updateStatus(String newStatus) {
    status = newStatus;
    setChanged();
    notifyObservers(newStatus);
    logger.log(Level.INFO, "Order " + getOrderNumber() + "'s status has been changed to " + newStatus);
  }

  /**
   * Return a string representation of this order, with its order number, contents and any modifications of
   * the item ordered.
   *
   * @return The string representation of this order.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("Order ").append(orderNumber).append(": ");
    sb.append(menuItem.getName());
    for (String mod : menuItem.getItemModifications()) {
      sb.append(" ");
      sb.append(mod);
    }
    return sb.toString();
  }
}
