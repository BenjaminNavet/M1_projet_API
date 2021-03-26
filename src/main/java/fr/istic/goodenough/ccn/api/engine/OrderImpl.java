package fr.istic.goodenough.ccn.api.engine;

public class OrderImpl implements Order{

    private final Product product;
    private final Customer customer;
    private int amount;

    /** Create the order object
     * Using the setAmount() method to define the amount after the creation of the object is mandatory
     * @param prod product object
     * @param custom customer object */
    public OrderImpl(Product prod, Customer custom){
        this.product = prod;
        this.customer = custom;
        this.amount = 0;
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

    /** Define the amount of product this order must contains, have to be done just after the order's creation.
     * If requested amount is superior to previous amount the method will try to take the missing quantity from the
     * stock and update the amount into the stock,
     * if desired quantity is not present in product stock no changes are made and false is returned.
     * If requested amount is inferior to the previous amount the method will put the difference
     * back in the product stock.
     * @param amount total quantity of product in this order
     * @return true if product amount in order was successfully modified, false if not. */
    @Override
    public boolean setAmount(int amount) {
        if (amount < 0) {
            return false;
        }
        if (amount > product.getStock() && product.getStock() != -1) {
            return false;
        }
        if (this.amount < amount) {
            if(this.product.takeFromStock(Math.abs(this.amount - amount))){
                this.amount = amount;
                return true;
            }
            return false;
        } else if (this.amount > amount) {
            if(this.product.putInStock(this.amount - amount)){
                this.amount = amount;
                return true;
            }
            return false;
        } else { // If the user use this method with the amount as the original one.
            return true;
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
       boolean cancel = this.getProduct().putInStock(this.getAmount());
        if (cancel) {
           this.setAmount(0);
       }
       return cancel;
    }
}
