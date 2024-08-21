package com.crm.crm.controller;

import com.crm.crm.models.dto.CustomerDto;
import com.crm.crm.models.entities.Customer;
import com.crm.crm.services.CustomerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.crm.crm.utils.MyHttpResponse.response;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable Long id) {
        return response(HttpStatus.OK, "Customer retrieved successfully", customerService.getCustomerById(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<Object> getCustomers() {
        List<CustomerDto> customers = this.customerService.getAllCustomers()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());

        return response(HttpStatus.OK, "Customers retrieved successfully", customers);
    }

    @PostMapping("/customer")
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Customer customer = this.customerService.createCustomer(modelMapper.map(customerDto, Customer.class));
        CustomerDto customerResponse = modelMapper.map(customer, CustomerDto.class);

        return response(HttpStatus.CREATED, "Customer created successfully", customerResponse);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDto customerDto) {
        Customer customer = this.customerService.updateCustomer(id, modelMapper.map(customerDto, Customer.class));
        CustomerDto customerResponse = modelMapper.map(customer, CustomerDto.class);

        return response(HttpStatus.OK, "Customer updated successfully", customerResponse);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        this.customerService.deleteCustomer(id);
        return response(HttpStatus.OK, "Customer deleted successfully", null);
    }
}
