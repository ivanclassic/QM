package com.ivan.messenger.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ivan.messenger.firebase.database.UserFirebaseDBHelper;
import com.ivan.messenger.firebase.model.User;
import com.ivan.messenger.sp.KUserConfigManager;
import com.ivan.messenger.utils.ILog;

import java.io.Serializable;

/**
 * Created by zhaoyifan on 17-9-6.
 */

public class AuthPresenter extends BasePresenter implements Serializable {
    private static final String TAG = "AuthPresenter";

    private FirebaseAuth mAuth;
    private IView mView;

    public AuthPresenter(IView view) {
        mAuth = FirebaseAuth.getInstance();
        mView = view;
    }

    public void onCreate() {
        ILog.d(TAG, "AuthPresenter onCreate");
        if (mAuth.getCurrentUser() != null) {
            // 已经授权，跳转住界面
            ILog.d(TAG, "AuthPresenter authed");
            mView.onAuth();
        } else {
            String userName = KUserConfigManager.getInstance().getSignedName();
            if (TextUtils.isEmpty(userName)) {
                // 从未授权过
                ILog.d(TAG, "AuthPresenter never authed");
                mView.onNotAuth();
            } else {
                // 需要重新授权
                ILog.d(TAG, "AuthPresenter need re-login");
                mView.onReAuth(userName);
            }
        }
    }

    public void createUser(String userName, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            mView.onAuth();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        User me = new User();
        me.nickName = username;
        me.userId = user.getEmail();
        UserFirebaseDBHelper ufh = new UserFirebaseDBHelper();
        ufh.saveValue(me);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public void signinUser(String userName, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mView.onAuth();
                        }
                    }
                });
    }

    public static interface IView {
        public void onAuth();
        public void onNotAuth();
        public void onReAuth(String userName);
    }
}
