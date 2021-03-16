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

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public String getShortName() {
		return shortName;
	}

	@Override
	public double getCurrentPrice() {
		return currentPrice;
	}

}
