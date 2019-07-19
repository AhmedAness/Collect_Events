package com.example.dt.collect_events.model;

import android.content.Context;

import com.example.dt.collect_events.presenter.Collector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;

public class UserDataManager implements Collector.Model {

    private String Email;
    private List<Event> events;


    @Override
    public void setDetails(String Email) {
        this.Email=Email;
    }

    @Override
    public List<PersonUtils> getRecycler(Context context) {

        List<PersonUtils> personUtilsList = new ArrayList<>();
        CalendarProvider calendarProvider = new CalendarProvider(context);
        List<Calendar> calendars = calendarProvider.getCalendars().getList();
        for (int i = 0; i < calendars.size(); i++) {
            if (calendars.get(i).accountName.equals(Email)) {
                if (calendarProvider.getEvents(calendars.get(i).id).getList().size() > 0) {
                    events = calendarProvider.getEvents(calendars.get(i).id).getList();
                }
            }
        }
//                events = calendarProvider.getEvents(6).getList();
        if (events.get(0) != null) {
            for (int i = 0; i < events.size(); i++) {

                // Creating date format
                DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");

                personUtilsList.add(new PersonUtils(events.get(i).displayName, events.get(i).title, String.valueOf(simple.format(new Date(events.get(i).dTStart))), String.valueOf(simple.format(new Date(events.get(i).dTend)))));
            }

        }

        return personUtilsList;
    }
}
