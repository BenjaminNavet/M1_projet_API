package fr.istic.goodenough.ccn.api.engine;

import fr.istic.goodenough.ccn.api.data.PhonyData;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnginePhonyImpl implements Engine {

    public static Engine currentEngine = new EnginePhonyImpl();

    // TODO : METTRE LES ATTRIBUTS NON MODIFIE EN FINAL !
    private Map<String, Customer> customers; // TODO : UTILISER L'UID POUR LA CLE DE LA MAP, AUCUNE GARANTIE QUE LE NOM SOIT UNIQUE
    private Map<String, Product>  products; // TODO : UTILISER LE PID POUR LA CLE DE LA MAP, AUCUNE GARANTIE QUE LE SHORTNAME SOIT UNIQUE

    /** Examples for testing values */
    public EnginePhonyImpl() {
        customers = PhonyData.generatePhonyCustomers();
        products  = PhonyData.generatePhonyProducts();
    }

    /** Get the customer object associated with given uid.
     * @param uid customer id
     * @return Optional object that may or may not contain a customer object */
    // TODO : ÉCRIRE LA FONCTION ^^
    @Override
    public Optional<Customer> getCustomer(int uid) {
        return Optional.empty();
    }

    /** Get the customer object associated with given name and password.
     * @param name customer name
     * @param passwd customer password
     * @return Optional object that may or may not contain a customer object */
    @Override
    // TODO : OPTIMISER LA FONCTION EN UTILISANT Optional.ofNullable()
    // TODO : VERIFIER QUE LE PASSWORD DU CUSTOMER MATCH AVANT DE LE RENVOYER !!!
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
    // TODO : ÉCRIRE LA FONCTION ^^
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
