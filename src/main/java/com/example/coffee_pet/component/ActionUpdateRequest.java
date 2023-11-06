package com.example.coffee_pet.component;

import com.example.coffee_pet.entity.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ActionUpdateRequest {
    private Long id;
    private Action action;
}
