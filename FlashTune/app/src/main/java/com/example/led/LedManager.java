package com.example.led;

public class LedManager {
    static {
        System.loadLibrary("led_jni");
    }
    public native void turnOnLED();
    public native void turnOffLED();
    public native void blinkLED(int times);
    public native void blinkOnNotification();

    // Example usage
    public void demoPattern() {
        try {
            turnOnLED();
            Thread.sleep(500);
            blinkLED(3);
            turnOffLED();
            blinkOnNotification();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
