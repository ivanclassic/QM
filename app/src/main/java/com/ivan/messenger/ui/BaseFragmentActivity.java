package com.ivan.messenger.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.ivan.messenger.Env;

public abstract class BaseFragmentActivity extends FragmentActivity {

    protected void addFragment(Fragment fragment, boolean addToBackStack) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .add(getFragmentContainerId(), fragment, fragment.getClass().getName());
            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getName());
            }
            transaction.commitAllowingStateLoss();
        } catch (Throwable e) {
            if (Env.DEBUG) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract int getFragmentContainerId();
}
