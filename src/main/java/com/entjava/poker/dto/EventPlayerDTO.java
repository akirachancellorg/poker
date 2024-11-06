package com.entjava.poker.dto;

public class EventPlayerDTO {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    private PlayerDTO player;
    private EventDTO event;
    private boolean isWinner;
    private String hands;

    public String getHands() {
        return hands;
    }

    public void setHands(String hands) {
        this.hands = hands;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setIsWinner(boolean winner) {
        isWinner = winner;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }



}

