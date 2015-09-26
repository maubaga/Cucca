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
     *
     */

    public void selectCardToPlay(byte seed,byte[] table){


        if(table.length==0){ // I'm the first
            //I must search if I have ace of briscola!
             int ace=super.findCard((byte)((seed/10)*10+9));

            if(ace!= -1){
                //play the ace
                super.playByIndex((byte)ace);

            }
            else{//play any card

                //generated a random number and play hand[index]
                Random r=new Random();
                byte index=(byte)(r.nextInt((int)(super.getNumberOfCards())));
                super.playByIndex(index);

            }


        }
        else if(table.length>=1){
            //hidden the cards not playable



        }


    }
}
