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

    /** Tests if the pid returned by the getPid method() is the same as initiated in setup: 1004
     *  Init: nothing more than setup
     *  Expected: returned pid is the same as 1004
     *  */
    @Test
    @Tag("UnitTest")
    void getPid() {
        assertEquals(1004, product.getPid(), "The pid should be the same as 1004");
    }



    /** Tests if the full name of a product returned by the getFullName method() is the same as
     * initiated in the setup : Jambon Fromage
     *  Init: nothing more than setup
     *  Expected: return full name of product as "Jambon Fromage"
     *  */
    @Test
    @Tag("UnitTest")
    @DisplayName("Return the Full name conform to the product setup")
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

    /** Test if the stock of the product returned by getStock method() is the same as initiated setup: -1
     * which is infinite stock
     * init : nothing more than setup
     * Expected : return the stock of the product as -1 (unlimited stock)
     * */
    @Test
    @Tag("UnitTest")
    @DisplayName("Return the stock conform to the product setup")
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

    /** Test if the takeFromStock method() doesn't change stock value of a product when this one
     * is initiated at -1 (unlimited)
     * Init:
     *  - set quantity to take from stock at 0
     *  - set quantity to take from stock at 2
     *  - set quantity to take from stock at 800000
     * Expected: stock doesn't change
     *  - Stock quantity is -1 (x3)
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock")
    @CsvSource({"0","2","800000"})
    @Tag("UnitTest")
    @DisplayName("Test the stock amount when initiated unlimited and quantity is taken from it")
    void takeFromStockWithUnlimitedStockUnit(int amount) {
        int oldStock=product.getStock();
        assertTrue(product.takeFromStock(amount));
        int newStock=product.getStock();
        assertEquals(oldStock,newStock);
    }

    /** Try to take -1 product from unlimited stock
     * Assert that it's not possible
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} but is bad value")
    @CsvSource({"-1"})
    @Tag("RobustnessTest")
    void takeFromWithUnlimitedStockRobustness(int amount) {
        int oldStock=product.getStock();
        assertFalse(product.takeFromStock(amount));
        int newStock=product.getStock();
        assertEquals(oldStock,newStock);
    }


    /**Test if the takeFromStock method() does correctly change stock value
     * of a product when this one is limited at 5 and user try to take quantity from stock
     * Init:
     *  - set quantity to take from stock at 0
     *  - set quantity to take from stock at 2
     *  - set quantity to take from stock at 5
     * Expected: stock change as we take quantity from it
     *  - Stock quantity is 5
     *  - Stock quantity is 3
     *  - Stock quantity is 0
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock-Amount")
    @CsvSource({"0","2","5"})
    @Tag("UnitTest")
    @DisplayName("Test the stock amount when limited and quantity is taken from it")
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

    /** Test if the putInStock() method change the stock correctly when we ask to put 0, 2 and 5 product(s) in limited stock
     * Init:
     * - set quantity to put in stock at 0
     * - set quantity to put in stock at 2
     * - set quantity to put in stock at 5
     * Expected : the new stock is equal to the old stock + the quantity (added)
     * - Stock quantity is 5
     * - Stock quantity is 7
     * - Stock quantity is 10
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


     /** Test if the putInStock method() does not change the stock value of unlimited stock and limited stock
       when parametized with incorrect value -1.
     * Init:
     *  - set quantity to take from stock at -1
     * Expected: the old stock and the newStock are equals (because it's an unlimited stock)
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} but is bad value")
    @CsvSource({"-1"})
    @Tag("RobustnessTest")
    @DisplayName("Test to putInStock() on limited stock and unlimited stock : try to put -1 quantity")
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