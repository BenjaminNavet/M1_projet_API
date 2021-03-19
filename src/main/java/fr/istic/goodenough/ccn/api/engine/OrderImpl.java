package fr.istic.goodenough.ccn.api.engine;

public class OrderImpl implements Order{

    // attribute
    private Product product;
    private Customer customer;
    private int amount;

    /**
     * Create the order object
     * @param prod take the product object
     * @param custom take the customer object
     * @param amount take the amount product
     */
    public OrderImpl(Product prod, Customer custom, int amount){
        this.product = prod;
        this.customer = custom;
        this.amount = amount;
    }


    /** Get customer related to this order
     * @return related customer object */
    @Override
    public Customer getCustomer() {
        return customer;
    }

    /** Get product related to this order
     * @return related product object */
    @Override
    public Product getProduct() {
        return product;
    }

    /** Get the amount of product this order contains
     * @return amount of product */
    @Override
    public int getAmount() {
        return amount;
    }

    /** Define the amount of product this order must contains.
     * If requested amount is superior to previous amount the method will try to take the missing quantity from the stock
     * and update the amount into the stock, if desired quantity is not present in product stock no changes are made and false is returned.
     * If requested amount is inferior to the previous amount the method will put the difference
     * back in the product stock.
     * @param amount total quantity of product in this order
     * @return true if product amount in order was successfully modified, false if not. */
    @Override
    public boolean setAmount(int amount) {
        if (this.product.getStock()==-1){
            this.amount = amount;
            return true;
        }
        else {
            if (this.product.getStock() < amount) {
                return false;
            } else {
                if (this.amount < amount) {
                    this.product.takeFromStock(Math.abs(this.amount - amount));
                    this.amount = amount;
                    return true;
                } else if (this.amount > amount) {
                    this.product.putIn(Mamount - this.amount);
                    this.amount = amount;
                    return true;
                }
                else { // If the user use this method with the amount as the original one.
                    return true;
                }
            }
        }
    }

    /** Get the total price for this order, product price multiplied by product amount
     * @return total price of the order */
    @Override
    public double getOrderPrice() {
        return this.product.getPrice()*amount;
    }

    /** Cancel this order by putting the amount of product in the order back in the product stock
     * @return true if cancel is success, false if not */
    @Override
    public boolean cancel() {
       return this.getProduct().putInStock(this.getAmount());
    }
}
