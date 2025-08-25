// led_jni.cpp

#include <jni.h>
#include "../hal/led_hal.h"


extern "C" {
JNIEXPORT void JNICALL Java_com_example_led_LedManager_turnOnLED(JNIEnv* env, jobject thiz) {
    turnOnFlash(env, thiz);
}
JNIEXPORT void JNICALL Java_com_example_led_LedManager_turnOffLED(JNIEnv* env, jobject thiz) {
    turnOffFlash(env, thiz);
}
JNIEXPORT void JNICALL Java_com_example_led_LedManager_blinkLED(JNIEnv* env, jobject thiz, jint times) {
    blinkLED(env, thiz, times);
}
JNIEXPORT void JNICALL Java_com_example_led_LedManager_blinkOnNotification(JNIEnv* env, jobject thiz) {
    blinkOnNotification(env, thiz);
}
}


// JNI registration for AOSP style
static JNINativeMethod gMethods[] = {
    {"turnOnLED", "()V", (void*)Java_com_example_led_LedManager_turnOnLED},
    {"turnOffLED", "()V", (void*)Java_com_example_led_LedManager_turnOffLED},
    {"blinkLED", "(I)V", (void*)Java_com_example_led_LedManager_blinkLED},
    {"blinkOnNotification", "()V", (void*)Java_com_example_led_LedManager_blinkOnNotification}
};

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void*) {
    JNIEnv* env = nullptr;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    jclass clazz = env->FindClass("com/example/led/LedManager");
    if (clazz == nullptr) {
        return JNI_ERR;
    }
    if (env->RegisterNatives(clazz, gMethods, sizeof(gMethods)/sizeof(gMethods[0])) < 0) {
        return JNI_ERR;
    }
    return JNI_VERSION_1_6;
}

// Add error handling and logging for real hardware integration
