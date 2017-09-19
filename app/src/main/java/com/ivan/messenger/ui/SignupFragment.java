package com.ivan.messenger.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ivan.messenger.EntryActivity;
import com.ivan.messenger.R;
import com.ivan.messenger.lib.utils.common.CommonUtils;
import com.ivan.messenger.ui.widget.PasswordSwitcher;

/**
 * 注册用Activity，上报到runtime database。
 * 注册后，不可更改。
 *
 * Created by zhaoyifan on 17-9-1.
 */

public class SignupFragment extends AuthBaseFragment implements View.OnClickListener {
    private static final String TAG = "SignupFragment";

    private Toolbar mToolbar;
    private EditText mNameEditor;
    private EditText mPasswordEditor;
    private EditText mConfirmEditor;
    private View mSignupBtn;
    private PasswordSwitcher mVisibilitySwitch1;
    private PasswordSwitcher mVisibilitySwitch2;

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
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.text_signup);
        mToolbar.setLogo(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setOnClickListener(this);
        mNameEditor = rootView.findViewById(R.id.field_username);
        mNameEditor.requestFocus();
        mPasswordEditor = rootView.findViewById(R.id.field_password);
        mConfirmEditor = rootView.findViewById(R.id.field_confirm_password);
        mSignupBtn = rootView.findViewById(R.id.btn_confirm);
        mSignupBtn.setOnClickListener(this);
        mNameEditor.addTextChangedListener(mNameWatcher);
        mVisibilitySwitch1 = rootView.findViewById(R.id.switcher_pwd_visibility);
        mVisibilitySwitch2 = rootView.findViewById(R.id.switcher_confirm_visibility);
        mVisibilitySwitch1.setEditor(mPasswordEditor);
        mVisibilitySwitch2.setEditor(mConfirmEditor);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                checkUserNameRegisted();
                break;
            case R.id.toolbar:
                ((EntryActivity) getActivity()).removeFragment(true);
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
