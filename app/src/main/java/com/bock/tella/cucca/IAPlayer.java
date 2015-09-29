package com.bock.tella.cucca;

import java.util.Random;

/**
 * Created by bock on 26/09/15.
 */
public class IAPlayer extends  Player {


    /**
     * Class constructor that make a IA player with his id
     * @param id the id of the IA player
     */
    public IAPlayer(int id){
        super(id);
    }

    /**
     * Class constructor that make a IA player with his id
     * @param id the id of the IA player
     * @param points the start points of the IA player
     */
     public IAPlayer(int id,int points){
         super(id,points);
     }

    /**
     * A method that think a whick card play
     * @param seed seed on the table
     * @param table table in this moment
     * @return return the byte index of card and if return -1 there are problem
     */

    public byte selectCardToPlay(byte seed,byte[] table){


        if(table.length==0){ // I'm the first
            //I must search if I have ace of briscola!
             int ace=super.findCard((byte)((seed/10)*10+9));

            if(ace!= -1){
                //play the ace
                //super.playByIndex((byte)ace);
                return (byte)ace;
            }
            else{//play any card

                //generated a random number and play hand[index]
                Random r=new Random();
                byte index=(byte)(r.nextInt((int)(super.getNumberOfCards())));
                //super.playByIndex(index);
                return index;

            }


        }
        else if(table.length>=1){
            //hidden the cards not playable

            byte [] tmp_hand=super.getHand();
            int [] valid_card=new int[tmp_hand.length];
            int valid=0;

            for(int i=0;i<valid_card.length;i++){
                if(Cucca.isValidCard(tmp_hand[i],table,seed,this) == Cucca.VALID_CARD){
                    valid_card[i]=1; valid++;
                }
                else{valid_card[i]=-1;}
            }

            byte [] playable_card= new byte[valid];

            int j=0;

            for(int i=0;i<valid_card.length;i++){
                 if(valid_card[i]==1){
                     playable_card[j]=tmp_hand[i];
                     j++;
                 }
            }

            Random r= new Random();
            byte index=(byte)(r.nextInt(valid));
            return index;


        }

        return -1; //if return -1 there's a problem

    }

    /**
     * A method that choose some cards to change
     * @param hand hand of player
     * @param seed seed of table
     * @return returns an array of cards to change
     */

    public byte[] changeBadCard(byte[] hand,byte seed){

        byte numberChangeCard=0;
        int [] cardChange= new int[hand.length];

        for(int i=0;i<hand.length;i++){
            if(Cucca.hasSameSeed(hand[i],seed)){
                cardChange[i]=1;
            }
            else{cardChange[i]=-1; numberChangeCard++;}

        }

        if(numberChangeCard == 5){
            cardChange[findMax(hand)]=1;
            numberChangeCard--;
        }

        byte[] badCard= new byte[numberChangeCard];
        int j=0;

        for(int i=0;i<cardChange.length;i++){
            if(cardChange[i]== -1){
                badCard[j]=hand[i];
                j++;
            }
        }

    return badCard;

    }


    /**
     * A simple method that find the max in a byte array
     *
     * @param array the array to find the max
     * @return the position of max element in the array
     */
    private static int findMax(byte[] array) {
        int max = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max)
                max = i;
        }
        return max;
    }
}
