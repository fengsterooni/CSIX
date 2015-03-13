package org.csix.android.csix.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;

import org.csix.android.csix.R;
import org.csix.android.csix.models.Group;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GroupAdapter extends ArrayAdapter<Group> {

    static class ViewHolder {
        @InjectView(R.id.tvGroupTitle) TextView title;
        @InjectView(R.id.ivGroupImage) ImageView image;
        @InjectView(R.id.tvGroupCoordinator) TextView coordinator;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    public GroupAdapter(Context context, ArrayList<Group> groups) {
        super(context, R.layout.group_item, groups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Group group = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.group_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.title.setText(Html.fromHtml(group.getGroupName()));
        viewHolder.coordinator.setText(group.getCoordinator());

        ParseFile imageFile = group.getGroupImage();

        if (imageFile != null) {
            try {
                byte[] image = imageFile.getData();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                viewHolder.image.setImageBitmap(bitmap);

            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}
