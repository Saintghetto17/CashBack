package bank.bankComponents;

import java.io.Serializable;
import java.util.*;

/**
 * This class let the user create bank.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
public class Bank implements Serializable {
    private final String name;
    private int limit;

    private Map<String, Card> nameCards = new HashMap<>();

    /**
     * Creates bank with INF limit on cashback
     *
     * @param name name of bank
     */

    public Bank(final String name) {
        this(name, Integer.MAX_VALUE);
    }

    /**
     * Creates a bank
     *
     * @param name  name of the bank
     * @param limit the limit on cashback
     */

    public Bank(final String name, final int limit) {
        this.name = name;
        if (limit < 0) {
            System.out.println("limit can't be less than zero, limit is setted to the INF");
            this.limit = Integer.MAX_VALUE;
        } else {
            this.limit = limit;
        }
    }

    /**
     * Simply returns the limit of the bank on the cashbacks
     *
     * @return
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Simply returns the name of bank
     *
     * @return name of the bank
     */

    public String getName() {
        return name;
    }

    /**
     * The realisation of command add-card. Adds a card into Bank, otherwise notify user that it is already added
     *
     * @param name
     */
    public void addCard(String name) {
        if (nameCards.containsKey(name)) {
            System.out.printf("\"%s\" card is already created in %s, please, provide options", name, this.name);
            return;
        }
        nameCards.put(name, new Card(name, this));
        System.out.printf("Your card : \"%s\" in %s has been successfully registered%n", name, this.name);
    }

    /**
     * Check whether the bank contain the card with this name
     *
     * @param name
     * @return true if contains, else false
     */

    public boolean containsCard(String name) {
        return nameCards.containsKey(name);
    }

    /**
     * The realisation of add-current-cashback command that updates the category in the card if necessary.
     * If the last update was provided less than 30 days after the previous, the update will not be done.
     *
     * @param name         The name of the card in which to add the category
     * @param categoryName The category to add to the card
     * @param percent      The percentage of buy that will be returned to user after transaction
     * @param permanent    Flag that shows whether the category is permanent : 1 -> permanent; otherwise -> monthly
     */
    public void addCategoryToCard(String name, String categoryName, double percent, int permanent) {
        if (!containsCard(name)) {
            System.out.println("Add a card to manipulate with it");
            return;
        }
        nameCards.get(name).addCurrentCategory(categoryName, percent, permanent);
    }

    /**
     * The realisation of add-future-cashback command that postpone the update of category
     *
     * @param name         The name of the card in which to add the category in future
     * @param categoryName The category to add to the card in the future
     * @param percent      The percentage of buy that will be returned to user after transaction
     * @param permanent    Flag that shows whether the category is permanent : 1 -> permanent; otherwise -> monthly
     */
    public void addFutureCategoryToCard(String name, String categoryName, double percent, int permanent) {
        if (!containsCard(name)) {
            System.out.println("Add a card to manipulate with it");
            return;
        }
        nameCards.get(name).addFutureCategory(categoryName, percent, permanent);
    }

    /**
     * The realisation of remove-current-cashback command
     *
     * @param cardName The name of the card in which to delete the category
     * @param category The category to delete
     */
    public void removeCardCurrentCategory(String cardName, String category) {
        if (!nameCards.containsKey(cardName)) {
            System.out.println("You can't delete the card that doesn't exist - add it");
            return;
        }
        nameCards.get(cardName).removeCurrentCategory(category);
    }

    /**
     * The realisation of remove-future-cashback command
     *
     * @param cardName The name of the card in which to delete the category
     * @param category The category to delete
     */
    public void removeCardFutureCategory(String cardName, String category) {
        if (!nameCards.containsKey(cardName)) {
            System.out.println("You can't delete the card that doesn't exist - add it");
            return;
        }
        nameCards.get(cardName).removeFutureCategory(category);
    }

    /**
     * The realisation of card-list that shows all cards in the bank
     */
    public void showCardList() {
        System.out.println(name + ":");
        for (Card card : nameCards.values()) {
            System.out.println("\t" + card.getName() + ":");
            for (Map.Entry<String, Category> nameCategory : card.getCurrentCategories().entrySet()) {
                System.out.println("\t\t" + nameCategory.getKey() + " " + nameCategory.getValue().getPercentage() + "%");
            }
        }
    }

    /**
     * Return the name of the best card in Bank in provided category
     *
     * @param name  provided name of the category
     * @param value the amount of money given by user
     * @return first -> the name of card to use, second->cashback with that card
     */

    public Map.Entry<String, Double> getBestCardInCategory(String name, double value) {
        double maxCashBack = -1;
        String nameMaxCashBack = "";
        for (Card card : nameCards.values()) {
            if (!card.getCurrentCategories().containsKey(name)) {
                continue;
            }
            Category category = card.getCurrentCategories().get(name);
            double possibleCashback = value * category.getPercentage() / 100;
            double accumulatedCashBack = card.getLastMonthCashBack();
            double realCashback =
                    limit <= possibleCashback + accumulatedCashBack ? limit - accumulatedCashBack : possibleCashback;
            if (realCashback > maxCashBack) {
                maxCashBack = realCashback;
                nameMaxCashBack = card.getName();
            }
        }
        return Map.entry(nameMaxCashBack, maxCashBack);
    }

    /**
     * Removes the card with given name
     *
     * @param name
     */
    public void removeCard(String name) {
        nameCards.remove(name);
    }

    /**
     * Pays for given category by the card
     *
     * @param card     name of the card to pay
     * @param category name of the category
     * @param value    the price for category
     */
    public void payForCategory(String card, String category, double value) {
        nameCards.get(card).pay(category, value);
    }

    public void showCashBack() {
        for (Map.Entry<String, Card> card : nameCards.entrySet()) {
            System.out.println(card.getValue().getName() + " " + card.getValue().getLastMonthCashBack() + "P");
        }
    }
}
