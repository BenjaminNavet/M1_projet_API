package fr.istic.goodenough.ccn.api.engine;

public class OrderImpl implements Order{
    /** Get customer related to this order
     * @return related customer object */
    @Override
    public Customer getCustomer() {
        return null;
    }

    /** Get product related to this order
     * @return related product object */
    @Override
    public Product getProduct() {
        return null;
    }

    /** Get the amount of product this order contains
     * @return amount of product */
    @Override
    public int getAmount() {
        return 0;
    }

    /** Define the amount of product this order must contains.
     * If requested amount is superior to previous amount the method will try to take the missing quantity from
     * the stock, if desired quantity is not present in product stock no changes are made and false is returned.
     * If requested amount is inferior to the previous amount the method will put the difference
     * back in the product stock.
     * @param amount total quantity of product in this order
     * @return true if product amount in order was successfully modified, false if not. */
    @Override
    public boolean setAmount(int amount) {
        return false;
    }

    /** Get the total price for this order, product price multiplied by product amount
     * @return total price of the order */
    @Override
    public double getOrderPrice() {
        return 0;
    }

    /** Cancel this order by putting the amount of product in the order back in the product stock
     * @return true if cancel is success, false if not */
    @Override
    public boolean cancel() {
        return false;
    }
}
