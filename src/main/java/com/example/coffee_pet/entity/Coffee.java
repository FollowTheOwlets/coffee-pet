package com.example.coffee_pet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "coffee")
public class Coffee {

    @Getter
    @AllArgsConstructor
    public enum Enum {
        ESPRESSO("ESPRESSO"),
        AMERICANO("AMERICANO"),
        CAPPUCCINO("CAPPUCCINO"),
        LATTE("LATTE");
        private final String name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "coffee", cascade = CascadeType.ALL)
    private List<CoffeeResource> resourceList;
}
