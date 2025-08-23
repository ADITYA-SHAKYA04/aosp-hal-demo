// IHapticsService.aidl
package com.example.haptics;
// IHapticsService.aidl
// Usage: Used by HapticsManager and system service for vibration control
package com.example.haptics;

interface IHapticsService {
    /**
     * Vibrate for the given duration and intensity
     */
    void vibrate(int durationMs, int intensity);

    /**
     * Stop vibration
     */
    void stop();
}
