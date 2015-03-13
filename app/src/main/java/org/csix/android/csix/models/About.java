package org.csix.android.csix.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("About")
public class About extends ParseObject {
    public String getAboutTitle() {
        return getString("Title");
    }

    public void setAboutTitle(String title) {
        put("Title", title);
    }

    public String getAboutContent() {
        return getString("Content");
    }

    public void setAboutContent(String content) {
        put("Content", content);
    }
}
