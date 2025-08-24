// ILedManager.aidl
// Stub for LED AIDL interface

package com.example.led;

interface ILedManager {
    void turnOnLED();
    void turnOffLED();
    void blinkLED(int times);

    // New LED patterns
    void breathingLED(int cycles);
    void strobeLED(int flashes);
    void rainbowLED(int cycles);
    void blinkOnNotification();
}
