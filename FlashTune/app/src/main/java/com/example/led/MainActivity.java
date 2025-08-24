package com.example.led;


import android.app.Activity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends Activity {
    private LedManager mLedManager = new LedManager();
    private ImageView flashStatusIcon;
    private TextView flashStatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_FlashTune);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    flashStatusIcon = findViewById(R.id.flashStatusIcon);
    flashStatusText = findViewById(R.id.flashStatusText);

            // Request camera permission if needed
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }

    Button btnOn = findViewById(R.id.btnOn);
    Button btnOff = findViewById(R.id.btnOff);
    Button btnBlink = findViewById(R.id.btnBlink);
    Button btnNotify = findViewById(R.id.btnNotify);

        btnOn.setOnClickListener(v -> {
            setFlashlight(true);
            animateFlashStatus(flashStatusIcon, true);
            flashStatusText.setText("Flash ON");
            Toast.makeText(this, "Flashlight turned ON", Toast.LENGTH_SHORT).show();
        });

        btnOff.setOnClickListener(v -> {
            setFlashlight(false);
            animateFlashStatus(flashStatusIcon, false);
            flashStatusText.setText("Flash OFF");
            Toast.makeText(this, "Flashlight turned OFF", Toast.LENGTH_SHORT).show();
        });

        btnBlink.setOnClickListener(v -> {
            blinkFlash(5);
            flashStatusText.setText("Blinking...");
            Toast.makeText(this, "LED is blinking", Toast.LENGTH_SHORT).show();
        });

        btnNotify.setOnClickListener(v -> {
            sendNotification();
        });
    }

    // Real notification integration: triggers LED blink on notification
    private void sendNotification() {
        String channelId = "led_notify_channel";
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "LED Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
        }
        Notification.Builder builder = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                new Notification.Builder(this, channelId) : new Notification.Builder(this);
        builder.setContentTitle("FlashTune Notification")
                .setContentText("LED will blink on notification!")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true);
        nm.notify(1, builder.build());
        flashStatusText.setText("Notification Blink");
        blinkFlash(3);
        Toast.makeText(this, "Notification sent! LED blinked.", Toast.LENGTH_SHORT).show();
    }

        // Real flash control using Camera2 API
        private void setFlashlight(boolean enabled) {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                String cameraId = null;
                for (String id : cameraManager.getCameraIdList()) {
                    if (cameraManager.getCameraCharacteristics(id).get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE) == Boolean.TRUE) {
                        cameraId = id;
                        break;
                    }
                }
                if (cameraId != null) {
                    cameraManager.setTorchMode(cameraId, enabled);
                } else {
                    Toast.makeText(this, "No flash available", Toast.LENGTH_SHORT).show();
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
                Toast.makeText(this, "Camera error", Toast.LENGTH_SHORT).show();
            }
        }

        // Blink flash n times
        private void blinkFlash(int times) {
            new Thread(() -> {
                for (int i = 0; i < times; i++) {
                        runOnUiThread(() -> {
                            setFlashlight(true);
                            animateFlashStatus(findViewById(R.id.flashStatusIcon), true);
                            flashStatusText.setText("Flash ON");
                        });
                        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                        runOnUiThread(() -> {
                            setFlashlight(false);
                            animateFlashStatus(findViewById(R.id.flashStatusIcon), false);
                            flashStatusText.setText("Flash OFF");
                        });
                        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                }
            }).start();
        }

    // Animate flash status icon
    private void animateFlashStatus(ImageView icon, boolean on) {
        icon.animate().rotation(on ? 20f : 0f).setDuration(150).withEndAction(() -> {
            icon.setColorFilter(on ? 0xFFFFEB3B : 0xFFB0BEC5);
        }).start();
    }
}
