// MainActivity.java
// Stub for LED App UI

package com.example.led;

import android.app.Activity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    private LedManager mLedManager = new LedManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOn = findViewById(R.id.btnOn);
        Button btnOff = findViewById(R.id.btnOff);
        Button btnBlink = findViewById(R.id.btnBlink);
        Button btnBreath = findViewById(R.id.btnBreath);
        Button btnStrobe = findViewById(R.id.btnStrobe);
        Button btnRainbow = findViewById(R.id.btnRainbow);
        Button btnNotify = findViewById(R.id.btnNotify);

        btnOn.setOnClickListener(v -> {
            mLedManager.turnOnLED();
            Toast.makeText(this, "LED ON", Toast.LENGTH_SHORT).show();
        });

        btnOff.setOnClickListener(v -> {
            mLedManager.turnOffLED();
            Toast.makeText(this, "LED OFF", Toast.LENGTH_SHORT).show();
        });

        btnBlink.setOnClickListener(v -> {
            mLedManager.blinkLED(5);
            Toast.makeText(this, "LED Blinking", Toast.LENGTH_SHORT).show();
        });

        btnBreath.setOnClickListener(v -> {
            mLedManager.breathingLED(3);
            Toast.makeText(this, "Breathing Pattern", Toast.LENGTH_SHORT).show();
        });

        btnStrobe.setOnClickListener(v -> {
            mLedManager.strobeLED(5);
            Toast.makeText(this, "Strobe Pattern", Toast.LENGTH_SHORT).show();
        });

        btnRainbow.setOnClickListener(v -> {
            mLedManager.rainbowLED(2);
            Toast.makeText(this, "Rainbow Pattern", Toast.LENGTH_SHORT).show();
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
        mLedManager.blinkOnNotification();
        Toast.makeText(this, "Notification sent, LED blinked!", Toast.LENGTH_SHORT).show();
    }
        // Example: Add UI stubs for new patterns
        // Button btnBreath = findViewById(R.id.btnBreath);
        // btnBreath.setOnClickListener(v -> mLedManager.breathingLED(3));
        // Button btnStrobe = findViewById(R.id.btnStrobe);
        // btnStrobe.setOnClickListener(v -> mLedManager.strobeLED(5));
        // Button btnRainbow = findViewById(R.id.btnRainbow);
        // btnRainbow.setOnClickListener(v -> mLedManager.rainbowLED(2));
        // Button btnNotify = findViewById(R.id.btnNotify);
        // btnNotify.setOnClickListener(v -> mLedManager.blinkOnNotification());
    }
}
