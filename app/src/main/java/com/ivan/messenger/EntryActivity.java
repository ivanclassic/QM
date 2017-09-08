package com.ivan.messenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ivan.messenger.presenter.AuthPresenter;
import com.ivan.messenger.ui.MainActivity;
import com.ivan.messenger.ui.SignupActivity;
import com.ivan.messenger.utils.ILog;

public class EntryActivity extends AppCompatActivity
        implements AuthPresenter.IView {
    private static final String TAG = "test.MainActivity";

    private AuthPresenter mAuthPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthPresenter = new AuthPresenter(this);
        mAuthPresenter.onCreate();
    }

    @Override
    public void onAuth() {
        ILog.d(TAG, "授权成功");
        startMainActivity();
    }

    @Override
    public void onNotAuth() {
        ILog.d(TAG, "授权未成功");
        startSignupActivity();
    }


    private void startMainActivity() {
        MainActivity.start(this);
        finish();
    }

    private void startSignupActivity() {
        SignupActivity.start(this);
        finish();
    }
}
