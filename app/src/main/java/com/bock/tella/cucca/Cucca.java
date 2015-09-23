package com.bock.tella.cucca;


/**
 * Created by Mauro on 09/09/2015.
 * A class that manage the game
 */
public class Cucca {
    private Deck deck;
    private int numOfPlayer;
    private int currentTurn;
    private int topOfTable;
    private byte endOfDeck;        // briscola
    private byte[] table;
    private int[] mapCardPlayer;   // Need to mapping the cards play by the players
    private Player[] players;
    private int[] currentTurnPoints;

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
        currentTurnPoints = new int[numOfPlayer];

        setCurrentTurn(0);
        cleanTable();

        deck = new Deck();
    }

    /**
     * A method that return the id of the player
     * @return an int number that represents the id of the player
     */
    public int getCurrentTunr(){
        return currentTurn;
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
     * A method that return the player's hand with id index
     * @param index the id of the player
     * @return a byte array that represent the hand of the index player
     */
    public byte[] getPlayerHand(int index){
        return players[index].getHand();
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
     * @param end the card at the bottom of the deck, must be between 0 and 39
     */
    public void setEndOfDeck(byte end){
        if(end < 0 || end >= 40)
            throw new IllegalArgumentException("The card " + end + " is not a valid card");
        endOfDeck = end;
    }

    /**
     * A method that add a card at the table
     * @param card the card to add, must be between 0 and 39
     * @param playerID the id of the player that play the card
     */
    public void tableAddCard(byte card, int playerID){
        if(card < 0 || card >= 40)
            throw new IllegalArgumentException("The card " + card + " is not a valid card");
        mapCardPlayer[topOfTable] = playerID;
        table[topOfTable++] = card;
    }

    /**
     * A method that check who win this hand
     * @return the index of the card that wins this hand (index in the table)
     */
    public int tableWinner(){ //TODO change in the player that wins this hand
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
     * A method that check who win this hand
     * @return the index of the player that wins this hand (index in the table)
     */
    public int playerTableWinner(){
        int winner = -1;
        byte max = -1;
        for(int i = 0; i < table.length; i++){
            byte tmp = cardValueConverter(table[i]);
            if(tmp > max){
                max = tmp;
                winner = i;
            }
        }
        return mapCardPlayer[winner];
    }

    /**
     * A method that permits to a player to change at most 4 cards
     * @param cardsToChange a byte array that contains the card value to change
     * @param playerID the player id that change the card
     */
    public void changeCards(byte[] cardsToChange, int playerID){
        if(cardsToChange.length >= 5)
            throw new IllegalArgumentException("You can change at most 4 cards!");

        for (byte card : cardsToChange) {
            players[playerID].play(card);
            players[playerID].draw(deck.draw());
        }
    }

    /**
     * A method that permits to a player to change at most 4 cards
     * @param cardsToChange a byte array that contains the card index in the hand to change
     * @param playerID the player id that change the card
     */
    public void changeCardsByIndex(byte[] cardsToChange, int playerID){
        if(cardsToChange.length >= 5)
            throw new IllegalArgumentException("You can change at most 4 cards!");

        for (byte cardIndex : cardsToChange) {
            players[playerID].playByIndex(cardIndex);
            players[playerID].draw(deck.draw());
        }
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

    /**
     * A method that check if the player can play the card or not
     * @param card the card to play
     * @param player the player that wants to play a card
     * @return true if the card is playable, false otherwise
     */
    public boolean isValidCard(byte card, Player player){ //TODO return different values
        byte firstCard = table[0];
        if(firstCard == -1)               // There are no card on the table
            return true;

        if (hasSameSeed(firstCard, card)) // The seed is the same on the table
            return true;

        // the card has not the same seed
        if(!hasSeedInHand(firstCard, player)){     // Check if has not same seed in hand
            if(!hasSeedInHand(endOfDeck, player))  // Check if has not "briscola"
                return true;
            else
                return false;                      // The player has a "briscola"
        }

        return false;
    }

    /**
     * A method that start the game give 5 cards for each players and set the end of deck
     */
    public void startGame(){
        deck.mix();
        for(int i = 0; i < numOfPlayer; i++){  // each players draw 5 card
            for(int j = 0; j < 5; j++){
                players[i].draw(deck.draw());
            }
            currentTurnPoints[i] = 0;
        }

        setEndOfDeck(deck.draw());
    }

    /**
     * This method end the phase of the game and update the players' point, and check if someone wins the match
     * @return the ID of the winner or -1 if nobody win this match
     */
    public int endGame(){
        int minPoint = players[0].getPoints();
        int winnerID = -1;
        for(int i = 0; i < currentTurnPoints.length; i++){
            if(currentTurnPoints[i] == 0)
                players[i].addPoints(5);
            else
                players[i].removePoints(currentTurnPoints[i]);

            if (players[i].getPoints() < minPoint){
                minPoint = players[i].getPoints();
                winnerID = i;
            }
        }

        if(players[winnerID].getPoints() <= 0)
            return winnerID;
        return -1;
    }


    /**
     * This method converter the value of the card in relation with the "briscola" and the first
     * card plays in the table
     * @param card the card to converter
     * @return a byte number between 20 and 29 for the briscola's card,
     *                       between 10 and 19 for the card that have the same seed of the endOfDeck
     *                       between  0 and  9 for the card that have no value in this hand
     */
    private byte cardValueConverter(byte card){
        byte newCard = (byte) (card / 10);

        if(hasSameSeed(card, endOfDeck))
            return (byte)(card - newCard * 10 + 20); // Delete the decade and add 20
        if(hasSameSeed(card, table[0]))
            return (byte)(card - newCard * 10 + 10); // Delete the decade and add 10
        return (byte) (card - newCard * 10);
    }

    /**
     * A method that check if two cards has the same seed
     * @param card1 the first card to check
     * @param card2 the second card to check
     * @return true if the cards has the same seed, false otherwise
     */
    private boolean hasSameSeed(byte card1, byte card2){
        byte newCard1 = (byte) (card1 / 10);
        byte newCard2 = (byte) (card2 / 10);
        return newCard1 == newCard2;
    }

    /**
     * A method that check if a player has a seed in hand, the seed is determinate by a card
     * @param card the card that represent the seed to check
     * @param player the player to check if has the seed of card in hand
     * @return true if the player has the seed, false otherwise
     */
    private boolean hasSeedInHand(byte card, Player player){
        byte[] hand = player.getHand();

        for (byte cardHand : hand)
            if (hasSameSeed(card, cardHand))
                return true;

        return false;
    }
}
