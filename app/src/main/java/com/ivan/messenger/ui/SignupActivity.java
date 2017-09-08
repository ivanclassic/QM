package com.ivan.messenger.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ivan.messenger.R;
import com.ivan.messenger.utils.common.CommonUtils;

/**
 * 注册用Activity，上报到runtime database。
 * 注册后，不可更改。
 *
 * Created by zhaoyifan on 17-9-1.
 */

public class SignupActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";

    private EditText mNameEditor;
    private EditText mPasswordEditor;
    private EditText mConfirmEditor;
    private Button mSignupBtn;

    private TextWatcher mNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            boolean enable = !TextUtils.isEmpty(editable.toString()) &&
                    CommonUtils.isEmailValid(editable.toString());
            mSignupBtn.setEnabled(enable);
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        CommonUtils.safeStartIntent(context, intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mNameEditor = findViewById(R.id.field_username);
        mPasswordEditor = findViewById(R.id.field_password);
        mConfirmEditor = findViewById(R.id.field_confirm_password);
        mSignupBtn = findViewById(R.id.btn_confirm);
        mSignupBtn.setOnClickListener(this);
        mNameEditor.addTextChangedListener(mNameWatcher);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                checkUserNameRegisted();
                break;
        }
    }

    private void checkUserNameRegisted() {
        String userName = mNameEditor.getText().toString();
        String password = mPasswordEditor.getText().toString();
        String confirmPw = mConfirmEditor.getText().toString();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        if (!TextUtils.equals(password, confirmPw)) {
            return;
        }
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "OK! Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
