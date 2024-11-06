package com.entjava.poker.game;

import com.entjava.poker.hand.Hand;
import com.entjava.poker.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A player in the game.
 */
public class Player {

	private String name;
	private List<Card> hand = new ArrayList<>();
	private boolean isWinner = false;
	private boolean isLoser = false;

	// Constructor
	public Player(String name) {
		this.name = name;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return hand;
	}

	void addToHand(Card card) {
		hand.add(card);
	}

	void clearHand() {
		hand.clear();
	}

	public String toString() {
		return name;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean winner) {
		isWinner = winner;
	}

	public boolean isLoser() {
		return isLoser;
	}

	public void setLoser(boolean loser) {
		isLoser = loser;
	}

	public Hand getPlayableHand() {
		return playableHand;
	}

	public void setPlayableHand(Hand playableHand) {
		this.playableHand = playableHand;
	}

	private Hand playableHand;
}

