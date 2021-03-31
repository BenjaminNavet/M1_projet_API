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
import org.junit.jupiter.api.DisplayName;

public class LoginTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(Login.class);
    }

    /** Test if the customer uid returned by the api on /login path with a valid name and password is the same as expected
     *  Init :
     *  1- Build and execute request.
     *  2- Build customerDTO object from JSON.
     *  Expected :
     *  1- Http response code is 200 / http_ok.
     *  2- CustomerDTO uid is equal to the value associated with the given credentials in the test data */
    @Test
    @DisplayName("User credentials are valid and user uid is returned")
    public void testGetUserOk(){
        // Build and execute request
        Response response= target("/login")
                .queryParam("name","nemo")
                .queryParam("passwd","98765")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        // Assert HTTP_OK
        assertEquals(200, response.getStatus());
        // Build DTO
        CustomerDTO customer = response.readEntity(new GenericType<CustomerDTO>(){});
        // Check customer id
        assertEquals("3",customer.uid);
    }
}
