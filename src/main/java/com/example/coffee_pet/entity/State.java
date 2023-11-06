package com.example.coffee_pet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "state")
public class State {

    @Getter
    @AllArgsConstructor
    public enum Enum {
        ON("ON"),
        WORK("WORK"),
        OFF("OFF");
        private final String name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    String name;
}
