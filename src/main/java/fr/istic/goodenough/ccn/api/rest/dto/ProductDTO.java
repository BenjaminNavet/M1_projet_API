package fr.istic.goodenough.ccn.api.rest.dto;

/** Data transfer object for a product */
public class ProductDTO {
    public String pid;
    public String type;
    public String name;
    public double price;

    public ProductDTO() {}
    
    public ProductDTO(String id, String type, String name, double price) {
        this.pid = id;
        this.type = type;
        this.name = name;
        this.price = price;
    }
}
