package com.ivan.messenger.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.ivan.messenger.lib.Env;
import com.ivan.messenger.lib.utils.ILog;

public abstract class BaseFragmentActivity extends FragmentActivity {
    private static final String TAG = "BaseFragmentActivity";

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

    protected void replaceFragment(Fragment fragment) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContainerId(), fragment, fragment.getClass().getName());
            transaction.commitAllowingStateLoss();
        } catch (Throwable e) {
            if (Env.DEBUG) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void removeFragment(boolean immediate) {
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            if (immediate) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            ILog.d(TAG, "all fragment removed, finish activity");
            finish();
        }
    }

    protected abstract int getFragmentContainerId();
}
