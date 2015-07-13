package com.jattcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Javan on 13/7/2015.
 */
public abstract class ScreenSwitcher {

    private final FragmentManager fragmentManager;

    private final int containerId;

    ScreenSwitcher(ScreenCompatActivity activity, int containerId) {
        this.fragmentManager = activity.getSupportFragmentManager();
        this.containerId = containerId;
    }

    protected abstract Fragment getFragment(int screenId);

    public void defaultScreen() {
        changeFragmentTransaction(getFragment(-1), null);
    }

    public void changeScreen(int screenId) {
        changeFragmentTransaction(getFragment(screenId), null);
    }

    public void changeScreen(int screenId, Bundle bundle) {
        changeFragmentTransaction(getFragment(screenId), bundle);
    }

    private void changeFragmentTransaction(Fragment fragment, Bundle bundle) {
        if (bundle != null) fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
