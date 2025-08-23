// HapticsManagerService.java
package com.example.haptics;
import android.os.Binder;
import android.os.Binder;
import android.util.Log;

public class HapticsManagerService extends Binder implements IHapticsService {
    private static final String TAG = "HapticsManagerService";

    @Override
    public void vibrate(int durationMs, int intensity) {
        Log.i(TAG, "Service vibrate: " + durationMs + "ms, intensity: " + intensity);
        try {
            NativeHaptics.nativeVibrate(durationMs, intensity);
        } catch (Exception e) {
            Log.e(TAG, "nativeVibrate failed", e);
        }
    }

    @Override
    public void stop() {
        Log.i(TAG, "Service stop");
        try {
            NativeHaptics.nativeStop();
        } catch (Exception e) {
            Log.e(TAG, "nativeStop failed", e);
        }
    }
}
