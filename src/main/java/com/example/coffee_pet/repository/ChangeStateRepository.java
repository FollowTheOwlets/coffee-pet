package com.example.coffee_pet.repository;

import com.example.coffee_pet.entity.ChangeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChangeStateRepository extends JpaRepository<ChangeState, Long> {
    Optional<ChangeState> findFirstByOrderByIdDesc();
}
