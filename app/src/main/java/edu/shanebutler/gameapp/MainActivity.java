package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);


        //How to set a button's background colour to random colour from array.
        AssignRandomColour(btnOne);
        AssignRandomColour(btnTwo);
        AssignRandomColour(btnThree);
        AssignRandomColour(btnFour);

    }

    private void AssignRandomColour(Button button)
    {
        int[] randomColours = getResources().getIntArray(R.array.randomColours);
        ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf( randomColours[new Random().nextInt(randomColours.length)]));
    }
}