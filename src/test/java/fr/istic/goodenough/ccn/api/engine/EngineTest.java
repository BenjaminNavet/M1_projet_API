package fr.istic.goodenough.ccn.api.engine;

import fr.istic.goodenough.ccn.api.data.PhonyData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.swing.text.html.Option;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {
    
    private Engine engine;
    private Map<String,Customer> customers;
    private Map<String, Product> products;

    @BeforeEach
    void setUp() {
        engine    = EnginePhonyImpl.currentEngine;
        customers = PhonyData.generatePhonyCustomers();
        products  = PhonyData.generatePhonyProducts();
    }

    /** Try to get a customer by uid from engine.
     * Assert that they have the right params */
    @Test
    @Tag("UnitTest")
    @DisplayName("Get valid customer")
    void testGetCustomerValid() {
        Optional<Customer> customer = engine.getCustomer(1);
        assertTrue(customer.isPresent());
        assertEquals(customer.get().getUid(), 1);
        assertEquals(customer.get().getName(),"Emma");
        assertEquals(customer.get().getPasswd(), "1234");
    }

    /** Try to get a customer by uid from engine.
     * Assert that they have the right params */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Get invalid customer")
    void testGetCustomerInvalid() {
        Optional<Customer> customer = engine.getCustomer(9999);
        assertFalse(customer.isPresent());
    }

    /** Try to get a customer by name and passwd from engine.
     * Assert that the returned object has the right name and the right passwd */
    @ParameterizedTest
    @Tag("UnitTest")
    @DisplayName("Get customer by valid credential")
    @CsvSource({"1,Emma,1234", "1,emma,1234", "4,Jean-Pièrre,FuckTh1sSh1t!"})
    void testGetCustomerByCredentialsValid(int uid, String name, String passwd) {
        Optional<Customer> customer = engine.getCustomerByCredentials(name, passwd);
        assertTrue(customer.isPresent());
        assertEquals(customer.get().getUid(),uid);
        assertTrue(customer.get().getName().equalsIgnoreCase(name));
        assertEquals(customer.get().getPasswd(), passwd);
    }

    /** Try to get a false customer by name and passwd from engine.
     * Assert that an empty optional object is returned */
    @ParameterizedTest
    @Tag("RobustnessTest")
    @DisplayName("Get customer by invalid credentials")
    @CsvSource({"Gilbert,Delatourette","Emma,pouet","Gilbert,1234","Obélix,0000","Jean-Pièrre,fuckth1ssh1t!"})
    void testGetCustomerByCredentialsInvalid(String name, String passwd) {
        Optional<Customer> customer = engine.getCustomerByCredentials(name, passwd);
        assertFalse(customer.isPresent());
    }

    /** Try to get a product by pid from engine.
     * Asserts that returned object have the right values */
    @Test
    @Tag("UnitTest")
    @DisplayName("Get valid product")
    void testGetProductValid() {
        Optional<Product> product = engine.getProduct(1);
        assertTrue(product.isPresent());
        assertEquals(product.get().getPid(),1);
        assertEquals(product.get().getFullName(),"Hotdog");
        assertEquals(product.get().getShortName(),"HD");
        assertEquals(product.get().getType(),"sandwich");
        assertEquals(product.get().getPrice(),2.0);
        assertEquals(product.get().getStock(),-1);
    }

    /** Try to get a product by pid from engine.
     * Asserts that returned object is empty */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Get invalid product")
    void testGetProductInvalid() {
        Optional<Product> product = engine.getProduct(9999);
        assertFalse(product.isPresent());
    }

    /** Try to get all products from engine.
     * Asserts that all the data samples are provided by trying to get them from the reference map */
    @Test
    @Tag("UnitTest")
    @DisplayName("Get all products")
    void testGetAllProducts() {
        Collection<Product> engineProducts = engine.getAllProducts();
        for (Product engineProduct: engineProducts){
            assertNotNull(products.get(Integer.toString(engineProduct.getPid())));
        }
    }

    /** Try to delete the content of the collection returned by getAllProducts().
     * Assert the operation is unsupported and throws an exception */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Clear products collection")
    void testGetAllProductsClear(){
        Collection<Product> engineProducts = engine.getAllProducts();
        assertThrows(UnsupportedOperationException.class, engineProducts::clear);
    }

    /** Try to add a new product to the collection returned by getAllProducts().
     * Assert the operation is unsupported and throws an exception */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Add product in collection")
    void testGetAllProductsAdd(){
        Collection<Product> engineProducts = engine.getAllProducts();
        Product product = new ProductImpl(66,"Un test","IT",66,-1,"test");
        assertThrows(UnsupportedOperationException.class, () -> engineProducts.add(product));
    }

    /** Try to remove a product from the collection returned by getAllProducts().
     * Assert the operation is unsupported and throws an exception */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Remove product in collection")
    void testGetAllProductsRemove(){
        Collection<Product> engineProducts = engine.getAllProducts();
        Product product = engineProducts.iterator().next();
        assertThrows(UnsupportedOperationException.class, () -> engineProducts.remove(product));
    }

    /** Try to get all customers from engine.
     * Asserts that all the data samples are provided by trying to get them from the reference map */
    @Test
    @Tag("UnitTest")
    @DisplayName("Get all customers")
    void testGetAllCustomers() {
        Collection<Customer> engineCustomers = engine.getAllCustomers();
        for (Customer engineCustomer: engineCustomers){
            assertNotNull(customers.get(Integer.toString(engineCustomer.getUid())));
        }
    }

    /** Try to delete the content of the collection returned by getAllCustomers().
     * Assert the operation is unsupported and throws an exception */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Clear customers collection")
    void testGetAllCustomersClear(){
        Collection<Customer> engineCustomers = engine.getAllCustomers();
        assertThrows(UnsupportedOperationException.class, engineCustomers::clear);
    }

    /** Try to add a new customer to the collection returned by getAllCustomers().
     * Assert the operation is unsupported and throws an exception */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Add customer in collection")
    void testGetAllCustomersAdd(){
        Collection<Customer> engineCustomers = engine.getAllCustomers();
        Customer customer = new CustomerImpl("test","testouille",9999);
        assertThrows(UnsupportedOperationException.class, () -> engineCustomers.add(customer));
    }

    /** Try to remove a customer from the collection returned by getAllCustomers().
     * Assert the operation is unsupported and throws an exception */
    @Test
    @Tag("RobustnessTest")
    @DisplayName("Remove customer in collection")
    void testGetAllCustomerRemove(){
        Collection<Customer> engineCustomers = engine.getAllCustomers();
        Customer customer = engineCustomers.iterator().next();
        assertThrows(UnsupportedOperationException.class, () -> engineCustomers.remove(customer));
    }
}