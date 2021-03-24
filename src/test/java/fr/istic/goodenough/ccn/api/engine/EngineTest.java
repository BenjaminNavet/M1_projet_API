package fr.istic.goodenough.ccn.api.engine;

import fr.istic.goodenough.ccn.api.data.PhonyData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {
    
    private final Engine engine = EnginePhonyImpl.currentEngine;
    
    private final Collection<Customer> customers = PhonyData.generatePhonyCustomers().values();
    private final Collection<Product> products = PhonyData.generatePhonyProducts().values();

    @BeforeEach
    void setUp() {
        
    }

    /** Try to get all customers by uid from engine
     * Assert that they have the right uid */
    @Test
    void testGetCustomer() {
        for (Customer c : customers) {
            Optional<Customer> customer = engine.getCustomer(c.getUid());
            assertTrue(customer.isPresent());
            assertEquals(customer.get().getUid(), c.getUid());
        }
    }

    /** Try to get all customers by name and passwd from engine
     * Assert that they have the right name and the right passwd */
    @Test
    void testGetCustomerByCredentials() {
        for (Customer c : customers) {
            Optional<Customer> customer = engine.getCustomerByCredentials(c.getName(), c.getPasswd());
            assertTrue(customer.isPresent());
            assertEquals(customer.get().getName(),c.getName());
            assertEquals(customer.get().getPasswd(), c.getPasswd());
        }
    }

    /** Try to get all products by pid from engine
     * Asserts that they have the right pid */
    @Test
    void testGetProduct() {
        for (Product p : products) {
            Optional<Product> product = engine.getProduct(p.getPid());
            assertTrue(product.isPresent());
            assertEquals(product.get().getPid(),p.getPid());
        }
    }

    /** Try to get all products from engine
     * Asserts that all the data samples are provided by testing pids */
    @Test
    void testGetAllProducts() {
        Collection<Product> engineProducts = engine.getAllProducts();
        for (Product reference : products) {
            boolean foundInEngine = false;
            for (Product p : engineProducts) {
                if (p.getPid() == reference.getPid()) {
                    foundInEngine = true;
                }
            }
            assertTrue(foundInEngine);
        }
    }

    /** Try to get all customers from engine
     * Asserts that all the data samples are provided by testing uids */
    @Test
    void testGetAllCustomers() {
        Collection<Customer> engineCustomers = engine.getAllCustomers();
        for (Customer reference : customers) {
            boolean foundInEngine = false;
            for (Customer c : engineCustomers) {
                if (c.getUid() == c.getUid()) {
                    foundInEngine = true;
                }
            }
            assertTrue(foundInEngine);
        }
    }
}