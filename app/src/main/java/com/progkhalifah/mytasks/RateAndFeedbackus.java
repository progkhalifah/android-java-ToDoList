package com.progkhalifah.mytasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateAndFeedbackus extends AppCompatActivity {


    RatingBar rate_us;
    TextView txt_your_opinion;
    ImageView image_first,image_second,image_third,image_fourth,image_fifth;
    ImageView btn_facebook_social, btn_twitter_social, btn_snapchat_social, btn_instagram_social, btn_whatsapp_social;
    Button btn_submit_to_googleplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_and_feedbackus);

        rate_us = findViewById(R.id.rate_us);
        txt_your_opinion = findViewById(R.id.txt_your_opinion_matters);
        image_first = findViewById(R.id.image_first);
        image_second = findViewById(R.id.image_second);
        image_third = findViewById(R.id.image_third);
        image_fourth = findViewById(R.id.image_fourth);
        image_fifth = findViewById(R.id.image_fifth);
        btn_facebook_social = findViewById(R.id.img_facebook);
        btn_twitter_social = findViewById(R.id.img_twitter);
        btn_snapchat_social = findViewById(R.id.img_snap);
        btn_instagram_social = findViewById(R.id.img_insta);
        btn_whatsapp_social = findViewById(R.id.img_whats);
        btn_submit_to_googleplay = findViewById(R.id.btn_sumbit_to_googleplay);

        rate_us.setMax(5);
        rate_us.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int ratingInt = (int) rating;
                if (ratingInt == 1){
                    image_first.setVisibility(View.VISIBLE);
                    image_second.setVisibility(View.INVISIBLE);
                    image_third.setVisibility(View.INVISIBLE);
                    image_fourth.setVisibility(View.INVISIBLE);
                    image_fifth.setVisibility(View.INVISIBLE);

                }else if (ratingInt == 2){
                    image_first.setVisibility(View.INVISIBLE);
                    image_second.setVisibility(View.VISIBLE);
                    image_third.setVisibility(View.INVISIBLE);
                    image_fourth.setVisibility(View.INVISIBLE);
                    image_fifth.setVisibility(View.INVISIBLE);
                }else if (ratingInt == 3){
                    image_first.setVisibility(View.INVISIBLE);
                    image_second.setVisibility(View.INVISIBLE);
                    image_third.setVisibility(View.VISIBLE);
                    image_fourth.setVisibility(View.INVISIBLE);
                    image_fifth.setVisibility(View.INVISIBLE);
                }else if (ratingInt == 4){
                    image_first.setVisibility(View.INVISIBLE);
                    image_second.setVisibility(View.INVISIBLE);
                    image_third.setVisibility(View.INVISIBLE);
                    image_fourth.setVisibility(View.VISIBLE);
                    image_fifth.setVisibility(View.INVISIBLE);
                }else if (ratingInt == 5){
                    image_first.setVisibility(View.INVISIBLE);
                    image_second.setVisibility(View.INVISIBLE);
                    image_third.setVisibility(View.INVISIBLE);
                    image_fourth.setVisibility(View.INVISIBLE);
                    image_fifth.setVisibility(View.VISIBLE);

                }else {
                    image_first.setVisibility(View.INVISIBLE);
                    image_second.setVisibility(View.INVISIBLE);
                    image_third.setVisibility(View.INVISIBLE);
                    image_fourth.setVisibility(View.INVISIBLE);
                    image_fifth.setVisibility(View.INVISIBLE);
                }
            }
        });// TODO: 10/13/2021 end of rating

        btn_facebook_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysocialmedia("https://www.facebook.com/profile.php?id=100076185465448");
            }
        });

        btn_twitter_social.setOnClickListener(v -> {
            mysocialmedia("https://twitter.com/TechnlogyPlanet/");
        });

        btn_snapchat_social.setOnClickListener(v -> {
            mysocialmedia("http://www.snapchat.com/add/raad733raad");
//            Toast.makeText(this, "We are working on it...", Toast.LENGTH_SHORT).show();
        });

        btn_instagram_social.setOnClickListener(v -> {
            mysocialmedia("https://www.instagram.com/technlogyplanet/");
        });

        btn_whatsapp_social.setOnClickListener(v -> {
            mysocialmedia("https://api.whatsapp.com/send?phone="+"0550731413");
        });

        btn_submit_to_googleplay.setOnClickListener(v -> {
            apigooglplaytoratingus();
        });





    }

    private void apigooglplaytoratingus() {
        Toast.makeText(this, "Under working", Toast.LENGTH_SHORT).show();
    }

    private void mysocialmedia(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}