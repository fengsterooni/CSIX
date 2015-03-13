package org.csix.android.csix;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import org.csix.android.csix.models.About;
import org.csix.android.csix.models.Event;
import org.csix.android.csix.models.Group;

public class CSixApplication extends Application {
    public static final String APPLICATION_ID = "6zIkQkbu4CkK4ZQsDgUaM1ge8Uwa9iVyatQ44Rwn";
    public static final String CLIENT_KEY = "vdj5GcMy0Lwoexi5rFH3oPbZRCx60Kb8yuwEUjcv";

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(About.class);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
    }
}
