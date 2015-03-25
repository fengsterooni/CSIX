package org.csix.android.csix.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.csix.android.csix.R;
import org.csix.android.csix.models.Event;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EventDescriptionActivity extends ActionBarActivity {

    @InjectView(R.id.eventDescriptionTitle)
    TextView title;
    @InjectView(R.id.eventDescription)
    TextView notes;
    @InjectView(R.id.dToolbar)
    Toolbar toolbar;

    private Typeface font = null;
    private String eventId;
    private Event event = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventId = getIntent().getStringExtra("eventId");
        setContentView(R.layout.activity_event_description);
        ButterKnife.inject(this);
        font = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/Roboto-Light.ttf");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getEvent();
    }

    public void getEvent() {

        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.getInBackground(eventId, new GetCallback<Event>() {
            public void done(Event item, ParseException e) {
                if (e == null) {
                    // item was found
                    event = item;
                    String eventTopic = event.getTopic();
                    Log.i("INFO", eventTopic);

                    title.setTypeface(font);
                    title.setText(eventTopic);
                    String eventNotes = event.getNotes();
                    // Log.i("INFO", eventNotes);

                    notes.setTypeface(font);
                    if (!TextUtils.isEmpty(eventNotes))
                        notes.setText(eventNotes);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.top_down, R.anim.bottom_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.top_down, R.anim.bottom_out);
    }
}
