package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceActivity extends AppCompatActivity
{
    enum GameState
    {
        SHOWING_SEQUENCE,
        READING_SEQUENCE_INPUT
    }

    private GameState gameState;

    //Activity Elements.
    private TextView tvGameState;
    private Button btnNorth, btnSouth,btnEast,btnWest;
    private Button[] buttons;

    //Showing sequence to user.
    private int sequenceIndex = 0;
    private Handler sequenceHandler = new Handler();
    private Button buttonInSequence;
    private int millisecondsBetweenButtonFlashing = 1500;
    private int millisecondsToFlash = 500;
    private int millisecondsToGoToDefaultColour = 500;



    int[] buttonColours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        tvGameState = findViewById(R.id.tvGameState);
        btnNorth = findViewById(R.id.btnNorth);
        btnSouth = findViewById(R.id.btnSouth);
        btnEast = findViewById(R.id.btnEast);
        btnWest = findViewById(R.id.btnWest);
        buttons = new Button[]{btnNorth,btnSouth,btnEast,btnWest};
        GameInfo.startingSequenceAmount = buttons.length;


        buttonColours = getIntent().getIntArrayExtra("ButtonColours");


        for(int i = 0; i < buttons.length;i++)
        {
            ViewCompat.setBackgroundTintList(buttons[i], ColorStateList.valueOf(buttonColours[i]));
        }


        ShowSequenceToUser();
    }

    private void ShowSequenceToUser()
    {
        gameState = GameState.SHOWING_SEQUENCE;
        tvGameState.setText("Showing Sequence");
        sequenceRunnable.run();
    }

    private Runnable sequenceRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            buttonInSequence = buttons[GameInfo.sequence.get(sequenceIndex)];
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
                    sequenceHandler.postDelayed(toDefaultRunnable, millisecondsToGoToDefaultColour);

                } // end runnable
            };
            sequenceHandler.postDelayed(toWhiteRunnable, millisecondsToFlash);


            if(sequenceIndex + 1 < GameInfo.sequence.size())
            {
                sequenceHandler.postDelayed(this,millisecondsBetweenButtonFlashing);
                sequenceIndex++;
            }
            else
            {
                gameState = GameState.READING_SEQUENCE_INPUT;
                tvGameState.setText("Enter Sequence");
            }
        }
    };

    private int GetColourOfButton(Button button) { return button.getBackgroundTintList().getDefaultColor(); }

}