package edu.kalam.registrations.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAddressService {

    private final JdbcTemplate jdbcTemplate;

    public CustomerAddressService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves a list of addresses for a given customer ID.
     *
     * @param customerId The ID of the customer.
     * @return A list of addresses associated with the customer.
     */
    public List<String> getCustomerAddresses(int customerId) {
        // Implementation to retrieve customer addresses from the database
        // DB interaction code would go here
        // May be another API call or a database query
        // except
        return List.of("123 Main St", "456 Oak Ave");
    }

}
