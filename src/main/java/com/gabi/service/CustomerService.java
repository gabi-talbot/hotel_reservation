package com.gabi.service;

import com.gabi.models.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A singleton service class for the Customer model
 *
 * @author gabi
 *
 */
public class CustomerService {

    // Private class variable only accessible via the getInstance method
    private static final CustomerService reference = new CustomerService();
    private Map<String, Customer> customerMap;

    private CustomerService() {
        this.customerMap = new HashMap<>();
    }

    /**
     * Allows access to the class variable
     * @return Single CustomerService instance
     */
    public static CustomerService getInstance() {
        return reference;
    }

    public void addCustomer(Customer customer) {
        this.customerMap.put(customer.getEmail(), customer);
    }

    public Customer getCustomer(String customerEmail){
        return this.customerMap.get(customerEmail);
    }
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(this.customerMap.values());
    }
}
