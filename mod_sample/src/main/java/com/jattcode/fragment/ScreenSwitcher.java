package com.jattcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;

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
        changeFragmentTransaction(getFragment(0), true, null);
    }

    public void changeScreen(int screenId) {
        changeFragmentTransaction(getFragment(screenId), screenId == 0, null);
    }

    public void changeScreen(int screenId, Bundle bundle) {
        changeFragmentTransaction(getFragment(screenId), screenId == 0, bundle);
    }

    public boolean onBackPressed() {
        final Screen screen = (Screen) fragmentManager.findFragmentById(containerId);
        return screen.onBackPressed();
    }

    private void changeFragmentTransaction(Fragment fragment, boolean isRoot, Bundle bundle) {
        if (bundle != null) fragment.setArguments(bundle);
        if (isRoot) {
            fragmentManager.beginTransaction()
                    .replace(containerId, fragment, "root")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
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
}
