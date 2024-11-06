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

	public Player() {};
	public Player(String name) {
		this.name = name;
	}

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

	public Hand getPlayableHand() {
		return playableHand;
	}

	public void setPlayableHand(Hand playableHand) {
		this.playableHand = playableHand;
	}

	private Hand playableHand;

}
