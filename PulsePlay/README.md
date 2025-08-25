# 📱 PulsePlay - Hybrid Haptics Implementation

**Dual-Mode Android Haptics Demo: Educational JNI/HAL + Functional Android API**

PulsePlay demonstrates both approaches to haptic control in Android, showcasing technical depth alongside practical functionality.

## 🎯 Project Overview

This project implements **two distinct haptic control paths**:

### 🔧 **Educational Mode - Pure JNI/HAL**
- **JNI Bridge**: Direct native method integration
- **HAL Implementation**: Hardware abstraction with sysfs simulation
- **System-Level Programming**: Demonstrates Android internals

### 📱 **Functional Mode - Android API**
- **Real Haptics**: Actually vibrates on real devices
- **Production Ready**: Uses Android's Vibrator service
- **Cross-Device Compatibility**: Works across different manufacturers

## 🏗️ Hybrid Architecture

```
MainActivity
    │
    ▼
┌─────────────────┐         ┌─────────────────┐
│ EDUCATIONAL     │         │ FUNCTIONAL      │
│ JNI/HAL PATH    │         │ ANDROID API     │
└─────────────────┘         └─────────────────┘
    │                               │
    ▼                               ▼
NativeHaptics                   HapticsManager
.nativeVibrate()               .vibrate()
    │                               │
    ▼                               ▼
JNI Bridge                      Android Vibrator
(HapticsJni.cpp)               Service
    │                               │
    ▼                               ▼
HAL Layer                       System Hardware
(HapticsHal.cpp)               (Real Vibrator)
    │
    ▼
sysfs/Mock
(Educational)
```

## ⚡ Core Features

### 🔧 **Educational JNI/HAL Path**
- **`nativeVibrate(duration, intensity)`**: Direct HAL calls via JNI
- **`nativeStop()`**: HAL-level vibration control
- **sysfs Integration**: Attempts `/sys/class/timed_output/vibrator/enable`
- **Mock Fallback**: Console logging when hardware unavailable

### 📱 **Functional Android API Path**
- **`vibrate(VibrationEffect)`**: Modern Android haptic API
- **Cross-Device Support**: Works on all Android devices
- **Permission-Based**: Uses standard `VIBRATE` permission
- **Real Hardware**: Actually triggers device vibration

## 🚀 Getting Started

### Prerequisites
- Android Studio with NDK support
- Android device (for testing real haptics)

### Build Process
```bash
./gradlew assembleDebug
```

## 🔧 Implementation Details

### Educational Mode (JNI/HAL)
```java
// Direct native method calls - bypasses Android APIs
NativeHaptics.nativeVibrate(200, 100);  // duration, intensity
NativeHaptics.nativeStop();             // immediate stop
```

### Functional Mode (Android API)
```java
// Standard Android development approach
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    mVibrator.vibrate(VibrationEffect.createOneShot(200, 100));
} else {
    mVibrator.vibrate(200);  // Legacy support
}
```

## 📋 File Structure

```
PulsePlay/
├── app/src/main/
│   ├── java/com/example/
│   │   ├── pulseplay/MainActivity.java    # Dual-mode UI
│   │   └── haptics/
│   │       ├── NativeHaptics.java         # JNI declarations  
│   │       └── HapticsManager.java        # Android API wrapper
│   └── cpp/
│       ├── CMakeLists.txt                 # Native build config
│       ├── HapticsJni.cpp                 # JNI bridge
│       └── HapticsHal.cpp                 # HAL implementation
├── hal/
│   ├── IHapticsHal.h                      # HAL interface
│   ├── HapticsHal.cpp                     # HAL implementation
│   └── test_HapticsHal.cpp                # HAL testing
└── docs/
    └── overview.md                        # Technical docs
```

## ⚖️ Trade-off Analysis

### 🎓 **Educational JNI/HAL**
- ✅ **AOSP Learning**: Authentic system development patterns
- ✅ **Technical Depth**: Understanding Android internals
- ❌ **Root Required**: sysfs writes need elevated permissions
- ❌ **No Real Haptics**: Won't actually vibrate on most devices

### 📱 **Android API**
- ✅ **Actually Works**: Real haptic feedback on real devices
- ✅ **Cross-Platform**: Compatible with all Android devices
- ❌ **High-Level Only**: Abstracts away system implementation
- ❌ **Limited Learning**: Doesn't teach AOSP development

## 🎯 Why This Hybrid Approach?

**Best of Both Worlds**: Technical demonstration + practical functionality
**Educational Value**: Teaches both system and application development
**Portfolio Strength**: Demonstrates comprehensive Android expertise

---

**Educational Focus**: This project demonstrates both the **technical depth** of AOSP system development and the **practical skills** of Android application development.
