// led_hal.cpp
#include <string>
#include <iostream>
#include <thread>
#include <chrono>
#include <android/log.h>
#include <jni.h>
#define LOG_TAG "FlashTuneHAL"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" {
// Helper to call LedManager.setFlashlight from JNI
void callSetFlashlight(JNIEnv* env, jobject ledManagerObj, bool enabled) {
    if (!env || !ledManagerObj) {
        LOGI("JNI: env or ledManagerObj is null - skipping Android API call");
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
    
    // Check for JNI exceptions
    if (env->ExceptionCheck()) {
        env->ExceptionDescribe();
        env->ExceptionClear();
        LOGI("JNI: Exception occurred while calling setFlashlight");
    } else {
        LOGI("JNI: Successfully called setFlashlight(%s)", enabled ? "true" : "false");
    }
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

void turnOnFlash(JNIEnv* env, jobject ledManagerObj) {
    hal_log("HAL: Turn on LED");
    writeSysfs("/sys/class/leds/led0/brightness", "255");
    
    // Also call the actual flashlight API
    callSetFlashlight(env, ledManagerObj, true);
}

void turnOffFlash(JNIEnv* env, jobject ledManagerObj) {
    hal_log("HAL: Turn off LED");
    writeSysfs("/sys/class/leds/led0/brightness", "0");
    
    // Also call the actual flashlight API
    callSetFlashlight(env, ledManagerObj, false);
}

void blinkLED(JNIEnv* env, jobject ledManagerObj, int times) {
    hal_log("HAL: Blink LED " + std::to_string(times) + " times");
    for (int i = 0; i < times; ++i) {
        writeSysfs("/sys/class/leds/led0/brightness", "255");
        callSetFlashlight(env, ledManagerObj, true);
        
        // Add proper delay for blinking
        std::this_thread::sleep_for(std::chrono::milliseconds(250));
        
        writeSysfs("/sys/class/leds/led0/brightness", "0");
        callSetFlashlight(env, ledManagerObj, false);
        
        if (i < times - 1) { // Don't sleep after last blink
            std::this_thread::sleep_for(std::chrono::milliseconds(250));
        }
    }
}

// Logging helper
void hal_log(const std::string& msg) {
    LOGI("%s", msg.c_str());
}

// New LED patterns

// Notification/Sensor blink - 3 quick blinks to indicate notification
void blinkOnNotification(JNIEnv* env, jobject ledManagerObj) {
    hal_log("HAL: Blink LED on notification - 3 quick blinks");
    
    for (int i = 0; i < 3; ++i) {
        writeSysfs("/sys/class/leds/led0/brightness", "255");
        callSetFlashlight(env, ledManagerObj, true);
        
        // Shorter delay for notification blinks (faster blinking)
        std::this_thread::sleep_for(std::chrono::milliseconds(150));
        
        writeSysfs("/sys/class/leds/led0/brightness", "0");
        callSetFlashlight(env, ledManagerObj, false);
        
        if (i < 2) { // Don't sleep after last blink
            std::this_thread::sleep_for(std::chrono::milliseconds(150));
        }
    }
    
    hal_log("HAL: Notification blink sequence completed");
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
