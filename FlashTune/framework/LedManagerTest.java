// LedManagerTest.java
// Unit test stub for LedManager framework API

package com.example.led;

public class LedManagerTest {
    public static void main(String[] args) {
        LedManager manager = new LedManager();
        manager.turnOnLED();
        manager.turnOffLED();
        manager.blinkLED(2);
        manager.breathingLED(1);
        manager.strobeLED(1);
        manager.rainbowLED(1);
        manager.blinkOnNotification();
        manager.demoPattern();
    }
}
