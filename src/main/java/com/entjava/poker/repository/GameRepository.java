package com.entjava.poker.repository;

import com.entjava.poker.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository <GameEntity, Long> {
}
