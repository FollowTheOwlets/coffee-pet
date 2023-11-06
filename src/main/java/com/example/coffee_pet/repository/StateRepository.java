package com.example.coffee_pet.repository;

import com.example.coffee_pet.entity.ChangeState;
import com.example.coffee_pet.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    State findByName(String name);
}
