package com.example.tong.babylog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.tong.babylog.R.id;
import static com.example.tong.babylog.R.id.drawer_layout;
import static com.example.tong.babylog.R.layout;
import static com.example.tong.babylog.R.string.navigation_drawer_close;
import static com.example.tong.babylog.R.string.navigation_drawer_open;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected TextView ageContentView;
    protected TextView nameContentView;

    protected String bName;
    protected String bBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);

        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, navigation_drawer_open, navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nameContentView = (TextView) findViewById(R.id.nameTextView_MainContent);
        nameContentView.setText("Baby");


        ageContentView = (TextView) findViewById(R.id.ageTextView_MainContent);
        ageContentView.setText("5weeks");

        File f  = getFilesDir();
        String path = f.getAbsolutePath();

        // Read Json file to get the baby's name and birthday

        StringBuffer babyInfo = null;

        try {
            babyInfo = readBabyFile();
            Toast.makeText(this, babyInfo.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "IO Reading ERROR", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSON Reading ERROR in file reading", Toast.LENGTH_LONG).show();
        }




        try {

            //JSONArray data = new JSONArray(babyInfo.toString());
            JSONObject data = new JSONObject(babyInfo.toString());
            Toast.makeText(this, "data "+ data.toString(), Toast.LENGTH_LONG).show();
            bName = data.getString("name");
            bBirthday = data.getString("birthday");

            Toast.makeText(this,"bName is : "+bName+"\n birthday is "+bBirthday, Toast.LENGTH_LONG).show();

            String babyAge = babyAgeCalc(bBirthday);
            nameContentView.setText(bName);
            ageContentView.setText(babyAge);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSON Reading ERROR"+e.toString(), Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nursing) {

        } else if (id == R.id.nav_bottle) {

        } else if (id == R.id.nav_Diaper) {

        } else if (id == R.id.nav_sleep) {

        } else if (id == R.id.nav_baby) {
            // go to baby info page
            Intent intent = new Intent(this,BabyInfoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_month) {
            Intent intent = new Intent(this, MilestonesActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public StringBuffer readBabyFile() throws IOException, JSONException {
        FileInputStream fis = openFileInput("babyInfo");
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer b = new StringBuffer();
        while(bis.available() != 0){
            char c = (char) bis.read();
            b.append(c);
        }
        bis.close();
        fis.close();

        return b;
    }

    public String babyAgeCalc(String bBirthday) {
        String ageString = null;
        Date date = stringToDate(bBirthday);
        if(date != null){
            int years = 0;
            int months = 0;
            int days = 0;
            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(date.getTime());
            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);
            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthDay.get(Calendar.MONTH) + 1;
            //Get difference between months
            months = currMonth - birthMonth;
            //if month difference is in negative then reduce years by one and calculate the number of months.
            if (months < 0)
            {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--;
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            {
                years--;
                months = 11;
            }
            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
            } else
            {
                days = 0;
                if (months == 12)
                {
                    years++;
                    months = 0;
                }
            }

            ageString = ""+years+"years,"+months+"months,"+days+"days";
        }


        return ageString;
    }

    public Date stringToDate(String bBirthday){
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = dateFormat.parse(bBirthday);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Date parse failure", Toast.LENGTH_LONG).show();
        }
        return date;
    }
}
