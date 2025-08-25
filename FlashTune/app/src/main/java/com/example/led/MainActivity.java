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

public class MainActivity extends Activity {
    private LedManager mLedManager = new LedManager();
    private ImageView flashStatusIcon;
    private TextView flashStatusText;
    private boolean isFlashOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_FlashTune);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashStatusIcon = findViewById(R.id.flashStatusIcon);
        flashStatusText = findViewById(R.id.flashStatusText);

        Button btnOn = findViewById(R.id.btnOn);
        Button btnOff = findViewById(R.id.btnOff);
        Button btnBlink = findViewById(R.id.btnBlink);
        Button btnNotify = findViewById(R.id.btnNotify);

        btnOn.setOnClickListener(v -> {
            try {
                mLedManager.turnOnLED();
                isFlashOn = true;
                updateFlashStatus(true);
                Toast.makeText(this, "LED turned ON via HAL", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to turn ON LED: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnOff.setOnClickListener(v -> {
            try {
                mLedManager.turnOffLED();
                isFlashOn = false;
                updateFlashStatus(false);
                Toast.makeText(this, "LED turned OFF via HAL", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to turn OFF LED: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                mLedManager.blinkOnNotification();
                sendNotification();
                Toast.makeText(this, "Notification LED blink triggered", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to trigger notification blink: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        flashStatusText.setText("Notification Sent");
        Toast.makeText(this, "Notification sent! LED blinked via HAL.", Toast.LENGTH_SHORT).show();
    }

    // Animate flash status icon
    private void animateFlashStatus(ImageView icon, boolean on) {
        icon.animate().rotation(on ? 20f : 0f).setDuration(150).withEndAction(() -> {
            icon.setColorFilter(on ? 0xFFFFEB3B : 0xFFB0BEC5);
        }).start();
    }
}
