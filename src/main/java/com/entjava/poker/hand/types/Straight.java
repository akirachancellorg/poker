package com.entjava.poker.hand.types;


import com.entjava.poker.card.Card;
import com.entjava.poker.hand.Hand;
import com.entjava.poker.hand.HandType;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight">What is a Straight?</a>
 */
public class Straight extends Hand {

    private List<Card> cards;

    public Straight(List<Card> cards) {
        this.cards = cards;
        setCurrentHand(this.cards.subList(0,5));
    }

    public HandType getHandType() {
        return HandType.STRAIGHT;
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * @return The name of the hand and the high card, e.g. Straight (A High)
     */
    @Override
    public String toString() {
        return "Straight (" + cards.get(0).getRank() + " High)";
    }

}
