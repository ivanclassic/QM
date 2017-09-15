package com.ivan.messenger.firebase.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by zhaoyifan on 17-7-28.
 */

public abstract class FirebaseDBHelper {

    public static final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public abstract DatabaseReference getTable();

    public abstract void saveValue();

//    public static void post(String table, Map<String, Object> postValues) {
//        String key = database.child(table).push().getKey();
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/" + table + "/" + key, postValues);
//        database.updateChildren(childUpdates);
//    }
//
//    public static Query query(String table) {
//        return database.child(table);
//    }
}
