// HapticsJni.cpp
#include <jni.h>
#include "../hal/HapticsHal.cpp"

#include <android/log.h>
#define LOG_TAG "HapticsJni"

static HapticsHal gHal;

extern "C" {
JNIEXPORT void JNICALL Java_com_example_haptics_NativeHaptics_nativeVibrate(JNIEnv* env, jobject thiz, jint ms, jint intensity) {
    try {
        gHal.vibrate(ms, intensity);
    } catch (...) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "nativeVibrate failed");
    }
}
JNIEXPORT void JNICALL Java_com_example_haptics_NativeHaptics_nativeStop(JNIEnv* env, jobject thiz) {
    try {
        gHal.stop();
    } catch (...) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "nativeStop failed");
    }
}
}
