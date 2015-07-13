package com.jattcode.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Javan on 13/7/2015.
 */
public abstract class BaseLoggerFragment extends Fragment {

    private static final String TAG = BaseLoggerFragment.class.getSimpleName();

    protected boolean LOGGER_ON = true;

    private void log(String msg) {
        if (LOGGER_ON) {
            Log.d(TAG, ".... " + msg);
        }
    }

    protected void logState(String msg) {
        Log.d(TAG, "............ " + msg);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        log("F.A0-onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("F.A1-onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        log("F.A2-onCreateView");
        return null;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("F.A3-onActivityCreated");
    }

    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
        log("F.B1-setInitialSavedState: ????");
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        log("F.B2/C-onViewStateRestored: Should happen on orientation and press home. ");
    }

    public void onStart() {
        super.onStart();
        log("F.B3-onStart: setup listeners");
    }

    public void onResume() {
        super.onResume();
        log("F.C-onResume: Should NOT do anything here. Perhaps SHOW progress dialog if needed");
    }

    public void onPause() {
        super.onPause();
        log("F.D-onPause: Should NOT do anything here. Perhaps DISMISS progress dialog if needed");
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        log("D/E-onSaveInstanceState: Should happen on orientation and press home. not backpress");
    }

    public void onStop() {
        super.onStop();
        log("F.E-onStop: remove listeners");
    }

    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView: ??");
    }

    public void onDetach() {
        super.onDetach();
        log("onDetach: ??");
    }
}
