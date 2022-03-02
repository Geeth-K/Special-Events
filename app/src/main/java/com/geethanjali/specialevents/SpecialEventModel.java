package com.geethanjali.specialevents;

public class SpecialEventModel {
    private String event_name;
    private String event_date;
    private String event_venue;
    private String event_desc;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public SpecialEventModel(String name, String date, String venue, String desc) {
        this.event_name = name;
        this.event_date = date;
        this.event_venue = venue;
        this.event_desc = desc;
    }
}
