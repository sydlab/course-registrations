package edu.kalam.registrations.models;

import java.util.List;

public class Customer {

    private String customerId;
    private String customerName;
    private List<String> addresses;

    public Customer() {
    }

    public Customer(String customerId,
                    String customerName,
                    List<String> addresses) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addresses = addresses;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public List<String> getAddresses() {
        return addresses;
    }

}
