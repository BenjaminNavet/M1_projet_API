package fr.istic.goodenough.ccn.api.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Customer cust;
    private Product prod;
    private Order order;
    private Product prod2;
    private Order order2;

    @BeforeEach
    void setUp() {
    }

    /** Try to get the customer from an order
     * Asserts that the customer is equal to the Order's costumer */
    @Test
    void getCustomer() {
    }

    @Test
    void getProduct() {
        assertEquals(prod, order.getProduct(),"The product should be the same as Jambon fromage");
    }

    @Test
    void getAmount() {
        assertEquals(0, order.getAmount(), "The amount should be 0");
    }

    /** Try to set the amount of an order
     * Asserts that the amount is well-set
     * test with an unlimited product stock */
    @ParameterizedTest(name = "Amount is {0} and answer is {1}")
    @CsvSource({"-1,1","0,0","2,2","800000,800000"})
    void setAmountUnlimitedStock(int amount, int rep) {
        assertEquals(amount==rep,order.setAmount(amount));
        assertEquals(rep, order.getAmount());
    }

    @Test
    @DisplayName("Order's price must match amount*price")
    void getOrderPrice() {
        assertEquals(order.getAmount()*prod.getPrice(), order.getOrderPrice());
    }

    /** test the deletion of amount in the order.
     * test is repeated on empty order */
    @RepeatedTest(2)
    @DisplayName("Product's amount must be 0")
    void cancel() {
        assertTrue(order.cancel());
        assertEquals(0,order.getAmount());
        assertEquals(0,order.getOrderPrice());
        assertEquals(prod,order.getProduct());
    }
}