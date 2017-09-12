package com.ivan.messenger.firebase.model;

import com.ivan.messenger.firebase.database.UserFirebaseDBHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyifan on 17-9-11.
 */

public class User implements IDataInterface {
    public String uid;
    public String userName;
    public String nickName;

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("nickName", nickName);
        return result;
    }

    public void save() {
        InnerDBHelper helper = new InnerDBHelper(this);
        helper.saveValue();
    }

    private static class InnerDBHelper extends UserFirebaseDBHelper {
        private User mUser;

        InnerDBHelper(User user) {
            mUser = user;
        }

        @Override
        public void saveValue() {
            getTable().child(mUser.uid).updateChildren(mUser.toMap());
        }
    }
}
