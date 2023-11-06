package com.example.coffee_pet.controller;

import com.example.coffee_pet.annotation.ResponseWithState;
import com.example.coffee_pet.component.AddResourceRequest;
import com.example.coffee_pet.component.NewActionRequest;
import com.example.coffee_pet.component.ResponseState;
import com.example.coffee_pet.entity.Resource;
import com.example.coffee_pet.exception.ResourceNotFoundException;
import com.example.coffee_pet.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/add_resource")
@Tag(name = "Добавление ресурсов", description = "Долить молока, воды или досыпать кофе  (мл/гр)")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping("/milk")
    @ResponseWithState
    @ApiResponse(description = "В ответе приходит текстовое описание и остатки ресурсов машины")
    @Operation(description = "Добавление молока")
    public ResponseState addMilk(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddResourceRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"count\": \"500\"}"
                            )
                    )
            )
            @RequestBody AddResourceRequest req
    ) {
        return addResource("milk", req);
    }

    @PostMapping("/water")
    @ResponseWithState
    @ApiResponse(description = "В ответе приходит текстовое описание и остатки ресурсов машины")
    @Operation(description = "Добавление воды")
    public ResponseState addWater(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddResourceRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"count\": \"500\"}"
                            )
                    )
            )
            @RequestBody AddResourceRequest req
    ) {
        return addResource("water", req);
    }

    @PostMapping("/coffee")
    @ResponseWithState
    @ApiResponse(description = "В ответе приходит текстовое описание и остатки ресурсов машины")
    @Operation(description = "Добавление кофе")
    public ResponseState addCoffee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddResourceRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"count\": \"500\"}"
                            )
                    )
            )
            @RequestBody AddResourceRequest req
    ) {
        return addResource("coffee", req);
    }


    private ResponseState addResource(String resource, AddResourceRequest req) {
        ResponseState state = new ResponseState();

        if (resourceService.getResources().stream().noneMatch((t) -> t.getName().equals(resource))) {
            throw new ResourceNotFoundException(resource);
        } else {
            int remains = resourceService.addResource(req.getAddCount(), resource);
            state.setPayload(remains == 0 ? "Ресурс пополнен" : "После пополнения ресурса до максимума остался остаток: " + remains);
        }

        return state;
    }

}
