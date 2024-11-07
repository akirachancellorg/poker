package com.entjava.poker.repository;

import com.entjava.poker.game.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
