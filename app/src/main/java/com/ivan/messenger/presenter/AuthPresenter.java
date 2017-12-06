package com.ivan.messenger.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ivan.messenger.firebase.model.User;
import com.ivan.messenger.lib.utils.ILog;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoyifan on 17-9-6.
 */

public class AuthPresenter extends BasePresenter implements Serializable {
    private static final String TAG = "AuthPresenter";
    private static boolean sProcessExist = false;

    private static final List<String> FB_PERMISSIONS = Arrays.asList("user_likes", "user_status");

    private transient FirebaseAuth mAuth;
    private transient IView mView;

    private CallbackManager mCallbackManager;
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            ILog.d(TAG, "facebook login success.");
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                @Override
                public void onCompleted(final JSONObject user, GraphResponse graphResponse) {
                    ILog.d(TAG, "facebook login, get user info: " + user);
                    if (user != null) {
                        // TODO handle user login success
                        String uid = null;
                        String name = null;
                        try {
                            uid = user.getString("id");
                            name = user.getString("name");
                        } catch (Throwable e) {
                            ILog.e(TAG, "get user info detail failed.", e);
                        }
                        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(name)) {
                            onFacebookAuthSuccess(uid, name);
                            mView.onAuth();
                        } else {
                            mView.onNotAuth();
                        }
                    } else {
                        mView.onNotAuth();
                    }
                }
            });
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            ILog.d(TAG, "facebook login cancel.");
            mView.onNotAuth();
        }

        @Override
        public void onError(FacebookException error) {
            ILog.e(TAG, "facebook login failed.", error);
            mView.onNotAuth();
        }
    };

    public AuthPresenter(IView view) {
        mAuth = FirebaseAuth.getInstance();
        mView = view;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        LoginManager.getInstance().setDefaultAudience(DefaultAudience.FRIENDS);
        LoginManager.getInstance().registerCallback(mCallbackManager, mFacebookCallback);
    }

    public boolean isAuthed() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return mAuth.getCurrentUser() != null ||
                !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    public void onCreate() {
        ILog.d(TAG, "开始");
        if (needWelcomeShow()) {
            doWelcomeProgress();
            sProcessExist = true;
        } else {
            doMainProgress();
        }
    }

    private boolean needWelcomeShow() {
        // TODO: 更新逻辑，云控控制每天的展示次数
        // 展示条件（优先级顺序）：
        // 1. 没有授权
        // 2. 进程没有加载
        // 3. 其他 （云控）
        return !isAuthed() || !sProcessExist;
    }

    private void doWelcomeProgress() {
        ILog.d(TAG, "启动欢迎流程");
        mView.onWelcome(isAuthed() || !sProcessExist);
    }

    private void doMainProgress() {
        ILog.d(TAG, "启动主流程");
        if (isAuthed()) {
            // 已经授权，跳转住界面
            ILog.d(TAG, "AuthPresenter authed");
            mView.onAuth();
        } else {
            ILog.d(TAG, "AuthPresenter not authed");
            mView.onNotAuth();
        }
    }

    public void onWelcomeComplete() {
        doMainProgress();
    }

    public void startSignup() {
        mView.onStartSignup();
    }

    public void startSignin() {
        mView.onStartSignin();
    }

    public void startFacebookLogin(Fragment fragment) {
        LoginManager.getInstance().logInWithReadPermissions(fragment, FB_PERMISSIONS);
    }

    public void onFacebookLoginResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void createUser(String userName, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onFirebaseAuthSuccess(task.getResult().getUser());
                            mView.onAuth();
                        }
                    }
                });
    }

    private void onFirebaseAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        User me = new User();
        me.uid = user.getUid();
        me.nickName = username;
        me.userName = user.getEmail();
        me.type = User.USER_TYPE_FIREBASE;
        me.save();
    }

    private void onFacebookAuthSuccess(String uid, String name) {
        User me = new User();
        me.uid = uid;
        me.nickName = name;
        me.userName = name;
        me.type = User.USER_TYPE_FACEBOOK;
        me.save();
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
        public void onWelcome(boolean countDown);
        public void onAuth();
        public void onNotAuth();
        public void onStartSignup();
        public void onStartSignin();
    }
}
