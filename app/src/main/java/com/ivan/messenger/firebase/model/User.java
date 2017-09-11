package com.ivan.messenger.firebase.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyifan on 17-9-11.
 */

public class User implements IDataInterface {
    public String userId;
    public String nickName;

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("nickName", nickName);
        return result;
    }
}
