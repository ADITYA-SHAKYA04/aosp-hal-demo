package com.example.led;

import android.util.Log;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class LedManager {
    private static final String TAG = "LedManager";
    private static CameraManager cameraManager;
    private static String cameraId;
    private static boolean isFlashOn = false;
    
    static {
        try {
            System.loadLibrary("led_jni");
            Log.i(TAG, "Native library led_jni loaded successfully");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Failed to load native library led_jni", e);
        }
    }
    
    // Initialize camera manager for actual flash control
    public void initializeFlashlight(Context context) {
        try {
            cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            if (cameraManager != null) {
                String[] cameraIds = cameraManager.getCameraIdList();
                for (String id : cameraIds) {
                    if (cameraManager.getCameraCharacteristics(id).get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                        cameraId = id;
                        Log.i(TAG, "Camera with flash found: " + cameraId);
                        break;
                    }
                }
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to initialize camera for flashlight", e);
        }
    }
    
    // Native method declarations - these call into HAL via JNI
    public native void turnOnLED();
    public native void turnOffLED();
    public native void blinkLED(int times);
    public native void blinkOnNotification();
    
    // Actual flashlight control using Android Camera API
    public void setFlashlight(boolean enabled) {
        if (cameraManager == null || cameraId == null) {
            Log.e(TAG, "Camera manager not initialized");
            return;
        }
        
        try {
            cameraManager.setTorchMode(cameraId, enabled);
            isFlashOn = enabled;
            Log.i(TAG, "Flashlight " + (enabled ? "ON" : "OFF"));
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to set flashlight: " + e.getMessage());
        }
    }
    
    public boolean isFlashlightOn() {
        return isFlashOn;
    }
    
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
