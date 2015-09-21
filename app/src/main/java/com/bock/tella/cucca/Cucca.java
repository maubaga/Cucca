package com.bock.tella.cucca;


/**
 * Created by Mauro on 09/09/2015.
 * A class that manage the game
 */
public class Cucca {
    private int numOfPlayer;
    private int currentTurn;
    private int topOfTable;
    private byte endOfDeck;
    private byte[] table;
    private int[] mapCardPlayer;   // Need to mapping the cards play by the players
    private Player[] players;

    /**
     * Class constructor that generate and manage the game, id make an id for each player,
     * the id go from 0 to numOfPlayer - 1
     * @param numOfPlayer the number of players at the game, must be between 2 and 7
     */
    public Cucca(int numOfPlayer){
        if(numOfPlayer < 2 || numOfPlayer > 7)
            throw new IllegalArgumentException("The number " + numOfPlayer + " is not valid!");

        this.numOfPlayer = numOfPlayer;
        table = new byte[numOfPlayer];

        players = new Player[numOfPlayer];
        for(int i = 0; i < numOfPlayer; i++)
            players[i] = new Player(i);

        mapCardPlayer = new int[numOfPlayer];

        setCurrentTurn(0);
        cleanTable();
    }

    /**
     * A method that return the cards on the table
     * @return a byte array that contains the cards on the table
     */
    public byte[] getTable(){
        byte[] output = new byte[topOfTable];
        int count = 0;
        for(int i = 0; i < topOfTable; i++)
            if(table[i] != -1)
                output[count++] = table[i];
        return output;
    }

    /**
     * A method that return the player with id index
     * @param index the id of the player
     * @return
     */
    public Player getPlayer(int index){
        return players[index];
    }

    /**
     * A method that return the id of the player
     * @return
     */
    public int getCurrentTunr(){
        return currentTurn;
    }
    /**
     * A method that set the player's turn
     * @param turn  the id of the player
     */
    public void setCurrentTurn(int turn){
        if(turn < 0 || turn >= numOfPlayer)
            throw new IllegalArgumentException("The player " + turn + " doesn't exist!" );
        currentTurn = turn;
    }

    /**
     * A method that set the main seed at the bottom of the deck
     * @param end the card at the bottom of the deck
     */
    public void setEndOfDeck(byte end){
        if(end < 0 || end >= 40)
            throw new IllegalArgumentException("The card " + end + " is not a valid card");
        endOfDeck = end;
    }

    /**
     * A method that add a card at the table
     * @param card the card to add
     */
    public void tableAddCard(byte card){
        if(card < 0 || card >= 40)
            throw new IllegalArgumentException("The card " + card + " is not a valid card");
        table[topOfTable++] = card;
    }

    /**
     * A method that check who win this hand
     * @return
     */
    public int tableWinner(){
        int winner = -1;
        byte max = -1;
        for(int i = 0; i < table.length; i++){
            byte tmp = cardValueConverter(table[i]);
            if(tmp > max){
                max = tmp;
                winner = i;
            }
        }
        return winner;
    }

    /**
     * A method that clean the table
     */
    public void cleanTable(){
        for(int i = 0; i < numOfPlayer; i++){
            table[i] = -1;
            mapCardPlayer[i] = -1;
        }
        topOfTable = 0;
    }


    public boolean isValidCard(byte card, Player player){
        if(table[0] == -1)               // There are no card on the table
            return true;

        if (hasSameSeed(table[0], card)) // The seed is the same on the table
            return true;
        if (cardValueConverter(card) >= 20 && !hasSeedInHand(table[0], player)) // The card is a briscola and the player has not the same seed on the table in hand
            return true;

        return false;
    }


    private byte cardValueConverter(byte card){
        byte newCard = (byte) (card / 10);

        if(hasSameSeed(card, endOfDeck))
            return (byte)(card - newCard * 10 + 20); // Delete the decade and add 20
        if(hasSameSeed(card, table[0]))
            return (byte)(card - newCard * 10 + 10); // Delete the decade and add 10
        return (byte) (card - newCard * 10);
    }

    private boolean hasSameSeed(byte card1, byte card2){
        byte newCard1 = (byte) (card1 / 10);
        byte newCard2 = (byte) (card2 / 10);
        if(newCard1 == newCard2)
            return true;
        return false;
    }

    private boolean hasSeedInHand(byte card, Player player){
        byte[] hand = player.getHand();

        for(int i = 0; i < hand.length; i++)
            if(hasSameSeed(card, hand[i]))
                return true;

        return false;
    }
}
