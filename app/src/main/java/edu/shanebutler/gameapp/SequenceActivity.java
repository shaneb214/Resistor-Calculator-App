package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceActivity extends AppCompatActivity implements SensorEventListener
{
    //Activity Elements.
    private TextView tvX, tvY, tvZ, tvOrientation;
    private Button btnNorth, btnSouth,btnEast,btnWest;
    private ArrayList<Button> buttons;

    int[] buttonColours;

    //Sensor.
    private SensorManager sensorManager;
    private Sensor sensor;

    //Selecting sequence.
    private int currentSequenceIndex;
    private boolean buttonIsSelected;
    private int indexOfSelectedButton;
    private int defaultColourOfButtonSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        tvX = findViewById(R.id.tvX);
        tvY = findViewById(R.id.tvY);
        tvZ = findViewById(R.id.tvZ);
        tvOrientation = findViewById(R.id.tvOrientation);


        btnNorth = findViewById(R.id.btnNorth);
        btnSouth = findViewById(R.id.btnSouth);
        btnEast = findViewById(R.id.btnEast);
        btnWest = findViewById(R.id.btnWest);
        buttons = new ArrayList<Button>(){};
        buttons.add(btnNorth);
        buttons.add(btnSouth);
        buttons.add(btnEast);
        buttons.add(btnWest);
        GameInfo.startingSequenceAmount = buttons.size();


        buttonColours = getIntent().getIntArrayExtra("ButtonColours");


        for(int i = 0; i < buttons.size();i++)
        {
            ViewCompat.setBackgroundTintList(buttons.get(i), ColorStateList.valueOf(buttonColours[i]));
        }

    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);
        float z = Math.abs(event.values[2]);

        tvX.setText(String.valueOf(x));
        tvY.setText(String.valueOf(y));
        tvZ.setText(String.valueOf(z));


        if(PhoneTiltedNorth(x,y,z) && !buttonIsSelected)
        {
            OnButtonSelected(btnNorth);

            tvOrientation.setText("North");
        }
        else if(PhoneTiltedSouth(x,y,z) && !buttonIsSelected)
        {
            OnButtonSelected(btnSouth);

            tvOrientation.setText("South");
        }
        else if(PhoneTiltedEast(y,x) && !buttonIsSelected)
        {
            OnButtonSelected(btnEast);

            tvOrientation.setText("East");
        }
        else if(PhoneTiltedWest(x,y) && !buttonIsSelected)
        {
            OnButtonSelected(btnWest);

            tvOrientation.setText("West");
        }
        else
        {
            tvOrientation.setText("NONE");

            if(buttonIsSelected)
            {
                buttonIsSelected = false;
                ViewCompat.setBackgroundTintList(buttons.get(indexOfSelectedButton), ColorStateList.valueOf(defaultColourOfButtonSelected));

                //CHECK IF INDEX OF SELECTED BUTTON MATCHES SEQUENCE INDEX.
                //IF YES - INCREASE SEQUENCE COUNTER.
                // CHECK IF AT END OF SEQUENCE ARRAY.
                // IF YES - LOAD MAIN ACTIVITY, GO TO NEXT ROUND.

                //IF NO - RESET ROUND TO 1. RESET SEQUENCE ARRAY. GO BACK TO MAIN ACTIVITY.

            }
        }
    }

    private void OnButtonSelected(Button buttonToSelect)
    {
        buttonIsSelected = true;

        //Find Button to select.
        indexOfSelectedButton = buttons.indexOf(buttonToSelect);
        Button selectedButton = buttons.get(indexOfSelectedButton);

        //Colours.
        defaultColourOfButtonSelected = GetColourOfButton(selectedButton);
        ViewCompat.setBackgroundTintList(selectedButton, ColorStateList.valueOf(getResources().getColor(R.color.White)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private int GetColourOfButton(Button button) { return button.getBackgroundTintList().getDefaultColor(); }


    private boolean PhoneTiltedNorth(float X, float Y,float Z){return X > 3f && Y < 0.6f && Z < 10f;}     //North - X greater than 3.5 AND Y less than 0.3     X > 4 && Y < 0.4
    private boolean PhoneTiltedSouth(float X,float Y, float Z){return X > 3f && Y < 0.2f && Z < 7.5;}            //South - X greater than 6 AND Z less than 7 Y < 0.3
    private boolean PhoneTiltedEast(float Y, float X){return Y > 3 && X < 1;}            //East - Y greater than 3 AND X less than 1.
    private boolean PhoneTiltedWest(float X, float Y){return X > 1 && Y > 3;}            //West - X greater than 1 AND Y greater than 3

}