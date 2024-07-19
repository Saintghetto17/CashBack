package bank.cli;

import bank.bankComponents.Bank;
import picocli.CommandLine;

/**
 * This class is the CLI realisation of add-card command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "add-card", description = {"Adds a card to specified bank"})
class AddCardCommand implements Runnable {

    @CommandLine.Option(names = "--bank", required = true, description = {"bank name"})
    String bankName;

    @CommandLine.Option(names = "--card", required = true, description = {"card name"})
    String cardName;

    @Override
    public void run() {
        if (!BankCommands.nameBank.containsKey(bankName)) {
            System.out.println("Firstly register this bank");
            return;
        }
        Bank bank = BankCommands.nameBank.get(bankName);
        bank.addCard(cardName);
        System.out.println("\n");
    }
}
