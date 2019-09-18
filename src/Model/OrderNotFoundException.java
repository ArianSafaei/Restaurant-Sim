package Model;

// Corresponds to exceptions when an order cannot be found in the system.

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
