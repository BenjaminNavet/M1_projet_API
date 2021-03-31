package fr.istic.goodenough.ccn.api.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
// JUnit 4 Test helper
// JUnit 5 Assertions (!!!)
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(Login.class);
    }

    @Test
    @DisplayName("User credentials are valid and user uid is returned")
    public void testGetUserOk(){
        // Build and execute request
        Response response= target("/login")
                .queryParam("name","nemo")
                .queryParam("passwd","98765")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        assertEquals(200, response.getStatus()); // Assert HTTP_OK
    }
}
