package com.crm.crm.repositories;

import com.crm.crm.models.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(properties = "spring.jpa.properties.javax.persistence.validation.mode=none")
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void itShouldFindCustomerByPhoneNumber() {

        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("+1234567890")
                .build();

        customerRepository.save(customer);
        Optional<Customer> excepted = this.customerRepository.findByPhoneNumber(customer.getPhoneNumber());

        assertThat(excepted).isPresent();
        assertThat(excepted.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(excepted.get().getLastName()).isEqualTo(customer.getLastName());
        assertThat(excepted.get().getPhoneNumber()).isEqualTo(customer.getPhoneNumber());
    }

    @Test
    void itShouldNotFindCustomerByPhoneNumber() {
        Optional<Customer> excepted = this.customerRepository.findByPhoneNumber("+1234567890");
        assertThat(excepted).isEmpty();
    }

    @Test
    void itNotShouldFindCustomerByEmail() {
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        assertThatThrownBy(() -> customerRepository.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}