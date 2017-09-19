package com.ivan.messenger.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ivan.messenger.lib.Env;
import com.ivan.messenger.lib.utils.ILog;

import java.util.List;
import java.util.Stack;

public abstract class BaseFragmentActivity extends FragmentActivity {
    private static final String TAG = "BaseFragmentActivity";
    private Fragment mTopFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTopFragment();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                getTopFragment();
            }
        });
    }

    private void getTopFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null || fragments.isEmpty()) {
            mTopFragment = null;
        } else {
            mTopFragment = fragments.get(fragments.size() - 1);
        }
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        try {
            if (mTopFragment != null && mTopFragment instanceof AuthBaseFragment) {
                return;
            }
            BaseFragment bf = (BaseFragment) fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(bf.getEnterAnim(), bf.getExitAnim())
                    .add(getFragmentContainerId(), fragment, fragment.getClass().getName());
            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getName());
            }
            transaction.commitAllowingStateLoss();
            mTopFragment = fragment;
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
            BaseFragment bf = (BaseFragment) fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(bf.getEnterAnim(), bf.getExitAnim())
                    .replace(getFragmentContainerId(), fragment, fragment.getClass().getName());
            transaction.commitAllowingStateLoss();
            mTopFragment = fragment;
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
