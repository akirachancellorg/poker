package com.entjava.poker.game;

import com.entjava.poker.hand.Hand;
import com.entjava.poker.hand.WinningHandCalculator;
import com.entjava.poker.model.Event;
import com.entjava.poker.model.EventPlayer;
import com.entjava.poker.card.Card;
import com.entjava.poker.deck.Deck;
import com.entjava.poker.deck.DeckBuilder;
import com.entjava.poker.hand.HandIdentifier;
import com.entjava.poker.model.PlayerEntity;
import com.entjava.poker.repository.EventPlayerRepository;
import com.entjava.poker.repository.EventRepository;
import com.entjava.poker.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The game engine.
 */
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

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventPlayerRepository eventPlayerRepository;

    @Autowired
    private EventRepository eventRepository;

    public Game(DeckBuilder deckBuilder,
                HandIdentifier handIdentifier,
                WinningHandCalculator winningHandCalculator) {
        players.add(new Player("Alex"));
        players.add(new Player("Bob"));
        players.add(new Player("Jane"));

        this.deckBuilder = deckBuilder;
        this.handIdentifier = handIdentifier;
        this.winningHandCalculator = winningHandCalculator;

        startNewGame();
    }

    /**
     * Starts a new game.
     *
     * <h3>The following describes a new game.</h3>
     * <ul>
     * <li>Players' previous hands are cleared</li>
     * <li>Community cards are cleared</li>
     * <li>A new deck is used</li>
     * <li>The deck is shuffled</li>
     * <li>Players' are dealt with new cards.</li>
     * </ul>
     */
    public void startNewGame() {
        players.forEach(Player::clearHand);
        communityCards.clear();

        deck = deckBuilder.buildDeck();
        deck.shuffle();

        dealHands();
    }

    public void startNewGameWithPlayers(List<String> playerNames) {
        this.players = playerNames.stream()
            .map(Player::new)
            .collect(Collectors.toList());
        
        startNewGame();
    }
    /**
     * The action to take after a new game has been started.
     *
     * <ol>
     * <li>Deal three community cards</li>
     * <li>Deal one community card</li>
     * <li>Deal another community card</li>
     * <li>Determine the winner/s</li>
     * </ol>
     * <p>
     * Dealt community are of course removed from the deck at the time their placed on the table.
     */
    public void nextAction() {
        if (communityCards.isEmpty()) {
            burnCard();
            dealThreeCommunityCards();
        } else if (communityCards.size() < MAX_COMMUNITY_CARDS) {
            burnCard();
            dealOneCommunityCard();
        }

        if (hasEnded()) {
            identifyWinningHand();
            saveGameResult();
            System.out.println(winningHand.getCurrentHand());
        }
    }

    public void saveGameResult() {

        Event currentEvent = new Event();
        eventRepository.save(currentEvent);

        List<Player> players = getPlayers();

        for (Player player : players) {
            Optional<PlayerEntity> playerEntityOptional = playerRepository.findByName(player.getName());

            if (playerEntityOptional.isPresent()) {
                PlayerEntity playerEntity = playerEntityOptional.get();

                EventPlayer eventPlayer = new EventPlayer();
                eventPlayer.setPlayer(playerEntity);
                eventPlayer.setEvent(currentEvent);
                eventPlayer.setWinner(checkIfPlayerWon(player));
                eventPlayer.setHand(player.getPlayableHand().getCurrentHand().toString());

                eventPlayerRepository.save(eventPlayer);
            }

        }
        // for each player
        //   get their player ids in database using their string names
        //   check if they are winner
        //   save them in eventplayer table
    }
    /**
     * Checks the combination of the players and community cards to identify the winning hand.
     *
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    public void identifyWinningHand() {
        List<Hand> playerHands = players.stream()
                .map(this::identifyPlayerHand)
                .collect(Collectors.toList());
        Optional<Hand> optionalHand = winningHandCalculator.calculateWinningHand(playerHands);

        winningHand = optionalHand.get();
        System.out.println(winningHand);
    }

    /**
     * Checks if the player won
     *
     * @param player
     * @return true if the player's hand is equal to the winning hand.
     */
    public boolean checkIfPlayerWon(Player player) {
        Hand playerHand = identifyPlayerHand(player);
        return winningHand != null && (winningHand.getCurrentHand()).equals(player.getPlayableHand().getCurrentHand());
    }

    /**
     * Identifies the player's hand. A hand is combination of the two cards in the player's
     * possession and the community cards on the table.
     *
     * @param player
     * @return The {@link} of a player, e.g. High Card, One Pair, Straight, etc.
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    public Hand identifyPlayerHand(Player player) {
        List<Card> playerCards = player.getHand();
        Hand playableHand = handIdentifier.identifyHand(playerCards, communityCards);
        player.setPlayableHand(playableHand);
        return playableHand;
    }

    /**
     * @return The list of {@link Player}s
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return The list of community cards {@link Card}
     */
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * @return true if the number of community cards is equal to the maximum community cards allowed.
     */
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
