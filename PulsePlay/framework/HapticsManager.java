// HapticsManager.java
package com.example.haptics;
import android.os.RemoteException;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.Manifest;
import android.content.pm.PackageManager;

public class HapticsManager {
    private final IHapticsService mService;
    private final Context mContext;

    public HapticsManager(Context context) {
        mContext = context;
        mService = IHapticsService.Stub.asInterface(
            ServiceManager.getService("haptics"));
    }

    public void vibrate(int durationMs, int intensity) {
        if (mContext.checkCallingOrSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            try { mService.vibrate(durationMs, intensity); }
            catch (RemoteException e) { }
        }
    }

    public void stop() {
        try { mService.stop(); }
        catch (RemoteException e) { }
    }
}
