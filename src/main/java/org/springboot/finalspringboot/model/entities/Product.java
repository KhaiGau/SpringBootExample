package org.springboot.finalspringboot.model.entities;

import jakarta.persistence.*;

import lombok.Data;


@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double price;
    private String imageUrl;
}
