# FlashTune - LED Control via JNI/HAL

An educational Android application demonstrating **authentic AOSP HAL integration** for LED control through JNI calls, bypassing high-level Android APIs.

## 🎯 Project Overview

FlashTune showcases the complete AOSP development stack:
- **Java Application Layer**: Simple UI for LED control
- **JNI Bridge**: Native method integration between Java and C++
- **HAL Implementation**: Hardware abstraction layer for LED hardware access
- **System-Level Integration**: Direct sysfs interaction for hardware control

## 🏗️ Architecture

```
┌─────────────────┐
│   MainActivity  │  ← User Interface (Java)
└─────────┬───────┘
          │ mLedManager.turnOnLED()
┌─────────▼───────┐
│   LedManager    │  ← Native Method Declarations (Java)
└─────────┬───────┘
          │ JNI Call
┌─────────▼───────┐
│   led_jni.cpp   │  ← JNI Bridge (C++)
└─────────┬───────┘
          │ HAL Function Call
┌─────────▼───────┐
│   led_hal.cpp   │  ← Hardware Abstraction Layer (C++)
└─────────┬───────┘
          │ sysfs write
┌─────────▼───────┐
│   /sys/class/   │  ← Linux Kernel Interface
│   leds/led0/    │
└─────────────────┘
```

## ⚡ Core Features

### Native Implementation
- **turnOnLED()**: Direct LED activation via HAL
- **turnOffLED()**: LED deactivation with proper cleanup
- **blinkLED(times)**: Configurable blink patterns
- **blinkOnNotification()**: System event LED response

### Technical Stack
- **JNI Registration**: Proper AOSP-style native method binding
- **HAL Architecture**: Hardware abstraction following Android patterns
- **Error Handling**: Comprehensive logging and exception management
- **CMake Integration**: Native library build configuration

## 🚀 Getting Started

### Prerequisites
- Android Studio with NDK support
- Android device/emulator with LED capability
- Understanding of JNI and HAL concepts

### Build Process
```bash
# Native library compilation handled by Gradle
./gradlew assembleDebug

# CMake builds led_jni.so with HAL integration
# JNI methods automatically registered via JNI_OnLoad
```

## 🔧 Implementation Details

### Java Layer (MainActivity.java)
```java
// Direct JNI integration - no Camera2 API
private LedManager mLedManager = new LedManager();

// Hardware control via HAL
mLedManager.turnOnLED();    // → JNI → HAL → sysfs
mLedManager.blinkLED(5);    // → Pattern control (5 blinks)
```

### JNI Bridge (led_jni.cpp)
```cpp
// Proper JNI signature for integer parameter
JNIEXPORT void JNICALL Java_com_example_led_LedManager_blinkLED
    (JNIEnv* env, jobject thiz, jint times) {
    blinkLED(env, thiz, times);  // Call to HAL
}
```

### HAL Implementation (led_hal.cpp)
```cpp
// Direct hardware access via sysfs
void turnOnFlash(JNIEnv* env, jobject ledManagerObj) {
    writeSysfs("/sys/class/leds/led0/brightness", "255");
}
```

## 📋 File Structure

```
FlashTune/
├── app/src/main/
│   ├── java/com/example/led/
│   │   ├── MainActivity.java      # UI + JNI calls
│   │   └── LedManager.java        # Native method declarations
│   └── cpp/
│       └── CMakeLists.txt         # Native build configuration
├── jni/
│   └── led_jni.cpp               # JNI bridge implementation
├── hal/
│   ├── led_hal.cpp               # HAL implementation
│   └── led_hal.h                 # HAL interface
└── framework/
    └── LedManager.java           # System service (future)
```

## 🎓 Educational Value

### AOSP Development Skills
- **JNI Integration**: Bridge between Java and native C++
- **HAL Architecture**: Understanding hardware abstraction
- **System Programming**: Direct kernel interface interaction
- **Android Internals**: How system services actually work

### Learning Outcomes
- Real AOSP development patterns beyond Android SDK
- System-level programming techniques
- Hardware abstraction principles
- Native development workflow integration

## ⚠️ Important Notes

### Development Environment
- This is an **educational implementation** demonstrating HAL concepts
- Actual hardware LED control requires root access and proper permissions
- HAL functions simulate real hardware interaction for learning purposes

### System Integration
- Real AOSP integration would require system-level permissions
- Production HAL modules run in separate processes with proper IPC
- This demo shows the development patterns and architecture

## 🔮 Next Steps

1. **System Service Integration**: Add proper Android system service
2. **AIDL Interface**: Implement inter-process communication
3. **Hardware Validation**: Test on devices with controllable LEDs
4. **Permission Model**: Add proper security framework integration

---

**Technical Focus**: This project prioritizes authentic AOSP patterns over simple functionality, providing a genuine learning experience in Android system development.
