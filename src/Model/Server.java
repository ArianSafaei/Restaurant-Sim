/*
The Server class. Servers are responsible for inputting requests made by the customer into the system, including
creation and cancellation of orders, delivering the order to the customers, and sending the order back into the
system upon customer request.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Server extends Employee {

  public Server(String name, String id, String password) {
    super(name, id, password);
  }

  /**
   * Creates and returns an order with this menuItem, if there is sufficient ingredients in
   * inventory to prepare this order, and there are no orders waiting to be delivered. Add this
   * order to the Table that placed this order, enter the order into the system, and broadcasts an
   * update.
   *
   * @param menuItem The item that this order contains.
   * @param table The table that this order belongs to.
   * @return The order created.
   */
  public boolean createOrder(MenuItem menuItem, Table table, Inventory inventory) {
    OrderManager oM = OrderManager.getOrderManager();
    if (!(oM.hasOrdersReadyForDelivery())) {
      if (menuItem.canBeMade(inventory)) {
        Order order = Order.getNewOrder(menuItem, table);
        table.addToPlacedOrders(order);
        order.addObserver(oM);
        oM.addToNewOrders(order);
        order.updateStatus("unseen");
        logger.log(
            Level.INFO,
            "Server "
                + getID()
                + " created a new order for table "
                + table.getTableID()
                + ". "
                + order.toString());
        return true;
      } else {
        // log failure to create order - insufficient ingredients
        logger.log(
            Level.WARNING,
            "Server " + getID() + " cannot place this order due to insufficient ingredients.");
        return false;
      }
    }
    // log failure to create order - there is an order ready for delivery
    logger.log(
        Level.WARNING,
        "Server "
            + getID()
            + " cannot place this order because there is an order that is ready to be delivered.");
    return false;
  }

  /**
   * Register that this order has been cancelled for this reason. Remove this order from its table's
   * list of orders, change its status to cancelled, and broadcasts an update. Return true iff
   * successful. An order may be cancelled any time before it is delivered.
   *
   * @param order The order to be cancelled.
   * @param reason Why the order was cancelled.
   * @return Whether this action was successful.
   */
  public boolean cancelOrder(Order order, String reason) {
    if (!(order.getStatus().equals("delivered"))) {
      order.setCancelledReason(reason);
      order.updateStatus("cancelled");
      order.getTable().removeFromPlacedOrders(order);
      logger.log(
              Level.INFO,
              "Server "
                      + getID()
                      + " cancelled order "
                      + order.getOrderNumber()
                      + " because of "
                      + reason
                      + ". "
                      + order.toString());
      return true;
    }
    return false;
  }

  /**
   * Delivers this order to the customer at seatNumber at its table, and add the order to the table
   * and seat's list of delivered orders, if this seatNumber exists. Updates order status to
   * 'delivered', broadcasts an update, and returns true iff successful.
   *
   * @param order The order delivered.
   * @return boolean Whether this action was successful.
   */
  public boolean deliverOrder(Order order, int seatNumber) {
    Table table = order.getTable();
    table.addToPlacedOrders(order);
    if (table.getSeats().length > seatNumber) {
      Seat seat = table.getSeats()[seatNumber];
      table.addToDeliveredOrders(order);
      seat.setOccupied(true);
      seat.addToDeliveredOrders(order);
      order.updateStatus("delivered");
      logger.log(
          Level.INFO,
          "Server "
              + getID()
              + " delivered order "
              + order.getOrderNumber()
              + ". "
              + order.toString());
      return true;
    } else {
      logger.log(
          Level.WARNING,
          "Server "
              + getID()
              + "failed to deliver order "
              + order.getOrderNumber()
              + " because this table does not have seat with seat number "
              + seatNumber
              + ". "
              + order.toString());
      return false;
    }
  }

  /**
   * Register that this order has been sent back for this reason. Cancel this order and send another
   * order with the same item back into the system with the status 'unseen'. Return true iff
   * successful.
   *
   * @param order The order to be sent back.
   * @param reason Why this order was sent back.
   * @return boolean Whether this action was successful.
   */
  public boolean sendBackOrder(Order order, String reason) {
    cancelOrder(order, reason);
    order.updateStatus("unseen");
    OrderManager.getOrderManager().addToNewOrders(order);
    logger.log(
        Level.INFO,
        "Server "
            + getID()
            + " sent back order "
            + order.getOrderNumber()
            + " because of "
            + reason
            + ". "
            + order.toString());
    return true;
  }

  /**
   * Returns one bill for this entire table. If the table has 8 or more occupied seats, attach a 15%
   * tip to the bill.
   *
   * @param table The table whose bill is to be created.
   * @return The bill for this table.
   */
  public Bill printOneBillForTable(Table table) {
    if (table.countNumOccupiedSeats() >= 3) {
      table.setBill(Bill.createBill(table, 15));
    } else {
      table.setBill(Bill.createBill(table, 0));
    }
    logger.log(
        Level.INFO, "Server " + getID() + " created a bill for table " + table.getTableID() + ".");
    return table.getBill();
  }

  /**
   * Returns a list of bills, one for each occupied seat at this table. If the table has 8 or more
   * occupied seats, attach a 15% tip to every bill.
   *
   * @param table The table whose bill is to be created.
   * @return The list of bills for this table.
   */
  public List<Bill> splitBillForTable(Table table) {
    List<Bill> bills = new ArrayList<>();
    for (Seat seat : table.getOccupiedSeats()) {
      if (seat.isOccupied()) {
        if (table.countNumOccupiedSeats() >= 3) {
          Bill b1 = Bill.createBill(seat, 15);
          seat.setBill(b1);
          bills.add(b1);
        } else {
          Bill b2 = Bill.createBill(seat, 0);
          seat.setBill(b2);
          bills.add(b2);
        }
      }
    }
    logger.log(
        Level.INFO,
        "Server "
            + getID()
            + " created a bill for each person at table "
            + table.getTableID()
            + ".");
    return bills;
  }

  /**
   * Return a string representation of this server, with their role, name and id.
   *
   * @return the string representation of this server.
   */
  public String toString() {
    return "Server " + getName() + ", " + getID();
  }
}
