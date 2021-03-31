package fr.istic.goodenough.ccn.api.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
// JUnit 4 Test helper
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

    /** Test if the customer basket is correctly emptied by the api call on /clear with a valid uid as param.
     *  Init :
     *  1- Add a product in the customer basket directly into the engine
     *  2- Build and execute request.
     *  Expected :
     *  1- Http response code is 200 / http_ok.
     *  2- Customer basket is empty */
    @Test
    @DisplayName("Customer basket is emptied")
    public void testClearOk(){
        // TODO : Ajouter un truc au panier sans passer par l'api avant
        // Build and execute request
        Response response= target("/clear/1")
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);
        assertEquals(200, response.getStatus()); // Assert HTTP_OK
        // TODO : Vérifier que le panier est bien vide
    }
}
