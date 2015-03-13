package org.csix.android.csix.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
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
    private String groupId;
    private Group group;

    @InjectView(R.id.gToolbar)
    Toolbar toolbar;
    private String locationAddress;
    private String groupTime;
    private String groupLocation;

    Typeface font = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.inject(this);

        groupId = getIntent().getStringExtra("groupId");
        locationAddress = getIntent().getStringExtra("address");
        //groupTime = getIntent().getStringExtra("time");
        groupLocation = getIntent().getStringExtra("location");
        LatLng latLng = LocationUtils.getAddress(this, locationAddress);

        setupMap(latLng, R.id.groupMap, groupLocation, locationAddress);
        getGroup();

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        font = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/Roboto-Light.ttf");

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
