/*
The Restaurant class. This is the class from which the program is run. A restaurant has a menu and list of employees
and tables. It contains an inventory and has a system responsible for managing orders.
 */
package Model;

import GUI.Login;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Restaurant {

  private static List<Employee> employees; // List of restaurant employees.
  private static List<Table> tables; // List of Tables in the restaurant.
  private static Map<Integer, Double> paymentRecord; // Record of bills paid.
  private Login loginForm; // Used to log employees in to the system.
  private Logger logger = Log.getLogger(this.getClass().getName());

  /** Initialize a new restaurant through configuration files. */
  public Restaurant() {
    logger.log(Level.INFO, "Restaurant Initialized.");
    employees = new ArrayList<>();
    tables = new ArrayList<>();
    paymentRecord = new HashMap<>();
    readConfig();
  }

  public static Restaurant getRestaurant() {
    return new Restaurant();
  }

  public static boolean addPaymentToRecord(int BillID, double sumPaid) {
    paymentRecord.put(BillID, sumPaid);
    return true;
  }

  public static Map<Integer, Double> getPaymentRecord() {
    return paymentRecord;
  }

  // Main method.
  public static void main(String[] args) {
    Restaurant r = new Restaurant();
    r.loginForm = new Login(-1, -1);
    r.loginForm.setValidEmployees(employees);
    r.loginForm.setValidTables(tables); //must be max of 15
  }

  /**
   * Initializes the employees and tables of this restaurant using contents of 'config.txt', as
   * specified in README_updated.txt.
   */
  private void readConfig() {
    try {
      FileReader fileReader = new FileReader("restaurant settings/config.txt");
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String firstLine = bufferedReader.readLine();
      String[] s = firstLine.split(": |, ");
      int numTables = s.length;
      for (int i = 1; i < numTables; i++) {
        Table table = new Table(Integer.parseInt(s[i]));
        tables.add(table);
      }

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] substrings = line.split(", ");
        String role = substrings[0];
        String name = substrings[1];
        String id = substrings[2];
        String password = substrings[3];
        if (role.equals("Server")) {
          Employee employee = EmployeeFactory.getEmployee("Server", name, id, password);
          employees.add(employee);
        } else if (role.equals("Cook")) {
          Employee employee = EmployeeFactory.getEmployee("Cook", name, id, password);
          employees.add(employee);
        } else if (role.equals("Manager")) {
          Employee employee = EmployeeFactory.getEmployee("Manager", name, id, password);
          employees.add(employee);
        } else {
          // log attempted creation of invalid employee.
          logger.log(Level.INFO, role + " is not a valid role in the restaurant.");
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.log(Level.SEVERE, "File not found.");
    } catch (IOException e) {
      e.printStackTrace();
      logger.log(Level.SEVERE, "I/O error.");
    }
  }

  /**
   * Get the list of tables in this restaurant.
   *
   * @return The list of tables.
   */
  public static List<Table> getTables() {
    return tables;
  }
}


