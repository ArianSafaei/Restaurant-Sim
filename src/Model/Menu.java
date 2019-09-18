package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a menu contains a list of items that are served in the restaurant
 */
public class Menu {
  private static final String MENU =
      "restaurant settings/Menu.txt"; // the url to the text file representing the menu
  private ArrayList<MenuItem> menuItems; // the list of items on the menu
  private Logger logger = Log.getLogger(this.getClass().getName());

  /** instantiates a Menu of MenuItems according to the items specified in MENU */
  public Menu() {
      menuItems = new ArrayList<>();
      try {
          FileReader fileReader = new FileReader(MENU);
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          String line;
          while ((line = bufferedReader.readLine()) != null) {
              String[] substrings = line.split(", ");
              String name = substrings[0];
              int price = Integer.parseInt(substrings[1]);
              String icon = substrings[2];
              HashMap<String, Integer> ingredients = new HashMap<>();
              for (int i = 3; i < substrings.length; i += 2) {
                  String ingredient = substrings[i];
                  int amount = Integer.parseInt(substrings[i + 1]);
                  ingredients.put(ingredient, amount);
              }
              MenuItem menuItem = new MenuItem(name, price, icon, ingredients);
              menuItems.add(menuItem);
          }
          bufferedReader.close();
          logger.log(Level.INFO, "Menu has successfully been created.");
      } catch (FileNotFoundException exception) {
          System.out.println("file not found");
      } catch (IOException exception) {
          System.out.println("io exception");
      }
  }

  /**
   * returns a string representation of this menu, where each line contains one menuItem
   *
   * @return the string representation of this menu
   */
  public String toString() {
    StringBuilder toString = new StringBuilder();
    for (MenuItem menuItem : menuItems) {
      toString.append(menuItem.toString());
      toString.append("\n");
    }
    return toString.toString();
  }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

  /**
   * return the menuItem with this name if it exists, otherwise, notify the user.
   *
   * @param name the name of the menuItem
   * @return the menuItem with this name
   */
  public MenuItem getMenuItem(String name) throws NotOnMenuException {
    for (MenuItem menuItem : menuItems) {
      if (name.equals(menuItem.getName())) {
        return menuItem.clone();
      }
    }
    throw new NotOnMenuException(name + " does not exist on the menu.");
  }
}

