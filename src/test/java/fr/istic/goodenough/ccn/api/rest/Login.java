package fr.istic.goodenough.ccn.api.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import javax.ws.rs.core.Application;
public class Login extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Products.class);
    }

    @Test
    public void test() {
        String response = target("/products").queryParam("uid", 1).request()
                    .get(String.class);

       System.out.println("réponse : "+response);
    }
}