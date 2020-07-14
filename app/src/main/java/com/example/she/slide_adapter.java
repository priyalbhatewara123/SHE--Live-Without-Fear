package com.example.she;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class slide_adapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public slide_adapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.mipmap.use1,
            R.mipmap.use2,
            R.mipmap.use3

    };

    public String[] slide_heading = {
            "STEP 1",
            "STEP 2",
            "STEP 3"
    };

    public String[] slide_dis = {
            "In order to ask for help, you need to first add your family or friends contact number.",
            "At the time of emergency press the emergency button to send a sms with your location to registered contacts.",
            "If needed, you can call on Helpline numbers for help."
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView imageView = view.findViewById(R.id.image_view);
        TextView textView1 = view.findViewById(R.id.txt_view5);
        TextView textView2 = view.findViewById(R.id.txt_view6);
        imageView.setImageResource(slide_images[position]);
        textView1.setText(slide_heading[position]);
        textView2.setText(slide_dis[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
