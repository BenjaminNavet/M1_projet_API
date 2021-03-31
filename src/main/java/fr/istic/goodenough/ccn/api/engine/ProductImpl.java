package fr.istic.goodenough.ccn.api.engine;

public class ProductImpl implements Product {

    private final int pid;
    private final String fullName;
    private final String shortName;
    private final double currentPrice;
    private int stock;
    private final String type;

    /** Create product
     * @param pid product id
     * @param fullName product full name
     * @param shortName product shortname (Abbreviated designation)
     * @param price product price
     * @param stock product stock
     * @param type product type */
    public ProductImpl(int pid, String fullName, String shortName, double price, int stock, String type) {
        this.pid = pid;
        this.fullName = fullName;
        this.shortName = shortName;
        this.currentPrice = price;
        this.stock = stock;
        this.type = type;
    }

    /** Get product id
     * @return pid */
    @Override
    public int getPid() {
        return pid;
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
     * @return current product stock */
    @Override
    public int getStock() {
        return stock;
    }


    /** Get the product type
     * @return product type*/
    @Override
    public String getType(){
        return this.type;
    }

    /** Remove the designated amount from the stock
     * @param amount value take from the stock
     * @return true if operation success, false if not */
    @Override
    public boolean takeFromStock(int amount) {
        if (amount < 0)       { return false; }
        if (this.stock == -1) { return true;  }
        if (amount > stock)   { return false; }
        else {
            this.stock -= amount;
            return true;
        }
    }

    /** Put the designated amount into the stock
     * @param amount value put into the stock
     * @return true if operation success, false if not */
    @Override
    public boolean putInStock(int amount) {
        if (amount < 0)       { return false; }
        if (this.stock == -1) { return true; }
        this.stock += amount;
        return true;
    }
}
