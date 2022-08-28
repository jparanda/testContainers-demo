package org.juan.learn.testcontainers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Entity(name = "customer")
public class Customer {

    @Id
    @Column(name = "email")
    @JsonProperty("email")
    @NotEmpty(message = "The 'email' field is required")
    @Email(message = "The 'email' field is invalid")
    private String email;

    @Column(name = "first_name")
    @JsonProperty("firstName")
    @NotEmpty(message = "The 'firstName' field is required")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("lastName")
    @NotEmpty(message = "The 'lastName' field is required")
    private String lastName;

}
