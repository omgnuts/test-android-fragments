package com.jattcode.fragment;

import android.content.Intent;

import com.jattcode.activity.BaseLoggerActivity;

public abstract class ScreenCompatActivity extends BaseLoggerActivity {

    public ScreenCompatActivity() {
        LOGGER_ON = false;
    }

    public abstract ScreenSwitcher getSwitcher();

    // on backpress
    // and then you define a method allowBackPressed with the logic to allow back pressed or not
    @Override
    public void onBackPressed() {
//        if(!drawer.isClosing() && getSwitcher().onBackPressed()) {
        if (getSwitcher().onBackPressed()) {
            super.onBackPressed();
        }
    }

    /*
     Need to call super on ActivityResult - Bug in CompatActivity
      http://stackoverflow.com/questions/6147884/onactivityresult-not-being-called-in-fragment
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
