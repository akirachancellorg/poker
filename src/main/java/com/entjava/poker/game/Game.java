package com.entjava.poker.game;

import com.entjava.poker.card.Card;
import com.entjava.poker.deck.Deck;
import com.entjava.poker.deck.DeckBuilder;
import com.entjava.poker.hand.Hand;
import com.entjava.poker.hand.HandIdentifier;
import com.entjava.poker.hand.WinningHandCalculator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Game {

    private List<Player> players = new ArrayList<>();
    private List<Card> communityCards = new ArrayList<>();
    private DeckBuilder deckBuilder;
    private HandIdentifier handIdentifier;
    private WinningHandCalculator winningHandCalculator;
    private Deck deck;
    private Hand winningHand = null;
    private static final int MAX_PLAYER_CARDS = 2;
    private static final int MAX_COMMUNITY_CARDS = 5;

    public Game(DeckBuilder deckBuilder, HandIdentifier handIdentifier, WinningHandCalculator winningHandCalculator) {
        this.deckBuilder = deckBuilder;
        this.handIdentifier = handIdentifier;
        this.winningHandCalculator = winningHandCalculator;
        addPlayers(); // Directly adding players
        startNewGame();
    }

    private void addPlayers() {
        List<String> playerNames = Arrays.asList("Dadan", "Haze", "Joanny", "Migs", "Jude", "Robyn", "Jesse");
        Collections.shuffle(playerNames); // Shuffle the list to randomize the selection

        // Select the first 4 players from the shuffled list
        for (int i = 0; i < 4; i++) {
            players.add(new Player(playerNames.get(i)));
        }
    }

    public void startNewGame() {
        players.forEach(Player::clearHand);
        communityCards.clear();
        deck = deckBuilder.buildDeck();
        deck.shuffle();
        dealHands();
    }

    public void nextAction() {
        if (communityCards.isEmpty()) {
            burnCard();
            dealThreeCommunityCards();
        } else if (communityCards.size() < MAX_COMMUNITY_CARDS) {
            burnCard();
            dealOneCommunityCard();
        }

        players.forEach(player -> {
            Hand playerHand = handIdentifier.identifyHand(player.getHand(), communityCards);
            player.setPlayableHand(playerHand);
        });

        if (hasEnded()) {
            identifyWinningHand();
        }
    }

    public void identifyWinningHand() {
        List<Hand> playerHands = players.stream()
                .map(this::identifyPlayerHand)
                .collect(Collectors.toList());

        playerHands.sort((h1, h2) -> {
            int rankComparison = Integer.compare(h2.getRank(), h1.getRank());
            if (rankComparison != 0) return rankComparison;
            for (int i = 0; i < h1.getCurrentHand().size(); i++) {
                int cardComparison = h2.getCurrentHand().get(i).compareTo(h1.getCurrentHand().get(i));
                if (cardComparison != 0) return cardComparison;
            }
            return 0;
        });

        Hand winningHand = playerHands.get(0); // Get the winning hand
        Player winningPlayer = players.get(playerHands.indexOf(winningHand)); // Get the player with the winning hand

        winningHand.setPlayer(winningPlayer); // Set the player to the winning hand
        this.winningHand = winningHand; // Save the winning hand in the game
        System.out.println("Winner: " + winningPlayer.getName() + " with hand: " + winningHand);
    }

    public Optional<Hand> getWinningHand() {
        return Optional.ofNullable(winningHand);
    }



    public boolean checkIfPlayerWon(Player player) {
        Hand playerHand = identifyPlayerHand(player);
        return winningHand != null && (winningHand.getCurrentHand()).equals(player.getPlayableHand().getCurrentHand());
    }

    public Hand identifyPlayerHand(Player player) {
        List<Card> playerCards = player.getHand();
        Hand playableHand = handIdentifier.identifyHand(playerCards, communityCards);
        player.setPlayableHand(playableHand);
        return playableHand;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public boolean hasEnded() {
        return communityCards.size() >= MAX_COMMUNITY_CARDS;
    }

    private void dealHands() {
        for (int i = 0; i < MAX_PLAYER_CARDS; i++) {
            dealOneCardToEachPlayer();
        }
    }

    private void dealOneCardToEachPlayer() {
        players.forEach(player -> player.addToHand(deck.removeFromTop()));
    }

    private void dealThreeCommunityCards() {
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
    }

    private void dealOneCommunityCard() {
        communityCards.add(deck.removeFromTop());
    }

    private void burnCard() {
        deck.removeFromTop();
    }

    public String displayCurrentHand(Player player) {
        return player.getHand().get(0).getRank().toString();
    }
}
