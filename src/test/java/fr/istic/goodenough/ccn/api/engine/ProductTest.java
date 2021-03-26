package fr.istic.goodenough.ccn.api.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new ProductImpl(1004, "Jambon fromage", "Jam/From", 10.0, -1, "nourriture");
    }

    /** Try to get the pid of a product
     * Asserts that the pid is equal to 1004 */
    @Test
    void getPid() {
        assertEquals(1004, product.getPid(), "The pid should be the same as 1004");
    }

    /** Try to get the full name of a product
     * Asserts that the full name is equal to "Jambon fromage" */
    @Test
    void getFullName() {
        assertEquals("Jambon fromage", product.getFullName(), "The full name should be the same as \"Jambon fromage\"");
    }

    /** Try to get the short name of a product
     * Asserts that the short name is equal to "Jam/From" */
    @Test
    void getShortName() {
        assertEquals("Jam/From", product.getShortName(), "The short name should be the same as \"Jam/From\"");
    }

    /** Try to get the price of a product
     * Asserts that the price is equal to 10.0 */
    @Test
    void getPrice() {
        assertEquals(10.0, product.getPrice(), "The price should be the same as 10.0");
    }

    /** Try to get the stock of a product
     * Asserts that the stock is equal to -1 (unlimited stock) */
    @Test
    void getStock() {
        assertEquals(-1, product.getStock(), "The stock should be the same as -1");
    }

    /** Try to get the type of a product
     * Asserts that the type is equal to "nourriture" */
    @Test
    void getType() {
        assertEquals("nourriture", product.getType(), "The type should be the same as \"nourriture\"");
    }

    @Test
    void takeFromStock() {
    }

    @Test
    void putInStock() {
    }
}