package fr.istic.goodenough.ccn.api.engine;

import java.util.Collection;

public interface Customer {

	public String getName();
	
	public Collection<Order> getPendingOrders();
	
	public double getCredit();
	
}
