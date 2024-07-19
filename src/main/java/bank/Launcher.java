package bank;

import bank.bankComponents.Bank;
import bank.cli.BankCommands;
import picocli.CommandLine;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the entry-point to every start of app.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */
public class Launcher {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        Map<String, Bank> nameBank = null;
        try (FileInputStream fis = new FileInputStream("src\\main\\resources\\data.bin");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            nameBank = (Map<String, Bank>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            nameBank = new HashMap<>();
        }
        int exitCode = new CommandLine(new BankCommands(nameBank)).execute(args);
        save(nameBank);
        System.exit(exitCode);
    }

    private static void save(Map<String, Bank> bankDataSet) {
        try (FileOutputStream fos = new FileOutputStream("src\\main\\resources\\data.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(bankDataSet);
        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
    }
}