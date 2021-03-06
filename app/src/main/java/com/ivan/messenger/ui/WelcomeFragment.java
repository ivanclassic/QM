package com.ivan.messenger.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ivan.messenger.R;

import java.lang.ref.WeakReference;

import static com.ivan.messenger.lib.KFinalValues.EXTRA_KEY_COUNTDOWN;

/**
 * Created by zhaoyifan on 17-8-22.
 */

public class WelcomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "SignupFragment";

    private View mSignPanel;
    private View mSignupBtn;
    private View mSigninBtn;
    private View mFacebookLoginBtn;
    private Handler mHandler;
    private InnerRunnable mRunnable;
    private int mSeconds;
    private int mSignPanelHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        boolean countDown = getArguments().getBoolean(EXTRA_KEY_COUNTDOWN, true);
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
        mSignPanel = rootView.findViewById(R.id.sign_panel);
        mSignupBtn = rootView.findViewById(R.id.btn_sign_up);
        mSigninBtn = rootView.findViewById(R.id.btn_sign_in);
        mFacebookLoginBtn = rootView.findViewById(R.id.btn_facebook_login);
        mSignupBtn.setOnClickListener(this);
        mSigninBtn.setOnClickListener(this);
        mFacebookLoginBtn.setOnClickListener(this);
        if (countDown) {
            mSignPanel.post(new Runnable() {
                @Override
                public void run() {
                    mSignPanelHeight = mSignPanel.getHeight();
                    startWelcomeAnimation();
                }
            });
        } else {
            mSignPanel.post(new Runnable() {
                @Override
                public void run() {
                    mSignPanelHeight = mSignPanel.getHeight();
                    hideSignPanel();
                    showSignPanel();
                }
            });
        }
        return rootView;
    }

    @Override
    public int getEnterAnim() {
        return 0;
    }

    @Override
    public int getExitAnim() {
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAuthPresenter.onFacebookLoginResult(requestCode, resultCode, data);
    }

    private void startWelcomeAnimation() {
        // TODO： 默认三秒，需要云控，用于展示广告
        hideSignPanel();
        mSeconds = 3;
        mRunnable = new InnerRunnable(this);
        mHandler = new Handler();
        mHandler.post(mRunnable);
    }

    private void hideSignPanel() {
        mSignPanel.setTranslationY(mSignPanelHeight);
        mSignPanel.setVisibility(View.GONE);
    }

    public void showSignPanel() {
        mSignPanel.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mSignPanel, "translationY", mSignPanelHeight, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }

    private void countDown() {
        mSeconds--;
        if (mSeconds == 0) {
            mAuthPresenter.onWelcomeComplete();
        } else {
            mHandler.postDelayed(mRunnable, 1000);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                mAuthPresenter.startSignin();
                break;
            case R.id.btn_sign_up:
                mAuthPresenter.startSignup();
                break;
            case R.id.btn_facebook_login:
                mAuthPresenter.startFacebookLogin(this);
                break;
        }
    }

    private static class InnerRunnable implements Runnable {
        private WeakReference<WelcomeFragment> mReference;

        InnerRunnable(WelcomeFragment fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @Override
        public void run() {
            if (mReference != null && mReference.get() != null) {
                mReference.get().countDown();
            }
        }
    }
}
