# AOSP HAL Demo: JNI-based LED & Haptics Control

**FlashTune** is a high-performance haptics tuning tool for Android devices, enabling developers to customize vibration patterns and feedback with precision. It provides an intuitive interface for real-time adjustments and testing.

**PulsePlay** is a demo application showcasing advanced haptic effects powered by FlashTune. It demonstrates various use cases, including gaming, notifications, and accessibility, highlighting the capabilities of modern haptics hardware and APIs.

This repository demonstrates a complete Android Open Source Project (AOSP) Hardware Abstraction Layer (HAL) for LED/flash and haptics control using JNI (Java Native Interface).

## Repository Overview

- **FlashTune**: Main demo app and HAL implementation for LED control and haptics tuning.
- **PulsePlay**: Demo application showcasing advanced haptic effects powered by FlashTune, including gaming, notifications, and accessibility use cases.
- **HAL Layer (C++):** Implements hardware access and sysfs writes for LED/flash and haptics control.
- **JNI Bridge (C++):** Connects native HAL to Java, exposing hardware features to the app.
- **Java Layer:** Provides a modern, recruiter-friendly UI and app logic for LED and haptics control.
- **No Camera2 API:** All hardware access is direct via sysfs, suitable for AOSP and embedded devices.
- **Documentation:** Includes architecture diagrams, usage instructions, and troubleshooting tips.

## Key Features
- End-to-end pipeline: App UI → Java → JNI → Native HAL → Hardware (sysfs)
- Simple, modern Material Design UI
- LED ON/OFF, Blink, Notification-triggered blink
- Real-time haptics tuning and demo effects (PulsePlay)
- Easily extensible for other hardware features

## Getting Started
- Open the `FlashTune` folder for the full Android Studio project
- Build and run on an AOSP device with `/sys/class/led/led0/brightness` and haptics support
- See `FlashTune/README.md` for detailed instructions

## License
MIT

---

For more details, see the `FlashTune/README.md` file.
