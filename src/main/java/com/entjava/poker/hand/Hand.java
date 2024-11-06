package com.entjava.poker.hand;

import com.entjava.poker.card.Card;
import com.entjava.poker.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {

    private List<Card> currentHand = new ArrayList<>();
    private Player player;
    private int rank;

    public Hand(List<Card> currentHand) {
        this.currentHand = currentHand;
        this.rank = calculateHandRank();
    }

    public Hand() {
        // Empty constructor for placeholders
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<Card> getCurrentHand() {
        return currentHand;
    }

    public void setCurrentHand(List<Card> currentHand) {
        this.currentHand = currentHand;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Calculates the rank of the hand (1 = Royal Flush, 2 = Straight Flush, etc.)
    public int calculateHandRank() {
        // Example logic for calculating hand rank
        if (isRoyalFlush()) return 1;
        if (isStraightFlush()) return 2;
        // Continue for other hand rankings...
        return 0;
    }

    private boolean isRoyalFlush() {
        // Implement logic to check if the hand is a Royal Flush
        return false; // Placeholder
    }

    private boolean isStraightFlush() {
        // Implement logic to check if the hand is a Straight Flush
        return false; // Placeholder
    }

    // Implement other poker hand recognition methods like isFourOfAKind(), isFullHouse(), etc.

    @Override
    public String toString() {
        return currentHand.stream()
                .map(card -> card.getRank().toString() + " of " + card.getSuit().toString())
                .collect(Collectors.joining(", "));
    }
}
