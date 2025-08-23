package com.example.haptics;
import android.os.RemoteException;
import android.content.Context;
import android.os.Vibrator;
import android.os.Build;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;

public class HapticsManager {
    private final Vibrator mVibrator;
    private final Context mContext;

    public HapticsManager(Context context) {
        mContext = context;
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate(int durationMs, int intensity) {
        if (mContext.checkCallingOrSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mVibrator.vibrate(android.os.VibrationEffect.createOneShot(durationMs, intensity));
            } else {
                mVibrator.vibrate(durationMs);
            }
        }
    }

    public void stop() {
        mVibrator.cancel();
    }
}
