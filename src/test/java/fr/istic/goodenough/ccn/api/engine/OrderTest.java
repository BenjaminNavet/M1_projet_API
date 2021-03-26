package fr.istic.goodenough.ccn.api.engine;

import fr.istic.goodenough.ccn.api.data.PhonyData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Customer cust;
    private Product prod;
    private Order order;
    private Product prod2;
    private Order order2;

    @BeforeEach
    void setUp() {
        cust = new CustomerImpl("Jean Hubert", "creponutella", 1002);
        prod = new ProductImpl(1004, "Jambon fromage", "Jam/From", 10.0, -1, "nourriture");
        order = new OrderImpl(prod,cust,1);
    }

    /** Try to get the customer from an order
     * Asserts that the customer is equal to the Order's costumer */
    @Test
    void getCustomer() {
        assertEquals(cust, order.getCustomer(), "The customer should be the same as Jean Hubert");
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

    /** Try to set the amount of an order
     * Asserts that the amount is well-set
     * test with a product stock limited to 5 */
    @ParameterizedTest(name = "Limited stock : Amount is {0} and answer is {1}")
    @CsvSource({"-1,1","0,0","2,2","6,1"})
    void setAmountLimitedStock(int amount, int rep){
        Product prod2 = new ProductImpl(1004, "Jambon fromage", "Jam/From", 10.0, 5, "nourriture");
        Order order2 = new OrderImpl(prod2,cust,1);
        assertEquals(amount==rep,order2.setAmount(amount));
        assertEquals(rep, order2.getAmount());
    }

    /** test the getter for order's price */
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