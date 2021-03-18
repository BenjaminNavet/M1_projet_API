package fr.istic.goodenough.ccn.api.engine;

import java.util.Collection;
import java.util.Optional;

public interface Engine {
	/** Get the customer object associated with given name and password.
	 * @param name customer name
	 * @param passwd customer password
	 * @return Optional object that may or may not contain a customer object*/
	Optional<Customer> getCustomerByCredentials(String name, String passwd);

	/** Get a collection of all products
	 * @return Collection of all products */
	Collection<Product> getAllProducts();

	/** Get a collection of all customers
	 * @return Collection of all customers */
	Collection<Customer> getAllCustomers();
	
}
