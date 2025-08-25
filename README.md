# 🚀 AOSP HAL Demo: Android Hardware Abstraction Learning

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![AOSP](https://img.shields.io/badge/AOSP-Educational-orange.svg)](https://source.android.com/)

> **Educational demonstration of Android system programming with JNI/HAL integration**

This repository contains two Android projects demonstrating different approaches to hardware control: **FlashTune** (pure JNI/HAL) and **PulsePlay** (hybrid JNI/HAL + Android API). Perfect for learning Android system programming concepts.

## 🎯 Overview

**Educational Focus**: JNI integration, HAL development, and Android system programming through two complementary approaches - **FlashTune** (pure JNI/HAL) and **PulsePlay** (hybrid implementation).

## 📱 Projects

### 🔦 **FlashTune - Pure JNI/HAL**
```
MainActivity → LedManager → JNI → HAL → sysfs
```
- Native LED control without Android APIs
- Complete JNI/HAL implementation stack
- Direct hardware access demonstration

### 🎮 **PulsePlay - Hybrid Approach**  
```
MainActivity → [JNI/HAL Path OR Android API Path]
```
- Educational JNI/HAL implementation + functional Android API
- Demonstrates trade-offs between approaches
- Real haptics functionality on devices

## 🚀 Quick Start

```bash
# Clone and build
git clone https://github.com/ADITYA-SHAKYA04/aosp-hal-demo.git
cd aosp-hal-demo/FlashTune
./gradlew assembleDebug

# Install
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Requirements**: Android Studio with NDK, API 29+
## 📚 Key Components

### **FlashTune Files**
- `MainActivity.java` - UI with LED controls
- `LedManager.java` - Native method declarations  
- `led_jni.cpp` - JNI bridge implementation
- `led_hal.cpp` - HAL with sysfs access

### **PulsePlay Files**
- `MainActivity.java` - Haptics control interface
- `HapticsJni.cpp` - JNI implementation
- `HapticsHal.cpp` - Native HAL layer

## 🎓 Learning Outcomes

**Technical Skills**: JNI programming, HAL development, CMake build systems, sysfs interface, native Android development

**System Understanding**: Hardware abstraction principles, Android internals, native/Java integration, trade-offs between system vs application level APIs

## 📄 License

MIT License - Perfect for learning Android system programming and HAL development.

---

<div align="center">
  
  ### 🚀 **Android System Programming Made Simple**
  
  <p><em>Master JNI, HAL development, and hardware abstraction through hands-on examples</em></p>
  
  <br>
  
  [![⭐ Star this repo](https://img.shields.io/badge/⭐_Star_this_repo-FFD700?style=for-the-badge&logoColor=white)](https://github.com/ADITYA-SHAKYA04/aosp-hal-demo)
  [![🐛 Report Bug](https://img.shields.io/badge/🐛_Report_Bug-FF6B6B?style=for-the-badge&logoColor=white)](https://github.com/ADITYA-SHAKYA04/aosp-hal-demo/issues)
  [![💡 Request Feature](https://img.shields.io/badge/💡_Request_Feature-4ECDC4?style=for-the-badge&logoColor=white)](https://github.com/ADITYA-SHAKYA04/aosp-hal-demo/issues)
  
  <br>
  
  **🎯 Educational** • **🔧 Practical** • **📱 Android-Focused**
  
  <p><sub>Built with ❤️ for developers learning Android system programming</sub></p>
  
</div>
