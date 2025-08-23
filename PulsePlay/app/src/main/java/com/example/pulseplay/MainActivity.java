package com.example.pulseplay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haptics.NativeHaptics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button vibrateBtn = findViewById(R.id.vibrateBtn);
        Button stopBtn = findViewById(R.id.stopBtn);

        vibrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NativeHaptics.nativeVibrate(200, 100);
                Toast.makeText(MainActivity.this, "HAL: Vibrate triggered", Toast.LENGTH_SHORT).show();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NativeHaptics.nativeStop();
                Toast.makeText(MainActivity.this, "HAL: Stop triggered", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
