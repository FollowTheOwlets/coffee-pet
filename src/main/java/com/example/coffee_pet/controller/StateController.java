package com.example.coffee_pet.controller;

import com.example.coffee_pet.annotation.ResponseWithState;
import com.example.coffee_pet.component.ResponseState;
import com.example.coffee_pet.exception.CoffeeOffWhenWorkException;
import com.example.coffee_pet.service.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/state")
@Tag(name = "Включение/выключение кофе-машины", description = "Нажатие на кнопку")
public class StateController {

    private final StateService stateService;

    @GetMapping()
    @ResponseWithState
    @ApiResponse(description = "В ответе приходит текстовое описание и остатки ресурсов машины")
    @Operation(description = "Включение/выключение кофе-машины")
    public ResponseState clickOnOffButton(){
        ResponseState state = new ResponseState();

        if(stateService.isWork()){
            throw new CoffeeOffWhenWorkException();
        }

        stateService.clickButton();
        state.setPayload(stateService.isOn() ? "Кофе-машина включена" : "Кофе-машина выключена");

        return state;
    }

}
