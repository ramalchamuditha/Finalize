package com.example.introscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdaptor introViewPagerAdaptor;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnskip;
    Button btnGetStarted;
    Animation btnAnim;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //check wether it is opend or not before
        if(restorePrefData()){
            Intent ActivityLogin = new Intent(getApplicationContext(), Activity_Login.class);
            startActivity(ActivityLogin);
            finish();
        }
        setContentView(R.layout.activity_intro);

        //hide action bar
        getSupportActionBar().hide();

        //initial views
        btnNext=findViewById(R.id.btn_Next);
        btnGetStarted=findViewById(R.id.btn_get_started);
        tabIndicator=findViewById(R.id.tab_indicator);
        btnAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        btnskip = findViewById(R.id.btn_skip);

        //fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Enjoy Your Meal With Us","Who knew that ordering food could be such a hassle? It can be tedious to call a restaurant and order a food. You might get put on hold, and oftentimes you have to talk over the background noise of a busy restaurant. Thankfully, in the age of apps, one can order food with just a few taps of a phone screen. Whether the goal is to be anti-social, to avoid having to pause that Game of Thrones marathon, so \"Ramp\" here to help.”. ",R.drawable.ramp));
        mList.add(new ScreenItem("Benifits And Conditions","Unique concept of ordering different cuisines under one roof. Enjoy a rich variety of dishes and coffee, selection of juUnique concept of ordering different cuisines under one roof. Enjoy a rich variety of dishes and coffee, selection oices, aromatic teas and delicious desserts made by our cafetaria. Stay healthy with our healthy corner serving fresh vegetables, fruits and yoghurts.",R.drawable.sample2));
        mList.add(new ScreenItem("How to Order","We serve breakfast,lunch,dinner for you on business days from 07:00 – 18:00",R.drawable.food));
        mList.add(new ScreenItem("How To Pay","You can use the smart card that is provided by the organization for you.",R.drawable.payment));

        //setup view pager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdaptor = new IntroViewPagerAdaptor(this,mList);
        screenPager.setAdapter(introViewPagerAdaptor);

        //setup tab layout
        tabIndicator.setupWithViewPager(screenPager);

        //onclick listner
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = screenPager.getCurrentItem();
                if(position<mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position==mList.size()-1){
                    //open the to start button
                    loadLastScreen();
                }
            }
        });
        //tab layout add change listner
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //get started button listner
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open the main activity
                Intent ActivityLogin=new Intent(getApplicationContext(), Activity_Login.class);
                startActivity(ActivityLogin);
                //using a shared preference allowing to save the last open location
                savePrefsData();
                finish();
            }
        });
        //setting up the skip button
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open the main activity
                Intent ActivityLogin=new Intent(getApplicationContext(), Activity_Login.class);
                startActivity(ActivityLogin);

                //using a shared preference allowing to save the last open location
                savePrefsData();
                finish();
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpendBefore = pref.getBoolean("isIntroOpend",false);
        return isIntroActivityOpendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpend",true);
        editor.commit();
    }

    //open the to start button
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnskip.setVisibility(View.INVISIBLE);

        //animation for get started
        //animation for the button
        btnGetStarted.setAnimation(btnAnim);
    }
}
