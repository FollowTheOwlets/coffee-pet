package com.example.coffee_pet.service;

import com.example.coffee_pet.entity.*;
import com.example.coffee_pet.entity.Error;
import com.example.coffee_pet.exception.ActionException;
import com.example.coffee_pet.exception.ActionNotFoundException;
import com.example.coffee_pet.exception.ChangeActionException;
import com.example.coffee_pet.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;
    private final OperationRepository operationRepository;
    private final CoffeeRepository coffeeRepository;
    private final StateRepository stateRepository;
    private final ResourceRepository resourceRepository;
    private final ErrorRepository errorRepository;

    @Scheduled(fixedDelay = 500)
    public void ScheduledStartAction() throws InterruptedException {
        Optional<Action> optional = actionRepository.findFirstActionsByOperationNameOrderByStartTimeAsc(Operation.Enum.WAIT.getName());

        if (optional.isEmpty() || optional.get().getStartTime().isAfter(LocalDateTime.now())) return;

        Action action = optional.get();

        if (!checkCoffeeResources(action, action.getCoffee().getName())) return;

        action.setOperation(operationRepository.findByName(Operation.Enum.START.getName()));
        actionRepository.saveAndFlush(action);

        State workState = new State();
        workState.setName(State.Enum.WORK.getName());
        stateRepository.saveAndFlush(workState);

        Thread.sleep(5000);

        action.setEndTime(LocalDateTime.now());
        action.setOperation(operationRepository.findByName(Operation.Enum.END.getName()));
        actionRepository.saveAndFlush(action);

        State onState = new State();
        onState.setName(State.Enum.ON.getName());
        stateRepository.saveAndFlush(onState);
    }

    public Action getActionById(Long id) {
        return actionRepository.findOneById(id).or(() -> {
            throw new ActionNotFoundException(id + "");
        }).get();
    }

    public Action cancelActionById(Long id) {
        Action action = getActionForUpdateById(id);
        action.setEndTime(LocalDateTime.now());
        action.setOperation(operationRepository.findByName(Operation.Enum.CANCEL.getName()));
        actionRepository.saveAndFlush(action);
        return action;
    }

    public Action updateActionById(Long id, Action newAction) {
        Action action = getActionForUpdateById(id);
        action.setCoffee(newAction.getCoffee());
        action.setStartTime(newAction.getStartTime());
        actionRepository.saveAndFlush(action);
        return action;
    }

    public Action createDelayAction(String coffee, LocalDateTime startTime) {
        return getNewAction(coffee, startTime);
    }

    public Action createAction(String coffee) {
        return getNewAction(coffee, LocalDateTime.now());
    }

    private Action getNewAction(String coffee, LocalDateTime startTime) {
        Action action = new Action();
        action.setCreateTime(LocalDateTime.now());
        action.setStartTime(startTime);
        action.setOperation(operationRepository.findByName(Operation.Enum.WAIT.getName()));
        action.setCoffee(coffeeRepository.findByName(coffee));
        actionRepository.saveAndFlush(action);
        return action;
    }

    private Action getActionForUpdateById(Long id) {
        Action action = getActionById(id);
        if (!action.getOperation().getName().equals(Operation.Enum.WAIT.getName())) {
            throw new ChangeActionException(action.getId() + "");
        }
        return action;
    }

    private boolean checkCoffeeResources(Action action, String coffeeName) {
        Coffee coffee = coffeeRepository.findByName(coffeeName);

        for (CoffeeResource pair : coffee.getResourceList()) {
            Resource res = pair.getResource();
            if (res.getCount() < pair.getCount()) {
                Error error = new Error();
                error.setAction(action);
                String errorMsg = "Нехватка ресурса " + res.getName() + " для приготовления " + coffeeName;
                error.setMessage(errorMsg);
                errorRepository.saveAndFlush(error);
                action.setEndTime(LocalDateTime.now());
                action.setOperation(operationRepository.findByName(Operation.Enum.ERROR.getName()));
                actionRepository.saveAndFlush(action);
                throw new ActionException(errorMsg);
            }
        }

        for (CoffeeResource pair : coffee.getResourceList()) {
            Resource res = pair.getResource();
            res.setCount(res.getCount() - pair.getCount());
            resourceRepository.save(res);
        }

        resourceRepository.flush();
        return true;
    }
}
