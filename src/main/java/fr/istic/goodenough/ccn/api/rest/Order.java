package fr.istic.goodenough.ccn.api.rest;

import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;

import javax.inject.Singleton;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Singleton
@Path("order")
public class Order {

    private Engine engine;

    public Order() {
        engine = EnginePhonyImpl.currentEngine;
    }

    /** Order a customer's basker
     * @param uid customer id
     * @return 404 error if uid not found */
    @PUT
    @Path("{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public void order(@PathParam("uid") String uid) {
        Optional<Customer> customer = engine.getCustomer(Integer.parseInt(uid));
        if (customer.isPresent()) {
            customer.get().order();
        }

        //404
    }
}
