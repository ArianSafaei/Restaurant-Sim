package Model;

// Corresponds to exceptions when an item does not exist on the menu.

public class NotOnMenuException extends Exception {

  public NotOnMenuException(String message) {
    super(message);
  }
}
