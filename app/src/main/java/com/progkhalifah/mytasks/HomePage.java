package com.progkhalifah.mytasks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class HomePage extends AppCompatActivity {

    Button btn_myday, btn_important, btn_myplan;
    TextView txt_ratefeedback,toolbar_change_language;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_home_page);
        inital();
    ///////////////////////////////////////////////////////////////////////////
        txt_ratefeedback.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, RateAndFeedbackus.class));
        });
        ///////////////////////////////////////////////////////////////////////////
        btn_myday.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, MydaysTasks.class));
        });
        ///////////////////////////////////////////////////////////////////////////
        btn_important.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, MyImportance.class));
        });

        btn_myplan.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, MyPlan.class));
        });
        //////////////////////////////////////////////////////////////////////////

        toolbar_change_language.setOnClickListener(v -> {
            showchangeLanguageDialog();
        });


        Toast.makeText(this, "Welcome To Your Home", Toast.LENGTH_SHORT).show();//todo delete this toast

    }// TODO: 10/13/2021


    private void showchangeLanguageDialog() {
        final String[] listItem = {"English"," العربية"};
        AlertDialog.Builder mBulider = new AlertDialog.Builder(HomePage.this);
        mBulider.setTitle("Choose Language...");
        mBulider.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                mBulider.setCancelable(false);
                if (i == 0){
                //English
                setLocale("en");
                recreate();
            }else if (i == 1){
                //Arabic
                setLocale("ar");
                recreate();
            }
            dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBulider.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }
    // load language saved in shared preference
    public void loadLocale(){
        SharedPreferences prefs =getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String languages =prefs.getString("My_Lang","");
        setLocale(languages);
    }


    public void inital(){
        btn_myday = findViewById(R.id.btn_my_day);
        btn_important = findViewById(R.id.btn_important);
        btn_myplan = findViewById(R.id.btn_my_play);
        txt_ratefeedback = findViewById(R.id.txt_rate_feedback_us);
        toolbar = findViewById(R.id.toolbar_myhomepage);
        toolbar_change_language = findViewById(R.id.toolbar_change_language);
    }


}