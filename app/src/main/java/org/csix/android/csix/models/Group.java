package org.csix.android.csix.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Group")
public class Group extends ParseObject {
    public String getGroupName() {
        return getString("Name");
    }

    public void setGroupName(String name) {
        put("Name", name);
    }

    public String getCoordinator() {
        return getString("Coordinator");
    }

    public void setCoordinator(String coordinator) {
        put("Coordinator", coordinator);
    }

    public String getMeetingLocation() {
        return getString("Location");
    }

    public void setMeetingLocation(String location) {
        put("Location", location);
    }

    public String getMeetingAddress() {
        return getString("Address");
    }

    public void setMeetingAddress(String address) {
        put("Address", address);
    }

    public String getMeetingTime() {
        return getString("Time");
    }

    public void setMeetingTime(String time) {
        put("Time", time);
    }

    public ParseFile getGroupImage() {
        return getParseFile("Image");
    }

    public void setGroupImage(ParseFile image) {
        put("Image", image);
    }

}
