/*
The Employee class. An employee has a name and an ID. Any employee can scan
ingredients back into the inventory upon arrival of a shipment of ingredients.
 */
package Model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Employee {

  private String name; // The name of this employee.
  private String id; // The ID of this employee.
  private String password; // Unique password used to log this employee in to the system.
  private boolean isLoggedIn; // Whether this employee is currently logged in to the system.
  protected Logger logger;

  /**
   * Instantiates an employee with name name and ID id.
   *
   * @param name The name of the employee.
   * @param id The ID of the employee.
   * @param password The employee's password.
   */
  public Employee(String name, String id, String password) {
    this.name = name;
    this.id = id;
    this.password = password;
    isLoggedIn = false;
    this.logger = Log.getLogger(this.getClass().getName());
  }

  public String getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    isLoggedIn = loggedIn;
  }

  /**
   * Register that a shipment of this ingredient of this amount has arrived, and add it into this
   * inventory.
   *
   * @param inventory The inventory to add the ingredients to.
   * @param ingredient The ingredient to be added to the inventory.
   * @param amount The quantity of the ingredient to be added.
   */
  public boolean updateInventory(Inventory inventory, String ingredient, int amount) {
    inventory.addIngredientStock(ingredient, amount);
    logger.log(
        Level.INFO, amount + " units of " + ingredient + " has been added to the inventory.");
    return true;
  }

  /**
   * Return true iff ps patches the employee's password.
   *
   * @param ps the password entered.
   * @return Whether the password is correct.
   */
  public boolean hasCorrectPassword(String ps){
    return ps.equals(password);
  }

  /**
   * Return a string representation of this employee, with their name and id.
   *
   * @return the string representation of this employee.
   */
  public String toString() {
    return "Employee " + name + ", " + id;
  }
}
