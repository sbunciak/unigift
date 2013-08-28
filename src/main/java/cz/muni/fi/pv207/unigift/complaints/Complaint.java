package cz.muni.fi.pv207.unigift.complaints;

import java.io.Serializable;

import cz.muni.fi.pv207.unigift.products.Product;

public class Complaint implements Serializable {

    private static final long serialVersionUID = 2257624264034097028L;
    private String contact;
    private String description;
    private Product product;
    
    public Complaint(String contact, String description, Product product) {
        super();
        this.contact = contact;
        this.description = description;
        this.product = product;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    
    @Override
    public String toString() {
        return "Complaint about " + product + " from " + contact;
    }
}
