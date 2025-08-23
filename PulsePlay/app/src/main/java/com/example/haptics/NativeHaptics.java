package com.example.haptics;

public class NativeHaptics {
    static {
        System.loadLibrary("haptics_jni");
    }

    public static native void nativeVibrate(int ms, int intensity);
    public static native void nativeStop();
}
