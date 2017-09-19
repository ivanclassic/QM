package com.ivan.messenger.ui;

import com.ivan.messenger.R;

/**
 * Created by zhaoyifan on 17-9-11.
 */

public class AuthBaseFragment extends BaseFragment {
    public static final String FRAGMENT_ARGUMENT_PRESENTER = "fragment_argument_presenter";

    @Override
    public int getEnterAnim() {
        return R.anim.right_enter_anim;
    }

    @Override
    public int getExitAnim() {
        return R.anim.left_exit_anim;
    }
}
