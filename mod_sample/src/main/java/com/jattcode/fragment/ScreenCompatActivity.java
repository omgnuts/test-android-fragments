package com.jattcode.fragment;

import android.content.Intent;

import com.jattcode.activity.BaseLoggerActivity;

/**
 * Created by Javan on 13/7/2015.
 */
public abstract class ScreenCompatActivity extends BaseLoggerActivity {

    public ScreenCompatActivity() {
        LOGGER_ON = false;
    }

    public abstract ScreenSwitcher getSwitcher();

    /*
     Need to call super on ActivityResult - Bug in CompatActivity
      http://stackoverflow.com/questions/6147884/onactivityresult-not-being-called-in-fragment
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
