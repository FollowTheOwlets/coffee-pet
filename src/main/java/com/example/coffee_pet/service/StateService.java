package com.example.coffee_pet.service;

import com.example.coffee_pet.entity.ChangeState;
import com.example.coffee_pet.entity.State;
import com.example.coffee_pet.repository.ChangeStateRepository;
import com.example.coffee_pet.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateService {
    private final ChangeStateRepository changeStateRepository;
    private final StateRepository stateRepository;

    public void clickButton () {
        ChangeState changeState = new ChangeState();
        State newState = new State();
        newState.setName(isOn() ? State.Enum.OFF.getName() : State.Enum.ON.getName());

        changeState.setTime(LocalDateTime.now());
        changeState.setState(isOn() ? getOffState() : getOnState());

        changeStateRepository.saveAndFlush(changeState);
    }

    public boolean isWork(){
        Optional<ChangeState> changeState =  changeStateRepository.findFirstByOrderByIdDesc();
        return changeState.isPresent() && changeState.get().getState().getName().equals(State.Enum.WORK.getName());
    }

    public boolean isOn () {
        Optional<ChangeState> changeState =  changeStateRepository.findFirstByOrderByIdDesc();
        return changeState.isPresent() && changeState.get().getState().getName().equals(State.Enum.ON.getName());
    }

    private State getOnState(){
        return stateRepository.findByName("ON");
    }

    private State getOffState(){
        return stateRepository.findByName("OFF");
    }

    private State getWorkState(){
        return stateRepository.findByName("WORK");
    }

}
