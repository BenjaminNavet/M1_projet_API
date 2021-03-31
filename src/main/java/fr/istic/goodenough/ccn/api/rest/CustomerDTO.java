package fr.istic.goodenough.ccn.api.rest;

// Data transfer object for a product
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The DTO for the Customer API data type
 * @author plouzeau */
@XmlRootElement(name = "Customer")
public class CustomerDTO {
    public String uid;
//    public double credit;
//    public String name;
//    public Collection<ProductDTO> orders;

    public CustomerDTO(){}

    public CustomerDTO(String uid/*, String name*//*, double credit*/) {
        this.uid = uid;
//        this.name = name;
//        this.credit = credit;
//        this.orders = new LinkedList<>();
    }
}
