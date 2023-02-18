package com.example.medicareassabah.Pill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicareassabah.AlertService;
import com.example.medicareassabah.Config;
import com.example.medicareassabah.DlistActivity;
import com.example.medicareassabah.Notification.AlarmReceiver;
import com.example.medicareassabah.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Viewmedicine extends AppCompatActivity {
    Spinner spinner;
    TextView mname, mdose, presday, mdate;
    Button remaind;
    EditText setTime, setDate;
    String name, times, spins, doses, presdays, dates, img;
    ImageView imageView;
    DatePicker pickerDate;
    TimePicker pickerTime;
    Button buttonSetAlarm;
    TextView info;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";


    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmedicine);

        mname = findViewById(R.id.mname);
        imageView = findViewById(R.id.image);
        mdose = findViewById(R.id.dosage);
        presday = findViewById(R.id.pdays);
        mdate = findViewById(R.id.adddate);
        spinner = findViewById(R.id.spin);
        remaind = findViewById(R.id.setrem);
        pickerDate = findViewById(R.id.sdate);
        pickerTime = findViewById(R.id.stime);
        info = (TextView) findViewById(R.id.info);
        Calendar now = Calendar.getInstance();


        Intent i = getIntent();
        name = i.getStringExtra("name");
        doses = i.getStringExtra("dose");
        presdays = i.getStringExtra("presday");
        dates = i.getStringExtra("date");
        img = i.getStringExtra("img");
        Picasso.get().load(Config.imageURL + img).into(imageView);

        mname.setText(name);
        mdose.setText(doses);
        presday.setText(presdays);
        mdate.setText(dates);

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);


        pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));


        remaind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getCurrentHour(),
                        pickerTime.getCurrentMinute(),
                        00);


                if (cal.compareTo(current) <= 0) {
                    cal.add(Calendar.DATE, 0);
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                } else {
                    setAlarm(cal);
                }

            }
        });

    }

    private void setAlarm(Calendar targetCal) {

        info.setText("\n\n***\n"
                + "Remainder is set@ " + targetCal.getTime() + "\n"
                + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        name = mname.getText().toString();
        intent.putExtra("cid", name);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), AlarmManager.RTC_WAKEUP, pendingIntent);

    }
}