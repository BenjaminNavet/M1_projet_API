package fr.istic.goodenough.ccn.api.engine;

public class ProductImpl implements Product {

	private String fullName;
	private String shortName;
	private double currentPrice;

	public ProductImpl(String fullName, String shortName, double price) {
		this.fullName = fullName;
		this.shortName = shortName;
		this.currentPrice = price;
	}

	/** Get product id
	 * @return pid */
	@Override
	public int getPid() {
		return 0;
	}

	/** Get product full name
	 * @return product full name */
	@Override
	public String getFullName() {
		return fullName;
	}

	/** Get product short name (Abbreviated designation)
	 * @return product short name */
	@Override
	public String getShortName() {
		return shortName;
	}

	/** Get product price
	 * @return product price */
	@Override
	public double getPrice() {
		return currentPrice;
	}

	/** Get product available stock. -1 value means unlimited stock.
	 * @return current product stock*/
	@Override
	public int getStock() {
		return 0;
	}
}
