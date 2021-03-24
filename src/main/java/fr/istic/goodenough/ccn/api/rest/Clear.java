package fr.istic.goodenough.ccn.api.rest;

import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Optional;

@Singleton
@Path("clear")
public class Clear {

    private Engine engine;

    public Clear() {
        engine = EnginePhonyImpl.currentEngine;
    }

    /** Clear a customer's basket
     * @param uid customer uid
     * @return 404 Error if uid not found */
    @DELETE
    @Path("{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("uid") String uid) {
        Optional<Customer> customer;
        try {
            customer = engine.getCustomer(Integer.parseInt(uid));
        } catch (NullPointerException | NumberFormatException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        if (customer.isPresent()) {
            if (customer.get().clear()){
                return Response
                        .status(Response.Status.OK)
                        .entity("")
                        .build();
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity("{\"message\" : \"Account not found\"}")
                .build();
    }
}
