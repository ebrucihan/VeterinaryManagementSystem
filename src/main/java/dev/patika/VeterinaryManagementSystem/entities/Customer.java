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
    private long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_mail")
    private String customerMail;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_city")
    private String customerCity;

    // One customer can have multiple animals
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyGroup("AnimalDetails")
    private List<Animal> animals;

}
