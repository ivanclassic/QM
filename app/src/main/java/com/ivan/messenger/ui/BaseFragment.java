package com.ivan.messenger.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.messenger.presenter.AuthPresenter;

/**
 * Created by zhaoyifan on 17-9-8.
 */

public abstract class BaseFragment extends Fragment {
    public static final String FRAGMENT_ARGUMENT_PRESENTER = "fragment_argument_presenter";

    protected AuthPresenter mAuthPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuthPresenter = (AuthPresenter) getArguments().getSerializable(FRAGMENT_ARGUMENT_PRESENTER);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract int getEnterAnim();
    public abstract int getExitAnim();
}
