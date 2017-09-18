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

import com.ivan.messenger.EntryActivity;
import com.ivan.messenger.R;
import com.ivan.messenger.lib.utils.common.CommonUtils;

/**
 * 注册用Activity，上报到runtime database。
 * 注册后，不可更改。
 *
 * Created by zhaoyifan on 17-9-1.
 */

public class SigninFragment extends AuthBaseFragment implements View.OnClickListener {
    private static final String TAG = "SigninFragment";

    private Toolbar mToolbar;
    private EditText mNameEditor;
    private EditText mPasswordEditor;
    private Button mSigninBtn;

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
            mSigninBtn.setEnabled(enable);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);
        mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.text_signup);
        mToolbar.setLogo(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setOnClickListener(this);
        mNameEditor = rootView.findViewById(R.id.field_username);
        mPasswordEditor = rootView.findViewById(R.id.field_password);
        mSigninBtn = rootView.findViewById(R.id.btn_confirm);
        mSigninBtn.setOnClickListener(this);
        mNameEditor.addTextChangedListener(mNameWatcher);
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
        mAuthPresenter.signinUser(userName, password);
    }
}
