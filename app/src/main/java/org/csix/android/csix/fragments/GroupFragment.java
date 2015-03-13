package org.csix.android.csix.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.csix.android.csix.R;
import org.csix.android.csix.activities.GroupActivity;
import org.csix.android.csix.adapters.GroupAdapter;
import org.csix.android.csix.models.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GroupFragment extends Fragment {
    private static final String TAG = GroupFragment.class.getSimpleName();
    @InjectView(R.id.gvSIGs)
    GridView sgvGroups;

    private ArrayList<Group> groups;
    private ArrayAdapter<Group> aGroup;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groups = new ArrayList<Group>();
        aGroup = new GroupAdapter(getActivity(), groups);
        getGroups();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_group_list, container, false);
        ButterKnife.inject(this, v);

        sgvGroups.setAdapter(aGroup);
        sgvGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupActivity.class);
                Group group = groups.get(position);
                intent.putExtra("groupId", group.getObjectId());
                intent.putExtra("address", group.getMeetingAddress());

                intent.putExtra("location", group.getMeetingLocation());
                intent.putExtra("time", group.getMeetingTime());
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        return v;
    }

    public void getGroups() {

        // Define the class we would like to query
        ParseQuery<Group> query = ParseQuery.getQuery(Group.class);
        // Define our query conditions
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Group>() {
            public void done(List<Group> results, ParseException e) {
                if (e == null) {
                    // results have all the Story
                    groups.addAll(results);
                    aGroup.notifyDataSetChanged();
                } else {
                    // There was an error
                }
            }
        });
    }
}
