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

    /** TODO FAIRE LA DOC ! */
    public EnginePhonyImpl() {

        addPhonyCustomer("Emma", 10.0);
        addPhonyCustomer("Obelix", 7.0);

        addPhonyProduct("Hotdog", "HD", 2.0);
        addPhonyProduct("Ball bearings pizza", "BBPIZ", 3.5);

    }

    /** TODO FAIRE LA DOC ! */
    private void addPhonyProduct(String longName, String shortName,
                                 double price) {
        products.put(shortName, new ProductImpl(longName, shortName, price));

    }

    /** TODO FAIRE LA DOC ! */
    private void addPhonyCustomer(String name, double credit) {
        customers.put(name, new CustomerImpl(name));
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
