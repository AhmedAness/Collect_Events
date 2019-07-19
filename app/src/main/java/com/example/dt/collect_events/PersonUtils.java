package com.example.dt.collect_events;

public class PersonUtils {

    private String eventname;
    private String title;
    private String StartTime;
    private String EndTime;

    public PersonUtils(String eventname, String title, String startTime, String endTime) {
        this.eventname = eventname;
        this.title = title;
        StartTime = startTime;
        EndTime = endTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
