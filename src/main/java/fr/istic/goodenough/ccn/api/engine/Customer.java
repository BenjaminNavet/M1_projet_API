package fr.istic.goodenough.ccn.api.engine;

import java.util.Collection;

public interface Customer {

	/** Get the Customer id
	 * @return customer uid */
	int getUid();

	/** Get the Customer name
	 * @return customer name */
	String getName();

	/** Get the Customer password
	 * @return customer password */
	String getPasswd();

	/** Get a collection of all order objects related to this customer (his basket)
	 * @return customer basket in a collection */
	Collection<Order> getPendingOrders();

	/** Add a product in the customer basket by creating a new order object with the desired product and amount
	 * and add it to the order collection of this user.
	 * If a order object is already present in the user basket this method will try to change the amount in the order
	 * object to match the requested amount. If desired amount is impossible to match for any reason no modification
	 * will be done dans method will return false.
	 * @param product Product object to add to the basket
	 * @param amount Amount of product to add to the basket
	 * @return true if order was correctly created or modified, false otherwise */
	boolean addProduct(Product product, int amount);

	/** "Validate" customer basket and "send" all products to the customer,
	 * destroy all related order objects and empty pending order collection.
	 * @return true if basket is correctly emptied and products sent to customer, false if basket was empty or order can't be done */
	boolean order();

	/** Cancel customer basket, cancel all related order objects.
	 * @return true if cancel of all order is success, false otherwise */
	boolean clear();

	/** Get customer credit
	 * @deprecated Not to be used in current version
	 * @return customer credit*/
	double getCredit();
}