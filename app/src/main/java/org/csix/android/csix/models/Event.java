package org.csix.android.csix.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("Event")
public class Event extends ParseObject {

    public Date getDate() {
        return getDate("Date");
    }

    public void setDate(Date date) {
        put("Date", date);
    }

    public Date getStartTime() {
        return getDate("Date");
    }

    public void setStartTime(Date date) {
        put("Date", date);
    }

    public Date getEndTime() {
        return getDate("EndTime");
    }

    public void setEndTime(Date endTime) {
        put("EndTime", endTime);
    }

    public String getLocation() {
        return getString("Location");
    }

    public void setLocation(String location) {
        put("Location", location);
    }

    public String getTopic() {
        return getString("Topic");
    }

    public void setTopic(String topic) {
        put("Topic", topic);
    }

    public String getNotes() {
        return getString("Notes");
    }

    public void setNotes(String notes) {
        put("Notes", notes);
    }

    public String getCoordinator() {
        return getString("Coordinator");
    }

    public void setCoordinator(String coordinator) {
        put("Coordinator", coordinator);
    }

    public String getAddress() {
        return getString("Address");
    }

    public void setAddress(String address) {
        put("Address", address);
    }

    public ParseFile getImage() {
        return getParseFile("Image");
    }

    public void setImage(ParseFile image) {
        put("Image", image);
    }
}