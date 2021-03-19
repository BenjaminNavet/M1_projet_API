package fr.istic.goodenough.ccn.api.engine;

public class ProductImpl implements Product {

    private int pid;
    private String fullName;
    private String shortName;
    private double currentPrice;
    private int stock;
    private String type;

    /**
     * Create product
     * @param pid
     * @param fullName
     * @param shortName
     * @param price
     * @param stock
     * @param type
     */
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
     * @return current product stock*/
    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public boolean takeFromStock(int amount) {
        if (amount > stock) {
            return false;
        }
        else {
            this.stock -= amount;
            return true;
        }
    }

    /** Put the designated amount into the stock
     * @param amount value put into the stock
     * @return true if operation success, false if not*/
    @Override
    public boolean putInStock(int amount) {
        return false;
    }

    @Override
    /** Get the product type
     * @return product type*/
     public String getType(){
         return this.type;
     }
}
