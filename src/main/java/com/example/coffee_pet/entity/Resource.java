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
@Table(name = "resource")
public class Resource {

    @Getter
    @AllArgsConstructor
    public enum Enum {
        MILK("milk"),
        WATER("water"),
        COFFEE("coffee");
        private final String name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    String name;

    int count;

    @JsonIgnore
    int max_count;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resource", cascade = CascadeType.ALL)
    private List<CoffeeResource> coffeeList;
}
