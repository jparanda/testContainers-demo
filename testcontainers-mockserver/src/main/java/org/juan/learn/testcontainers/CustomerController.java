package org.juan.learn.testcontainers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/customers")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(service.register(customer));
    }
}
