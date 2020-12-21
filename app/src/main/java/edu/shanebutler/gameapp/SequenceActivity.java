package edu.shanebutler.gameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.Intent;
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

    //Sensor fields for determining rotation of phone - using magnetic field and accelerometer sensors.
    private SensorManager sensorManager;
    private Sensor aSensor;
    private Sensor mSensor;

    private float gravity[];
    private float magnetic[];
    private float accels[] = new float[3];
    private float mags[] = new float[3];
    private float[] values = new float[3];

    private float azimuth;
    private float pitch;
    private float roll;

    //Selecting sequence.
    private int currentSequenceIndex = 0;
    private int indexOfSelectedButton;
    private Button selectedButton;
    private boolean buttonIsSelected;
    private int defaultColourOfButtonSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);


        sensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);


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
        sensorManager.registerListener(this, aSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mags = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accels = event.values.clone();
                break;
        }

        if (mags != null && accels != null) {
            gravity = new float[9];
            magnetic = new float[9];
            SensorManager.getRotationMatrix(gravity, magnetic, accels, mags);
            float[] outGravity = new float[9];
            SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X,SensorManager.AXIS_Z, outGravity);
            SensorManager.getOrientation(outGravity, values);

            azimuth = Math.abs(values[0] * 57.2957795f);
            pitch = Math.abs(values[1] * 57.2957795f);
            roll = Math.abs(values[2] * 57.2957795f);
            mags = null;
            accels = null;

            tvX.setText(String.valueOf((int) azimuth));
            tvY.setText(String.valueOf((int) pitch));
            tvZ.setText(String.valueOf((int)roll));


            if(PhoneTiltedNorth() && !buttonIsSelected)
            {
                OnButtonSelected(btnNorth);

                tvOrientation.setText("North");
            }
            else if(PhoneTiltedSouth() && !buttonIsSelected)
            {
                OnButtonSelected(btnSouth);

                tvOrientation.setText("South");
            }
            else if(PhoneTiltedEast() && !buttonIsSelected)
            {
                OnButtonSelected(btnEast);

                tvOrientation.setText("East");
            }
            else if(PhoneTiltedWest() && !buttonIsSelected)
            {
                OnButtonSelected(btnWest);

                tvOrientation.setText("West");
            }
            else if(PhoneIsFlat())
            {
                tvOrientation.setText("NONE");

                if(buttonIsSelected)
                {
                    buttonIsSelected = false;
                    ViewCompat.setBackgroundTintList(buttons.get(indexOfSelectedButton), ColorStateList.valueOf(defaultColourOfButtonSelected));


                    if(indexOfSelectedButton == GameInfo.sequence.get(currentSequenceIndex))
                    {
                        Log.i("HELLO","Got it right.");
                        currentSequenceIndex++;

                        if(currentSequenceIndex == GameInfo.sequence.size())
                        {
                            GameInfo.GoToNextRound();
                            Log.i("HELLO","Reached end of sequence.");
                            Intent mainIntent = new Intent( this,MainActivity.class);
                            mainIntent.putExtra("ButtonColours",buttonColours);
                            startActivity(mainIntent);
                        }
                    }
                    else
                    {
                        Log.i("HELLO","Got it wrong - load game over");
                        PlayerGotSequenceWrong();

                        Intent gameOverIntent = new Intent( this,GameOverActivity.class);
                        startActivity(gameOverIntent);
                    }




                    //CHECK IF INDEX OF SELECTED BUTTON MATCHES SEQUENCE INDEX.
                    //IF YES - INCREASE SEQUENCE COUNTER.
                    // CHECK IF AT END OF SEQUENCE ARRAY.
                    // IF YES - LOAD MAIN ACTIVITY, GO TO NEXT ROUND.

                    //IF NO - RESET ROUND TO 1. RESET SEQUENCE ARRAY. GO BACK TO MAIN ACTIVITY.

                }
            }
        }
    }


    private void OnButtonSelected(Button buttonToSelect)
    {
        buttonIsSelected = true;

        //Find Button to select.
        indexOfSelectedButton = buttons.indexOf(buttonToSelect);
        selectedButton = buttons.get(indexOfSelectedButton);

        //Colours.
        defaultColourOfButtonSelected = GetColourOfButton(selectedButton);
        ViewCompat.setBackgroundTintList(selectedButton, ColorStateList.valueOf(getResources().getColor(R.color.White)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private int GetColourOfButton(Button button) { return button.getBackgroundTintList().getDefaultColor(); }


    //private boolean PhoneTiltedNorth(float X, float Y,float Z){return X > 2.2f && Y > 0.07f && Z > 8f;}     //North - X greater than 3.5 AND Y less than 0.3     X > 4 && Y < 0.4
    //private boolean PhoneTiltedSouth(float X,float Y, float Z){return X > 2.7f && Y < 0.07f && Z < 10;}     //South - X greater than 6 AND Z less than 7 Y < 0.3
    //private boolean PhoneTiltedEast(float Y, float X){return Y > 3 && X < 1;}                             //East - Y greater than 3 AND X less than 1.
    //private boolean PhoneTiltedWest(float X, float Y){return X > 1 && Y > 3;}                             //West - X greater than 1 AND Y greater than 3



    //MAG + ACCEL
    private boolean PhoneTiltedNorth(){return azimuth > 100 && pitch < 80f && pitch > 60f && roll < 100f;}
    private boolean PhoneTiltedSouth(){return azimuth < 70f && pitch < 70f;}
    private boolean PhoneTiltedEast(){return azimuth < 70f && pitch < 82f && roll < 35f ;}
    private boolean PhoneTiltedWest(){return azimuth < 110f && pitch < 85f && roll > 170f;}
    private boolean PhoneIsFlat(){return azimuth > 120 && pitch > 80;}

    private void PlayerGotSequenceWrong()
    {
        currentSequenceIndex = 0;
    }

    //X = AZIMUTH
    //Y = PITCH
    //Z = ROLL


    //NORTH - pitch < 72 && Z > 85
    //SOUTH - pitch < 70 && Z < -85
    //EAST - AZIMUTH < -15
    //WEST - AZIMUTH > 115

}