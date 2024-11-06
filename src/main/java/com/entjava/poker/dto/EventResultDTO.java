package com.entjava.poker.dto;

import java.util.List;

public class EventResultDTO {
    private List<PlayerHandDTO> players;
    private List<String> winners;  // Changed to a list of winners

    // Constructor
    public EventResultDTO(List<PlayerHandDTO> players, List<String> winners) {
        this.players = players;
        this.winners = winners;
    }

    // Getters and Setters
    public List<PlayerHandDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerHandDTO> players) {
        this.players = players;
    }

    public List<String> getWinners() {
        return winners;
    }

    public void setWinners(List<String> winners) {
        this.winners = winners;
    }
}
