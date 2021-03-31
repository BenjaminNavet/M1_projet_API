package fr.istic.goodenough.ccn.api.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;


import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
// JUnit 4 Test helper
import org.junit.Test;
// JUnit 5 Assertions (!!!)
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class AddTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(Add.class);
    }

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
        assertEquals(200, response.getStatus()); // Assert HTTP_OK
        // TODO : Vérifier que l'user 1 a bien 6 item 3141592 dans son panier (et rien d'autre)
    }
}
