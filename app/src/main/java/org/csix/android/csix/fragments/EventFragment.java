package org.csix.android.csix.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.csix.android.csix.R;
import org.csix.android.csix.activities.EventActivity;
import org.csix.android.csix.adapters.EventAdapter;
import org.csix.android.csix.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {

    private ListView lvEvent;
    private EventAdapter aEvent;
    private ArrayList<Event> events;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<>();
        aEvent = new EventAdapter(getActivity(), events);
        getEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        // Set the adapter
        lvEvent = (ListView) view.findViewById(android.R.id.list);
        lvEvent.setAdapter(aEvent);

        // Set OnItemClickListener so we can be notified on item clicks
        lvEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("INFO", parent + " " + view.toString() + " " + position + " " + id);
                Intent intent = new Intent(getActivity(), EventActivity.class);

                Event event = events.get(position);
                intent.putExtra("eventId", event.getObjectId());

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        return view;
    }

    public void getEvents() {

        // Define the class we would like to query
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        // Define our query conditions
        query.orderByAscending("Date");
        query.findInBackground(new FindCallback<Event>() {
            public void done(List<Event> results, ParseException e) {
                if (e == null) {
                    // results have all the Story
                    events.addAll(results);
                    aEvent.notifyDataSetChanged();
                } else {
                    // There was an error
                }
            }
        });
    }
}
