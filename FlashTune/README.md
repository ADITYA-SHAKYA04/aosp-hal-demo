# FlashTune AOSP Haptics/LED HAL Demo

This project demonstrates an AOSP-style HAL for LED/flash control, including native (JNI/C++) and Java integration. It is designed for recruiter-friendly presentation and real hardware or sysfs simulation.

## Features
- Native C++ HAL for LED/flash control
- JNI bridge to Java (`LedManager`)
- Patterns: Blink, Blink on Notification
- Modern Android UI (Material Design)
- No Camera2 API required (uses sysfs for hardware access)

## End-to-End Pipeline Diagram

End-to-end LED pipeline: App → Framework → System Service → JNI → HAL → Hardware

```
[App UI] (Java)
    │  turnOnLED()
    ▼
[LedManager API] (Framework Java)
    │  AIDL Binder
    ▼
[LedManagerService @ system_server]
    │  JNI Bridge (libled_jni.so)
    ▼
[LED HAL] (C++ libled_hal.so)
    │
    └──> /sys/class/led
                │
                ▼
          [LED Hardware]
```

## How It Works
- **Native Layer:** Implements LED patterns in C++ and writes to sysfs (`/sys/class/led/led0/brightness`).
- **Java Layer:** `LedManager` provides native method calls for UI and app logic.
- **UI:** Buttons trigger patterns; status is shown in the app.

## Usage
1. Build and install the app on an AOSP device with sysfs LED support.
2. Use the UI to turn LED ON/OFF, blink, or blink on notification.
3. Actions will control the LED/flash via sysfs writes (simulated if not available).

## File Structure
- `hal/led_hal.cpp` — Native HAL implementation
- `hal/led_hal.h` — Native HAL header
- `jni/led_jni.cpp` — JNI bridge
- `app/src/main/java/com/example/led/LedManager.java` — Java manager
- `app/src/main/java/com/example/led/MainActivity.java` — Main UI
- `app/src/main/res/layout/activity_main.xml` — UI layout

## Requirements
- Android NDK
- AOSP device with `/sys/class/led/led0/brightness` (or update path for your hardware)
- CMake/Gradle for build

## Customization
- For real hardware, ensure your device exposes the required sysfs nodes.

## Troubleshooting
- If patterns do not work, check sysfs path and device permissions.
- For build errors, ensure JNI and NDK include paths are set in `CMakeLists.txt`.

## License
MIT
