package com.bock.tella.cucca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        switch (item.getItemId()) {
            case R.id.action_test:
                startTest();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_guide:
                startGuide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    /**
     * startTest() create a new Intent for execute some tests
     */
    private void startTest() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    /**
     * startGuide() create a new Intent that explain the game rules
     */
    private void startGuide() {
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
    }

}
