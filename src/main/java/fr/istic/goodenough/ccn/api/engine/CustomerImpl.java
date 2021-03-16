package fr.istic.goodenough.ccn.api.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * This _NOT_ a DTO.
 * 
 * @author plouzeau
 *
 */
public class CustomerImpl implements Customer {

	private String name;
	double credit;
	private Collection<Order> pendingOrders;

	public CustomerImpl(String name, double credit) {
		this.name = name;
		this.credit = credit;
		pendingOrders = new LinkedList<>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<Order> getPendingOrders() {

		return Collections.unmodifiableCollection(pendingOrders);
	}

	@Override
	public double getCredit() {
		return credit;
	}

	
}
