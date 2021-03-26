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

    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock-Amount")
    @CsvSource({"0","2","800000"})
    @Tag("UnitTest")
    void takeFromWithUnlimitedStockUnit(int amount) {
        int stockCourant=product.getStock();
        assertTrue(product.takeFromStock(amount));
        assertEquals(stockCourant-amount,product.getStock());
    }

    @ParameterizedTest(name = "Amount is {0} ")
    @CsvSource({"0","2","800000"})
    @Tag("RobustnessTest")
    void takeFromWithUnlimitedStockRobustness() {
        product.takeFromStock(-1);
    }

    @Test
    @Tag("UnitTest")
    void takeFromStockWithLimitedStock() {
    }

    @Test
    @Tag("UnitTest")
    void putInStockWithUnlimitedStock() {
    }

    @Test
    @Tag("UnitTest")
    void putInStockWithLimitedStock() {
    }
}