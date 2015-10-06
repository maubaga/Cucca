package com.bock.tella.cucca;

/**
 * Created by Mauro on 08/09/2015.
 * A class that manage a player with 5 cards
 */
public class Player {
    private final byte TOTAL_CARDS = 5;
    private byte[] hand;
    private byte numberOfCards;
    private int id;
    private int points;

    /**
     * Class constructor that make a player with his id
     * @param id the id of the player
     */
    public Player(int id){
        hand = new byte[TOTAL_CARDS];
        for(int i = 0; i < TOTAL_CARDS; i++)
            hand[i] = -1;
        numberOfCards = 0;
        this.id = id;
        points = 0;
    }

    /**
     * Class constructor that make a player with his id
     * @param id the id of the player
     * @param points the start points of the player
     */
    public Player(int id, int points){
        hand = new byte[TOTAL_CARDS];
        for(int i = 0; i < TOTAL_CARDS; i++)
            hand[i] = -1;
        numberOfCards = 0;
        this.id = id;
        this.points = points;
    }

    /**
     * A method that add points at the player
     * @param pointsToAdd the number of points to add
     */
    public void addPoints(int pointsToAdd){
        points = points + pointsToAdd;
    }

    /**
     * A method that add a card in the hand of the player
     * @param card the number of the card, it must be between 1 and 40
     */
    public void draw(byte card){
        if(numberOfCards == 5)
            throw new IllegalArgumentException("The hand has already 5 cards");
        if (card < 0 || card >= 40)
            throw new IllegalArgumentException("The number " + card + " is not a valid card!");
        numberOfCards++;
        int emptySlot = findCard((byte) -1);
        hand[emptySlot] = card;
    }

    /**
     * A method that find the index of a card in the hand of the player
     * @param card the number of the card to find
     * @return the index of the card in the hand
     */
    public int findCard(byte card){
        for(int i = 0; i < TOTAL_CARDS; i++)
            if (card == hand[i])
                return i;
        return -1;
    }

    /**
     * A method that return the cards in the hand
     * @return a byte array that contains the cards in the hand
     */
    public byte[] getHand(){
        byte[] hand = new byte[numberOfCards];
        int j = 0;
        for(int i = 0; i < TOTAL_CARDS; i++)
            if(this.hand[i] != -1)
                hand[j++] = this.hand[i];
        return hand;
    }

    /**
     * A method that return the id of the player
     * @return return the id of the player
     */
    public int getId(){
        return id;
    }

    /**
     * A method that return the number of cards in the hand
     * @return a number of card in the hand
     */
    public byte getNumberOfCards(){
        return numberOfCards;
    }

    /**
     * A method that return the points of the player
     * @return the points of the player
     */
    public int getPoints(){
        return points;
    }

    /**
     * A method that play the card
     * @param card the number of card to play, between 1 and 40
     */
    public void play(byte card){
        if (numberOfCards == 0)
            throw new IllegalArgumentException("You don't have enough card!");
        int cardIndex = findCard(card);
        if (cardIndex == -1)
            throw new IllegalArgumentException("The number " + card + " is not in the hand!");
        numberOfCards--;
        hand[cardIndex] = -1;
    }

    /**
     * A method that play the card
     * @param cardIndex the index in the hand of card to play, between 0 and 5
     */
    public void playByIndex(byte cardIndex){
        if (numberOfCards == 0)
            throw new IllegalArgumentException("You don't have enough card!");
        if (cardIndex < 0 || cardIndex >= TOTAL_CARDS)
            throw new IllegalArgumentException("The index " + cardIndex + " is out of hand!");
        numberOfCards--;
        hand[cardIndex] = -1;
    }

    /**
     * A method that remove points to the player
     * @param pointsToRemove the points to remove
     */
    public void removePoints(int pointsToRemove){
        points = points - pointsToRemove;
    }

    /**
     * Return a texture representation of the hand,
     * @return a String that contain the cards' number in the hand separated by a space
     */
    public String toString(){
        String output = "";
        for(int i = 0; i < TOTAL_CARDS; i++){
            if(hand[i] != -1)
                output = output + hand[i] + " ";
        }
        return output;
    }

}
