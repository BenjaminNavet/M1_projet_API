package fr.istic.goodenough.ccn.api.engine;

public interface Product {
	/** Get product id
	 * @return pid */
	int    getPid();

	/** Get product full name
	 * @return product full name */
	String getFullName();

	/** Get product short name (Abbreviated designation)
	 * @return product short name */
	String getShortName();

	/** Get product price
	 * @return product price */
	double getPrice();

	/** Get product available stock. -1 value means unlimited stock.
	 * @return current product stock*/
	int    getStock();
	
}
