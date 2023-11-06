package com.example.coffee_pet.state;

import com.example.coffee_pet.controller.StateController;
import com.example.coffee_pet.entity.ChangeState;
import com.example.coffee_pet.entity.State;
import com.example.coffee_pet.exception.CoffeeOffWhenWorkException;
import com.example.coffee_pet.repository.ChangeStateRepository;
import com.example.coffee_pet.repository.StateRepository;
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

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceTests {

    @Mock
    private ChangeStateRepository changeStateRepository;

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateService stateService;

    @BeforeEach
    public void initEach(){
        changeStateRepository = BDDMockito.mock(ChangeStateRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        stateRepository = BDDMockito.mock(StateRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        stateService = new StateService(changeStateRepository, stateRepository);
    }

    @DisplayName("Проверка включения кофе-машины")
    @Test
    public void isOnTest(){
        ChangeState changeState = new ChangeState();
        State state = new State();
        state.setName(State.Enum.ON.getName());
        changeState.setState(state);

        given(changeStateRepository.findFirstByOrderByIdDesc()).willReturn(Optional.of(changeState));

        Assertions.assertTrue(stateService.isOn());

        System.out.println("Проверка включения кофе-машины прошла. Это хорошо!");
    }

    @DisplayName("Проверка рабочего состояния кофе-машины")
    @Test
    public void isWorkTest(){
        ChangeState changeState = new ChangeState();
        State state = new State();
        state.setName(State.Enum.WORK.getName());
        changeState.setState(state);

        given(changeStateRepository.findFirstByOrderByIdDesc()).willReturn(Optional.of(changeState));

        Assertions.assertTrue(stateService.isWork());

        System.out.println("Проверка рабочего состояния кофе-машины прошла. Это хорошо!");
    }

    @DisplayName("Проверка выключенного состояния кофе-машины")
    @Test
    public void isOffTest(){
        ChangeState changeState = new ChangeState();
        State state = new State();
        state.setName(State.Enum.OFF.getName());
        changeState.setState(state);

        given(changeStateRepository.findFirstByOrderByIdDesc()).willReturn(Optional.of(changeState));

        Assertions.assertFalse(stateService.isOn());

        System.out.println("Проверка выключенного состояния кофе-машины прошла. Это хорошо!");
    }
}
