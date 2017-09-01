package com.ivan.messenger;

public class RuntimeCheck {
    public static final String IMES_SERVICE_PROCESSNAME = ":service";

    private static boolean s_bIsSerivceProcess = false;
    private static boolean s_bIsUiProcess      = false;
    private static Thread s_mainThread         = null;

    public static void init(String procName) {
        s_mainThread = Thread.currentThread();
        if (procName.contains(IMES_SERVICE_PROCESSNAME)) {
            s_bIsSerivceProcess = true;
        } else {
            s_bIsUiProcess = true;
        }
    }
    
    public static void setServiceProcess() {
        s_bIsUiProcess = false;
        s_bIsSerivceProcess = true;
    }
    
    public static void checkUiProcess() {
        if (!s_bIsUiProcess) {
            throw new RuntimeException("Must run in UI Process");
        }
    }

    public static void checkServiceProcess() {
        if (!s_bIsSerivceProcess) {
            throw new RuntimeException("Must run in Service Process");
        }
    }
    
    public static void checkMainThread() {
        if (Thread.currentThread() != s_mainThread) {
            throw new RuntimeException("Must run in UI Thread");
        }
    }
    
    public static void checkNoMainThread() {
        if (Thread.currentThread() == s_mainThread) {
            throw new RuntimeException("Must not run in UI Thread");
        }
    }

    public static boolean isUIProcess() {
        return s_bIsUiProcess;
    }
    
    public static boolean isServiceProcess() {
        return s_bIsSerivceProcess;
    }
}
