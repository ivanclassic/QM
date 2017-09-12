package com.ivan.messenger.firebase.database;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by zhaoyifan on 17-9-11.
 */

public abstract class UserFirebaseDBHelper extends FirebaseDBHelper {

    @Override
    public final DatabaseReference getTable() {
        return database.child("users");
    }
}
