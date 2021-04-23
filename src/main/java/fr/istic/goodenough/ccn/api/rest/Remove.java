package fr.istic.goodenough.ccn.api.rest;

import fr.istic.goodenough.ccn.api.engine.*;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Optional;

import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Singleton
@Path("rem")
public class Remove {

    private final Engine engine;

    public Remove(){
        this.engine= EnginePhonyImpl.currentEngine;
    }

    @DELETE
    @Path("{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("uid") String uid,@QueryParam("pid") String pid) {
        Optional<Customer> customerOPT;
        Optional<Product> productOPT;
        try {
            customerOPT = engine.getCustomer(Integer.parseInt(uid));
            productOPT = engine.getProduct(Integer.parseInt(pid));
        } catch (NullPointerException | NumberFormatException e){
            return Response // Invalid parameters format
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        if (customerOPT.isPresent() && productOPT.isPresent()) {
            if(customerOPT.get().removeProduct(productOPT.get())){
                return Response
                        .status(Response.Status.OK)
                        .build();
            }
        }

        return Response // Customer or product not found
                .status(Response.Status.NOT_FOUND)
                .entity("{\"message\" : \"Account not found\"}")
                .build();
    }



}
