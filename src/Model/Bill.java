package Model;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// The Bill class. A bill belongs to either a Seat or a Table, and keeps tracks of orders on itself.

public class Bill {

  private static int numBills; // number of bills created.
  private int billID; // ID of this bill
  private int taxPercentage = 13;
  private int tipPercentage;
  private Object seatOrTable; // Must be either Seat or Table.
  private double subTotal; // Sum of raw prices of all orders, in dollars.
  private double taxAmount; // Amount taxed in dollars
  private double grandTotal; // Total including tax and tip in dollars.
  private List<Order> orders; // List of orders displayed by this Bill
  private Logger logger = Log.getLogger(this.getClass().getName());

  /**
   * Create a bill for param (either Table or Seat) with this tipPercentage. Automatically
   * calculates subTotal, taxAmount and grandTotal.
   *
   * @param param Table or Seat.
   * @param tipPercentage The tip in percent.
   */
  private Bill(Object param, int tipPercentage) {
    this.tipPercentage = tipPercentage;
    numBills++;
    billID = numBills;
    int subTotalInCents = 0;
    double taxMultiplier = ((double) taxPercentage) / 100;

    if (param instanceof Seat) {
      seatOrTable = param;
      orders = ((Seat) param).getDeliveredOrders();
    } else if (param instanceof Table) {
      seatOrTable = param;
      orders = ((Table) param).getDeliveredOrders();
    } else {
      throw new InvalidParameterException();
    }

    for (Order order : orders) {
      subTotalInCents += order.calculatePrice();
    }
    subTotal = subTotalInCents / 100;
    taxAmount = subTotal * taxMultiplier;
    grandTotal = getGrandTotal();
    logger.log(Level.INFO, "Bill " + billID + " has been created with " + orders.size() + " order(s).");
  }

  /**
   * Static factory method for Bill.
   *
   * @param param Table or Seat.
   * @param tipPercentage Tip in percent
   * @return Bill created.
   */
  public static Bill createBill(Object param, int tipPercentage) {
    return new Bill(param, tipPercentage);
  }

  public double getSubTotal() {
    return subTotal;
  }

  public double getTaxAmount() {
    return taxAmount;
  }

  public double calculateTip() {
    return ((subTotal + taxAmount) * (((double) tipPercentage) / 100));
  }

  public double getGrandTotal() {
    return taxAmount + calculateTip() + subTotal;
  }

  /**
   * Confirms that this bill has been paid. Add this payment to the restaurant's payment record, and
   * reset the Table or Seat by clearing their list of delivered orders, and setting the seat's
   * status to unoccupied.
   *
   * @return Whether this action was successful.
   */
  public boolean confirmPayment(){
    DecimalFormat df = new DecimalFormat("0.00");
    if (seatOrTable instanceof Table){
      ((Table) seatOrTable).clearDeliveredOrders();
      for (Seat seat : ((Table) seatOrTable).getSeats()) {
        seat.clearDeliveredOrders();
      }
    } else if (seatOrTable instanceof Seat){
      Restaurant.getTables().get(((Seat) seatOrTable).getTableID() - 1)
              .getDeliveredOrders().removeAll(((Seat) seatOrTable).getDeliveredOrders());
      ((Seat) seatOrTable).clearDeliveredOrders();
      ((Seat) seatOrTable).setOccupied(false);
    }
    logger.log(Level.INFO, "Payment of $" + df.format(grandTotal) + " has been recorded for Bill " + billID + ".");
    return Restaurant.addPaymentToRecord(billID, grandTotal);
  }

  /**
   * Get the list of orders in this bill.
   *
   * @return The list of orders in this bill.
   */
  public List<Order> getOrders() {
    return orders;
  }
}
