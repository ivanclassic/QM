package com.ivan.messenger.firebase.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyifan on 17-7-28.
 */

public class Me {

    public String uid;
    public String name;
    public String phone;
    public String resume;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("phone", phone);
        result.put("resume", resume);
        return result;
    }
}
