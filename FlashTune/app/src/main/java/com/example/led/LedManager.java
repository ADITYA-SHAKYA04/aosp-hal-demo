package com.example.led;

import android.util.Log;

public class LedManager {
    private static final String TAG = "LedManager";
    
    static {
        try {
            System.loadLibrary("led_jni");
            Log.i(TAG, "Native library led_jni loaded successfully");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Failed to load native library led_jni", e);
        }
    }
    
    // Native method declarations - these call into HAL via JNI
    public native void turnOnLED();
    public native void turnOffLED();
    public native void blinkLED(int times);
    public native void blinkOnNotification();
    
    // Demo pattern showing full integration
    public void demoPattern() {
        try {
            Log.i(TAG, "Starting demo pattern");
            turnOnLED();
            Thread.sleep(500);
            blinkLED(3);
            Thread.sleep(1500);
            turnOffLED();
            blinkOnNotification();
            Log.i(TAG, "Demo pattern completed");
        } catch (Exception e) {
            Log.e(TAG, "Error in demo pattern", e);
        }
    }
}
