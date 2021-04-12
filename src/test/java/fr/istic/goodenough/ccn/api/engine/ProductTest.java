package fr.istic.goodenough.ccn.api.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Return the pid of the product conform to the setup")
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

    /** Test if the short name of a product returned by getShortName() is the same as initiated in setup: "Jam/From"
     * Init: nothing more than setup
     * Expected : return short name of the product as "Jam/From"
     */
    @Test
    @Tag("UnitTest")
    @DisplayName("Return the short name conform to the product setup")
    void getShortName() {
        assertEquals("Jam/From", product.getShortName(), "The short name should be the same as \"Jam/From\"");
    }

    /** Test if the price of a product returned by getPrice() is the same as initiated setup : 10.0
     * Init: nothing more than setup
     * Expected : return price of the product as 10.0
     */
    @Test
    @Tag("UnitTest")
    @DisplayName("Return the price conform to the product setup")
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

    /** Test if the type of a product returned by getType() is the same as initiated setup : "nourriture"
     * Init : nothing more than setup
     * Expected : return type of the product as "nourriture"
     */
    @Test
    @Tag("UnitTest")
    @DisplayName("Return the product's type conform to the product setup")
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

    /** Test if the takeFromStock() method doesn't change the stock when we ask to take -1 product from unlimited stock
     * Init : set quantity to take from stock at -1
     * Expected : the old stock and the newStock are equals (takeFromStock() doesn't change anything)
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} but is bad value")
    @CsvSource({"-1"})
    @Tag("RobustnessTest")
    @DisplayName("Test to takeFromStock() on unlimited stock : try to take -1 product (Not possible)")
    void takeFromStockWithUnlimitedStockRobustness(int amount) {
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

    /** Test if the takeFromStock() method doesn't change the stock when we ask to take -1, 6 and 800000 product from limited stock
     * Init :
     * - set quantity to take from stock at -1
     * - set quantity to take from stock at 6
     * - set quantity to take from stock at 800000
     * Expected :  the old stock and the newStock are equals (takeFromStock() doesn't change anything because it's not possible to do)
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} but is bad value")
    @CsvSource({"-1","6","800000"})
    @Tag("RobustnessTest")
    @DisplayName("Test to takeFromStock() on limited stock : try to take -1, 6 and 800000 product(s) (Not possible)")
    void takeFromWithLimitedStockRobustness(int amount) {
        int oldStock=product2.getStock();
        assertFalse(product2.takeFromStock(amount));
        int newStock = product2.getStock();
        assertEquals(oldStock,newStock);
    }

    /** Test if the putInStock() method doesn't change the stock when we ask to put 0, 2 and 800000 product(s) in unlimited stock
     * Init:
     * - set quantity to put in stock at 0
     * - set quantity to put in stock at 2
     * - set quantity to put in stock at 800000
     * Expected : the old stock and the newStock are equals (because it's an unlimited stock)
     * @param amount int Product's amount */
    @ParameterizedTest(name = "Amount is {0} and newStock=oldStock")
    @CsvSource({"0","2","800000"})
    @Tag("UnitTest")
    @DisplayName("Test to putInStock() on unlimited stock : try to put 0,2 and 800000 product(s) (Not possible)")
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
    @DisplayName("Test to putInStock() on limited stock : try to put 0, 2 and 5 product(s)")
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