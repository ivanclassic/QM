package com.ivan.messenger.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ivan.messenger.R;
import com.ivan.messenger.presenter.AuthPresenter;
import com.ivan.messenger.utils.common.CommonUtils;

/**
 * 注册用Activity，上报到runtime database。
 * 注册后，不可更改。
 *
 * Created by zhaoyifan on 17-9-1.
 */

public class SignupFragment extends AuthBaseFragment implements View.OnClickListener {
    private static final String TAG = "SignupFragment";

    public static final String FRAGMENT_ARGUMENT_PRESENTER = "fragment_argument_presenter";

    private EditText mNameEditor;
    private EditText mPasswordEditor;
    private EditText mConfirmEditor;
    private Button mSignupBtn;
    private AuthPresenter mAuthPresenter;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        mNameEditor = rootView.findViewById(R.id.field_username);
        mPasswordEditor = rootView.findViewById(R.id.field_password);
        mConfirmEditor = rootView.findViewById(R.id.field_confirm_password);
        mSignupBtn = rootView.findViewById(R.id.btn_confirm);
        mSignupBtn.setOnClickListener(this);
        mNameEditor.addTextChangedListener(mNameWatcher);
        mAuthPresenter = (AuthPresenter) getArguments().getSerializable(FRAGMENT_ARGUMENT_PRESENTER);
        return rootView;
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
        mAuthPresenter.createUser(userName, password);
    }
}
