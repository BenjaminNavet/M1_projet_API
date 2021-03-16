package fr.istic.goodenough.ccn.api.rest;

import java.util.Collection;
import java.util.LinkedList;

// Data transfer object for a product
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The DTO for the Customer API data type
 * 
 * @author plouzeau
 *
 */
@XmlRootElement(name = "Customer")
public class CustomerDTO {
	public String id;
	public double credit;
	public String name;
	public Collection<ProductDTO> orders;

	public CustomerDTO(String id, String name, double credit) {
		this.id = id;
		this.name = name;
		this.credit = credit;
		this.orders = new LinkedList<>();
	}
}
