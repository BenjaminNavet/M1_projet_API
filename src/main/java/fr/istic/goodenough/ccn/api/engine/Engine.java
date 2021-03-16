package fr.istic.goodenough.ccn.api.engine;

import java.util.Collection;
import java.util.Optional;

public interface Engine {

	public Optional<Customer> findCustomerByName(String nameToLookFor);
	
	public Collection<Product> getAllProducts();
	
	public Collection<Customer> getAllCustomers();
	
}
