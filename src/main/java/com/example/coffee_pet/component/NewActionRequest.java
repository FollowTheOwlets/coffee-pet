package com.example.coffee_pet.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NewActionRequest {
    private String coffee;
    private String type;
    private LocalDateTime startTime;
}
