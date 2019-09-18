/*
 * The OrderManager class. The OrderManager is responsible for monitoring the status if all orders
 * in progress of being made within the restaurant.
 */
package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

// The order managing system. The restaurant should only have one order manager.

public class OrderManager implements Observer {

  private static List<Order> readyForDelivery =
      new ArrayList<>(); // The list of all orders ready to be delivered.
  private static List<Order> newlyPlacedOrders =
      new ArrayList<>(); // The list of all newly taken orders.
  private static List<Order> readyForCooking =
      new ArrayList<>(); // The list of all orders ready to be cooked.
  private static OrderManager orderManager = new OrderManager();
  private Logger logger = Log.getLogger(this.getClass().getName());

  private OrderManager() {}

  /**
   * Returns the single instance of orderManager.
   *
   * @return The orderManager for this program.
   */
  public static OrderManager getOrderManager() {
    return orderManager;
  }

  public void addToNewOrders(Order order) {
    newlyPlacedOrders.add(order);
    logger.log(
        Level.INFO,
        "Order " + order.getOrderNumber() + " has been added to newlyPlacedOrders list.");
  }

  /**
   * Checks if there are orders ready to be delivered. Return true iff there is nothing to be
   * delivered.
   *
   * @return True if there is nothing to be delivered, false otherwise.
   */
  public boolean hasOrdersReadyForDelivery() {
    return readyForDelivery.size() != 0;
  }

  /**
   * Removes this order from the system, regardless of its status.
   *
   * @param order The order to be removed.
   */
  public void removeOrderFromSystem(Order order) {
    if (newlyPlacedOrders.contains(order)) {
      newlyPlacedOrders.remove(order);
      logger.log(
          Level.INFO,
          "Order " + order.getOrderNumber() + " has been removed from newlyPlacedOrders list.");
    } else if (readyForCooking.contains(order)) {
      readyForCooking.remove(order);
      logger.log(
          Level.INFO,
          "Order " + order.getOrderNumber() + " has been removed from readyforCooking list.");
    } else if (readyForDelivery.contains(order)) {
      readyForDelivery.remove(order);
      logger.log(
          Level.INFO,
          "Order " + order.getOrderNumber() + " has been removed from readyForDelivery list.");
    }
  }

  /**
   * Returns the corresponding order given an orderNumber. Merges all three lists of orders and
   * searches for the corresponding order. If such an order cannot be found, notifies the user that
   * this order does not exist within the system.
   *
   * @param orderNumber the identification number of the order.
   * @return The Order object that corresponds to orderNumber.
   * @throws OrderNotFoundException If there is no order with orderNumber in the system.
   */
  public Order getOrderByID(int orderNumber) throws OrderNotFoundException {
    List<Order> allOrders = new ArrayList<>();
    allOrders.addAll(newlyPlacedOrders);
    allOrders.addAll(readyForCooking);
    allOrders.addAll(readyForDelivery);
    for (Order order : allOrders) {
      if (order.getOrderNumber() == orderNumber) {
        return order;
      }
    }

    throw new OrderNotFoundException(
        "There is no order in the system with order number "
            + orderNumber
            + ". It either does not exist or it has been cancelled.");
  }

  public boolean orderExistsInSystem(int orderNumber) {
    List<Order> allOrders = new ArrayList<>();
    allOrders.addAll(newlyPlacedOrders);
    allOrders.addAll(readyForCooking);
    allOrders.addAll(readyForDelivery);
    for (Order o : allOrders) {
      if (orderNumber == o.getOrderNumber()) {
        return true;
      }
    }
    return false;
  }

  /**
   * If this order is ready to be prepared, tell this cook to begin preparing the Order with this
   * orderNumber, remove this order from newlyPlacedOrders and add it to readyForCooking. Return
   * true iff successful.
   *
   * @param orderNumber The identification number of the order being prepared.
   * @param cook The cook preparing this order.
   * @return Whether the action was successful
   * @throws OrderNotFoundException If there is no order with orderNumber in the system.
   */
  public boolean processPrepareOrder(int orderNumber, Cook cook) throws OrderNotFoundException {
    Order order = getOrderByID(orderNumber);
    if (order.getStatus().equals("unseen")) {
      return cook.prepareOrder(order);
    } else {
      logger.log(
          Level.WARNING,
          order.toString()
              + " cannot be prepared, it must be the list of placed orders. It is currently "
              + order.getStatus()
              + ".");
      return false;
    }
  }

  /**
   * If this order is ready to be completed, tell this cook to complete the Order with this
   * orderNumber using ingredients from this inventory. Removes this order from readyForCooking and
   * adds it to readyForDelivery. Return true iff successful.
   *
   * @param orderNumber The identification number of the order finished.
   * @param cook The cook preparing this order.
   * @param inventory The inventory of ingredients used to complete this order.
   * @return Whether the action was successful
   * @throws OrderNotFoundException If there is no order with orderNumber in the system.
   */
  public boolean processCompleteOrder(int orderNumber, Cook cook, Inventory inventory)
      throws OrderNotFoundException {
    Order order = getOrderByID(orderNumber);
    if (order.getStatus().equals("seen")) {
      return cook.completeOrder(order, inventory);
    } else {
      logger.log(
          Level.WARNING,
          order.toString()
              + " cannot be completed before it is seen. It is currently "
              + order.getStatus()
              + ".");
      return false;
    }
  }

  /**
   * If this order is ready to be delivered, tell this server to deliver the order with this
   * orderNumber to its table, and completed the relevant actions which correspond to successful
   * delivery. Removes this order from readyForDelivery. Otherwise, notify the user why this order
   * cannot be delivered. Return true iff successful.
   *
   * @param orderNumber The identification number of the order being delivered.
   * @param server The server who delivered the order.
   * @return Whether the action was successful
   * @throws OrderNotFoundException If there is no order with orderNumber in the system.
   */
  public boolean processDeliverOrder(int orderNumber, int seatNumber, Server server)
      throws OrderNotFoundException {
    Order order = getOrderByID(orderNumber);
    if (order.getStatus().equals("filled")) {
      return server.deliverOrder(order, seatNumber);
    } else {
      logger.log(
          Level.WARNING,
          order.toString()
              + " cannot be delivered before it is filled. It is currently "
              + order.getStatus()
              + ".");
      return false;
    }
  }

  /**
   * Tell this server to cancel the order with this orderNumber for this reason. Return true iff
   * successful.
   *
   * @param orderNumber The order to be cancelled.
   * @param server The server handling the order.
   * @param reason Reason for cancellation.
   * @return Whether the action was successful
   * @throws OrderNotFoundException If there is no order with orderNumber in the system.
   */
  public boolean processCancelOrder(int orderNumber, Server server, String reason)
      throws OrderNotFoundException {
    Order order = getOrderByID(orderNumber);
    return server.cancelOrder(order, reason);
  }

  /**
   * If this order is ready to be delivered, tell this server to send the Order with this
   * orderNumber back to the kitchen for this reason, and complete the relevant actions which
   * correspond to failed delivery. Otherwise, notify the user why this order cannot be sent back.
   * Return true iff successful.
   *
   * @param orderNumber The identification number of the order being sent back.
   * @param server The server who sent back the order.
   * @return Whether the action was successful
   * @throws OrderNotFoundException If there is no order with orderNumber in the system.
   */
  public boolean processReturnOrder(int orderNumber, Server server, String reason)
      throws OrderNotFoundException {
    Order order = getOrderByID(orderNumber);
    if (order.getStatus().equals("filled")) {
      return server.sendBackOrder(order, reason);
    } else {
      logger.log(
          Level.WARNING,
          order.toString()
              + " cannot be sent back before it is filled. It is currently "
              + order.getStatus()
              + ".");
      return false;
    }
  }

  public static List<Order> getReadyForDelivery() {
    return readyForDelivery;
  }

  /**
   * Adds the order to the appropriate list depending on its status, logs the event, and displays a
   * notification.
   *
   * @param order The order being updated.
   * @param status The updated status of the order.
   */
  public void update(Observable order, Object status) {
    if (order instanceof Order) {
      Order o = (Order) order;
      if (status.equals("unseen")) {
        JOptionPane.showMessageDialog(null, "Order " + o.getOrderNumber() + " has been placed.");
        logger.log(
            Level.INFO,
            order.toString() + " has been placed for table " + o.getTable().getTableID() + ".");

      } else if (status.equals("seen")) {
        newlyPlacedOrders.remove(o);
        readyForCooking.add(o);
        String msg = order.toString() + " is being prepared.";
        logger.log(Level.INFO, msg);
        JOptionPane.showMessageDialog(null, msg);

      } else if (status.equals("filled")) {
        readyForCooking.remove(o);
        readyForDelivery.add(o);
        String msg = order.toString() + " has been completed.";
        JOptionPane.showMessageDialog(null, msg);
        logger.log(Level.INFO, msg);

      } else if (status.equals("delivered")) {
        readyForDelivery.remove(order);
        JOptionPane.showMessageDialog(
            null, "Order " + o.getOrderNumber() + " has been successfully delivered.");
        logger.log(
            Level.INFO,
            order.toString()
                + " has been successfully delivered to table "
                + o.getTable().getTableID()
                + ".");

      } else if (status.equals("cancelled")) {
        removeOrderFromSystem(o);
        JOptionPane.showMessageDialog(
            null, "Order " + o.getOrderNumber() + " has been successfully cancelled.");
        logger.log(
            Level.INFO,
            order.toString()
                + " has been cancelled by table "
                + o.getTable().getTableID()
                + " because of: "
                + o.getCancelledReason());
      }
    }
  }
}
