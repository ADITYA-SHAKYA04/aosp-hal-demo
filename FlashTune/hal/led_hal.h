// led_hal.h
#pragma once

#ifdef __cplusplus
extern "C" {
#endif

void turnOnFlash(JNIEnv* env, jobject ledManagerObj);
void turnOffFlash(JNIEnv* env, jobject ledManagerObj);
void blinkLED(JNIEnv* env, jobject ledManagerObj, int times);
void blinkOnNotification(JNIEnv* env, jobject ledManagerObj);

#ifdef __cplusplus
}
#endif
