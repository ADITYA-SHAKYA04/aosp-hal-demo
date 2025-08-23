# 📱 AOSP Haptics HAL Demo

**End-to-End Android System Demo: HAL → JNI → Framework → App**

This project showcases a custom Haptics (Vibration) HAL integrated into the Android platform:
- HAL (C++) → Dummy haptics driver (vibrate, stop, pattern).
- JNI Bridge → Connects C++ HAL with Android system services.
- Framework (Java + AIDL) → Exposes HapticsManager API to apps.
- System Service → Runs inside system_server, handles Binder calls.
- App (Kotlin) → Simple UI to trigger vibration and patterns.

👉 Designed as a portfolio-ready AOSP module to demonstrate system-level Android expertise (HAL, Binder, Framework, App flow).
👉 Easily extensible to control real vibrator hardware via `/sys/class/timed_output/vibrator`.

---


**End-to-end haptics pipeline: App → Framework → HAL → Hardware**

```
[App UI] (Java)
   │  vibrate(500ms)
   ▼
[HapticsManager API] (Framework Java)
   │  AIDL Binder
   ▼
[HapticsManagerService @ system_server]
   │  JNI Bridge (libhaptics_jni.so)
   ▼
[Haptics HAL] (C++ libhaptics_hal.so)
   │
   └──> /sys/class/timed_output/vibrator
            │
            ▼
        [Hardware Motor]
```

```mermaid
flowchart TD
    A[App UI\n(Java)] --> B[HapticsManager API\n(Framework Java)]
    B --> C[AIDL Binder\nIHapticsService]
    C --> D[HapticsManagerService\nsystem_server]
    D --> E[JNI Bridge\nlibhaptics_jni.so]
    E --> F[Haptics HAL\nlibhaptics_hal.so]
    F --> G[/sys/class/timed_output/vibrator]
    G --> H[Haptic Motor Hardware]
```
**End-to-end haptics pipeline: App → Framework → HAL → Hardware**

```
[App UI] (Kotlin)
   │  vibrate(500ms)
   ▼
[HapticsManager API] (Framework Java)
   │  AIDL Binder
   ▼
[HapticsManagerService @ system_server]
   │  JNI Bridge (libhaptics_jni.so)
   ▼
[Haptics HAL] (C++ libhaptics_hal.so)
   │
   └──> /sys/class/timed_output/vibrator
            │
            ▼
        [Hardware Motor]
```

```mermaid
flowchart TD
    A[App UI\n(Kotlin)] --> B[HapticsManager API\n(Framework Java)]
    B --> C[AIDL Binder\nIHapticsService]
    C --> D[HapticsManagerService\nsystem_server]
    D --> E[JNI Bridge\nlibhaptics_jni.so]
    E --> F[Haptics HAL\nlibhaptics_hal.so]
    F --> G[/sys/class/timed_output/vibrator]
    G --> H[Haptic Motor Hardware]
```
