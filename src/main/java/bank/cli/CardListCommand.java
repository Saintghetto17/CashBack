package bank.cli;

import bank.bankComponents.Bank;
import picocli.CommandLine;
/**
 * This class is the CLI realisation of card-list command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "card-list", description = {"shows user all card with cashback"})

public class CardListCommand implements Runnable {
    @Override
    public void run() {
        for (Bank bank : BankCommands.nameBank.values()) {
            bank.showCardList();
        }
        System.out.println("\n");
    }
}
