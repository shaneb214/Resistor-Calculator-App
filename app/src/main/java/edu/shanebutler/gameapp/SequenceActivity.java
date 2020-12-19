package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceActivity extends AppCompatActivity
{
    Button btnNorth, btnSouth,btnEast,btnWest,fb;
    Button[] buttons;

    private boolean userEnteringSequence;

    private float timeBetweenSequences = 1f;

    private Handler mHandler = new Handler();

    enum GameState
    {
        SHOWING_SEQUENCE,
        READING_SEQUENCE_INPUT
    }

    private GameState gameState;

    //Showing sequence to user.
    private boolean showingSequenceToUser;
    private int[] sequence;
    private int sequenceIndex = 0;
    Handler sequenceHandler = new Handler();
    Button buttonInSequence;



    int[] buttonColours;
    ArrayList<Integer> colourList = new ArrayList<Integer>();
    ArrayDeque<Integer> colourSequence = new ArrayDeque<Integer>(GameInfo.currentTotalSequence);
    ArrayDeque<Button> buttonSequence = new ArrayDeque<Button>(GameInfo.currentTotalSequence);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        btnNorth = findViewById(R.id.btnNorth);
        btnSouth = findViewById(R.id.btnSouth);
        btnEast = findViewById(R.id.btnEast);
        btnWest = findViewById(R.id.btnWest);
        buttons = new Button[]{btnNorth,btnSouth,btnEast,btnWest};


        buttonColours = getIntent().getIntArrayExtra("ButtonColours");


        for(int i = 0; i < buttons.length;i++)
        {
            ViewCompat.setBackgroundTintList(buttons[i], ColorStateList.valueOf(buttonColours[i]));
        }


        sequence = new int[]{1,1,1,0};

        sequenceRunnable.run();

    }

    private Runnable sequenceRunnable = new Runnable() {
        @Override
        public void run()
        {
            buttonInSequence = buttons[sequence[sequenceIndex]];
            Log.i("HELLO",buttonInSequence.getText().toString());

            Runnable toWhiteRunnable = new Runnable() {
                public void run()
                {
                    final int defaultColour = GetColourOfButton(buttonInSequence);
                    ViewCompat.setBackgroundTintList(buttonInSequence, ColorStateList.valueOf(getResources().getColor(R.color.White)));

                    Runnable toDefaultRunnable = new Runnable() {
                        public void run()
                        {
                            ViewCompat.setBackgroundTintList(buttonInSequence, ColorStateList.valueOf(defaultColour));
                        }
                    };
                    sequenceHandler.postDelayed(toDefaultRunnable, 500);

                } // end runnable
            };
            sequenceHandler.postDelayed(toWhiteRunnable, 500);


            sequenceHandler.postDelayed(this,2000);
            sequenceIndex++;
        }
    };


    private void AddColoursToSequence(int amountToAddToSequence)
    {
        for(int i = 0; i < amountToAddToSequence;i++)
        {
            colourSequence.addLast(buttonColours[new Random().nextInt(buttonColours.length)]);
        }
    }

    private void flashButton(final Button button) {
        //fb = button;
        final int defaultColour = GetColourOfButton(button);
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                //button.setPressed(true);
                //button.invalidate();
                //button.performClick();
                ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(getResources().getColor(R.color.White)));
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        //button.setPressed(false);
                        //button.invalidate();
                        ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(defaultColour));
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }


    private int GetColourOfButton(Button button)
    {
        return button.getBackgroundTintList().getDefaultColor();
    }

    private Button GetButtonOfCertainColour(int colour)
    {
        for(int i = 0; i < buttons.length;i++)
        {
            int btnColour = GetColourOfButton(buttons[i]);

            if(btnColour == colour)
                return buttons[i];
        }

        return null;
    }
}