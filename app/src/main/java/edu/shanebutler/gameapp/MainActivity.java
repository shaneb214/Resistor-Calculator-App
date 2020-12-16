package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button[] buttons;

    ArrayDeque<Integer> randomColours = new ArrayDeque<Integer>(4);
    ArrayList<Integer> colourList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        buttons = new Button[]{btnOne,btnTwo,btnThree,btnFour};


        AddColoursToColourList(); //Add all colours from colours. xml to a list.

        for(int i = 0; i < buttons.length; i++) //Assign random colour to each button. List ensures that same colour isn't chosen more than once.
        {
            int randomIndex = new Random().nextInt(colourList.size());
            int randomColour = colourList.get(randomIndex);

            randomColours.addLast(randomColour);
            AssignColourToButton(buttons[i],randomColour);

            colourList.remove(randomIndex);
        }
    }

    private void AddColoursToColourList()
    {
        int[] colours = getResources().getIntArray(R.array.colours); //Get all possible colours.
        for(int i = 0; i < colours.length; i++) //Add all possible colours to list.
        {
            colourList.add((Integer) colours[i]);
        }
    }

    private void AssignColourToButton(Button button,int colour)
    {
        ViewCompat.setBackgroundTintList(button, ColorStateList.valueOf(colour));
    }
}