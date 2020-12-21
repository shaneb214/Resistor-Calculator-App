package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tvScoreInfo, tvRoundInfo, tvTop5ScoreNotification;
    EditText etEnterName;
    Button btnSubmitScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvScoreInfo = findViewById(R.id.tvScore);
        tvRoundInfo = findViewById(R.id.tvRound);
        tvTop5ScoreNotification = findViewById(R.id.tvTopScoreNotification);
        etEnterName = findViewById(R.id.etEnterName);
        btnSubmitScore = findViewById(R.id.btnSubmitScore);

        tvScoreInfo.setText("Your score was " + GameInfo.playerScore);
        tvRoundInfo.setText("You made it to round " + GameInfo.roundNumber);


        //If score in top 5?
        tvTop5ScoreNotification.setEnabled(true);
        etEnterName.setEnabled(true);
        btnSubmitScore.setEnabled(true);
        tvTop5ScoreNotification.setVisibility(View.VISIBLE);
        etEnterName.setVisibility(View.VISIBLE);
        btnSubmitScore.setVisibility(View.VISIBLE);

        //Otherwise - disable below.
        //tvTop5ScoreNotification.setEnabled(false);
        //etEnterName.setEnabled(false);
        //btnSubmitScore.setEnabled(false);
        //tvTop5ScoreNotification.setVisibility(View.GONE);
        //etEnterName.setVisibility(View.GONE);
        //btnSubmitScore.setVisibility(View.GONE);

    }

    public void OnPlayAgainClicked(View view)
    {
        GameInfo.Reset();
        Intent mainIntent = new Intent(view.getContext(),MainActivity.class);
        startActivity(mainIntent);
    }

    public void OnShowHighScoresClicked(View view)
    {
        Intent highScoreIntent = new Intent(view.getContext(),HighScores.class);
        startActivity(highScoreIntent);
    }

    public void OnSubmitScoreClicked(View view)
    {
        if(etEnterName.getText().toString() != "")
        {
            //Submit Score to database.
            //Disable edittext and button.
        }
    }
}