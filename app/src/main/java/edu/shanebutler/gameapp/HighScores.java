package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HighScores extends AppCompatActivity
{
    //UI Elements.
    Button btnPlayAgain;
    TextView tvHighScore1;
    TextView tvHighScore2;
    TextView tvHighScore3;
    TextView tvHighScore4;
    TextView tvHighScore5;
    TextView[] tvHighScoreTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);


        //Finding UI Elements.
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        tvHighScore1 = findViewById(R.id.tvHighScore1);
        tvHighScore2 = findViewById(R.id.tvHighScore2);
        tvHighScore3 = findViewById(R.id.tvHighScore3);
        tvHighScore4 = findViewById(R.id.tvHighScore4);
        tvHighScore5 = findViewById(R.id.tvHighScore5);
        tvHighScoreTable = new TextView[]{tvHighScore1,tvHighScore2,tvHighScore3,tvHighScore4,tvHighScore5};

    }

    public void OnPlayAgainClicked(View view)
    {
        GameInfo.Reset();
        Intent mainIntent = new Intent(view.getContext(),MainActivity.class);
        startActivity(mainIntent);
    }
}