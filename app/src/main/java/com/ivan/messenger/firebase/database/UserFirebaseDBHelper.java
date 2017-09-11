package com.ivan.messenger.firebase.database;

import com.google.firebase.database.DatabaseReference;
import com.ivan.messenger.firebase.model.IDataInterface;

import java.util.Map;

/**
 * Created by zhaoyifan on 17-9-11.
 */

public class UserFirebaseDBHelper extends FirebaseDBHelper {

    @Override
    public DatabaseReference getTable() {
        return database.child("users");
    }

    @Override
    public void saveValue(IDataInterface data) {
        Map<String, Object> userData = data.toMap();
        getTable().updateChildren(userData);
    }
}
