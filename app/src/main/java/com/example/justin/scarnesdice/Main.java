package com.example.justin.scarnesdice;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Main extends AppCompatActivity {
    private static Integer userOverall = 0;
    private static Integer turnTotal = 0;
    private static Integer compOverall = 0;
    private static Integer rollVal = 2;
    private static boolean userTurn = true;
    private Integer images[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
                                R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    private Integer currentImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateScore();
        setInitialImage();
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

    public void playGame() {
        while(compOverall < 100 && userOverall < 100) {
            if(userTurn){

            }
        }
    }
    public void randomRoll() {
        Random r = new Random();
        rollVal = r.nextInt(6) + 1;
    }

    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() {
        currentImage = rollVal - 1;
        final ImageView imageView = (ImageView) findViewById(R.id.currentDie);
        imageView.setImageResource(images[currentImage]);
    }


    public void rollDice(View view) {
        randomRoll();
        setCurrentImage();
        if(userTurn) {
            if(rollVal == 1) {
                turnTotal = 0;
                userTurn = false;
                computerTurn();
            }
            else {
                turnTotal += rollVal;
            }
        }
        else
        {
            if(rollVal == 1) {
                turnTotal = 0;
                userTurn = true;
            }
            else {
                turnTotal += rollVal;
            }
        }
        updateScore();
    }

    public void holdDice(View view) {
        if(userTurn) {
            userOverall += turnTotal;
            userTurn = false;
            turnTotal = 0;

            computerTurn();

        }
        else {
            compOverall += turnTotal;
            userTurn = true;
            turnTotal = 0;
        }
        updateScore();
    }

    public void reset(View view) {
        userOverall = 0;
        turnTotal = 0;
        compOverall = 0;
        userTurn = true;

        updateScore();
    }

    public void updateScore () {
        Resources res = getResources();
        String text = String.format(res.getString(R.string.score), userOverall, compOverall, turnTotal);
        ((TextView)findViewById (R.id.textView)).setText(text);
    }

    public void computerTurn (){
        final Button roll = (Button) findViewById(R.id.roll_dice);
        roll.setClickable(false);
        final Button hold = (Button) findViewById(R.id.hold_dice);
        hold.setClickable(false);

        computerRoll();
    }

    public void computerRoll() {
        final Button roll = (Button) findViewById(R.id.roll_dice);
        final Button hold = (Button) findViewById(R.id.hold_dice);
        final Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            @Override
            public void run() {
                if (turnTotal < 20) {
                    roll.performClick();

                } else {
                    hold.performClick();
                }

                if(!userTurn) {
                    timerHandler.postDelayed(this, 500);
                }
                else
                {
                    roll.setClickable(true);
                    hold.setClickable(true);
                }
            }
        };
        timerRunnable.run();

    }
}
