package com.ivan.messenger.presenter;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by zhaoyifan on 17-9-6.
 */

public class AuthPresenter extends BasePresenter {
    private static final String TAG = "UserSelfPresenter";

    private FirebaseAuth mAuth;
    private IView mView;

    public AuthPresenter(IView view) {
        mAuth = FirebaseAuth.getInstance();
        mView = view;
    }

    public void onCreate() {
        if (mAuth.getCurrentUser() != null) {
            // 已经授权，跳转住界面
            mView.onAuth();
        } else {
            // 没有授权，跳转登录界面
            mView.onNotAuth();
        }
    }

    public static interface IView {
        public void onAuth();
        public void onNotAuth();
    }
}
