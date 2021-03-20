package fr.istic.goodenough.ccn.api.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/** This _NOT_ a DTO.
 * @author plouzeau */
public class CustomerImpl implements Customer {

    // TODO : METTRE LES ATTRIBUTS NON MODIFIE EN FINAL !
    private String name;
    private int uid;
    double credit;
    private Collection<Order> pendingOrders;
    private String passwd;

    /**
     * Constructor Customer
     * @param name the client's name
     * @param passwd the client's password
     * @param uid the client's id
     */
    public CustomerImpl(String name, String passwd, int uid) {
        this.name = name;
        this.passwd = passwd;
        this.uid = uid;
        this.credit = -1;
        pendingOrders = new LinkedList<>();
    }

    /** Get the Customer id
     * @return customer uid */
    @Override
    public int getUid() {
        return uid;
    }

    /** Get the Customer name
     * @return customer name */
    @Override
    public String getName() {
        return name;
    }

    /** Get the Customer password
     * @return customer password */
    @Override
    public String getPasswd() {
        return passwd;
    }

    /** Get customer credit
     * @deprecated Not to be used in current version
     * @return customer credit*/
    @Override
    public double getCredit() {
        return credit;
    }

    /** Get a collection of all order objects related to this customer (his basket)
     * @return customer basket in a collection */
    @Override
    public Collection<Order> getPendingOrders() {
        return Collections.unmodifiableCollection(pendingOrders);
    }

    /** Add a product in the customer basket by creating a new order object with the desired product and amount
     * and add it to the order collection of this user.
     * If a order object is already present in the user basket this method will try to change the amount in the order
     * object to match the requested amount. If desired amount is impossible to match for any reason no modification
     * will be done dans method will return false.
     * @param product Product object to add to the basket
     * @param amount  Amount of product to add to the basket
     * @return true if order was correctly created or modified, false otherwise */
    @Override
    public boolean addProduct(Product product, int amount) {
        Order newOrder = new OrderImpl (product, this, amount);
        return this.pendingOrders.add(newOrder);
    }

    /** "Validate" customer basket and "send" all products to the customer,
     * destroy all related order objects and empty pending order collection.
     * @return true if basket is correctly emptied and products sent to customer, false if basket was empty or order can't be done */
    @Override
    // TODO : /!\ SURTOUT PAS DE CLEAR, LE CLEAR REMET TOUT LES PRODUITS DANS LE STOCK ALORS QU'ILS SONT CENSES PARTIR CHEZ LE CLIENT !!!
    public boolean order() {
        this.pendingOrders.clear();
        return true;
    }

    /** Cancel customer basket, cancel all related order objects.
     * @return true if cancel of all order is success, false otherwise */
    @Override
    public boolean clear() {
        for(Order item : pendingOrders){
            item.cancel();
        }
        this.pendingOrders.clear();
        return true;
    }
}
