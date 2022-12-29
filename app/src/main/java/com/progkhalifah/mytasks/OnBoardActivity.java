package com.progkhalifah.mytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.progkhalifah.mytasks.adapter.ViewImagePagerAdapter;

public class OnBoardActivity extends AppCompatActivity {// this page for crating pages for first time

    private ViewPager viewPager;
    private Button btn_left, btn_right;
    private ViewImagePagerAdapter adapter;
    private LinearLayout dotslayout;
    private TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        init();

    }

    private void init(){

        viewPager = findViewById(R.id.view_pager);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        dotslayout = findViewById(R.id.dotsLayout);
        adapter = new ViewImagePagerAdapter(this);
        addDots(0);
        viewPager.addOnPageChangeListener(listener);
        viewPager.setAdapter(adapter);


        btn_right.setOnClickListener(v -> {
            if (btn_right.getText().toString().equals("Next")){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }else {
                //its if finish we will start Auth activity
                startActivity(new Intent(OnBoardActivity.this, HomePage.class));
                finish();
            }
        });

        btn_left.setOnClickListener(v -> {
            // if button skip is clicked
            viewPager.setCurrentItem(viewPager.getCurrentItem()+2);
        });

    }

    private void addDots(int position){
        dotslayout.removeAllViews();
        dots = new TextView[3];
        for (int i = 0 ; i< dots.length; i++){
            dots[i] = new TextView(this);
            //this html code created dot
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorLightGrey));
            dotslayout.addView(dots[i]);

        }// end of loop
        //
        if (dots.length> 0){
            dots[position].setTextColor(getResources().getColor(R.color.colortoolbar));
        }
    }// end of addDots


    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            if (position == 0) {
                btn_left.setVisibility(View.VISIBLE);
                btn_left.setEnabled(true);
                btn_right.setText("Next");
            } else if (position == 1) {
                btn_left.setVisibility(View.GONE);
                btn_left.setEnabled(false);
                btn_right.setText("Next");
            }else{
                btn_left.setVisibility(View.GONE);
                btn_left.setEnabled(false);
                btn_right.setText("Finish");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };




}