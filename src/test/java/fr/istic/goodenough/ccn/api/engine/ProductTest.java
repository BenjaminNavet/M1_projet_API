package fr.istic.goodenough.ccn.api.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;
    private Product product2;

    @BeforeEach
    void setUp() {
        product = new ProductImpl(1004, "Jambon fromage", "Jam/From", 10.0, -1, "nourriture");
        product2 = new ProductImpl(1004, "Jambon fromage", "Jam/From", 10.0, 5, "nourriture");
    }

    /** Try to get the pid of a product
     * Asserts that the pid is equal to 1004 */
    @Test
    @Tag("UnitTest")
    void getPid() {
        assertEquals(1004, product.getPid(), "The pid should be the same as 1004");
    }

    /** Try to get the full name of a product
     * Asserts that the full name is equal to "Jambon fromage" */
    @Test
    @Tag("UnitTest")
    void getFullName() {
        assertEquals("Jambon fromage", product.getFullName(), "The full name should be the same as \"Jambon fromage\"");
    }

    /** Try to get the short name of a product
     * Asserts that the short name is equal to "Jam/From" */
    @Test
    @Tag("UnitTest")
    void getShortName() {
        assertEquals("Jam/From", product.getShortName(), "The short name should be the same as \"Jam/From\"");
    }

    /** Try to get the price of a product
     * Asserts that the price is equal to 10.0 */
    @Test
    @Tag("UnitTest")
    void getPrice() {
        assertEquals(10.0, product.getPrice(), "The price should be the same as 10.0");
    }

    /** Try to get the stock of a product
     * Asserts that the stock is equal to -1 (unlimited stock) */
    @Test
    @Tag("UnitTest")
    void getStock() {
        assertEquals(-1, product.getStock(), "The stock should be the same as -1");
    }

    /** Try to get the type of a product
     * Asserts that the type is equal to "nourriture" */
    @Test
    @Tag("UnitTest")
    void getType() {
        assertEquals("nourriture", product.getType(), "The type should be the same as \"nourriture\"");
    }

    /** Try to take products from unlimited stock
     * Assert that stock hasn't been changed
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock")
    @CsvSource({"0","2","800000"})
    @Tag("UnitTest")
    void takeFromWithUnlimitedStockUnit(int amount) {
        int stockCourant=product.getStock();
        assertTrue(product.takeFromStock(amount));
        assertEquals(stockCourant-amount,product.getStock());
    }

    /** Try to take -1 product from unlimited stock
     * Assert that it's not possible
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} but is bad value")
    @CsvSource({"-1"})
    @Tag("RobustnessTest")
    void takeFromWithUnlimitedStockRobustness() {
        product.takeFromStock(-1);
    }

    /** Try to take products from limited stock
     * Assert that stock has been changed
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock-Amount")
    @CsvSource({"0","2","5"})
    @Tag("UnitTest")
    void takeFromStockWithLimitedStockUnit(int amount) {
        int oldStock = product2.getStock();
        assertTrue(product2.takeFromStock(amount));
        int newStock = product2.getStock();
        assertEquals(oldStock-amount,newStock);
    }

    /** Try to take outlier amount from limited stock
     * Assert that it's not possible
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} but is bad value")
    @CsvSource({"-1","6","800000"})
    @Tag("RobustnessTest")
    void takeFromWithLimitedStockRobustness(int amount) {
        int oldStock=product2.getStock();
        assertFalse(product2.takeFromStock(amount));
        int newStock = product2.getStock();
        assertEquals(oldStock,newStock);
    }

    /** Try to put products in unlimited stock
     * Assert that stock hasn't been changed
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock")
    @CsvSource({"0","2","800000"})
    @Tag("UnitTest")
    void putInStockWithUnlimitedStockUnit(int amount) {
        int oldStock=product.getStock();
        assertTrue(product.putInStock(amount));
        int newStock = product.getStock();
        assertEquals(oldStock,newStock);
    }

    /** Try to put products in limited stock
     * Assert that stock has been changed
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock+Amount")
    @CsvSource({"0","2","5"})
    @Tag("UnitTest")
    void putInStockWithlimitedStockUnit(int amount) {
        int oldStock=product2.getStock();
        assertTrue(product2.putInStock(amount));
        int newStock=product2.getStock();
        assertEquals(oldStock+amount,newStock);
    }

    /** Try to put outlier amount to limited stock and unlimited stock
     * Assert that stock hasn't been changed
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} but is bad value")
    @CsvSource({"-1"})
    @Tag("RobustnessTest")
    void putInStockRobustnessTest(int amount) {
        int oldStock=product2.getStock();
        assertFalse(product2.putInStock(amount));
        int newStock=product2.getStock();
        assertEquals(oldStock,newStock);

        oldStock=product.getStock();
        assertFalse(product.putInStock(amount));
        newStock = product.getStock();
        assertEquals(oldStock,newStock);
    }
}