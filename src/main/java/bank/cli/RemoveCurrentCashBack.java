package bank.cli;

import picocli.CommandLine;

/**
 * This class is the CLI realisation of remove-current-cashback command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "remove-current-cashback", description = {"Removes a current cashback of a card at this month"})

public class RemoveCurrentCashBack implements Runnable {
    @CommandLine.Option(names = "--bank", required = true, description = {"bank name where to save"})
    String bankName;
    @CommandLine.Option(names = "--card", required = true, description = {"card name"})
    String cardName;
    @CommandLine.Option(names = "--category", required = true, description = {"category"})
    String category;

    @Override
    public void run() {
        if (!BankCommands.nameBank.containsKey(bankName)) {
            System.out.println("Firstly register this bank and add a card to it");
            return;
        }
        BankCommands.nameBank.get(bankName).removeCardCurrentCategory(cardName, category);
        System.out.println("\n");
    }
}
