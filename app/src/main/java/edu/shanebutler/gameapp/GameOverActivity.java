package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tvScoreInfo, tvRoundInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvScoreInfo = findViewById(R.id.tvScore);
        tvRoundInfo = findViewById(R.id.tvRound);


        tvScoreInfo.setText("Your score was " + GameInfo.playerScore);
        tvRoundInfo.setText("You made it to round " + GameInfo.roundNumber);
    }

    public void OnPlayAgainClicked(View view)
    {
        GameInfo.Reset();
        Intent mainIntent = new Intent(view.getContext(),MainActivity.class);
        startActivity(mainIntent);
    }

    public void OnShowHighScoresClicked(View view)
    {

    }
}