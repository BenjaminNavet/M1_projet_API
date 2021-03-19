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

    /** Get the product type
     * @return product type*/
    String getType();

    /** Get product price
     * @return product price */
    double getPrice();

    /** Put the designated amount into the stock
     * @param amount value put into the stock
     * @return true if operation success, false if not*/
    boolean putInStock(int amount);

    /** Get the product type
     * @return product type*/
    String getType();

    /** Get product price
     * @return product price */
    double getPrice();

    /** Get product available stock. -1 value means unlimited stock.
     * @return current product stock*/
    int    getStock();
}
