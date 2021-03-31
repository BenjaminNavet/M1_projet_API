package fr.istic.goodenough.ccn.api.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.istic.goodenough.ccn.api.engine.Customer;
import fr.istic.goodenough.ccn.api.engine.Product;
import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;

@Singleton
@Path("products")
public class Products {

    private final Engine engine;

    public Products() {
        engine = EnginePhonyImpl.currentEngine;
    }

    /** Get a collection of all product DTO
     * @return collection of productDTO*/
    private Collection<ProductDTO> getAllProducts() {
        Collection<ProductDTO> productDTOs = new ArrayList<>();
        for(Product c : engine.getAllProducts()) {
            ProductDTO dto = makeProductDTO(c);
            productDTOs.add(dto);
        }
        return Collections.unmodifiableCollection(productDTOs);
    }

    /** Convert a product object into a DTO
     * @return productDTO*/
    private ProductDTO makeProductDTO(Product product) {
        return new ProductDTO(
                Integer.toString(product.getPid()),
                product.getType(),
                product.getFullName(),
                product.getPrice());
    }

    /** Create a list of all available products and return it in json
     * @return list of all products or 401*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@QueryParam("uid") String uid) {
        Optional<Customer> cust;
        try {
            cust = engine.getCustomer(Integer.parseInt(uid));
        } catch (NullPointerException | NumberFormatException e){
            return Response // Invalid parameters format
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
        if (cust.isPresent()) {
            return Response // Response Ok
                    .status(Response.Status.OK)
                    .entity(getAllProducts())
                    .build();
        }
        return Response // Customer uid not found
                .status(Response.Status.UNAUTHORIZED)
                .entity("{\"message\" : \"You must be logged to access this ressource\"}")
                .build();
    }

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{pid}")
    public ProductDTO getProductById(@PathParam("pid") String productId) {
        return null; // HACK Return the proper error code instead
    }*/
}
