package com.example.coffee_pet.repository;

import com.example.coffee_pet.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Override
    List<Resource> findAll();

    @Query("SELECT CAST(r.count AS integer) FROM Resource r WHERE r.name = :name")
    int findCountByName(@Param("name") String name);

    @Query("SELECT CAST(r.max_count AS integer) FROM Resource r WHERE r.name = :name")
    int findMaxCountByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("UPDATE Resource r SET r.count = :newCount WHERE r.name = :name")
    void updateCountByName(@Param("newCount") int newCount, @Param("name") String name);

}

