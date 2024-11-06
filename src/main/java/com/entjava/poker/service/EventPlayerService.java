package com.entjava.poker.service;

import com.entjava.poker.dto.*;
import com.entjava.poker.model.Event;
import com.entjava.poker.model.EventPlayer;
import com.entjava.poker.model.PlayerEntity;
import com.entjava.poker.repository.EventPlayerRepository;
import com.entjava.poker.repository.PlayerRepository;
import com.entjava.poker.service.EventService;
import com.entjava.poker.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventPlayerService {

    @Autowired
    private EventPlayerRepository eventPlayerRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    public List<EventPlayerDTO> getPlayersByEventId(Integer eventId) {
        return eventPlayerRepository.findByEventId(eventId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EventPlayerDTO> getEventsByPlayerId(Integer playerId) {
        return eventPlayerRepository.findByPlayerId(playerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getWinnersByEventId(Integer eventId) {
        Set<EventPlayer> winners = eventPlayerRepository.findByEventIdAndIsWinnerTrue(eventId);

        return winners.stream()
                .map(eventPlayer -> eventPlayer.getPlayer().getName())
                .collect(Collectors.toList());
    }

    public List<PlayerHandDTO> getPlayerNamesAndHandsByEventId(Integer eventId) {
        return eventPlayerRepository.findByEventId(eventId)
                .stream()
                .map(eventPlayer -> new PlayerHandDTO(
                        eventPlayer.getPlayer().getName(),
                        eventPlayer.getHand()
                ))
                .collect(Collectors.toList());
    }

    public Optional<EventResultDTO> custom_getEventById(Integer id) {

        Optional<Event> events = eventService.getEventById(id);
        List<PlayerHandDTO> players = this.getPlayerNamesAndHandsByEventId(id);
        List<String> winners = this.getWinnersByEventId(id);
        EventResultDTO response = new EventResultDTO(players, winners);

        return Optional.of(response);
    }

    public List<String> checkPlayerNamesExist(List<String> playerNames) {

        List<PlayerEntity> foundPlayers = playerRepository.findByNameIn(playerNames);

        Set<String> foundPlayerNames = foundPlayers.stream()
                .map(PlayerEntity::getName)
                .collect(Collectors.toSet());

        return playerNames.stream()
                .filter(name -> !foundPlayerNames.contains(name))
                .collect(Collectors.toList());
    }

    public Map<String, Long> getTotalWinsByPlayer() {
        List<Object[]> results = eventPlayerRepository.findTotalWinsByPlayer();
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1]
                ));
    }
    
    private EventPlayerDTO convertToDTO(EventPlayer eventPlayer) {
        EventPlayerDTO dto = new EventPlayerDTO();
        dto.setId(eventPlayer.getId());
        dto.setIsWinner(eventPlayer.isWinner());
        dto.setHands(eventPlayer.getHand());

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(eventPlayer.getPlayer().getId());
        playerDTO.setName(eventPlayer.getPlayer().getName());
        dto.setPlayer(playerDTO);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(eventPlayer.getEvent().getId());
        dto.setEvent(eventDTO);

        return dto;
    }
}
