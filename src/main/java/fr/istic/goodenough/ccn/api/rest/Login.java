package fr.istic.goodenough.ccn.api.rest;

import java.util.Optional;
import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("login")
public class Login {
    private final Engine engine;

    public Login() {
        engine = EnginePhonyImpl.currentEngine;
    }

    /** Convert the customer object into customerDTO
     * @param customer customer object to convert
     * @return customerDTO*/
    private CustomerDTO makeCustomerDTO(Customer customer) {
        return new CustomerDTO(Integer.toString(customer.getUid()));
    }

    /** Get name and password to identify a custumer
     * @param name customer name
     * @param passwd customer password
     * @return Json containing customer uid if customer exist or 404 */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("name") String name, @QueryParam("passwd") String passwd) {
        Optional<Customer> cust = engine.getCustomerByCredentials(name,passwd);
        if (cust.isPresent()) {
            return Response // Response OK
                    .status(Response.Status.OK)
                    .entity(makeCustomerDTO(cust.get()))
                    .build();
        }
        return Response // Customer not found
                .status(Response.Status.NOT_FOUND)
                .entity("{\"message\" : \"Customer not found / Invalid credentials\"}")
                .build();
    }
}
