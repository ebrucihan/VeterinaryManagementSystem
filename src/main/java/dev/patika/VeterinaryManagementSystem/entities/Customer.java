package dev.patika.VeterinaryManagementSystem.entities;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyGroup;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId; // Unique identifier for the customer

    @Column(name = "customer_name")
    private String customerName; // Name of the customer

    @Column(name = "customer_phone")
    private String customerPhone; // Phone number of the customer

    @Column(name = "customer_mail")
    private String customerMail; // Email address of the customer

    @Column(name = "customer_address")
    private String customerAddress; // Address of the customer

    @Column(name = "customer_city")
    private String customerCity; // City where the customer lives

    // One customer can have multiple animals
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyGroup("AnimalDetails")
    private List<Animal> animals; // List of animals owned by the customer

}
