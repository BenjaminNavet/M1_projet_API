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

public class RemoveTest extends JerseyTest {

    private Engine engine;
    @Override
    protected Application configure() {
        return new ResourceConfig(Remove.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        EnginePhonyImpl.currentEngine = new EnginePhonyImpl();
        engine = EnginePhonyImpl.currentEngine;
    }

    @Test
    @DisplayName("test de retrait")
    public void testRetrait(){
        Product propro = engine.getProduct(3141592).get();
        engine.getCustomer(1).get().addProduct(propro,6);

        Response response1= target("/rem/1")
                .queryParam("pid", 3141592)
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertEquals(200, response1.getStatus());

        Optional<Customer> cust = engine.getCustomer(1);
        assertTrue(cust.isPresent(),"Customer should exist, invalid test data");
        Collection<Order> orderList = cust.get().getPendingOrders();
        assertEquals(0,orderList.size(),"User order size is invalid");
    }
}
