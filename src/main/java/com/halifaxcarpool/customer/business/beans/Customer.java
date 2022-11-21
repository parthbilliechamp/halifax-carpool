package com.halifaxcarpool.customer.business.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "customers_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customers_sequence"
    )
    private int customerId;

    @NotNull(message = "First Name cannot be empty")
    @Column(name = "customer_name")
    String customerName;

    @NotNull(message = "Last Name cannot be empty")
    @Column(name = "customer_contact")
    String customerContact;

    @NotNull(message = "Last Name cannot be empty")
    @Email(message = "Please enter a valid email address")
    @Column(name = "customer_email", unique = true)
    String customerEmail;
}
