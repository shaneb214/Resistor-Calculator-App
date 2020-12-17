package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;

public class SequenceActivity extends AppCompatActivity
{

    Button btnNorth;
    Button btnSouth;
    Button btnEast;
    Button btnWest;
    Button[] buttons;

    int[] buttonColours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        btnNorth = findViewById(R.id.btnNorth);
        btnSouth = findViewById(R.id.btnSouth);
        btnEast = findViewById(R.id.btnEast);
        btnWest = findViewById(R.id.btnWest);
        buttons = new Button[]{btnNorth,btnSouth,btnEast,btnEast};


        buttonColours = getIntent().getIntArrayExtra("ButtonColours");

        for(int i = 0; i < buttons.length;i++)
        {
            AssignColourToButton(buttons[i],buttonColours[i]);
        }

    }


    private void AssignColourToButton(Button button,int colour)
    {
        ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(colour));
    }
}