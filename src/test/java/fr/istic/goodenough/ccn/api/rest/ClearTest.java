package fr.istic.goodenough.ccn.api.rest;

import fr.istic.goodenough.ccn.api.engine.*;
import fr.istic.goodenough.ccn.api.engine.ProductImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
// JUnit 4 Test helper
import org.junit.Before;
import org.junit.Test;
// JUnit 5 Assertions (!!!)
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;
import fr.istic.goodenough.ccn.api.data.PhonyData;
import fr.istic.goodenough.ccn.api.engine.Product;
import org.junit.jupiter.api.DisplayName;

public class ClearTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Clear.class);
    }

    private Engine engine;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        EnginePhonyImpl.currentEngine = new EnginePhonyImpl();
        engine = EnginePhonyImpl.currentEngine;
    }

    /** Test if the customer basket is correctly emptied and products put back in stock by the api call on /clear
     *  with a valid uid as param.
     *  Init :
     *  1- Add a product in the customer basket directly into the engine
     *  2- Build and execute request.
     *  Expected :
     *  1- Http response code is 200 / http_ok.
     *  2- Customer basket is empty
     *  3- Customer products are back in stock */
    @Test
    @DisplayName("Customer basket is cleared and products are back in stock")
    public void testClearOk(){
        Customer customer = engine.getCustomer(1).get();
        Product product1 = engine.getProduct(45).get();
        Product product2 = engine.getProduct(46).get();
        customer.addProduct(product1,3);
        customer.addProduct(product2,2);

        //Products stock should be decremented by customer's order quantity
        assertEquals(5,product1.getStock());
        assertEquals(3,product2.getStock());

        // Build and execute request
        Response response= target("/clear/1")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // Http response code is 200 / http_ok
        assertEquals(200, response.getStatus());

        // Customer basket is empty
        engine.getCustomer(1).get().getPendingOrders().forEach(product -> {
            assertEquals(0, product.getProduct().getStock());
        });

        // Customer products are back in stock
        assertEquals(8,product1.getStock());
        assertEquals(5,product2.getStock());
    }


    /** Test if the customer basket isn't correctly emptied with an invalid uid as param.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 400 / bad_request.*/
    @Test
    @DisplayName("Customer basket is not cleared with invalid uid")
    public void testClearInvalidUid(){

        // Build and execute request
        Response response= target("/clear/wrong")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // Http response code is 400 / bad_request
        assertEquals(400, response.getStatus());

    }

    /** Test if the customer basket isn't correctly emptied with wrong uid as param.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found.*/
    @Test
    @DisplayName("Customer basket is not cleared with wrong uid")
    public void testClearWrongUid(){

        // Build and execute request
        Response response= target("/clear/100000")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // Http response code is 404 / http_not_found
        assertEquals(404, response.getStatus());

    }

    /** Test if the customer basket isn't correctly emptied with an empty as param.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found */
    @Test
    @DisplayName("Customer basket is not cleared with empty uid")
    public void testClearEmptyUid(){

        // Build and execute request
        Response response= target("/clear/")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // Http response code is 404 / http_not_found
        assertEquals(404, response.getStatus());

    }

    /** Test if the customer basket isn't emptied when sending an
     *  additional parameter in URL BEFORE the customer uid.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found.*/
    @Test
    @DisplayName("Customer basket is not cleared with added param before uid")
    public void testClearParamSuppBefore(){

        // Build and execute request
        Response response= target("/clear/supplParam/1")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // Http response code is 404 / http_not_found
        assertEquals(404, response.getStatus());

    }

    /** Test if the customer basket isn't correctly emptied with a supp param after uid.
     *  Init :
     *  1- Build and execute request.
     *  Expected :
     *  1- Http response code is 404 / http_not_found.*/
    @Test
    @DisplayName("Customer basket is not cleared with a supp param after uid")
    public void testClearParamSuppAfter(){

        // Build and execute request
        Response response= target("clear/1/paramsupp/")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // Http response code is 404 / http_not_found
        assertEquals(404, response.getStatus());

    }


    /** Test if the customer basket is correctly emptied and products put back in stock by the api call on /clear
     *  with a valid uid as param and added attribute.
     *  Init :
     *  1- Add a product in the customer basket directly into the engine
     *  2- Build and execute request.
     *  Expected :
     *  1- Http response code is 200 / http_ok.
     *  2- Customer basket is empty
     *  3- Customer products are back in stock */
    @Test
    @DisplayName("Customer basket is cleared and products are back in stock")
    public void testClearOkAttributeAdded(){
        Customer customer = engine.getCustomer(1).get();
        Product product1 = engine.getProduct(45).get();
        Product product2 = engine.getProduct(46).get();
        customer.addProduct(product1,3);
        customer.addProduct(product2,2);

        //Products stock should be decremented by customer's order quantity
        assertEquals(5,product1.getStock());
        assertEquals(3,product2.getStock());

        // Build and execute request
        Response response= target("/clear/1")
                .queryParam("add","mockette")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // Http response code is 200 / http ok
        assertEquals(200, response.getStatus());

        // Customer basket is empty
        engine.getCustomer(1).get().getPendingOrders().forEach(product -> {
            assertEquals(0, product.getProduct().getStock());
        });

        // Customer products are back in stock
        assertEquals(8,product1.getStock());
        assertEquals(5,product2.getStock());
    }

}
