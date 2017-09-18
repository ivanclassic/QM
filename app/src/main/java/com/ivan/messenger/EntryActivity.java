package com.ivan.messenger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ivan.messenger.presenter.AuthPresenter;
import com.ivan.messenger.ui.AuthBaseFragment;
import com.ivan.messenger.ui.BaseFragmentActivity;
import com.ivan.messenger.ui.MainActivity;
import com.ivan.messenger.ui.SigninFragment;
import com.ivan.messenger.ui.SignupFragment;
import com.ivan.messenger.ui.WelcomeFragment;
import com.ivan.messenger.lib.utils.ILog;

public class EntryActivity extends BaseFragmentActivity
        implements AuthPresenter.IView {
    private static final String TAG = "test.MainActivity";

    private AuthPresenter mAuthPresenter;
    private Fragment mSignupFragment;
    private Fragment mSigninFragment;
    private WelcomeFragment mWelcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mAuthPresenter = new AuthPresenter(this);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AuthBaseFragment.FRAGMENT_ARGUMENT_PRESENTER, mAuthPresenter);
        initAuthFragment(bundle);
        mAuthPresenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuthPresenter = null;
    }

    private void initAuthFragment(Bundle bundle) {
        mSignupFragment = new SignupFragment();
        mSigninFragment = new SigninFragment();
        mWelcomeFragment = new WelcomeFragment();
        mSignupFragment.setArguments(bundle);
        mSigninFragment.setArguments(bundle);
        mWelcomeFragment.setArguments(bundle);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.container;
    }

    @Override
    public void onWelcome(boolean countDown) {
        ILog.d(TAG, "欢迎欢迎，请看广告");
        Bundle bundle = mWelcomeFragment.getArguments();
        bundle.putBoolean(WelcomeFragment.EXTRA_KEY_COUNTDOWN, countDown);
        mWelcomeFragment.setArguments(bundle);
        addFragment(mWelcomeFragment, false);
    }

    @Override
    public void onAuth() {
        ILog.d(TAG, "授权成功");
        Toast.makeText(this, "OK! Success", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void startMainActivity() {
        MainActivity.start(this);
        finish();
    }

    @Override
    public void onNotAuth() {
        ILog.d(TAG, "授权未成功");
        mWelcomeFragment.showSignPanel();
    }

    @Override
    public void onStartSignup() {
        ILog.d(TAG, "去授权");
        addFragment(mSignupFragment, true);
    }

    @Override
    public void onStartSignin() {
        ILog.d(TAG, "重新授权");
        addFragment(mSigninFragment, true);
    }
}
