package com.example.coffee_pet.state;

import com.example.coffee_pet.controller.StateController;
import com.example.coffee_pet.exception.CoffeeOffWhenWorkException;
import com.example.coffee_pet.service.StateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ControllerTests {

    @Mock
    private StateService stateService;

    @InjectMocks
    private StateController stateController;

    @BeforeEach
    public void initEach(){
        stateService = BDDMockito.mock(StateService.class, BDDMockito.RETURNS_DEEP_STUBS);
        stateController = new StateController(stateService);
    }

    @DisplayName("Выключение работающей кофе-машины")
    @Test
    public void OffWithWorkTest(){
        given(stateService.isWork()).willReturn(true);
        given(stateService.isOn()).willReturn(true);

        Assertions.assertThrows(CoffeeOffWhenWorkException.class, stateController::clickOnOffButton);
        System.out.println("Кофе-машина не выключилась во время работы. Это хорошо!");
    }
}
