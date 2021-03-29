package fr.istic.goodenough.ccn.api.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
// JUnit 4 Test helper
import org.junit.Test;
// JUnit 5 Assertions (!!!)
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class Login extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Products.class);
    }

    @Test
    public void test() {
        Response response = target("/products").queryParam("uid", 1).request().get(Response.class);
        assertEquals(200,response.getStatus());
        System.out.println("réponse : " + response.readEntity(String.class));
    }
}