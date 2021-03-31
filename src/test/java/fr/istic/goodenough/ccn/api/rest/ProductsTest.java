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



public class ProductsTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Products.class);
    }

    @Test
    public void test() {
        Response response= target("/products")
                .queryParam("uid", 1)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        assertEquals(200, response.getStatus());
        List<ProductDTO> products = response.readEntity(new GenericType<List<ProductDTO>>(){});
        for (ProductDTO p : products) {
            System.out.println(p.pid+" "+p.name+" ("+p.type+") costs "+p.price);
        }
    }
}