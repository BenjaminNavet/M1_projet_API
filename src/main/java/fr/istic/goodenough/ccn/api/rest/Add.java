package fr.istic.goodenough.ccn.api.rest;

import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;
import fr.istic.goodenough.ccn.api.engine.Product;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Singleton
@Path("add")
public class Add {

    private final Engine engine;

    public Add() {
        engine = EnginePhonyImpl.currentEngine;
    }

    /** Add a product to the user basket if uid is valid and product is available
     * @param uid customer id in url path
     * @param pid product id to add
     * @param amount quantity of product to add */
    @POST
    @Path("{uid}")
    public Response post(@PathParam("uid") String uid, @QueryParam("pid") String pid, @QueryParam("amount") String amount) {
        Optional<Customer> customer;
        Optional<Product> product;
        int amnt = Integer.parseInt(amount);
        try {
            customer = engine.getCustomer(Integer.parseInt(uid));
            product = engine.getProduct(Integer.parseInt(pid));
        } catch (NullPointerException | NumberFormatException e){
            return Response // Invalid parameters format
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        if (customer.isPresent() && product.isPresent()) {
            if (customer.get().addProduct(product.get(), amnt)) {
                return Response // Response ok
                        .status(Response.Status.OK)
                        .build();
            }
            return Response // Something went wrong when adding the product to the basket
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response // Customer or product not found
                .status(Response.Status.NOT_FOUND)
                .entity(customer.isPresent()?
                        "{\"message\" : \"Product not found\"}":
                        "{\"message\" : \"Account not found\"}")
                .build();
    }
}
