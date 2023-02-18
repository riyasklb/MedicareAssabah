package com.example.medicareassabah.step_counter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicareassabah.R;

public class FootstepActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    TextView textView;
    Button btn1, btn2;
    Chronometer ch;
    StepDetector simpleStepDetector;
    SensorManager sensorManager;
    Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    int numSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footstep);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        textView = findViewById(R.id.tv_steps);
        ch=findViewById(R.id.chronometer);
        ch.setFormat("%s");
        btn1 = findViewById(R.id.btn_start);
        btn2 = findViewById(R.id.btn_stop);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                numSteps = 0;
                sensorManager.registerListener(FootstepActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                ch.setBase(SystemClock.elapsedRealtime());
                ch.start();


            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(FootstepActivity.this);
                ch.stop();

            }
        });


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        textView.setText(TEXT_NUM_STEPS + numSteps);
    }
}