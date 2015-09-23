package com.bock.tella.cucca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    Deck deck = new Deck();
    Player player = new Player(0);
    TextView[] card;
    int count = 0;
    Cucca cucca = new Cucca(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A method called by the button "mescola"
     * @param view the button reference
     */
    public void show(View view) {
        TextView textView = (TextView) findViewById(R.id.text_view);
        deck.mix();
        textView.setText(deck.toString());

        Button button = (Button) findViewById(R.id.winner_button);
        button.setText("Pesca");
        button.setVisibility(View.VISIBLE);

        byte numberOfCard = player.getNumberOfCards();
        for(byte i = 0; i < numberOfCard; i++)
            player.playByIndex(i);

        showHand();
    }

    /**
     * A method called by the button "Pesca"
     * @param view the button reference
     */
    public void winner(View view) {
        count++;
        if (count != 6) {
            TextView textView2 = (TextView) findViewById(R.id.text_view2);
            byte top = deck.draw();
            if (top != -1)
                textView2.setText(top + "");
            else
                textView2.setText("Il mazzo è finito!!");
            TextView textView = (TextView) findViewById(R.id.text_view);
            textView.setText(deck.toString());

            player.draw(top);
            cucca.tableAddCard(top, 0);

            showHand();
        }
        else {
            Button button = (Button) view;
            button.setVisibility(View.GONE);
            TextView textView2 = (TextView) findViewById(R.id.text_view2);
            byte top = deck.draw();
            if (top != -1)
                textView2.setText("La tavola e' " + top + "");
            cucca.setEndOfDeck(top);
            int winner = cucca.tableWinner();
            TextView textView = (TextView) findViewById(R.id.text_view);
            byte[] table = cucca.getTable();
            textView.setText("La carta che vince è " + table[winner]);
            count = 0;
            cucca.cleanTable();
        }
        if (count == 5){
            Button button = (Button) view;
            button.setText("Vedi Vincitore");
        }
    }

    /**
     * A method that permits to show the cards in hand
     */
    private void showHand() {
        String textureHand = "In mano hai " + player.getNumberOfCards() + " carte.\n";
        TextView textView3 = (TextView) findViewById(R.id.text_view3);
        textView3.setText(textureHand);

        final LinearLayout card_container = (LinearLayout) findViewById(R.id.card_conteiner);
        card_container.removeAllViews();

        byte[] hand = player.getHand();

        card = new TextView[5];
        for (int i = 0; i < hand.length; i++) {
            card[i] = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            card[i].setGravity(Gravity.CENTER);
            card[i].setTextSize(25);
            card[i].setText(hand[i] + "");
            card[i].setLayoutParams(params);
            card[i].setClickable(true);
            final int index = i;
            card[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    play(index);
                }

            });
            card_container.addView(card[i]);
        }
    }

    /**
     * A simple method that permits to play a card
     * @param index the index of the card in the hand to play
     */
    private void play(int index) {
        try {
            byte cardNumber = Byte.parseByte(String.valueOf(card[index].getText()));
            player.play(cardNumber);
        } catch (Exception e) {
            //TODO do something
        }
        showHand();
    }
}
