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

    /**
     *Test if the customer uid returned by the api on /login path with a valid name and password is the same as expected
     * even if there is a usless attribut
     * *  Init :
     *      *  1- Build and execute request.
     *      *  2- Build customerDTO object from JSON.
     *      *  Expected :
     *      *  1- Http response code is 200 / http_ok.
     *      *  2- CustomerDTO uid is equal to the value associated with the given credentials in the test data */
    @Test
    @DisplayName("With more attributes than necessary user uid is returned")
    public void testGetUserOkWithAttribute(){
        // Build and execute request
        Response response= target("/login")
                .queryParam("name","nemo")
                .queryParam("passwd","98765")
                .queryParam("sup", "acde")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        // Assert HTTP_OK
        assertEquals(200, response.getStatus());
        // Build DTO
        CustomerDTO customer = response.readEntity(new GenericType<CustomerDTO>(){});
        // Check customer id
        assertEquals("3",customer.uid);
    }

    @Test
    @DisplayName("Wrong password")
    public void testWrongPassword(){
        // Build and execute request
        Response response= target("/login")
                .queryParam("name","nemo")
                .queryParam("passwd","login")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        // Assert HTTP_ERROR
        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("password empty")
    public void testEmptyPassword(){
        // Build and execute request
        Response response= target("/login")
                .queryParam("name","nemo")
                .queryParam("passwd","")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        // Assert HTTP_Notfound
        assertEquals(404, response.getStatus());
        // Build DTO
        CustomerDTO customer = response.readEntity(new GenericType<CustomerDTO>(){});
        // Check customer id
        assertEquals(null,customer.uid);
    }

    @Test
    @DisplayName("Login empty")
    public void testEmptyLogin(){
        // Build and execute request
        Response response= target("/login")
                .queryParam("name","")
                .queryParam("passwd","ab")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        // Assert HTTP_OK
        assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Login wrong")
    public void testWrongLogin(){
        // Build and execute request
        Response response= target("/login")
                .queryParam("name","")
                .queryParam("passwd","abcd")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        // Assert HTTP_OK
        assertEquals(404, response.getStatus());
    }

}
