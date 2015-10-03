package com.bock.tella.cucca;

import java.util.Random;

/**
 * Created by Mauro on 08/09/2015.
 * A class that simulate a deck of 40 cards
 */
public class Deck {
    public final byte CARDS_NUMBER = 40;
    private byte[] deck;
    private byte top;

    /**
     * Create a 40 cards deck
     */
    public Deck() {
        deck = new byte[CARDS_NUMBER];
        for (byte i = 0; i < CARDS_NUMBER; i++)
            deck[i] = i;
        top = CARDS_NUMBER - 1;
    }

    /**
     * Mix the deck
     */
    public void mix() {
        top = CARDS_NUMBER - 1;
        Random random = new Random();
        for (int i = 0; i < CARDS_NUMBER; i++) {
            int next = random.nextInt(CARDS_NUMBER);
            byte tmp = deck[i];
            deck[i] = deck[next];
            deck[next] = tmp;
        }
    }

    /**
     * Draw the card on the top of the deck
     *
     * @return The number of the card on the top, return -1 if the deck is over
     */
    public byte draw() {
        if (top == -1)
            return -1;
        byte topCard = deck[top];
        top--;
        return topCard;
    }

    /**
     * Show the status of the deck in string form
     *
     * @return the number of the card separated by a space
     */
    public String toString() {
        String output = "";
        for (int i = 0; i <= top; i++)
            output = output + deck[i] + " ";
        return output;
    }
}

