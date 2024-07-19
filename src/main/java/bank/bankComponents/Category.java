package bank.bankComponents;

import java.io.Serializable;

/**
 * This class let the user create category for {@link Card}.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
public class Category implements Serializable {
    private final String name;

    private final double percentage;
    private int permanent;

    /**
     * Creates the category for the card
     *
     * @param name       name of category
     * @param percentage the amount of percentage
     * @param permanent  flag that shows is the category will be with cashback always
     */
    public Category(String name, double percentage, int permanent) {
        this.name = name;
        this.percentage = percentage;
        this.permanent = permanent;
    }

    /**
     * @return True if category permanent, false otherwise
     */
    public boolean isPermanent() {
        return permanent == 1;
    }

    /**
     * Return the percentage of this category cashback
     *
     * @return percentage
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Return the name of category
     *
     * @return
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Category)) {
            return false;
        }
        return name.equals(((Category) obj).name) && percentage == ((Category) obj).percentage
                && isPermanent() == ((Category) obj).isPermanent();
    }
}
