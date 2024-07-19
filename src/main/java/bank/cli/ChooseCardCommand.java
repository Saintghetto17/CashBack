package bank.cli;

import bank.bankComponents.Bank;
import picocli.CommandLine;

import java.util.Map;

/**
 * This class is the CLI realisation of choose-card command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "choose-card", description = {"gives a user best advice for paying for category"})

public class ChooseCardCommand implements Runnable {
    @CommandLine.Option(names = "--category", required = true, description = {"bank name where to save"})
    String category;
    @CommandLine.Option(names = "--value", description = {"card name"})
    String value;

    @Override
    public void run() {
        double parsedValue = -1;
        try {
            parsedValue = Double.parseDouble(value);
            if (parsedValue < 0) {
                System.out.println("The price for your category should be positive number");
                return;
            }
        } catch (NumberFormatException ex) {
            System.out.println("The price for your category should be positive number");
            return;
        }
        double maxValue = -1;
        String maxValueCardName = "";
        for (Map.Entry<String, Bank> bank : BankCommands.nameBank.entrySet()) {
            Map.Entry<String, Double> maxValueInCategoryBank = bank.getValue()
                    .getBestCardInCategory(category, parsedValue);
            if (maxValue < maxValueInCategoryBank.getValue()) {
                maxValueCardName = maxValueInCategoryBank.getKey();
                maxValue = maxValueInCategoryBank.getValue();
            }
        }
        if (maxValue == -1) {
            System.out.println("Choose any card you want, no special advantages");
            return;
        }
        System.out.println("Best card: " + maxValueCardName);
        System.out.println("Estimated cashback: " + maxValue);
        System.out.println("\n");
    }
}
