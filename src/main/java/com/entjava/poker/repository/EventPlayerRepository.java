package com.entjava.poker.repository;

import com.entjava.poker.model.EventPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface EventPlayerRepository extends JpaRepository<EventPlayer, Integer> {
    Set<EventPlayer> findByEventId(Integer eventId);
    Set<EventPlayer> findByPlayerId(Integer playerId);
    Set<EventPlayer> findByEventIdAndIsWinnerTrue(Integer eventId);
    @Query("SELECT ep.player.name, COUNT(ep) FROM EventPlayer ep WHERE ep.isWinner = true GROUP BY ep.player.name")
    List<Object[]> findTotalWinsByPlayer();
}
