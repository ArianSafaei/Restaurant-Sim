/*
The EmployeeFactory class. The EmployeeFactory is able to generate difference types of employees.
 */
package Model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeFactory {

  private static Logger logger = Log.getLogger("EmployeeFactory");

  /**
   * Return the corresponding Employee object given their job, and initializes them using name and
   * id.
   *
   * @param job The job of the employee in the restaurant.
   * @param name The employee's name.
   * @param id The employee's ID.
   * @param password the employee's password.
   * @return The corresponding employee object.
   */
  public static Employee getEmployee(String job, String name, String id, String password) {

    if ("Server".equalsIgnoreCase(job)) {
      logger.log(Level.INFO, "Server " + name + " has been created with ID " + id + ".");
      return new Server(name, id, password);
    } else if ("Cook".equalsIgnoreCase(job)) {
      logger.log(Level.INFO, "Cook " + name + " has been created with ID " + id + ".");
      return new Cook(name, id, password);
    } else if ("Manager".equalsIgnoreCase(job)) {
      logger.log(Level.INFO, "Manager " + name + " has been created with ID " + id + ".");
      return new Manager(name, id, password);
    }
    logger.log(Level.SEVERE, job + " is not a valid job in the restaurant.");
    return null;
  }
}
