package com.example.coffee_pet.resource;

import com.example.coffee_pet.component.AddResourceRequest;
import com.example.coffee_pet.controller.ResourceController;
import com.example.coffee_pet.controller.StateController;
import com.example.coffee_pet.exception.CoffeeOffWhenWorkException;
import com.example.coffee_pet.service.ResourceService;
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
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @BeforeEach
    public void initEach(){
        resourceService = BDDMockito.mock(ResourceService.class, BDDMockito.RETURNS_DEEP_STUBS);
        resourceController = new ResourceController(resourceService);
    }

    @DisplayName("Получение излишков добавления")
    @Test
    public void OffWithWorkTest(){
        int count = 500;
        given(resourceService.addResource(count, "milk")).willReturn(500);

        Assertions.assertEquals(
                "После пополнения ресурса до максимума остался остаток: 500",
                resourceController.addMilk( new AddResourceRequest(count)).getPayload()
        );
        System.out.println("Получение излишков добавления прошло. Это хорошо!");
    }
}
