package bank.cli;

import bank.bankComponents.Bank;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * This class is the entry point for every CLI class.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "", subcommands = {AddBankCommand.class, AddCardCommand.class, AddCurrentCashBack.class,
        AddFutureCashBack.class, RemoveCurrentCashBack.class, RemoveFutureCashBack.class,
        CardListCommand.class, AddTransactionCommand.class, ChooseCardCommand.class, RemoveCardCommand.class,
        EstimateCashbackCommand.class},
        description = "Banking application")
public class BankCommands implements Callable<Integer> {
    static Map<String, Bank> nameBank = new HashMap<>();

    public BankCommands(Map<String, Bank> nameBank) {
        BankCommands.nameBank = nameBank;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("\n");
        return 0;
    }
}