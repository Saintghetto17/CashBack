package bank.cli;

import picocli.CommandLine;

/**
 * This class is the CLI realisation of remove-card command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "remove-card", description = {"Removes card from specified bank"})

public class RemoveCardCommand implements Runnable {
    @CommandLine.Option(names = "--bank", required = true, description = {"bank name where to save"})
    String bankName;
    @CommandLine.Option(names = "--card", required = true, description = {"card name"})
    String cardName;

    @Override
    public void run() {
        if (!BankCommands.nameBank.containsKey(bankName)) {
            System.out.println("Can't remove card from non-existing bank");
            return;
        }
        BankCommands.nameBank.get(bankName).removeCard(cardName);
        System.out.println("\n");
    }
}
