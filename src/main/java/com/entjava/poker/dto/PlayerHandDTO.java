package com.entjava.poker.dto;

public class PlayerHandDTO {
    private String name;
    private String hand;

    // Constructors
    public PlayerHandDTO(String name, String hand) {
        this.name = name;
        this.hand = hand;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }
}

