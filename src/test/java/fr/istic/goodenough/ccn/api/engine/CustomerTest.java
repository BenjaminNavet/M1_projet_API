package fr.istic.goodenough.ccn.api.engine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Collection;
import java.util.Iterator;
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

    /** Test if the customer's Uid returned by the getUid method is the same as initiated in setup
     * Init : nothing more than setup
     * Expected : uid entered and uid of customer must be the same (int 1)*/
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Uid")
    void getUid() {
        assertEquals(customer.getUid(), 1);
    }

    /** Test if the customer's name returned by the getName method is the same as initiated in setup
     * Init : nothing more than setup
     * Expected : name entered and name of customer must be the same */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Name")
    void getName() {
        assertEquals(customer.getName(), "Hubert");
    }

    /** Test if the customer's password returned by the getPasswd method is the same as initiated in setup
     * Init : nothing more than setup
     * Expected : password entered and password of customer must be the same */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Password")
    void getPasswd() {
        assertEquals(customer.getPasswd(), "nutella");
    }

    /** Test if the customer's credit returned by the getCredit method is the same as initiated in constructor
     * Init : nothing more than setup
     * Expected : credit must be at -1 */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check Credit")
    void getCredit() {
        assertEquals(customer.getCredit(), -1);
    }

    /** Test if the customer's PendingOrder returned by the getPendingOrders method is empty after initialization
     * Init : nothing more than setup
     * Expected : PendingOrder must be empty */
    @Test
    @Tag("UnitTest")
    @DisplayName("Check if PendingOrders is empty")
    void getPendingOrdersEmpty() {
        assertTrue(customer.getPendingOrders().isEmpty());
    }

    /** Test if the customer's PendingOrder returned by the getPendingOrders method has been correctly filled with
     * the given values of 1 product added
     * Init : add a product with addProduct method
     * Expected :
     * 1 - the addProduct method must have worked
     * 2 - the product returned by the getProduct method must match the one entered in the addProduct method
     * 3 - the customer returned by the getCustomer method must match the one who added the product in his order
     * 4 - the amount of products returned by the getAmount method must match the one entered in the addProduct method*/
    @Test
    @Tag("UnitTest")
    @DisplayName("Check single PendingOrders")
    void getPendingOrdersSingle() {
        assertTrue(customer.addProduct(product0, 4));
        Order order = customer.getPendingOrders().iterator().next();
        assertEquals(order.getProduct(), product0);
        assertEquals(order.getCustomer(), customer);
        assertEquals(order.getAmount(), 4);
    }

    /** Test if the customer's PendingOrder returned by the getPendingOrders method has been correctly filled with
     * the given values of 2 product added
     * Init :
     * 1 - create a new product
     * 2 - add 2 products with addProduct method
     * Expected for each product added:
     * 1 - the addProduct method must have worked
     * 2 - the product returned by the getProduct method must match the one entered in the addProduct method
     * 3 - the customer returned by the getCustomer method must match the one who added the product in his order
     * 4 - the amount of products returned by the getAmount method must match the one entered in the addProduct method*/
    @Test
    @Tag("UnitTest")
    @DisplayName("Check multiple PendingOrders")
    void getPendingOrdersMultiple() {
        ProductImpl product1 = new ProductImpl(1, "pizza regina avec des champis", "regina", 11.0, 12, "pizza");
        assertTrue(customer.addProduct(product0, 3));
        assertTrue(customer.addProduct(product1, 4));
        for (Order order : customer.getPendingOrders()) {
            if (order.getProduct().equals(product0)){
                assertEquals(order.getAmount(), 3);
                assertEquals(order.getProduct(), product0);
                assertEquals(order.getCustomer(), customer);
            }
            if (order.getProduct().equals(product1)){
                assertEquals(order.getAmount(), 4);
                assertEquals(order.getProduct(), product1);
                assertEquals(order.getCustomer(), customer);
            }
        }
    }
    /** Test the addition of an unlimited product
     * Init : create new product with unlimited stock
     * Expected :
     * 1 - the addProduct method must have worked for the created product
     * 2 - the stock of products returned by the getStock method must not have been modified */
    @Test
    @Tag("UnitTest")
    @DisplayName("Add product with an unlimited stock")
    void addProductUnlimitedStock() {
        Product product1 = new ProductImpl(1, "plats artisanaux", "chou farci", 5, -1, "menu" );
        assertTrue(customer.addProduct(product1, 4));
        assertEquals(product1.getStock(), -1);
    }

    /** Test the addition of a limited product
     * Init : create new product with limited stock
     * Expected :
     * 1 - the addProduct method must have worked for the created product
     * 2 - the stock of products returned by the getStock method must have been updated, must match (intialStock-amount) */
    @Test
    @Tag("UnitTest")
    @DisplayName("Add product with a limited stock")
    void addProductLimitedStock() {
        Product product1 = new ProductImpl(1, "plats artisanaux", "chou farci", 5, 5, "menu" );
        assertTrue(customer.addProduct(product1, 4));
        assertEquals(product1.getStock(), 1);
    }

    /** Test the addition of a limited product and increase the amount
     * Init :
     * 1 - create a new product with limited stock
     * 2 - increase the amount of the product taken
     * Expected :
     * 1 - the addProduct method must have worked for the created product
     * 2 - the stock of products returned by the getStock method must have been updated, must match (intialStock-amount)
     * 2 - the addProduct method must have worked with the amount changes
     * 3 - the product returned by the getProduct method must match the one entered in the addProduct method
     * 4 - the customer returned by the getCustomer method must match the one who added the product in his order
     * 5 - the amount of products returned by the getAmount method must match the last one entered in the addProduct method
     * 6 - the stock of products returned by the getStock method must have been updated, must match (intialStock-lastAmount) */
    @Test
    @Tag("UnitTest")
    @DisplayName("Change the product's amount, take from stock")
    void changeProductAmount() {
        Product product1 = new ProductImpl(1, "plats artisanaux", "chou farci", 5, 5, "menu" );
        assertTrue(customer.addProduct(product1, 2));
        assertEquals(product1.getStock(), 3);
        assertTrue(customer.addProduct(product1, 4));
        Order orders = customer.getPendingOrders().iterator().next();
        assertEquals(orders.getProduct(), product1);
        assertEquals(orders.getCustomer(), customer);
        assertEquals(orders.getAmount(), 4);
        assertEquals(product1.getStock(), 1);
    }

    /** Test the addition of a limited product and decrease the amount
     * Init :
     * 1 - create a new product with limited stock
     * 2 - decrease the amount of the product taken
     * Expected :
     * 1 - the addProduct method must have worked for the created product
     * 2 - the stock of products returned by the getStock method must have been updated, must match (intialStock-amount)
     * 2 - the addProduct method must have worked with the amount changes
     * 3 - the product returned by the getProduct method must match the one entered in the addProduct method
     * 4 - the customer returned by the getCustomer method must match the one who added the product in his order
     * 5 - the amount of products returned by the getAmount method must match the last one entered in the addProduct method
     * 6 - the stock of products returned by the getStock method must have been updated, must match (intialStock-lastAmount) */
    @Test
    @Tag("UnitTest")
    @DisplayName("Change the product's amount, and test the method putInStock of ProductImpl class")
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

    /** Test that the order method works when ordering a product
     * Init :
     * 1 - add a product with addProduct method
     * 2 - Order with order method
     * Expected : the order must have been carried out correctly */
    @Test
    @Tag("UnitTest")
    @DisplayName("Order done with product")
    void order() {
        customer.addProduct(product0, 3);
        assertTrue (customer.order());
    }

    /** Tests the canceling of a customer's order if it is empty, and that the products return to the stock with the clear method
     *  Init :
     *  1 - create 3 products with limited stock
     *  2 - add all 3 products to the customer's pendingOrder
     *  3 - clear the customer's pendingOrder
     *  4 - order with the order method
     *  Expected :
     *  1 - the 3 addProduct methods must have worked for the 3 created products
     *  2 - the 3 different product stocks have been correctly updated (intial stock - Amount of products taken)
     *  3 - the order must not be possible
     *  4 - the pendingOrder returned with the getPendingOrders method must be empty
     *  5 - the 3 different product stocks must have the same amount of products as initially */
    @Test
    @Tag("UnitTest")
    @DisplayName("Clear test")
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
        customer.clear();
        assertFalse(customer.order());
        assertTrue(customer.getPendingOrders().isEmpty());
        assertEquals(product0.getStock(),20);
        assertEquals(product1.getStock(),12);
        assertEquals(product2.getStock(),4);
    }

    /** Tests the addition of products in incorrect quantities
     * Init : create a new product with limited stock
     * Expected :
     * 1 - the addition of products in amounts exceeding the available stock must not be possible
     * 2 - the addition of products in negative quantities must not be possible */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Get invalid add product")
    void addProductInvalid(){
        ProductImpl product0 = new ProductImpl(0, "pizza marguarita avec des champis", "marguarita", 10.0, 20, "pizza");
        assertFalse(customer.addProduct(product0, 21));
        assertFalse(customer.addProduct(product0, -1));
    }

    /** Tests the canceling of a customer's order if it is empty
     * Init : nothing more than setup
     * Expected :
     * 1 - no products must have been added to the customer's pendingOrder
     * 2 - the order must not be possible with a empty basket */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Get invalid order")
    void orderInvalid(){
        assertTrue (customer.getPendingOrders().isEmpty());
        assertFalse (customer.order());
    }
}