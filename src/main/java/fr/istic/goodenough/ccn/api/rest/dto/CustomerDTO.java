package fr.istic.goodenough.ccn.api.rest.dto;

/** Data transfer object for a Customer */
public class CustomerDTO {
    public String uid;

    public CustomerDTO(){}

    public CustomerDTO(String uid) {
        this.uid = uid;
    }
}
