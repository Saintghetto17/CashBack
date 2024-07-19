package bank.cli;

import picocli.CommandLine;

/**
 * This class is the CLI realisation of add-current-cashback command.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
@CommandLine.Command(name = "add-current-cashback", description = {"Adds a current cashback to a card in current month"})
class AddCurrentCashBack implements Runnable {

    protected enum Time {
        CURRENT,
        FUTURE
    }

    @CommandLine.Option(names = "--bank", required = true, description = {"bank name where to save"})
    String bankName;
    @CommandLine.Option(names = "--card", required = true, description = {"card name"})
    String cardName;
    @CommandLine.Option(names = "--category", required = true, description = {"category"})
    String category;

    @CommandLine.Option(names = "--percent", required = true, description = {"percent"})
    String percent;

    @CommandLine.Option(names = "--permanent", description = {"1 if always, otherwise monthly"})
    String permanent = "0";

    protected void runImpl(Time when) {
        if (!BankCommands.nameBank.containsKey(bankName)) {
            System.out.println("Firstly register this bank and add a card to it");
            return;
        }
        double percentage = 0;
        int permanentFlag = 0;
        try {
            percentage = Double.parseDouble(percent);
            permanentFlag = Integer.parseInt(permanent);
        } catch (NumberFormatException ex) {
            System.out.println(ex.getLocalizedMessage());
            return;
        }
        if (when == Time.CURRENT) {
            BankCommands.nameBank.get(bankName).addCategoryToCard(cardName, category, percentage, permanentFlag);
        } else if (when == Time.FUTURE) {
            BankCommands.nameBank.get(bankName).addFutureCategoryToCard(cardName, category, percentage, permanentFlag);
        }
    }

    @Override
    public void run() {
        runImpl(Time.CURRENT);
        System.out.println("\n");
    }
}
