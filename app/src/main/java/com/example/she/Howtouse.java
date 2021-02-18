package com.example.she;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Howtouse extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout linearLayout;
    private slide_adapter slide_adapter;
    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtouse);
        Window window = Howtouse.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(Howtouse.this, R.color.colorpink));

        viewPager = findViewById(R.id.view_page);
        linearLayout = findViewById(R.id.ll);
        slide_adapter = new slide_adapter(this);
        viewPager.setAdapter(slide_adapter);
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
    }
    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        linearLayout.removeAllViews();
        for (int i=0;i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transparentwhite));
            linearLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
