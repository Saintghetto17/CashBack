package bank.bankComponents;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import bank.tools.*;

/**
 * This class let the user create card for {@link Bank}.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
public class Card implements Serializable {

    public enum Time {
        CURRENT,
        FUTURE
    }

    private Pair<Double, Long> cashBackLastMonth = new Pair<>((double) 0, currentTimeStampDays());
    private Pair<Double, Long> expensesLastMonth = new Pair<>((double) 0, currentTimeStampDays());

    private Map<String, Category> currentCategory = new HashMap<>();
    private static final int MONTH = 30;
    private final String name;
    private final Bank bankProvider;


    private Map<String, Long> categoryTimeStamp = new HashMap<>();

    private Map<String, Category> futureCategories = new HashMap<>();

    /**
     * Creates the card
     *
     * @param name         name of the card
     * @param bankProvider bank to which the card relates
     */
    public Card(String name, Bank bankProvider) {
        this.name = name;
        this.bankProvider = bankProvider;
    }

    /**
     * Adds a current category with name and values for {@link Category} constructor
     *
     * @param name      name of the current category to add
     * @param percent   the percent of current category cashback
     * @param permanent flag that shows is the category will be with cashback always
     */
    public void addCurrentCategory(String name, double percent, int permanent) {
        long currentTimeDays = currentTimeStampDays();
        if (currentCategory.containsKey(name)) {
            if (currentCategory.get(name).isPermanent()) {
                futureCategories.remove(name);
                System.out.println("The category is already added with forever duration");
            } else {
                if (currentTimeDays - categoryTimeStamp.get(name) < MONTH) {
                    System.out.println("The category is already added, wait a month to update");
                } else {
                    if (futureCategories.containsKey(name)) {
                        currentCategory.put(name, futureCategories.get(name));
                        futureCategories.remove(name);
                        return;
                    }
                    currentCategory.put(name, new Category(name, percent, permanent));
                    categoryTimeStamp.put(name, currentTimeDays);
                }
            }
            return;
        }
        currentCategory.put(name, new Category(name, percent, permanent));
        categoryTimeStamp.put(name, currentTimeDays);
        System.out.printf("Category : %s has been successfully added \nTo your card : %s \nWith percentage : %f%% \n",
                name, this.name, percent);
    }

    /**
     * Adds a future category with name and values for {@link Category} constructor
     *
     * @param name      name of the future category
     * @param percent   the percent of category cashback
     * @param permanent flag that shows is the category will be with cashback always
     */
    public void addFutureCategory(String name, double percent, int permanent) {
        if (futureCategories.containsKey(name)) {
            System.out.println("Impossible to plan updating in two months further," +
                    "\nThe updating for next month is already planned");
            return;
        }
        futureCategories.put(name, new Category(name, percent, permanent));
        System.out.printf("Category : %s has been successfully planned \nTo your card : %s \nWith percentage : %f%%",
                name, this.name, percent);
    }

    private boolean containsCategory(String name, Time when) {
        if (when == Time.CURRENT) {
            return currentCategory.containsKey(name);
        } else if (when == Time.FUTURE) {
            return futureCategories.containsKey(name);
        }
        return false;
    }

    /**
     * Removes the current category with provided name inside the card
     *
     * @param name the name of the current category
     */

    public void removeCurrentCategory(String name) {
        if (!containsCategory(name, Time.CURRENT)) {
            System.out.printf("Card: %s doesn't have category: %s", this.name, name);
            return;
        }
        currentCategory.remove(name);
        System.out.printf("Current Category : %s from card : %s has been removed", name, this.name);
    }

    /**
     * Removes the future category inside the card
     *
     * @param name name of the category to remove
     */
    public void removeFutureCategory(String name) {
        if (!containsCategory(name, Time.FUTURE)) {
            System.out.printf("Card: %s doesn't have future category: %s", this.name, name);
            return;
        }
        futureCategories.remove(name);
        System.out.printf("Future Category : %s from card : %s has been removed", name, this.name);
    }

    /**
     * Returns the name of the card
     *
     * @return the name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * Checks is the category with given name not old. Every month with an update
     *
     * @param name name of category to check
     * @return true if the category was added less than 30 days earlier, false otherwise
     */
    private boolean validCategoryTime(String name) {
        return currentTimeStampDays() - categoryTimeStamp.get(name) < MONTH;
    }

    /**
     * Gives all the actual categories of the card
     *
     * @return return the copy of categories in the card
     */
    public Map<String, Category> getCurrentCategories() {
        for (Map.Entry<String, Category> nameCategory : currentCategory.entrySet()) {
            if (!validCategoryTime(nameCategory.getKey())) {
                currentCategory.remove(nameCategory.getKey());
                if (futureCategories.containsKey(nameCategory.getKey())) {
                    currentCategory.put(nameCategory.getKey(), futureCategories.get(nameCategory.getKey()));
                    futureCategories.remove(nameCategory.getKey());
                }
            }
        }
        return new HashMap<>(currentCategory);
    }

    private long currentTimeStampDays() {
        Instant now = Instant.now();
        long currentTimeMillis = now.toEpochMilli();
        return TimeUnit.MILLISECONDS.toDays(currentTimeMillis);
    }

    private void updateCardMoneyUnits() {
        long time = currentTimeStampDays();
        if (time - expensesLastMonth.getSecond() >= MONTH) {
            expensesLastMonth = new Pair<>((double) 0, time);
        }
        if (time - cashBackLastMonth.getSecond() >= MONTH) {
            cashBackLastMonth = new Pair<>((double) 0, time);
        }
    }

    public void pay(String category, double value) {
        updateCardMoneyUnits();
        getCurrentCategories();
        double prevExpenses = expensesLastMonth.getFirst();
        expensesLastMonth = new Pair<>(prevExpenses + value, expensesLastMonth.getSecond());
        if (currentCategory.containsKey(category)) {
            if (cashBackLastMonth.getFirst() < bankProvider.getLimit()) {
                double prevCashBack = cashBackLastMonth.getFirst();
                double possibleUpdate = prevCashBack + value * currentCategory.get(category).getPercentage() / 100;
                double currCashBack =
                        possibleUpdate < bankProvider.getLimit() ? possibleUpdate : bankProvider.getLimit();
                cashBackLastMonth = new Pair<>(currCashBack, cashBackLastMonth.getSecond());
            }
            System.out.println("Your payment for \"" + category + "\" with " + name + " has been successfully done");
            return;
        }
        System.out.println("You don't have category " + category + " in " + name);
    }

    public double getLastMonthCashBack() {
        updateCardMoneyUnits();
        return cashBackLastMonth.getFirst();
    }
}
