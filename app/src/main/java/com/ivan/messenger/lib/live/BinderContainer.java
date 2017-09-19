package com.ivan.messenger.lib.live;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.util.ArrayMap;

import com.ivan.messenger.lib.live.aidl.BinderObtainer;

import java.util.Map;

public class BinderContainer extends BinderObtainer.Stub {
    private static Map<String, Class<?> > mObjMap = new ArrayMap<String, Class<?>>();

    static{
    }

    @Override
    public IBinder GetBinder(String name) throws RemoteException {
        Class<?> cls = mObjMap.get(name);
        if ( cls != null ){
            try {
                IBinder newObj = (IBinder) cls.newInstance();
                return newObj;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            
        }
        return null;
    }
    
}
