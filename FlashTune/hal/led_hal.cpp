// led_hal.cpp
#include <string>
#include <iostream>
#include <android/log.h>
#include <jni.h>
#define LOG_TAG "FlashTuneHAL"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" {
// Helper to call LedManager.setFlashlight from JNI
void callSetFlashlight(JNIEnv* env, jobject ledManagerObj, bool enabled) {
    if (!env || !ledManagerObj) {
        LOGI("JNI: env or ledManagerObj is null");
        return;
    }
    jclass clazz = env->GetObjectClass(ledManagerObj);
    if (!clazz) {
        LOGI("JNI: LedManager class not found");
        return;
    }
    jmethodID setFlashlightMethod = env->GetMethodID(clazz, "setFlashlight", "(Z)V");
    if (!setFlashlightMethod) {
        LOGI("JNI: setFlashlight method not found");
        return;
    }
    env->CallVoidMethod(ledManagerObj, setFlashlightMethod, enabled ? JNI_TRUE : JNI_FALSE);
}

// Forward declaration for logging helper
void hal_log(const std::string& msg);

// Simulate hardware access and error handling
bool writeSysfs(const char* path, const char* value) {
    // In real HAL, this would write to /sys/class/led/...
    hal_log(std::string("Write ") + value + " to " + path);
    // Simulate success
    return true;
}

void turnOnFlash() {
    writeSysfs("/sys/class/led/led0/brightness", "1");
}

void turnOffFlash() {
    writeSysfs("/sys/class/led/led0/brightness", "0");
}

// Logging helper
void hal_log(const std::string& msg) {
    LOGI("%s", msg.c_str());
}

// New LED patterns

// Notification/Sensor stub
void blinkOnNotification(JNIEnv* env, jobject ledManagerObj) {
    hal_log("Blink LED on new notification (stub)");
}

} // extern "C"


void turnOnLED_real() {
    if (!writeSysfs("/sys/class/led/led0", "1")) {
        hal_log("Failed to turn on LED");
    }
}

void turnOffLED_real() {
    if (!writeSysfs("/sys/class/led/led0", "0")) {
        hal_log("Failed to turn off LED");
    }
}

void blinkLED_real(int times) {
    for (int i = 0; i < times; ++i) {
        turnOnLED_real();
        // Simulate delay
        turnOffLED_real();
    }
}
