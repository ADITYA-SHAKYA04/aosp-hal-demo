// led_hal_test.cpp
// Unit test stub for LED HAL

#include "led_hal.cpp"
#include <cassert>

int main() {
    turnOnLED();
    turnOffLED();
    blinkLED(2);
    breathingLED(1);
    strobeLED(1);
    rainbowLED(1);
    blinkOnNotification();
    assert(safeTurnOnLED());
    return 0;
}
