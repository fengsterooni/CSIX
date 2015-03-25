package org.csix.android.csix.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
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
import org.csix.android.csix.models.Group;
import org.csix.android.csix.utils.LocationUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GroupActivity extends BaseMapActivity {

    @InjectView(R.id.tvGroupAddress)
    TextView address;
    @InjectView(R.id.tvGroupDetailTitle)
    TextView title;
    @InjectView(R.id.tvGroupTime)
    TextView time;
    @InjectView(R.id.tvGroupLocation)
    TextView location;
    @InjectView(R.id.gToolbar)
    Toolbar toolbar;

    private String groupId;
    private Group group;
    private String locationAddress;
    private String groupTime;
    private String groupLocation;
    private LatLng latLng;
    private Typeface font = null;
    private GoogleMap map = null;
    private Marker marker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        font = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/Roboto-Light.ttf");

        groupId = getIntent().getStringExtra("groupId");
        locationAddress = getIntent().getStringExtra("address");
        groupLocation = getIntent().getStringExtra("location");
        latLng = LocationUtils.getAddress(this, locationAddress);
        getGroup();
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
            marker.setTitle(groupLocation);
            marker.setSnippet(locationAddress);
        }
    }

    public void getGroup() {
        ParseQuery<Group> query = ParseQuery.getQuery(Group.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.getInBackground(groupId, new GetCallback<Group>() {
            public void done(Group item, ParseException e) {
                if (e == null) {
                    // item was found
                    group = item;
                    // getName(group.getDate());
                    title.setText(Html.fromHtml(group.getGroupName()));
                    groupTime = group.getMeetingTime();
                    time.setText(groupTime);
                    locationAddress = group.getMeetingAddress();
                    groupLocation = group.getMeetingLocation();
                    location.setText(groupLocation);
                    address.setText(locationAddress);

                }
            }
        });
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
