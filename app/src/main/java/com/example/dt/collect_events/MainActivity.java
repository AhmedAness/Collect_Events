package com.example.dt.collect_events;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.facebook.AccessToken;
//import com.facebook.AccessTokenTracker;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Parameter;
import java.security.Policy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<PersonUtils> personUtilsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Event> events = null;
        int MyVersion = Build.VERSION.SDK_INT;

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        personUtilsList = new ArrayList<>();

        //Adding Data into ArrayList
//        personUtilsList.add(new PersonUtils("Bradley Matthews","Senior Developer"));
//        personUtilsList.add(new PersonUtils("Harley Gibson","Lead Developer"));
//        personUtilsList.add(new PersonUtils("Gary Thompson","Lead Developer"));
//        personUtilsList.add(new PersonUtils("Corey Williamson","UI/UX Developer"));
//        personUtilsList.add(new PersonUtils("Samuel Jones","Front-End Developer"));
//        personUtilsList.add(new PersonUtils("Michael Read","Backend Developer"));
//        personUtilsList.add(new PersonUtils("Robert Phillips","Android Developer"));
//        personUtilsList.add(new PersonUtils("Albert Stewart","Web Developer"));
//        personUtilsList.add(new PersonUtils("Wayne Diaz","Junior Developer"));


        if (!isMyServiceRunning(service.class)) {

            Intent intent = new Intent(this, service.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(intent);
            } else {
                this.startService(intent);
            }
        }


        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }else {
                CalendarProvider calendarProvider = new CalendarProvider(getApplicationContext());
                List<Calendar> calendars = calendarProvider.getCalendars().getList();
                for (int i = 0; i < calendars.size(); i++) {
                    if (calendars.get(i).accountName.equals("ahmed_aniss@yahoo.com")){
                        if (calendarProvider.getEvents(calendars.get(i).id).getList().size()>0){
                            events = calendarProvider.getEvents(calendars.get(i).id).getList();
                        }
                    }
                }
//                events = calendarProvider.getEvents(6).getList();
                if (events.get(0)!=null){
                    for (int i = 0; i <events.size() ; i++) {

                        // Creating date format
                        DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");

                        personUtilsList.add(new PersonUtils(events.get(i).displayName,events.get(i).title,String.valueOf(simple.format(new Date(events.get(i).dTStart))),String.valueOf(simple.format(new Date(events.get(i).dTend)))));
                    }

                }
//                List<Event> events = calendarProvider.getEvents(calendars.cn.id).getList();
                Toast.makeText(this, calendars.get(0).toString(), Toast.LENGTH_SHORT).show();


                mAdapter = new CustomRecyclerAdapter(this, personUtilsList);

                recyclerView.setAdapter(mAdapter);


//                ReadCalendar.readCalendar(MainActivity.this);
















            }
        }

    }





    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_SMS, Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted

                    CalendarProvider calendarProvider = new CalendarProvider(getApplicationContext());
                    List<Calendar> calendars = calendarProvider.getCalendars().getList();
                    Toast.makeText(this, calendars.get(0).toString(), Toast.LENGTH_SHORT).show();
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
