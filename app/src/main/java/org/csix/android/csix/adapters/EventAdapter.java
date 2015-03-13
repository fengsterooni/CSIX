package org.csix.android.csix.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;

import org.csix.android.csix.R;
import org.csix.android.csix.models.Event;
import org.csix.android.csix.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EventAdapter extends ArrayAdapter<Event> {

    static class ViewHolder {
        @InjectView(R.id.tvEventTopic) TextView topic;
        @InjectView(R.id.ivEventMember) ImageView member;
        @InjectView(R.id.tvHeader) TextView header;
        @InjectView(R.id.tvEventLocation)TextView location;
        @InjectView(R.id.tvEventTime) TextView time;
        @InjectView(R.id.tvEventSpeaker) TextView name;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.event_card, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.event_card, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        Resources res = getContext().getResources();
        //viewHolder.coordinator.setText(event.getCoordinator());
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Light.ttf");

        viewHolder.name.setTypeface(font);
        viewHolder.name.setText(event.getCoordinator());

        viewHolder.topic.setTypeface(font);
        viewHolder.topic.setText(event.getTopic());
        viewHolder.time.setTypeface(font);
        viewHolder.time.setText(DateUtils.getTime12(DateUtils.getTimeString(event.getStartTime()))
                + " - "
                + DateUtils.getTime12(DateUtils.getTimeString(event.getEndTime())));

        Date date = event.getDate();
        String strTime = DateUtils.getShortDayOfWeekString(date)
                + ", "
                + DateUtils.getShortMonthString(date)
                + " "
                + DateUtils.getDayString(date);
        viewHolder.header.setText(strTime.toUpperCase());

        viewHolder.location.setTypeface(font);
        viewHolder.location.setText(res.getString(R.string.event_location));


        ParseFile imageFile = event.getImage();

        if (imageFile != null) {
            try {
                byte[] image = imageFile.getData();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                viewHolder.member.setImageBitmap(bitmap);

            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}
