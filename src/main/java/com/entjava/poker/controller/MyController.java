package com.entjava.poker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entjava.poker.model.Event;
import com.entjava.poker.model.PlayerEntity;
import com.entjava.poker.dto.EventResultDTO;
import com.entjava.poker.service.EventService;
import com.entjava.poker.service.PlayerService;
import com.entjava.poker.service.EventPlayerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    private final EventService eventService;
    private final PlayerService playerService;
    private final EventPlayerService eventPlayerService;

    public MyController(
            EventPlayerService eventPlayerService,
            PlayerService playerService,
            EventService eventService
        ) {
        this.playerService = playerService;
        this.eventService = eventService;
        this.eventPlayerService = eventPlayerService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

//    @GetMapping("/event/{id}")
//    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
//        Event event = eventService.getEventById(id).orElse(null);
//        return ResponseEntity.ok(event);
//    }
//    // GetMapping Redefined below

    @GetMapping("/event/{id}")
    public ResponseEntity<EventResultDTO> custom_getEventById(@PathVariable int id) {
        EventResultDTO event = eventPlayerService.custom_getEventById(id).orElse(null);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerEntity>> getAllPlayers() {
        List<PlayerEntity> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<PlayerEntity> getPlayerById(@PathVariable Integer id) {
        PlayerEntity player = playerService.getPlayerById(id).orElse(null);
        return ResponseEntity.ok(player);
    }

}
