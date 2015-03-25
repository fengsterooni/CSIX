package org.csix.android.csix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.csix.android.csix.R;
import org.csix.android.csix.adapters.AboutAdapter;
import org.csix.android.csix.models.About;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutFragment extends Fragment {
    private ArrayList<About> items;
    private ArrayAdapter<About> aItem;
    @InjectView(android.R.id.list) ListView lvAbout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_about_list, container, false);
        ButterKnife.inject(this, view);

        items = new ArrayList<About>();
        aItem = new AboutAdapter(getActivity(), items);
        // lvAbout = (ListView) view.findViewById(android.R.id.list);
        lvAbout.setAdapter(aItem);
        getItems();
        return view;
    }

    public void getItems() {

        // Define the class we would like to query
        ParseQuery<About> query = ParseQuery.getQuery(About.class);
        // Define our query conditions
        query.orderByAscending("Date");
        query.findInBackground(new FindCallback<About>() {
            public void done(List<About> results, ParseException e) {
                if (e == null) {
                    // results have all the Story
                    items.addAll(results);
                    aItem.notifyDataSetChanged();
                } else {
                    // There was an error
                }
            }
        });
    }
}
