package com.gabi.service;

import com.gabi.models.Customer;

import java.util.*;


/**
 * A singleton service class for the Customer model
 *
 * @author gabi
 *
 */
public class CustomerService {

    // Private class variable only accessible via the getInstance method
    private static final CustomerService reference = new CustomerService();
    // should this be a class constant or is it ok as we only have one instance?
    private final Map<String, Customer> customerMap;

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
        customerMap.put(customer.getEmail(), customer);
    }

    public Optional<Customer> getCustomer(String customerEmail){
        Customer customer = customerMap.get(customerEmail);
        return Optional.ofNullable(customer);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    public Map<String, Customer> getCustomerMap() {
        return customerMap;
    }
}
