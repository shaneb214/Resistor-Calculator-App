package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTest = findViewById(R.id.btnTest);


       // String[] array = getResources().getStringArray(R.array.randomColours);



        //How to set a button's background colour to random colour from array.
        int[] randomColours = getResources().getIntArray(R.array.randomColours);
        ViewCompat.setBackgroundTintList(btnTest, ColorStateList.valueOf(randomColours[new Random().nextInt(randomColours.length)]));

    }
}