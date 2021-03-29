package fr.istic.goodenough.ccn.api.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
// JUnit 4 Test helper
import org.junit.Test;
// JUnit 5 Assertions (!!!)
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Login extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Products.class);
    }

    @Test
    public void test() {
        List<ProductDTO> products = target("/products")
                .queryParam("uid", 1)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ProductDTO>>(){});
        for (ProductDTO p : products) {
            System.out.println(p.pid+" "+p.name+" ("+p.type+") costs "+p.price);
        }
    }
}