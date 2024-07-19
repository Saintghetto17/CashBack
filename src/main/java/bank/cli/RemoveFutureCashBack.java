package bank.cli;


import picocli.CommandLine;

/**
 * This class is the CLI realisation of remove-future-cashback command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "remove-future-cashback", description = {"Removes a current cashback of a card at this month"})

public class RemoveFutureCashBack extends RemoveCurrentCashBack {
    @Override
    public void run() {
        if (!BankCommands.nameBank.containsKey(bankName)) {
            System.out.println("Firstly register this bank and add a card to it");
            return;
        }
        BankCommands.nameBank.get(bankName).removeCardFutureCategory(cardName, category);
        System.out.println("\n");
    }
}
