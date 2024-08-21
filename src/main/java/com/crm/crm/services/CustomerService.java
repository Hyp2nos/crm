package com.crm.crm.services;

import com.crm.crm.exception.ResourceAlreadyExistException;
import com.crm.crm.exception.ResourceNotFoundException;
import com.crm.crm.models.entities.Customer;
import com.crm.crm.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found customer with id :  " + customerId));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByPhoneNumber(customer.getPhoneNumber());
        if (existingCustomer.isPresent()) {
            throw new ResourceAlreadyExistException("Customer with phone number " + customer.getPhoneNumber() + " already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long customerId, Customer customer) {
        getCustomerById(customerId);
        customer.setId(customerId);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        getCustomerById(customerId);
        customerRepository.deleteById(customerId);
    }
}
