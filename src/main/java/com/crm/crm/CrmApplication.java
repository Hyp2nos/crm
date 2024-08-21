package com.crm.crm;

import com.crm.crm.models.entities.Customer;
import com.crm.crm.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(final CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567891"));
            customerRepository.save(new Customer(2L, "Kale", "Doe", "john.doe@example.com", "1234567892"));
            customerRepository.save(new Customer(3L, "Tom", "Doe", "john.doe@example.com", "1234567893"));
        };
    }

    @Bean
    ModelMapper modelMapper() {
        return  new ModelMapper();
    }

}
