package com.example.coffee_pet.action;

import com.example.coffee_pet.component.NewActionRequest;
import com.example.coffee_pet.controller.ActionController;
import com.example.coffee_pet.exception.CoffeeOffException;
import com.example.coffee_pet.service.ActionService;
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
    private ActionService actionService;

    @Mock
    private StateService stateService;

    @Mock
    private NewActionRequest request;

    @InjectMocks
    private ActionController actionController;


    @BeforeEach
    public void initEach() {
        actionService = BDDMockito.mock(ActionService.class, BDDMockito.RETURNS_DEEP_STUBS);
        stateService = BDDMockito.mock(StateService.class, BDDMockito.RETURNS_DEEP_STUBS);
        request = BDDMockito.mock(NewActionRequest.class, BDDMockito.RETURNS_DEEP_STUBS);
        actionController = new ActionController(actionService, stateService);
    }

    @DisplayName("Не выполнение команд при выключенной машине")
    @Test
    public void OffWithWorkTest() {
        given(stateService.isOn()).willReturn(false);

        Assertions.assertThrows(CoffeeOffException.class, () -> actionController.createAction(request));
        System.out.println("Не выполнение команд при выключенной машине. Это хорошо!");
    }
}
