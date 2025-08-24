// led_jni_test.cpp
// Unit test stub for JNI layer

#include "led_jni.cpp"
#include <cassert>

int main() {
    // These would normally be called from Java via JNI
    Java_com_example_led_LedManager_turnOnLED(nullptr, nullptr);
    Java_com_example_led_LedManager_turnOffLED(nullptr, nullptr);
    Java_com_example_led_LedManager_blinkLED(nullptr, nullptr, 2);
    Java_com_example_led_LedManager_breathingLED(nullptr, nullptr, 1);
    Java_com_example_led_LedManager_strobeLED(nullptr, nullptr, 1);
    Java_com_example_led_LedManager_rainbowLED(nullptr, nullptr, 1);
    Java_com_example_led_LedManager_blinkOnNotification(nullptr, nullptr);
    return 0;
}
