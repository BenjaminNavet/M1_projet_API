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

	public EnginePhonyImpl() {

		addPhonyCustomer("Emma", 10.0);
		addPhonyCustomer("Obelix", 7.0);

		addPhonyProduct("Hotdog", "HD", 2.0);
		addPhonyProduct("Ball bearings pizza", "BBPIZ", 3.5);

	}

	private void addPhonyProduct(String longName, String shortName,
			double price) {
		products.put(shortName, new ProductImpl(longName, shortName, price));

	}

	private void addPhonyCustomer(String name, double credit) {
		customers.put(name, new CustomerImpl(name, credit));
	}

	@Override
	public Optional<Customer> findCustomerByName(String nameToLookFor) {
		Customer result = customers.get(nameToLookFor);
		if (result != null) {
			return Optional.of(result);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Collection<Product> getAllProducts() {
		return Collections.unmodifiableCollection(products.values());
	}

	@Override
	public Collection<Customer> getAllCustomers() {
		return Collections.unmodifiableCollection(customers.values());
	}

}
