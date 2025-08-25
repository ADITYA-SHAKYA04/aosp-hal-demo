package com.example.led;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {
    private LedManager mLedManager = new LedManager();
    private ImageView flashStatusIcon;
    private TextView flashStatusText;
    private boolean isFlashOn = false;
    private static final int CAMERA_PERMISSION_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_FlashTune);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        } else {
            initializeFlashlight();
        }

        flashStatusIcon = findViewById(R.id.flashStatusIcon);
        flashStatusText = findViewById(R.id.flashStatusText);

        Button btnOn = findViewById(R.id.btnOn);
        Button btnOff = findViewById(R.id.btnOff);
        Button btnBlink = findViewById(R.id.btnBlink);
        Button btnNotify = findViewById(R.id.btnNotify);

        btnOn.setOnClickListener(v -> {
            try {
                Toast.makeText(this, "Attempting to turn ON LED via HAL...", Toast.LENGTH_SHORT).show();
                mLedManager.turnOnLED();
                isFlashOn = true;
                updateFlashStatus(true);
                Toast.makeText(this, "LED turned ON via HAL", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to turn ON LED: " + e.getMessage(), Toast.LENGTH_LONG).show();
                android.util.Log.e("MainActivity", "Error turning on LED", e);
            }
        });

        btnOff.setOnClickListener(v -> {
            try {
                Toast.makeText(this, "Attempting to turn OFF LED via HAL...", Toast.LENGTH_SHORT).show();
                mLedManager.turnOffLED();
                isFlashOn = false;
                updateFlashStatus(false);
                Toast.makeText(this, "LED turned OFF via HAL", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to turn OFF LED: " + e.getMessage(), Toast.LENGTH_LONG).show();
                android.util.Log.e("MainActivity", "Error turning off LED", e);
            }
        });

        btnBlink.setOnClickListener(v -> {
            try {
                mLedManager.blinkLED(5);
                flashStatusText.setText("Blinking via HAL...");
                Toast.makeText(this, "LED blinking 5 times via HAL", Toast.LENGTH_SHORT).show();
                
                // Update UI after blink sequence completes
                new Thread(() -> {
                    try {
                        Thread.sleep(2500); // 5 blinks * 500ms per blink
                        runOnUiThread(() -> {
                            isFlashOn = false;
                            updateFlashStatus(false);
                        });
                    } catch (InterruptedException ignored) {}
                }).start();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to blink LED: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnNotify.setOnClickListener(v -> {
            try {
                Toast.makeText(this, "Triggering notification blink...", Toast.LENGTH_SHORT).show();
                mLedManager.blinkOnNotification();
                sendNotification();
                Toast.makeText(this, "Notification LED blink completed!", Toast.LENGTH_SHORT).show();
                
                // Update UI to show notification blink is happening
                flashStatusText.setText("Notification Blink (HAL)");
                
                // Reset status after blink sequence completes
                new Thread(() -> {
                    try {
                        Thread.sleep(1000); // Wait for 3 blinks (3 * 150ms on + 150ms off)
                        runOnUiThread(() -> {
                            isFlashOn = false;
                            updateFlashStatus(false);
                        });
                    } catch (InterruptedException ignored) {}
                }).start();
                
            } catch (Exception e) {
                Toast.makeText(this, "Failed to trigger notification blink: " + e.getMessage(), Toast.LENGTH_LONG).show();
                android.util.Log.e("MainActivity", "Error with notification blink", e);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeFlashlight();
                Toast.makeText(this, "Camera permission granted - Flash ready!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera permission required to control flashlight", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initializeFlashlight() {
        try {
            mLedManager.initializeFlashlight(this);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to initialize flashlight: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFlashStatus(boolean on) {
        animateFlashStatus(flashStatusIcon, on);
        flashStatusText.setText(on ? "LED ON (HAL)" : "LED OFF (HAL)");
    }

    // Real notification integration: triggers LED blink on notification
    private void sendNotification() {
        String channelId = "led_notify_channel";
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                channelId, 
                "FlashTune LED Notifications", 
                NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifications that trigger LED blinks via HAL");
            nm.createNotificationChannel(channel);
        }
        
        Notification.Builder builder = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                new Notification.Builder(this, channelId) : new Notification.Builder(this);
                
        builder.setContentTitle("FlashTune HAL Notification")
                .setContentText("LED blinked 3 times via Hardware Abstraction Layer!")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT);
                
        nm.notify(1, builder.build());
        android.util.Log.i("MainActivity", "Notification sent - LED should have blinked via HAL");
    }

    // Animate flash status icon
    private void animateFlashStatus(ImageView icon, boolean on) {
        icon.animate().rotation(on ? 20f : 0f).setDuration(150).withEndAction(() -> {
            icon.setColorFilter(on ? 0xFFFFEB3B : 0xFFB0BEC5);
        }).start();
    }
}
