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

public class ProductsTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Products.class);
    }

    /** Test if the product list returned by the api call on /products path with a valid uid as param
     *  is the same as the reference CSV data.
     *  Init :
     *  1- Build and execute request.
     *  2- Build DTO list from JSON.
     *  3- Build reference product list from CSV.
     *  Expected :
     *  1- Http response code is 200 / http_ok.
     *  2- DTO list and reference list contains the same number of items
     *  3- All product ID in DTO list are present in reference list
     *  4- All product data in DTO are equals to reference data */
    @Test
    @DisplayName("Product list is returned and equals to reference data")
    public void testGetProductsOk() {
        // Build and execute request
        Response response= target("/products")
                .queryParam("uid", 1)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        assertEquals(200, response.getStatus()); // Assert HTTP_OK
        // Build DTO list
        List<ProductDTO> products = response.readEntity(new GenericType<List<ProductDTO>>(){});
        // Build product list from CSV
        Map<String, Product> productData = PhonyData.generatePhonyProducts();
        // Check that DTO list has the number of items as the reference list
        assertEquals(productData.size(),products.size());
        for (ProductDTO product : products) {
            assertTrue(productData.containsKey(product.pid)); // Check that DTO product exist in reference data
            Product refProduct = productData.get(product.pid);
            // Check DTO data is the same as reference data
            assertEquals(product.name,refProduct.getFullName());
            assertEquals(product.type,refProduct.getType());
            assertEquals(product.price,refProduct.getPrice());
        }
    }

    @Test
    @DisplayName("Add attributes to product list")
    public void testGetProductOkWithAttribute() {
        // Build and execute request
        Response response= target("/products")
                .queryParam("uid", 1)
                .queryParam("add","abdc")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        assertEquals(200, response.getStatus()); // Assert HTTP_OK
        // Build DTO list
        List<ProductDTO> products = response.readEntity(new GenericType<List<ProductDTO>>(){});
        // Build product list from CSV
        Map<String, Product> productData = PhonyData.generatePhonyProducts();
        // Check that DTO list has the number of items as the reference list
        assertEquals(productData.size(),products.size());
        for (ProductDTO product : products) {
            assertTrue(productData.containsKey(product.pid)); // Check that DTO product exist in reference data
            Product refProduct = productData.get(product.pid);
            // Check DTO data is the same as reference data
            assertEquals(product.name,refProduct.getFullName());
            assertEquals(product.type,refProduct.getType());
            assertEquals(product.price,refProduct.getPrice());
        }
    }

    @Test
    @DisplayName("Product uid is wrong")
    public void testGetProductWrong() {
        Response response= target("/products")
                .queryParam("uid", 6)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        assertEquals(401, response.getStatus()); // HTTP_ERROR_UNAUTHORIZED
    }

    @Test
    @DisplayName("Product uid is empty")
    public void testGetProductsUidEmpty() {
        Response response= target("/products")
                .queryParam("uid", "")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        assertEquals(400, response.getStatus()); // Assert HTTP_ERROR_UNAUTHORIZED
        }

    @Test
    @DisplayName("Wrong uid")
    public void testGetProductsWrongUid() {
        Response response= target("/products")
                .queryParam("uid", "548641658")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);
        assertEquals(401, response.getStatus()); // Assert HTTP_ERROR_UNAUTHORIZED
    }
}

