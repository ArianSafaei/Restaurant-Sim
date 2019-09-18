package Model;

// A manager of the restaurant. A manager can check the inventory, payment record and list of orders
// in the restaurant.
public class Manager extends Employee {

  /**
   * A new manager.
   *
   * @param name Name of the Manager.
   * @param id ID of the Manager.
   */
  public Manager(String name, String id, String password) {
    super(name, id, password);
  }

  /**
   * Prints a formatted version of this inventory to the console.
   *
   * @param inventory The inventory to be viewed.
   */
  public void getInventoryPrintout(Inventory inventory) {
    System.out.println(
        "\n" + "Inventory contains: " + "\n" + inventory.toString() + "End of inventory" + "\n");
  }

  /**
   * Return a string representation of this inventory manager, with their role, name, and id.
   *
   * @return The string representation of this inventory manager.
   */
  public String toString() {
    return "Manager " + getName() + ", " + getID();
  }
}
