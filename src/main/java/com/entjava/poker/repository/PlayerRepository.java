package com.entjava.poker.repository;

import com.entjava.poker.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    List<PlayerEntity> findByNameIn(List<String> names);
    Optional<PlayerEntity> findByName(String name);
}
