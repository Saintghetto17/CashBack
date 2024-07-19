package bank.cli;

import bank.bankComponents.Bank;
import picocli.CommandLine;

/**
 * This class is the estimate-cashback realisation.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */

@CommandLine.Command(name = "estimate-cashback", description = {"estimates current cashback"})

public class EstimateCashbackCommand implements Runnable {
    @Override
    public void run() {
        for (Bank bank : BankCommands.nameBank.values()) {
            bank.showCashBack();
            System.out.println();
        }
    }
}
