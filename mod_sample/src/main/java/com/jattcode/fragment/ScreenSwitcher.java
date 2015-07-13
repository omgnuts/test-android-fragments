package com.jattcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Javan on 13/7/2015.
 */
public abstract class ScreenSwitcher {

    // just for the loggin capabilitiues
    private final ScreenCompatActivity activity;

    private final FragmentManager fragmentManager;

    private final int containerId;

    ScreenSwitcher(ScreenCompatActivity activity, int containerId) {
        this.activity = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
        this.containerId = containerId;
    }

    protected abstract Fragment getFragment(int screenId);

    public void rootScreen() {
        changeFragmentTransaction(0, null);
    }

    public void changeScreen(int screenId) {
        changeFragmentTransaction(screenId, null);
    }

    public void changeScreen(int screenId, Bundle bundle) {
        changeFragmentTransaction(screenId, bundle);
    }

    private void changeFragmentTransaction(int screenId, Bundle bundle) {
        final boolean isRoot = screenId == 0;

        if (isRoot) {
            // create a new instance if root is not found.
            // else just pop backstack all the way to root.
            Fragment fragment = fragmentManager.findFragmentByTag("root");
            if (fragment == null) {
                fragment = getFragment(screenId);
                if (bundle != null) fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(containerId, fragment, "root")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                if (bundle != null) fragment.setArguments(bundle);
                int last = fragmentManager.getBackStackEntryCount() - 1;
                int id = fragmentManager.getBackStackEntryAt(last).getId();
                fragmentManager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        } else {
            // Original implementation used this code below
            // but that caused the pop before the replace happened
            // the root fragment had called onViewStateRestored momentarily.
            // it was so fast that it did not even cause a UI flicker.
            // however, I still don't like it because it made the fragment resume and pause so
            // quickly you do not know if there will be further repercussions
            // fragmentManager.popBackStack("screen", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // In this method, we remove the last entry after the replace is done.
            // this is performed by obtaining the entryId before the transaction,
            // and flushing it after the transaction happens.
            int id = -1;
            if (fragmentManager.getBackStackEntryCount()  > 0) {
                int last = fragmentManager.getBackStackEntryCount() - 1;
                BackStackEntry entry = fragmentManager.getBackStackEntryAt(last);
                id = entry.getId();
            }
            // another method to look at would be to use a remove within the transaction

            final Fragment fragment = getFragment(screenId);
            if (bundle != null) fragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(containerId, fragment, "screen")
                    .addToBackStack("screen")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

            if (id > -1) {
                fragmentManager.popBackStack(id, 0);
            }

        }
    }

    public boolean onBackPressed() {
        final Screen screen = (Screen) fragmentManager.findFragmentById(containerId);
        return screen.onBackPressed();
    }

    // light weight map for really small items like textview states that have not been saved
    // NOTE: DO NOT MISUSE. It should be really small.
    // This is to handle cases where the textviews are not recovered on backpress.
    // other views are properly recovered
    private final HashMap<String, Bundle> stateMap = new HashMap<String, Bundle>();

    public void putState(String key, Bundle state) {
        stateMap.put(key, state);
    }

    public Bundle getState(String key) {
        Bundle bundle = stateMap.get(key);
        stateMap.remove(bundle);
        return bundle;
    }

}
