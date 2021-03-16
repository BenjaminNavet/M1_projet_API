package fr.istic.goodenough.ccn.api.engine;

public interface Order {

	public Customer getCustomer();
	public Product getProduct();
	public double getOrderPrice();
	
}
