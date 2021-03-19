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
        Optional<Customer> customer = engine.getCustomer(Integer.parseInt(uid));
        Optional<Product> product = engine.getProduct(Integer.parseInt(pid));
        if (customer.isPresent() && product.isPresent()) {
            if (customer.get().addProduct(product.get(), Integer.parseInt(amount))) {
                return Response
                        .status(Response.Status.OK)
                        .entity("")
                        .build();
            }
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(customer.isPresent()?
                        "{\"message\" : \"Product not found\"}":
                        "{\"message\" : \"Account not found\"}")
                .build();
    }
}
