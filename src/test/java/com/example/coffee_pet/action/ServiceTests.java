package com.example.coffee_pet.action;

import com.example.coffee_pet.exception.ActionNotFoundException;
import com.example.coffee_pet.repository.*;
import com.example.coffee_pet.service.ActionService;
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
    private ActionRepository actionRepository;
    @Mock
    private OperationRepository operationRepository;
    @Mock
    private CoffeeRepository coffeeRepository;
    @Mock
    private StateRepository stateRepository;
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private ErrorRepository errorRepository;

    @InjectMocks
    private ActionService actionService;


    @BeforeEach
    public void initEach() {
        actionRepository = BDDMockito.mock(ActionRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        operationRepository = BDDMockito.mock(OperationRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        coffeeRepository = BDDMockito.mock(CoffeeRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        stateRepository = BDDMockito.mock(StateRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        resourceRepository = BDDMockito.mock(ResourceRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        errorRepository = BDDMockito.mock(ErrorRepository.class, BDDMockito.RETURNS_DEEP_STUBS);
        actionService = new ActionService(
                actionRepository,
                operationRepository,
                coffeeRepository,
                stateRepository,
                resourceRepository,
                errorRepository
        );
    }

    @DisplayName("Проверка ошибки при получении несуществующей задачи")
    @Test
    public void OffWithWorkTest() {
        given( actionRepository.findOneById(0L)).willReturn(Optional.empty());

        Assertions.assertThrows(ActionNotFoundException.class, () -> actionService.getActionById(0L));
        System.out.println("Oшибка при получении несуществующей задачи. Это хорошо!");
    }
}

