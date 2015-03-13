package org.csix.android.csix.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import org.csix.android.csix.R;
import org.csix.android.csix.fragments.AboutFragment;
import org.csix.android.csix.fragments.EventFragment;
import org.csix.android.csix.fragments.GroupFragment;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Events", "Groups", "About" };
    private Context context;

    private int[] imageResId = {
            R.drawable.ic_event,
            R.drawable.ic_group,
            R.drawable.ic_info
    };

    public BaseFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EventFragment();
            case 1:
                return new GroupFragment();
            case 2:
                return new AboutFragment();
        }
        
        return null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        //SpannableString sb = new SpannableString("  \n" + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
    
}