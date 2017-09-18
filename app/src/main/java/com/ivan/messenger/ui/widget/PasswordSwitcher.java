package com.ivan.messenger.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.ivan.messenger.R;
import com.ivan.messenger.lib.utils.ILog;

/**
 * Created by zhaoyifan on 17-9-18.
 */

public class PasswordSwitcher extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener {
    private static final String TAG = "PasswordSwitcher";

    private boolean mPwdVisible = false;
    private boolean mSwitchable = false;
    private int mImgOnRes;
    private int mImgOffRes;
    private EditText mEditor;

    public PasswordSwitcher(Context context) {
        this(context, null);
    }

    public PasswordSwitcher(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPwdVisible = false;
        mSwitchable = true;
        mImgOnRes = R.drawable.ic_visibility_black_24dp;
        mImgOffRes = R.drawable.ic_visibility_off_black_24dp;
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.PasswordSwitcher);
            mPwdVisible = a.getBoolean(R.styleable.PasswordSwitcher_switcher_visible, false);
            mSwitchable = a.getBoolean(R.styleable.PasswordSwitcher_switcher_switchable, true);
            mImgOnRes = a.getResourceId(R.styleable.PasswordSwitcher_switcher_on_img, mImgOnRes);
            mImgOffRes = a.getResourceId(R.styleable.PasswordSwitcher_switcher_off_img, mImgOffRes);
        } catch (Throwable e) {
            ILog.e(TAG, "init password switcher error", e);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        setOnClickListener(this);
    }

    public void setEditor(EditText editor) {
        mEditor = editor;
        setPwdVisibility(mPwdVisible);
    }

    public void setPwdVisibility(boolean visible) {
        if (mEditor != null) {
            if (mSwitchable) {
                mPwdVisible = visible;
            }
            changeVisible();
        }
    }

    private void changeVisible() {
        if (mPwdVisible) {
            mEditor.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            setImageResource(mImgOnRes);
        } else {
            mEditor.setTransformationMethod(PasswordTransformationMethod.getInstance());
            setImageResource(mImgOffRes);
        }
    }

    public void toggle() {
        setPwdVisibility(!mPwdVisible);
    }

    @Override
    public void onClick(View view) {
        toggle();
    }
}
