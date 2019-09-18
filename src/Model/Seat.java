package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// The seat class. A seat is occupied if it has delivered, but yet to be paid for orders.

public class Seat {

    private List<Order> deliveredOrders; // The list of orders placed by this table.
    private boolean isOccupied = false; // Whether this seat is occupied.
    private Bill bill; // The bill attached to this seat.
    private String seatID; // The ID of this seat.
    private Logger logger = Log.getLogger(this.getClass().getName());
    private boolean receiptWindowOpen; // Whether this table has a receipt that has not yet been dealt with.

    /**
     * Initiate a seat with this ID.
     *
     * @param seatID The ID of this seat.
     */
    public Seat(String seatID) {
        deliveredOrders = new ArrayList<>();
        this.seatID = seatID;
    }

    public String getSeatID() {
        return seatID;
    }

    public int getTableID() {
        try {
            return Integer.parseInt(seatID.substring(seatID.indexOf('t') + 1, seatID.indexOf('s')));
        } catch (NumberFormatException e) {
            this.logger.log(Level.SEVERE, "Can't parse table ID.");
            return 0;
        }
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public List<Order> getDeliveredOrders() {
        return deliveredOrders;
    }

    public void addToDeliveredOrders(Order order) {
        deliveredOrders.add(order);
    }

    public void clearDeliveredOrders(){
        deliveredOrders = new ArrayList<>();
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * Checks whether or not a receipt window is open for this seat.
     *
     * @return Whether or not a receipt window is open for this seat.
     */
    public boolean isReceiptWindowOpen() {
        return receiptWindowOpen;
    }


    public void setReceiptWindowOpen(boolean receiptWindowOpen) {
        this.receiptWindowOpen = receiptWindowOpen;
    }
}
