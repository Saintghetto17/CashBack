package bank.cli;

import bank.bankComponents.Bank;
import picocli.CommandLine;

/**
 * This class is the CLI realisation of add transaction command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "add-transaction", description = {"Adds a transaction"})

public class AddTransactionCommand implements Runnable {
    @CommandLine.Option(names = "--bank", required = true, description = {"bank name where to save"})
    String bankName;
    @CommandLine.Option(names = "--card", required = true, description = {"card name"})
    String cardName;
    @CommandLine.Option(names = "--category", required = true, description = {"category"})
    String category;

    @CommandLine.Option(names = "--value", required = true, description = {"category"})
    String value;

    @Override
    public void run() {
        if (!BankCommands.nameBank.containsKey(bankName)) {
            System.out.println("Firstly create a bank and then provide options");
        }
        Bank bank = BankCommands.nameBank.get(bankName);
        if (!bank.containsCard(cardName)) {
            System.out.println("You don't have a card " + cardName +
                    "\nUse \"choose-card\" to understand which card to pay for this category");
        }
        double parsedValue = 0;
        try  {
            parsedValue = Double.parseDouble(value);
        } catch(NumberFormatException ex) {
            System.out.println("Please, provide the correct price measured in decimal's");
            return;
        }
        bank.payForCategory(cardName, category, parsedValue);
    }
}
