package fr.istic.goodenough.ccn.api.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.istic.goodenough.ccn.api.engine.Product;
import fr.istic.goodenough.ccn.api.engine.Engine;
import fr.istic.goodenough.ccn.api.engine.EnginePhonyImpl;

@Singleton
@Path("products")
public class Products {

	private Engine engine;

	public Products() {
		engine = EnginePhonyImpl.currentEngine;
	}

	private Collection<ProductDTO> getAllProducts() {
		Collection<ProductDTO> productDTOs = new ArrayList<>();
		for(Product c : engine.getAllProducts()) {
			ProductDTO dto = makeProductDTO(c);
			productDTOs.add(dto);
		}
		return Collections.unmodifiableCollection(productDTOs);
	}
	private ProductDTO makeProductDTO(Product product) {
		return new ProductDTO(Integer.toHexString(product.hashCode()),
				product.getFullName(),product.getPrice());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ProductDTO> getProducts() {
		return getAllProducts();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public ProductDTO getProductById(@PathParam("id") String productId) {
		
		return null; // HACK Return the proper error code instead

	}
}
