package org.csix.android.csix.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.csix.android.csix.R;
import org.csix.android.csix.models.Event;
import org.csix.android.csix.utils.DateUtils;
import org.csix.android.csix.utils.LocationUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EventActivity extends BaseMapActivity {
    @InjectView(R.id.tvEventDetailTopic)
    TextView topic;
    @InjectView(R.id.tvEventDetailNotes)
    TextView notes;
    @InjectView(R.id.tvEventDetailAddress)
    TextView address;
    @InjectView(R.id.tvEventDetailTime)
    TextView time;
    @InjectView(R.id.eToolbar)
    Toolbar toolbar;
    @InjectView(R.id.tvEventDetailLocation)
    TextView location;
    @InjectView(R.id.ivEventDetailCalendar)
    ImageView calendar;

    @OnClick(R.id.ivEventDetailCalendar)
    void addToCalendar() {
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setData(CalendarContract.Events.CONTENT_URI);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, event.getTopic());
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                R.string.event_location);
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, R.string.event_address);

        GregorianCalendar startTime = new GregorianCalendar(year, month, day, 10, 0);
        GregorianCalendar endTime = new GregorianCalendar(year, month, day, 13, 0);

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                startTime.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                endTime.getTimeInMillis());
        calIntent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        calIntent.putExtra(CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY);

        startActivity(calIntent);
    }

    @InjectView(R.id.tvEventDetailMore)
    TextView more;

    @OnClick(R.id.tvEventDetailMore)
    void more() {
        Intent intent = new Intent(this, EventDescriptionActivity.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_up, R.anim.top_out);
    }

    int year;
    int month;
    int day;
    String eventNotes = null;
    private String eventId;
    private Event event;
    Typeface font = null;
    private String locationAddress;
    LatLng latLng;
    String sfc;
    private GoogleMap map = null;
    private Marker marker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        font = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/Roboto-Light.ttf");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventId = getIntent().getStringExtra("eventId");
        locationAddress = getResources().getString(R.string.event_address);
        latLng = LocationUtils.getAddress(this, locationAddress);
        sfc = getResources().getString(R.string.event_location);

        getEvent();
    }

    @Override
    protected void setupMap() {
        if (map == null) {
            map = getMap();
        }

        // Display the connection status
        if (marker != null)
            marker.setVisible(false);
        if (latLng != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.moveCamera(cameraUpdate);
            map.animateCamera(cameraUpdate);

            marker = map.addMarker(new MarkerOptions()
                    .position(latLng));
            dropPinEffect(marker);
            marker.setTitle(sfc);
            marker.setSnippet(locationAddress);
        }
    }


    public void getEvent() {

        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.getInBackground(eventId, new GetCallback<Event>() {
            public void done(Event item, ParseException e) {
                if (e == null) {
                    // item was found
                    event = item;
                    getDate(event.getDate());

                    Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(),
                            "fonts/Roboto-Light.ttf");
                    topic.setText(Html.fromHtml(event.getTopic()));

                    notes.setTypeface(font);
                    eventNotes = event.getNotes();
                    notes.setText(eventNotes);

                    time.setTypeface(font);
                    time.setText(DateUtils.getShortMonthString(event
                            .getDate())
                            + " "
                            + DateUtils.getDayString(event.getDate())
                            + " @ "
                            + DateUtils.getTime12(DateUtils.getTimeString(event.getStartTime()))
                            + " - "
                            + DateUtils.getTime12(DateUtils.getTimeString(event.getEndTime())));

                    address.setTypeface(font);
                    address.setText(R.string.event_address);
                    location.setTypeface(font);
                    location.setText(R.string.event_location);
                }
            }
        });
    }


    public void getDate(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
