package fr.istic.goodenough.ccn.api.engine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private CustomerImpl customer;
    private ProductImpl product0;
    private OrderImpl order;

    @BeforeEach
    void setUp() {
        customer = new CustomerImpl("Hubert", "nutella", 1);
        product0 = new ProductImpl(0, "pizza marguarita avec des champis", "marguarita", 10.0, -1, "pizza");
//        order = new  OrderImpl (product0, customer, 2);
    }

    /** Check the cutomer's Uid
     * Assert that returned object have the right values */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Uid")
    void getUid() {
        assertEquals(customer.getUid(), 1);
    }

    /** Check the cutomer's name
     * Assert that returned object have the right values */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Name")
    void getName() {
        assertEquals(customer.getName(), "Hubert");
    }

    /** Check the cutomer's password
     * Assert that returned object have the right values */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Password")
    void getPasswd() {
        assertEquals(customer.getPasswd(), "nutella");
    }

    /** Check the cutomer's credit
     * Assert that returned object have the right values */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Credit")
    void getCredit() {
        assertEquals(customer.getCredit(), -1);
    }

/*
    @ParameterizedTest
    @DisplayName("Check PendingOrders")
    @CsvSource({"product1, customer, 3","product2, customer, 4","product3, customer,5"})
    void getPendingOrders(Collection<Order> collectionExpected) {
        Product product1 = new ProductImpl(1, "plats artisanaux", "chou farci", 5, -1, "menu" );
        Product product2 = new ProductImpl(2, "sandwich jambon beurre", "parisien", 4, -1, "sandwich");
        Product product3 = new ProductImpl(3, "hot dog", "hot dog vege", 9, -1, "vegetarien");
        customer.addProduct(product1, 3);
        customer.addProduct(product2, 4);
        customer.addProduct(product3, 5);
        assertEquals (collectionExpected, customer.getPendingOrders());
        //assertEquals(customer.getPendingOrders(), "null" );
    }*/

    /** Check the cutomer's pendingOrders
     * Assert that returned object is empty */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check PendingOrders")
    void getPendingOrders() {
        assertTrue(customer.getPendingOrders().isEmpty());
    }

    /** Try to add an unlimited product
     * Assert that returned true that the product has been added and that the stock is -1 */
    @Test
    @Tag("UnitTest")
    void addProductUnlimitedStock() {
        Product product1 = new ProductImpl(1, "plats artisanaux", "chou farci", 5, -1, "menu" );
        assertTrue(customer.addProduct(product1, 4));
        assertEquals(product1.getStock(), -1);
    }

    /** Try to add a limited product
     * Assert that returned true that the product has been added and that the stock has been updated */
    @Test
    @Tag("UnitTest")
    void addProductLimitedStock() {
        Product product1 = new ProductImpl(1, "plats artisanaux", "chou farci", 5, 5, "menu" );
        assertTrue(customer.addProduct(product1, 4));
        assertEquals(product1.getStock(), 1);
    }

    /** Try to add a limited product
     * Assert that the stock and the amount are correctly updated
     * Assert that the getPendingOrders returned the good objects*/
    @Test
    @Tag("UnitTest")
    void addProductUpdateStock() {
        Product product1 = new ProductImpl(1, "plats artisanaux", "chou farci", 5, 5, "menu" );
        assertTrue(customer.addProduct(product1, 4));
        assertEquals(product1.getStock(), 1);
        assertTrue(customer.addProduct(product1, 2));
        Collection<Order> allOrders = customer.getPendingOrders();
        Order orders = allOrders.iterator().next();
        assertEquals(orders.getProduct(), product1);
        assertEquals(orders.getCustomer(), customer);
        assertEquals(orders.getAmount(), 2);
        assertEquals(product1.getStock(), 3);
    }

    /** Check the cutomer's orders
     * Assert that returned object is empty */
    @Test
    @Tag("UnitTest")
    void order() {
        customer.order();
        assert (customer.getPendingOrders().isEmpty());
        assertTrue (customer.order());
    }

    /** Try to cancel a customer's order, we assert the product are substracted from there stock by the addProduct
     *  then that they are put back in stock by the clear.
     * Assert that order had been clear and that the stock had been updated*/
    @Test
    @Tag("UnitTest")
    void clear() {
        ProductImpl product0 = new ProductImpl(0, "pizza marguarita avec des champis", "marguarita", 10.0, 20, "pizza");
        ProductImpl product1 = new ProductImpl(1, "pizza regina avec des champis", "regina", 11.0, 12, "pizza");
        ProductImpl product2 = new ProductImpl(2, "sandwich fromage", "marguarita", 10.0, 4, "sandwich");
        customer.addProduct(product0,8);
        customer.addProduct(product1,10);
        customer.addProduct(product2,3);
        assertEquals(product0.getStock(),12);
        assertEquals(product1.getStock(),2);
        assertEquals(product2.getStock(),1);
        //assertEquals(customer.getPendingOrders().get(0).getAmount(),8); A POURSUIVRE
        customer.clear();
        assertEquals(true, customer.getPendingOrders().isEmpty());
        assertEquals(product0.getStock(),20);
        assertEquals(product1.getStock(),12);
        assertEquals(product2.getStock(),4);
    }

    /**
     *
     */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Get invalid add product")
    void addProductInvalid(){
        ProductImpl product0 = new ProductImpl(0, "pizza marguarita avec des champis", "marguarita", 10.0, 20, "pizza");
        assertFalse(customer.addProduct(product0, 21));
        assertFalse(customer.addProduct(product0, -1));
    }

    @Test
    @Tag("RobustnessTest")
    @DisplayName("Get invalid order")
    void orderInvalid(){
        Order order0 = new OrderImpl(product0,customer);
        customer.clear();
        assertFalse(order0.cancel());
    }

    @Test
    @Tag("RobustnessTest")
    @DisplayName("Get invalid clear")
    void clearInvalid(){

    }
}