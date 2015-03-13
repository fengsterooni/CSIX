package org.csix.android.csix.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.csix.android.csix.R;
import org.csix.android.csix.models.About;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutAdapter extends ArrayAdapter<About> {

    Typeface font = null;

    static class ViewHolder {
        @InjectView(R.id.tvAboutTitle) TextView title;
        @InjectView(R.id.tvAboutContent) TextView content;
        @InjectView(R.id.ivCSixImage) ImageView csix;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    public AboutAdapter(Context context, ArrayList<About> abouts) {
        super(context, R.layout.about_item, abouts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final About about = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.about_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object

        viewHolder.content.setTypeface(font);
        viewHolder.title.setText(Html.fromHtml(about.getAboutTitle()));
        viewHolder.content.setText(about.getAboutContent());

        return convertView;
    }
}
