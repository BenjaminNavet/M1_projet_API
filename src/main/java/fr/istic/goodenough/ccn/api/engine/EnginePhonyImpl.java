package fr.istic.goodenough.ccn.api.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnginePhonyImpl implements Engine {

    public static Engine currentEngine = new EnginePhonyImpl();

    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Product> products = new HashMap<>();

    /**
     * Examples for testing values
     */
    public EnginePhonyImpl() {

        addPhonyCustomer("Emma", "1234", 1, 10.0);
        addPhonyCustomer("Obelix", "0000", 2, 7.0);

        addPhonyProduct(1, "Hotdog", "HD", 2.0, -1, "sandwich");
        addPhonyProduct(2, "Ball bearings pizza", "BBPIZ", 3.5, -1, "pizza");

    }

    /** Add the Product into the set products
     * @param pid product id
     * @param longName product long name
     * @param shortName product short name
     * @param price product price
     * @param stock product stock
     * @param type product type
     */
    private void addPhonyProduct(int pid,String longName, String shortName, double price, int stock, String type){
        products.put(shortName, new ProductImpl(pid, longName, shortName, price, stock, type));
    }

    /** Add the Costumer into the set customers
     * @param name customer name
     * @param password customer password
     * @param uid customer id
     * @param credit customer credit
     */
    private void addPhonyCustomer(String name, String password, int uid, double credit) {
        customers.put(name, new CustomerImpl(name, password, uid));
    }

    /** Get the customer object associated with given uid.
     * @param uid customer id
     * @return Optional object that may or may not contain a customer object */
    @Override
    public Optional<Customer> getCustomer(int uid) {
        return Optional.empty();
    }

    /** Get the customer object associated with given name and password.
     * @param name customer name
     * @param passwd customer password
     * @return Optional object that may or may not contain a customer object*/
    @Override
    public Optional<Customer> getCustomerByCredentials(String name, String passwd) {
        Customer result = customers.get(name);
        if (result != null) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    /** Get the product object associated with given pid.
     * @param pid product id
     * @return Optional object that may or may not contain a product object */
    @Override
    public Optional<Product> getProduct(int pid) {
        return Optional.empty();
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
