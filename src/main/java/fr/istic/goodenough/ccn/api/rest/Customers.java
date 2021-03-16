package fr.istic.goodenough.ccn.api.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;
import fr.istic.goodenough.ccn.api.engine.*;

@Singleton
@Path("customers")
public class Customers {

	private Engine engine;
	
	private Collection<CustomerDTO> getAllCustomers() {
		Collection<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
		for(Customer c : engine.getAllCustomers()) {
			CustomerDTO dto = makeCustomerDTO(c);
			customerDTOs.add(dto);
		}
		return Collections.unmodifiableCollection(customerDTOs);
	}

	public Customers() {
		engine = EnginePhonyImpl.currentEngine;
	}

	// Factories for DTOs
	
	private CustomerDTO makeCustomerDTO(Customer customer) {
		return new CustomerDTO(Integer.toHexString(customer.hashCode()),
				customer.getName(),customer.getCredit());
	}
	
	// ---- REST API --------
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<CustomerDTO> getCustomers() {
		return getAllCustomers(); 
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{cid}")
	public CustomerDTO getProductById(@PathParam("cid") String customerId) {
		
		return null; // HACK Return the proper error code instead

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{cid}/orders/{pid}")
	public CustomerDTO orderProduct(@PathParam("cid") String customerId,
			@PathParam("pid") String productId) {
		
		Logger.getGlobal()
				.info("Got order " + productId + " for customer " + customerId);
		// TODO: write proper code!
		return null;
	}
}
