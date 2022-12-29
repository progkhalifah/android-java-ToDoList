package com.progkhalifah.mytasks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.progkhalifah.mytasks.R;

public class ViewImagePagerAdapter extends PagerAdapter { // to create one time

    private Context context;
    private LayoutInflater inflater;

    public ViewImagePagerAdapter(Context context) {
        this.context = context;
    }


    private int images[] = {
            R.drawable.group_2,
            R.drawable.reminder_image,
            R.drawable.giving_notification_image
    };


    private int description[] = {
            R.string.first_page,
            R.string.second_page,
            R.string.third_page
    };




    @Override
    public int getCount() {
        return description.length;
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;

    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_pager,container,false);

        ImageView imageView = v.findViewById(R.id.img_View_pager);
        TextView txtTitle = v.findViewById(R.id.txt_Title_View_pager);

        imageView.setImageResource(images[position]);
        txtTitle.setText(description[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}// TODO: 10/13/2021 end of all
