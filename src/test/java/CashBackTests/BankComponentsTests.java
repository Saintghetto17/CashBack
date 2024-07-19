package CashBackTests;

import bank.bankComponents.Bank;
import bank.bankComponents.Card;
import bank.bankComponents.Category;
import org.junit.jupiter.api.*;

import java.util.Map;

/**
 * This class is the test class for BankComponents package.
 *
 * @author Ilya Novitskiy, @SainPher, Ilya.Novitskiy.04@mail.ru
 */

@DisplayName("Tests bank components")
public class BankComponentsTests {
    private Bank bank;

    private Category category;
    private Card card;

    @BeforeEach
    void initBank() {
        bank = new Bank("Tinkoff", 3000);
    }

    void initCategoryCard() {
        this.category = new Category("Games", 1.0, 1);
        this.card = new Card("Tinkoff-MIR", bank);
    }

    @Test
    void testCategory() {
        initCategoryCard();
        Assertions.assertEquals("Games", category.getName());
        Assertions.assertEquals(1.0, category.getPercentage());
        Assertions.assertTrue(category.isPermanent());
        Assertions.assertEquals(category, new Category("Games", 1.0, 1));
    }

    @Test
    void testCardGetters() {
        initCategoryCard();
        Assertions.assertEquals("Tinkoff-MIR", card.getName());
    }

    @Test
    void testCategoryAddingGetting() {
        initCategoryCard();
        card.addCurrentCategory(category.getName(), category.getPercentage(), 1);
        Map<String, Category> currCategories = card.getCurrentCategories();
        for (Category cat : currCategories.values()) {
            Assertions.assertEquals(category, cat);
        }
    }

    @Test
    void testRemoveCurrentCategory() {
        initCategoryCard();
        card.addCurrentCategory(category.getName(), category.getPercentage(), 1);
        card.removeCurrentCategory(category.getName());
        Assertions.assertTrue(card.getCurrentCategories().isEmpty());
    }

    @Test
    void testPaymentAndCashBackLimit() {
        initCategoryCard();
        card.addCurrentCategory(category.getName(), category.getPercentage(), 1);
        card.pay("Games", 10000000);
        Assertions.assertEquals(3000, card.getLastMonthCashBack());
    }

    @Test
    void testPaymentAndCashBackLess() {
        initCategoryCard();
        card.addCurrentCategory(category.getName(), category.getPercentage(), 1);
        card.pay("Games", 10000);
        Assertions.assertEquals(100, card.getLastMonthCashBack());
        card.pay("Games", 10000);
        Assertions.assertEquals(200, card.getLastMonthCashBack());
    }

    @Test
    void testBankGetters() {
        Assertions.assertEquals("Tinkoff", bank.getName());
        Assertions.assertEquals(3000, bank.getLimit());
    }

    void initBankCondition() {
        bank.addCard("Tinkoff-MIR");
        bank.addCard("Tinkoff-Young");
        bank.addCard("Tinkoff-Student");
        bank.addCategoryToCard("Tinkoff-MIR", "Games", 1.0, 1);
        bank.addCategoryToCard("Tinkoff-Young", "Games", 1.5, 1);
        bank.addCategoryToCard("Tinkoff-MIR", "Wine", 5, 1);
        bank.addCategoryToCard("Tinkoff-Young", "Wine", 1, 1);
        bank.addCategoryToCard("Tinkoff-Student", "Wine", 0.2, 1);
    }

    @Test
    void testAddCard() {
        initBankCondition();
        Assertions.assertTrue(bank.containsCard("Tinkoff-MIR"));
        Assertions.assertTrue(bank.containsCard("Tinkoff-Young"));
    }

    @Test
    void testAddCategoryToCardAndBest() {
        initBankCondition();
        Map.Entry<String, Double> best = bank.getBestCardInCategory("Games", 10000);
        Assertions.assertEquals("Tinkoff-Young", best.getKey());
        best = bank.getBestCardInCategory("Wine", 5000);
        Assertions.assertEquals("Tinkoff-MIR", best.getKey());
    }

    @Test
    void testSuggestionsAfterPayment() {
        initBankCondition();
        Map.Entry<String, Double> best = bank.getBestCardInCategory("Games", 10000);
        Assertions.assertEquals("Tinkoff-Young", best.getKey());

        bank.payForCategory("Tinkoff-Young", "Games", 100000000);
        best = bank.getBestCardInCategory("Games", 10000);
        Assertions.assertEquals("Tinkoff-MIR", best.getKey());

        bank.payForCategory("Tinkoff-MIR", "Wine", 1000000000);
        best = bank.getBestCardInCategory("Wine", 10000);
        Assertions.assertEquals("Tinkoff-Student", best.getKey());
    }

    @Test
    void testRemoveCardCategory() {
        initBankCondition();
        bank.removeCard("Tinkoff-MIR");
        Assertions.assertFalse(bank.containsCard("Tinkoff-MIR"));
    }
}
