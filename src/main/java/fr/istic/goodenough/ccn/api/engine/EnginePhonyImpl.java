package fr.istic.goodenough.ccn.api.engine;

import fr.istic.goodenough.ccn.api.data.PhonyData;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnginePhonyImpl implements Engine {

    public static Engine currentEngine = new EnginePhonyImpl();

    private final Map<String, Customer> customers;
    private final Map<String, Product>  products;

    /** Examples for testing values */
    public EnginePhonyImpl() {
        customers = PhonyData.generatePhonyCustomers();
        products  = PhonyData.generatePhonyProducts();
    }

    /** Get the customer object associated with given uid.
     * @param uid customer id
     * @return Optional object that may or may not contain a customer object */
    @Override
    public Optional<Customer> getCustomer(int uid) {
        return Optional.ofNullable(customers.get(Integer.toString(uid)));
    }

    /** Get the customer object associated with given name and password.
     * @param name customer name
     * @param passwd customer password
     * @return Optional object that may or may not contain a customer object */
    @Override
    public Optional<Customer> getCustomerByCredentials(String name, String passwd) {
        for (Customer c : customers.values()){
            if(c.getName().equalsIgnoreCase(name)){
                if (c.getPasswd().equals(passwd)){
                    return Optional.of(c);
                }
            }
        }
        return Optional.empty();
    }

    /** Get the product object associated with given pid.
     * @param pid product id
     * @return Optional object that may or may not contain a product object */
    @Override
    public Optional<Product> getProduct(int pid) {
        return Optional.ofNullable(products.get(Integer.toString(pid)));
    }

    /** Get a collection of all products
     * @return Collection of all products */
    @Override
    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    /** Get a collection of all customers
     * @return Collection of all customers */
    @Override
    public Collection<Customer> getAllCustomers() {
        return Collections.unmodifiableCollection(customers.values());
    }
}
