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
import javax.ws.rs.core.Response;
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
    public Response order(@PathParam("uid") String uid) {
        Optional<Customer> customer = engine.getCustomer(Integer.parseInt(uid));
        if (customer.isPresent()) {
            if (customer.get().order()) {
                return Response
                        .status(Response.Status.OK)
                        .build();
            }
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity("{\"message\" : \"Account not found\"}")
                .build();
    }
}
