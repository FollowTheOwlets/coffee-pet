package com.example.coffee_pet.controller;

import com.example.coffee_pet.annotation.ResponseWithStateAndAction;
import com.example.coffee_pet.component.ActionUpdateRequest;
import com.example.coffee_pet.component.NewActionRequest;
import com.example.coffee_pet.component.ResponseStateWithAction;
import com.example.coffee_pet.entity.Action;
import com.example.coffee_pet.exception.CoffeeOffException;
import com.example.coffee_pet.service.ActionService;
import com.example.coffee_pet.service.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Tag(name = "Создание, удаление, обновление и получение задач", description = "Методы по работе с задачами на приготовление кофе")
public class ActionController {
    private final ActionService actionService;
    private final StateService stateService;

    public ActionController(ActionService actionService, StateService stateService) {
        this.actionService = actionService;
        this.stateService = stateService;
    }

    @PostMapping()
    @ResponseWithStateAndAction
    @ApiResponse(description = "В ответе приходит тело действия(Action) и остатки ресурсов машины")
    @Operation(description = "Добавление нового заказа кофе.\n NOW - прямо сейчас, WAIT - на момент указанный в поле startTime")
    public ResponseStateWithAction createAction(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Обратите внимание, что используется лондонское время",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NewActionRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"coffee\": \"AMERICANO\",\n" +
                                            "  \"type\": \"NEW\",\n" +
                                            "  \"startTime\": \"2023-11-06T18:49:59.114Z\"\n" +
                                            "}"
                            )
                    )
            )
            @RequestBody NewActionRequest req
    ) {
        check();
        ResponseStateWithAction res = new ResponseStateWithAction();
        switch (req.getType()) {
            case "WAIT":
                res.setPayload(actionService.createDelayAction(req.getCoffee(), req.getStartTime()));
                break;
            default:
                res.setPayload(actionService.createAction(req.getCoffee()));
        }
        return res;
    }


    @GetMapping
    @ResponseWithStateAndAction
    @ApiResponse(description = "В ответе приходит тело действия(Action) и остатки ресурсов машины")
    @Operation(description = "Получение состояния задачи (Выполнена, в ожидании, а может быть получила ошибку из-за отсутствия ресурсов)")
    @Parameters(
            @Parameter(
                    name = "id",
                    example = "1"
            )
    )
    public ResponseStateWithAction getActionById(@RequestParam("id") Long id) {
        check();
        ResponseStateWithAction res = new ResponseStateWithAction();
        Action action = actionService.getActionById(id);
        res.setPayload(action);
        return res;
    }

    @DeleteMapping
    @ResponseWithStateAndAction
    @ApiResponse(description = "В ответе приходит тело действия(Action) и остатки ресурсов машины")
    @Operation(description = "Отмена задачи, если она еще в ожидании")
    public ResponseStateWithAction cancelActionById(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ActionUpdateRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"id\": 0,\n" +
                                            "  \"action\": {\n" +
                                            "    \"operation\": {\n" +
                                            "      \"name\": \"WAIT\"\n" +
                                            "    },\n" +
                                            "    \"coffee\": {\n" +
                                            "      \"name\": \"AMERICANO\"\n" +
                                            "    },\n" +
                                            "    \"createTime\": \"2023-11-06T04:16:22.043Z\",\n" +
                                            "    \"startTime\": \"2023-11-06T04:16:22.043Z\",\n" +
                                            "    \"endTime\": \"2023-11-06T04:16:22.043Z\"\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
            @RequestBody ActionUpdateRequest req
    ) {
        check();
        ResponseStateWithAction res = new ResponseStateWithAction();
        Action action = actionService.cancelActionById(req.getId());
        res.setPayload(action);
        return res;
    }

    @PutMapping
    @ResponseWithStateAndAction
    @ApiResponse(description = "В ответе приходит тело действия(Action) и остатки ресурсов машины")
    @Operation(description = "Обновление задачи, если она еще в ожидании")
    public ResponseStateWithAction updateActionById(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ActionUpdateRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"id\": 0,\n" +
                                            "  \"action\": {\n" +
                                            "    \"operation\": {\n" +
                                            "      \"name\": \"WAIT\"\n" +
                                            "    },\n" +
                                            "    \"coffee\": {\n" +
                                            "      \"name\": \"AMERICANO\"\n" +
                                            "    },\n" +
                                            "    \"createTime\": \"2023-11-06T04:16:22.043Z\",\n" +
                                            "    \"startTime\": \"2023-11-06T04:16:22.043Z\",\n" +
                                            "    \"endTime\": \"2023-11-06T04:16:22.043Z\"\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
            @RequestBody ActionUpdateRequest req
    ) {
        check();
        ResponseStateWithAction res = new ResponseStateWithAction();
        Action action = actionService.updateActionById(req.getId(), req.getAction());
        res.setPayload(action);
        return res;
    }

    private void check() {
        if (!stateService.isOn()) {
            throw new CoffeeOffException();
        }
    }
}
