package fr.istic.goodenough.ccn.api.rest;


import javax.xml.bind.annotation.XmlRootElement;

/** Data transfer object for a product */
@XmlRootElement(name = "Product")
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
