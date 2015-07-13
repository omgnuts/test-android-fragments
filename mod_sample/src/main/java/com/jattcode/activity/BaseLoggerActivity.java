package com.jattcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Javan on 13/7/2015.
 */
public abstract class BaseLoggerActivity extends AppCompatActivity {

    private static final String TAG = BaseLoggerActivity.class.getSimpleName();

    protected boolean LOGGER_ON = true;

    private void log(String msg) {
        if (LOGGER_ON) {
            Log.d(TAG, ".... " + msg);
        }
    }

    protected void logState(String msg) {
        Log.d(TAG, "............ " + msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("A-onCreate: Create views and UI ");

        /*
         .. create views
         */

        // load data
        onLoadPersistence();
    }

    protected void onRestart() {
        super.onRestart();
        log("B0-onRestart: do nothing but redirect to load models. I assume views are in place");
        onLoadPersistence();
    }

    private void onLoadPersistence() {
        // maybe this is where you should load all data
        log("B1-onLoadPersistence: load data models?");
    }

    protected void onStart() {
        super.onStart();
        log("B2-onStart: setup listeners");
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        log("B2/C-onRestoreInstanceState: Should happen on orientation and press home. ");
    }

    protected void onResume() {
        super.onResume();
        log("C-onResume: Should NOT do anything here. Perhaps SHOW progress dialog if needed");
    }

    protected void onPause() {
        super.onPause();
        log("D-onPause: Should NOT do anything here? Perhaps DISMISS progress dialog if needed");
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        log("D/E-onSaveInstanceState: Should happen on orientation and press home. not backpress");
    }

    protected void onStop() {
        super.onStop();
        log("E-onStop: remove listeners");
    }

    public void onBackPressed() {
        super.onBackPressed();
        log("Z-backpress: only thing to do is probably conditional checks to warn user");
    }

    public void finish() {
        super.finish();
        log("Z-finish: remove leaking listeners if onstop is skipped? like backpress");
    }


}
