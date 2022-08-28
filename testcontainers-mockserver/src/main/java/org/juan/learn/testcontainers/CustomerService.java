package org.juan.learn.testcontainers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerService {

    private final EmailService emailService;
    private final CustomerRepository repository;

    public Customer register(Customer customer) {
        emailService.sendWelcomeEmail(customer.getEmail());
        return repository.save(customer);
    }
}
