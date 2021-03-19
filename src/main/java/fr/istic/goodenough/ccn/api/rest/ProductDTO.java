package fr.istic.goodenough.ccn.api.rest;

/**
 * Data transfer object for a product
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Product")
public class ProductDTO {
    public String id;
    public double price;
    public String name;

    public ProductDTO(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
