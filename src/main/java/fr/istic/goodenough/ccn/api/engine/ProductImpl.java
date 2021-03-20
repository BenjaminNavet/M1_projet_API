package fr.istic.goodenough.ccn.api.engine;

public class ProductImpl implements Product {

    // TODO : METTRE LES ATTRIBUTS NON MODIFIE EN FINAL !
    private int pid;
    private String fullName;
    private String shortName;
    private double currentPrice;
    private int stock;
    private String type;

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

    // TODO : FAIRE EN SORTE QUE LES DEUX FONCTIONS SUIVANTES GÈRENT LE STOCK INFINI
    /** Remove the designated amount from the stock
     * @param amount value take from the stock
     * @return true if operation success, false if not */
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
     * @return true if operation success, false if not */
    @Override
    public boolean putInStock(int amount) {
        this.stock += amount;
        return true;
    }
}
