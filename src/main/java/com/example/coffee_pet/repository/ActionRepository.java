package com.example.coffee_pet.repository;

import com.example.coffee_pet.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    Optional<Action> findOneById(Long id);

    @Query("SELECT a FROM Action a WHERE a.operation.name = :operation ORDER BY a.startTime ASC LIMIT 1")
    Optional<Action> findFirstActionsByOperationNameOrderByStartTimeAsc(@Param("operation") String operation);
}

