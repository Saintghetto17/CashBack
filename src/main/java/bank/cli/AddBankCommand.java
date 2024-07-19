package bank.cli;

import bank.bankComponents.Bank;
import picocli.CommandLine;

/**
 * This class is the CLI realisation of add-bank command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "add-bank", description = "Adds a bank")
class AddBankCommand implements Runnable {
    @CommandLine.Option(names = {"--name"}, required = true, description = "name of the bank")
    private String name = "";

    @CommandLine.Option(names = {"-l", "--limit"}, description = "Cashback limit")
    private int limit = Integer.MAX_VALUE;

    @Override
    public void run() {
        if (BankCommands.nameBank.containsKey(name)) {
            System.out.println("This bank is already registered, provide options");
            return;
        }
        BankCommands.nameBank.put(name, new Bank(name, limit));
        System.out.println("Added bank: " + name + " with cashback limit: " + limit);
        System.out.println("\n");
    }
}
