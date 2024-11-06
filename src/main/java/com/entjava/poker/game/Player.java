package com.entjava.poker.game;

import com.entjava.poker.hand.Hand;
import com.entjava.poker.card.Card;

import javax.persistence.*;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * A player in the game.
 */
@Entity
@Table(name = "players")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	@Transient
	private List<Card> hand = new ArrayList<>();

	public Player() {};
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Transient
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

	@Transient
	public Hand getPlayableHand() {
		return playableHand;
	}

	public void setPlayableHand(Hand playableHand) {
		this.playableHand = playableHand;
	}

	@Transient
	private Hand playableHand;

}
