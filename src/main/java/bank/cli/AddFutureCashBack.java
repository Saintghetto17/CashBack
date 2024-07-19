package bank.cli;

import picocli.CommandLine;

/**
 * This class is the CLI realisation of add-future-cashback command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "add-future-cashback", description = {"Adds a future cashback to a card in next month"})
public class AddFutureCashBack extends AddCurrentCashBack {
    @Override
    public void run() {
        runImpl(Time.FUTURE);
        System.out.println("\n");
    }
}
