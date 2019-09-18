package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *a menuItem represents a dish and contains a map of necessary ingredients to their amounts
 */
public class MenuItem {
    private String name;
    //price in cents
    private int price;
    //map of ingredients to amounts
    //url of icon
    private String icon;
    private HashMap<String, Integer> ingredients;
    //a message is added here whenever ingredients are modified by the customer
    private ArrayList<String> itemModifications;
    private Logger logger = Log.getLogger(this.getClass().getName());

    /**
     * initializes a MenuItem with name, price, and necessary ingredients.
     *
     * @param name        the name of the item
     * @param price       the price of the item, in cents
     * @param ingredients the map of necessary ingredients to their amounts
     */
    public MenuItem(String name, int price, String icon, HashMap<String, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.icon = icon;
        this.ingredients = ingredients;
        this.itemModifications = new ArrayList<>();
    }

    /**
     * appends name with price
     *
     * @return the string representation of menuItem
     */
    public String toString() {
        return name + ", " + getPriceInString();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    /**
     * return price in string from
     *
     * @return the string representation of price
     */
    public String getPriceInString() {
        String price = String.valueOf(this.price);
        String dollars = price.substring(0, price.length() - 2);
        String cents = price.substring(price.length() - 2, price.length());
        price = "$" + dollars + "." + cents;
        return price;
    }

    public String getIcon() {
        return icon;
    }

    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getItemModifications() {
        return itemModifications;
    }

    /**
     * modify this menuItem by adding or subtracting ingredients from it, if possible
     *
     * @param ingredient the name of the ingredient.
     * @param amount     the amount change requested, where a negative amount represents a requested subtraction.
     */
    public void modifyIngredient(String ingredient, int amount) {
        if (amount > 0) {
            //addition noted
            logger.log(Level.INFO, "A MenuItem for "
                    + name + " has been modified for extra " + ingredient + ".");
            itemModifications.add("extra " + ingredient);
        } else {
            //cannot subtract unless item contained ingredient in the first place
            if (!ingredients.containsKey(ingredient)) {
                logger.log(Level.WARNING, "MenuItem " + name + " cannot have " + ingredient + " subtracted from it.");
            } else {
                //subtraction noted
                logger.log(Level.INFO, "A MenuItem for "
                        + name + " has been modified for less " + ingredient + ".");
                itemModifications.add("less " + ingredient);
            }
        }
        //add amount to original amount if ingredient originally in menuItem
        if (ingredients.containsKey(ingredient)) {
            amount += ingredients.get(ingredient);
        }
        if (amount >= 0) {
            //perform modification
            logger.log(Level.INFO, (amount-ingredients.get(ingredient))
                    + " units of " + ingredient + " was added to MenuItem " + name + ".");
            ingredients.put(ingredient, amount);
        } else {
            //refuses modification because menuItem did not contain enough ingredients in the first place
            logger.log(Level.WARNING, "MenuItem "
                    + name + " does not have enough "
                    + ingredient + " to subtract the specified amount from it.");
        }
    }

    public MenuItem clone() {
        return new MenuItem(name, price, icon, (HashMap) ingredients.clone());
    }

    /**
     * returns true if there is enough ingredients in this inventory to produce this menu item, false otherwise.
     *
     * @param inventory the inventory of ingredients to be searched
     * @return whether the item can be made with ingredients in this inventory
     */
    public boolean canBeMade(Inventory inventory) {
        List<String> existingIngredients = inventory.getExistingIngredients();
        for (String ingredient : ingredients.keySet()) {
            if (!(existingIngredients.contains(ingredient))){
                return false;
            }
            else if (inventory.getIngredientStock(ingredient) < ingredients.get(ingredient)) {
                return false;
            }
        }
        return true;
    }
}