package com.example.tong.babylog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class BabyInfoActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private boolean mVisible;

    private EditText babyNameEditText;
    private EditText birthdayEditText;
    private RadioButton genderRadioButton;
    private RadioGroup genderRadioGroup;
    private int selectedID;

    protected String babyName;
    protected String babyGender;
    protected String babyBirthday;

    private DatePickerDialog birthdayPicker;

    private Baby baby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_baby_info);

//        Calendar date = Calendar.getInstance();
//        int day = date.get(Calendar.DAY_OF_MONTH);
//        int month = date.get(Calendar.MONTH);
//        int year = date.get(Calendar.YEAR);

        mVisible = true;
        mContentView = findViewById(R.id.baby_info_layout);

        //register the views
        babyNameEditText = (EditText) findViewById(R.id.name_editText);
        birthdayEditText = (EditText) findViewById(R.id.birthday_editText);

        genderRadioGroup = (RadioGroup)findViewById(R.id.genderGroup);

        setDate();

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        //TODO:
        // Every time start this activity Read the file, if the file is empty,

        File f  = getFilesDir();
        String path = f.getAbsolutePath();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            //mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    /*
    TODO: check if the input is empty. if yes, then show a error message
     */
    private void getBabyInfo(){
        //get the baby name
        babyName = babyNameEditText.getText().toString();
        babyBirthday = birthdayEditText.getText().toString();
        //get the gender info
        selectedID = genderRadioGroup.getCheckedRadioButtonId();
        genderRadioButton = (RadioButton)findViewById(selectedID);
        babyGender = genderRadioButton.getText().toString();

    }

    private void setBabyInfo(){

        babyNameEditText.setText(babyName);
        birthdayEditText.setText(babyBirthday);
        genderRadioGroup.check(selectedID);
    }

    private void createBabyFile() throws IOException {
        String babyInfo = "Name:"+babyName+";Gender:"+babyGender+";Birthday:"+babyBirthday;
        FileOutputStream fos = openFileOutput("babyfile.txt", MODE_PRIVATE);
        fos.write(babyInfo.getBytes());
        fos.close();
    }
    private void readBabyFile() throws IOException {
        FileInputStream fis = openFileInput("myfile.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer b = new StringBuffer();
        while(bis.available() != 0){
            char c = (char) bis.read();
            b.append(c);
        }
        bis.close();
        fis.close();
    }

    // called when user click on submit
    public void gotoMainActivity(View view){
        getBabyInfo();

        baby = new Baby(babyName,babyGender,babyBirthday);

        // TODO:
        // maybe I should create a baby class list then pass to the mainacitivity
        // but I am not sure it will be there the next time run the app
        // Maybe I should just create a txt file and store the info in there and retrieve it every time

        Toast.makeText(this,"Baby added"+ babyName+", "+ babyGender +"; "+babyBirthday, Toast.LENGTH_LONG).show();
            finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    private void setDate(){
        birthdayEditText.setOnFocusChangeListener(this);
        birthdayEditText.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        birthdayPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, monthOfYear,dayOfMonth);
                birthdayEditText.setText(""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year);

            }
        }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus)
            birthdayPicker.show();
    }

    @Override
    public void onClick(View v) {
        if(v == birthdayEditText)
            birthdayPicker.show();
    }
}
