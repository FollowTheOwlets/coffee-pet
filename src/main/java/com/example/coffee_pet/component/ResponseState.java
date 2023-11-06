package com.example.coffee_pet.component;

import com.example.coffee_pet.entity.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseState {
    private String payload;
    private List<Resource> state;
}
