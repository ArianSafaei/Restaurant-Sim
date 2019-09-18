package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An inventory of ingredients with their stock and threshold so that if an ingredient drops below a
 * threshold, a request for more of that ingredient is written to requests.txt.
 */
public class Inventory {

  private Logger logger = Log.getLogger(this.getClass().getName());

  /**
   * File is formatted so each line is:
   * "ingredient_name_here:ingredient_stock_here:ingredient_threshold_here"
   */
  private static final String INVENTORY_FILENAME = "restaurant settings/Inventory.txt";

  /** File is going to be formatted: "request_amount_here units of ingredient_name_here" */
  private static final String REQUEST_FILENAME = "requests.txt";

  /** Default amount to request when stock goes below threshold. */
  private static final int DEFAULT_REQUEST_AMOUNT = 20;

  /** Map of requests where key is the ingredient and the value is the requested amount. */
  private static Map<String, Integer> requests;

  /**
   * Map of ingredients where key is ingredient and for value: [0][0] is stock and [0][1] is
   * threshold.
   */
  private static Map<String, int[][]> ingredients;

  private static Inventory inventory = new Inventory();

  /**
   * A new inventory.
   *
   * <p>Precondition: Inventory.txt must follow this format for each line:
   * "ingredient_name_here:ingredient_stock_here:ingredient_threshold_here"
   */
  private Inventory() {
    ingredients = new HashMap<>();
    requests = new HashMap<>();

    // Read ingredients from file
    try (BufferedReader fileReader = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
      String line = fileReader.readLine();
      while (line != null) {
        // Split the line using colons.
        String[] colonSplit = line.split(":");

        int[][] a = new int[1][2];
        a[0][0] = Integer.valueOf(colonSplit[1]);
        a[0][1] = Integer.valueOf(colonSplit[2]);

        ingredients.put(colonSplit[0], a);

        line = fileReader.readLine();
      }
      logger.log(Level.INFO, "Inventory successfully loaded from file.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Inventory getInventory() {
    return inventory;
  }

  /**
   * Checks whether an ingredient has less stock than its threshold and if so, writes the request to
   * requests.txt.
   *
   * <p>Precondition: Ingredient exists.
   *
   * @param ingredient The ingredient to be checked.
   */
  private void checkIngredientBelowThreshold(String ingredient) {
    if (ingredients.get(ingredient)[0][0] < ingredients.get(ingredient)[0][1]) {
      addRequest(ingredient);
    }
    /* Always write requests to file in case there are no requests which means
    requests.txt needs to be empty. */
    writeRequestsToFile();
  }

  /**
   * Adds this amount to the existing stock of an ingredient if it exists, otherwise, enter it
   * into the inventory wih this amount and a default threshold of 20 units.
   *
   * @param ingredient The ingredient to add stock to.
   * @param amount The amount of stock to add.
   */
  public void addIngredientStock(String ingredient, int amount) {
      if (ingredients.containsKey(ingredient.toLowerCase())) {
          int[][] temp = new int[1][2];
          temp[0][0] = ingredients.get(ingredient.toLowerCase())[0][0] + amount;
          temp[0][1] = ingredients.get(ingredient.toLowerCase())[0][1];
          ingredients.put(ingredient.toLowerCase(), temp);
          logger.log(Level.INFO, amount + " units of " + ingredient
                  + " has been added to the inventory. There is now "
                  + getIngredientStock(ingredient.toLowerCase())
                  + " units of " + ingredient + ".");
      } else {
          int[][] temp = new int[1][2];
          temp[0][0] = amount;
          temp[0][1] = 20;
          ingredients.put(ingredient.toLowerCase(), temp);
          logger.log(Level.WARNING, ingredient
                  + " was not found in the inventory and a new entry has been added for it with "
                  + amount + " units.");
      }
  }

  /**
   * Removes stock from an ingredient.
   *
   * <p>Precondition: Ingredient exists.
   *
   * @param ingredient The ingredient to subtract stock from.
   * @param amount The amount of stock to remove.
   */
  public void subtractIngredientStock(String ingredient, int amount) {
    if (ingredients.containsKey(ingredient.toLowerCase())) {
      int[][] temp = new int[1][2];
      temp[0][0] = ingredients.get(ingredient.toLowerCase())[0][0] - amount;
      temp[0][1] = ingredients.get(ingredient.toLowerCase())[0][1];
      ingredients.put(ingredient.toLowerCase(), temp);
      checkIngredientBelowThreshold(ingredient.toLowerCase());
      logger.log(Level.INFO, amount + " units of " + ingredient + " has been removed from the inventory.");
    }
  }

  /**
   * Get the stock of an ingredient. If no such ingredient exists in the inventory, return 0.
   *
   *
   * @param ingredient The ingredient to check stock of.
   * @return The stock of the ingredient.
   */
  public int getIngredientStock(String ingredient) {
      if (ingredients.containsKey(ingredient.toLowerCase())) {
          return ingredients.get(ingredient.toLowerCase())[0][0];
      }
      return 0;
  }

  public List<String> getExistingIngredients(){
    return new ArrayList<String>(ingredients.keySet());
  }

  /**
   * Adds a request to the requests HashMap for an ingredient using the default request amount.
   *
   * @param ingredient The ingredient to add a request for.
   */
  private void addRequest(String ingredient) {
    /* If there is already a request for the ingredient, request the old amount plus the default request amount.
    Else just add the request for the default request amount. */
    if (requests.containsKey(ingredient.toLowerCase())) {
      requests.put(ingredient.toLowerCase(), requests.get(ingredient.toLowerCase()) + DEFAULT_REQUEST_AMOUNT);
      int amount = requests.get(ingredient.toLowerCase()) + DEFAULT_REQUEST_AMOUNT;
      logger.log(Level.INFO, "A request for "
              + DEFAULT_REQUEST_AMOUNT
              + " more units of "
              + ingredient
              + " has been added to requests.txt for a total request of "
              + amount + " units.");
    } else {
      requests.put(ingredient.toLowerCase(), DEFAULT_REQUEST_AMOUNT);
      logger.log(Level.INFO, "A request for "
              + DEFAULT_REQUEST_AMOUNT
              + " units of "
              + ingredient
              + " has been added to requests.txt.");
    }
  }

  /** Writes the requests HashMap to requests.txt. */
  private void writeRequestsToFile() {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(REQUEST_FILENAME));
      for (Map.Entry<String, Integer> entry : requests.entrySet()) {
        writer.write(entry.getValue() + " units of " + entry.getKey());
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * String representation of this inventory.
   *
   * @return Every ingredients' name, stock and threshold on separate lines.
   */
  public String toString() {
    String inventory = "";
    for (Map.Entry<String, int[][]> entry : ingredients.entrySet()) {
      inventory +=
          entry.getKey()
              + ": Stock = "
              + entry.getValue()[0][0]
              + ", Threshold = "
              + entry.getValue()[0][1]
              + System.lineSeparator();
    }
    return inventory;
  }
}
