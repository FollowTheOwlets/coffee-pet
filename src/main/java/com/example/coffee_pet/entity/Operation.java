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
@Table(name = "operation")
public class Operation {

    @Getter
    @AllArgsConstructor
    public enum Enum {
        WAIT("WAIT"),
        START("START"),
        CANCEL("CANCEL"),
        END("END"),
        ERROR("ERROR");
        private final String name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    String name;
}
