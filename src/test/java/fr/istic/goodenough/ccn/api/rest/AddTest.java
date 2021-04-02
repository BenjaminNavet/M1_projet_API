package fr.istic.goodenough.ccn.api.rest;

import fr.istic.goodenough.ccn.api.engine.*;
import fr.istic.goodenough.ccn.api.engine.Order;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
// JUnit 4 Test helper
import org.junit.Before;
import org.junit.Test;
// JUnit 5 Assertions (!!!)
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import java.util.Collection;
import java.util.Optional;

public class AddTest extends JerseyTest {

    private Engine engine;

    @Override
    protected Application configure() {
        return new ResourceConfig(Add.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        EnginePhonyImpl.currentEngine = new EnginePhonyImpl();
        engine = EnginePhonyImpl.currentEngine;
    }

    //##########
    // CUSTOMER
    //##########
    /** Test if the product is correctly added to the customer basket by the api call on /add with a valid uid in path
     *  and valid pid and amount in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 200 / http_ok.
     *  2- Requested item is present in requested quantity in user basket */
    @Test
    @DisplayName("Valid product is added to valid user with correct amount")
    public void testAddProductOK(){
        // Build and execute request
        Response response= target("/add/1")
                .queryParam("pid", 3141592)
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(200, response.getStatus(), "Wrong http code"); // Assert HTTP_OK
        Optional<Customer> cust = engine.getCustomer(1);
        assertTrue(cust.isPresent(),"Customer should exist, invalid test data");
        Collection<Order> orderList = cust.get().getPendingOrders();
        assertEquals(1,orderList.size(),"User order size is invalid");
        Order order = orderList.iterator().next();
        assertEquals(3141592,order.getProduct().getPid(), "Product id invalid");
        assertEquals(6,order.getAmount(),"Product amount invalid");
    }

    /** Test if the request is properly rejected and no order is added by the api call on /add with an unknown uid and
     *  valid pid/amount in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found.
     *  2- Json message contains "Account not found" */
    @Test
    @DisplayName("Invalid user")
    public void testAddProductUnknownUid() {
        // Build and execute request
        Response response= target("/add/666")
                .queryParam("pid", 3141592)
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(404 , response.getStatus(), "Wrong http code");
        String body = response.readEntity(String.class);
        assertTrue(body.contains("Account not found"));
    }

    /** Test if the request is properly rejected and no order is added by the api call on /add with an empty uid in path
     *  and valid pid/amount in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found */
    @Test
    @DisplayName("Empty uid")
    public void testAddProductEmptyUid() {
        // Build and execute request
        Response response= target("/add")
                .queryParam("pid", 3141592)
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(404, response.getStatus(), "Wrong http code");
    }

    /** Test if the request is properly rejected and no order is added by the api call on /add with a wrong uid format
     *  and valid pid/amount in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 400 / http_bad_request */
    @Test
    @DisplayName("Wrong uid format")
    public void testAddProductLettersUid() {
        // Build and execute request
        Response response= target("/add/abcd")
                .queryParam("pid", 3141592)
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(400 , response.getStatus(), "Wrong http code");
    }

    //#########
    // Amount
    //#########
    /** Test if the request is properly rejected and no order is added by the api call on /add with a too much amount
     *  on a limited stock product.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 400 / http_bad_request
     *  2- Customer basket remains empty */
    @Test
    @DisplayName("Add too much quantity of a product")
    public void testAddProductTooMuch() {
        // Build and execute request
        Response response= target("/add/1")
                .queryParam("pid", 45)
                .queryParam("amount", 666)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(400 , response.getStatus(), "Wrong http code");
        assertTrue(engine.getCustomer(1).get().getPendingOrders().isEmpty(),"Customer basket should be empty");
    }

    /** Test if the request is properly rejected and no order is added by the api call on /add with a wrong amount
     *  format and valid uid/pid in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 400 / http_bad_request
     *  2- Customer basket remains empty */
    @Test
    @DisplayName("Wrong amount format")
    public void testAddProductLettersAmount() {
        // Build and execute request
        Response response= target("/add/1")
                .queryParam("pid", 3141592)
                .queryParam("amount", "abcd")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(400 , response.getStatus(), "Wrong http code");
        assertTrue(engine.getCustomer(1).get().getPendingOrders().isEmpty(),"Customer basket should be empty");
    }

    /** Test if the request is properly rejected and no order is added by the api call on /add with an empty amount
     *  format and valid uid/pid in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 400 / http_bad_request
     *  *  2- Customer basket remains empty */
    @Test
    @DisplayName("Empty amount")
    public void testAddProductEmptyAmount() {
        // Build and execute request
        Response response= target("/add/1")
                .queryParam("pid", 3141592)
                .queryParam("amount", "")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(400 , response.getStatus(), "Wrong http code");
        assertTrue(engine.getCustomer(1).get().getPendingOrders().isEmpty(),"Customer basket should be empty");
    }

    //#########
    // Product
    //#########
    /** Test if the request is properly rejected and no order is added by the api call on /add with an unknown pid and
     *  valid uid/amount in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found.
     *  2- Json message contains "Product not found".
     *  3- User basket remains empty */
    @Test
    @DisplayName("Invalid product")
    public void testAddProductUnknownPid() {
        // Build and execute request
        Response response= target("/add/1")
                .queryParam("pid", 666)
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(404 , response.getStatus(), "Wrong http code");
        String body = response.readEntity(String.class);
        assertTrue(body.contains("Product not found"));
        assertTrue(engine.getCustomer(1).get().getPendingOrders().isEmpty(),"Customer basket should be empty");
    }

    /** Test if the request is properly rejected and no order is added by the api call on /add with an empty pid and
     *  valid uid/amount in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found
     *  2- Customer basket remains empty */
    @Test
    @DisplayName("Empty pid")
    public void testAddProductEmptyPid() {
        // Build and execute request
        Response response= target("/add/1")
                .queryParam("pid", "")
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(400, response.getStatus(), "Wrong http code");
        assertTrue(engine.getCustomer(1).get().getPendingOrders().isEmpty(),"Customer basket should be empty");
    }

    /** Test if the request is properly rejected and no order is added by the api call on /add with a wrong pid format
     *  and valid uid/amount in params.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 400 / http_bad_request
     *  2- Customer basket remains empty */
    @Test
    @DisplayName("Wrong pid format")
    public void testAddProductLettersPid() {
        // Build and execute request
        Response response= target("/add/1")
                .queryParam("pid", "abcd")
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(400 , response.getStatus(), "Wrong http code");
        assertTrue(engine.getCustomer(1).get().getPendingOrders().isEmpty(),"Customer basket should be empty");
    }

    //######
    // MISC
    //######
    /** Test if the request is properly rejected by the api call on /add with valid uid/pid/amount params and added
     *  path param.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found
     *  2- Customer basket remains empty */
    @Test
    @DisplayName("Added path param")
    public void testAddProductAddedPathParam() {
        // Build and execute request
        Response response= target("/add/1/wtf")
                .queryParam("pid", "3141592")
                .queryParam("amount", 6)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.text(""));
        assertEquals(404 , response.getStatus(), "Wrong http code");
        assertTrue(engine.getCustomer(1).get().getPendingOrders().isEmpty(),"Customer basket should be empty");
    }
}
