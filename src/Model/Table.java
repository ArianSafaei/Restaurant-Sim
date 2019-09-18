/*
The Table class. A Table has an ID, a set capacity, a list of orders belonging to it and the bill attached
to it.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Table {
  private static int numTables = 0; // Total number of tables.
  private Bill bill; // The bill attached to this table.
  private int tableID; // The identification number of this table.
  private List<Order> placedOrders; // The list of Orders placed by this table.
  private List<Order> deliveredOrders; // The list of Orders placed by this table.
  private Seat[] seats; // The array of seats at this table.

  /**
   * Instantiate a table with this seatingCapacity and empty lists of placed orders and delivered
   * orders.
   *
   * @param seatingCapacity The number of seats at this table.
   */
  public Table(int seatingCapacity) {
    numTables++;
    tableID = numTables;
    placedOrders = new ArrayList<>();
    deliveredOrders = new ArrayList<>();
    seats = new Seat[seatingCapacity];
    for (int i = 0; i < seats.length; i++) {
      String seatID = "t" + String.valueOf(tableID) + "s" + String.valueOf(i);
      seats[i] = new Seat(seatID);
    }
  }

  public Seat[] getSeats() {
    return seats;
  }

  public void setBill(Bill bill) {
    this.bill = bill;
  }

  public int getTableID() {
    return tableID;
  }

  public Bill getBill() {
    return bill;
  }

  public List<Order> getDeliveredOrders() {
    return deliveredOrders;
  }

  public void addToPlacedOrders(Order order) {
    placedOrders.add(order);
  }

  public void removeFromPlacedOrders(Order order) {
    placedOrders.remove(order);
  }

  public void addToDeliveredOrders(Order order) {
    deliveredOrders.add(order);
  }

  public void clearDeliveredOrders() {
    deliveredOrders = new ArrayList<>();
  }

  /**
   * Return a list of seats which is currently occupied. A seat is occupied if it has delivered but
   * unpaid for orders.
   *
   * @return A list of currently occupied seats.
   */
  public List<Seat> getOccupiedSeats() {
    List<Seat> occupiedSeats = new ArrayList<>();
    for (Seat s : seats) {
      if (s.isOccupied()) {
        occupiedSeats.add(s);
      }
    }
    return occupiedSeats;
  }

  /**
   * Return the amount of occupied seats at this table.
   *
   * @return Amount of occupied seats at this table.
   */
  public int countNumOccupiedSeats() {
    int num = 0;
    for (Seat seat : seats) {
      if (seat.isOccupied()) {
        num++;
      }
    }
    return num;
  }

  /**
   * Check if any receipt window for this table is currently open.
   *
   * @return True if there is a window open, false otherwise.
   */
  public boolean isReceiptWindowOpen() {
    for (Seat seat : seats) {
      if (seat.isReceiptWindowOpen()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Set all the seats on this table to receiptWindowOpen.
   *
   * @param receiptWindowOpen Set all the seats on this table to receiptWindowOpen.
   */
  public void setReceiptWindowOpen(boolean receiptWindowOpen) {
    for (Seat seat : seats) {
      if (seat.isOccupied()) {
        seat.setReceiptWindowOpen(receiptWindowOpen);
      }
    }
  }
}
