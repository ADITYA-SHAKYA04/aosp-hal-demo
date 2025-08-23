// test_HapticsHal.cpp
#include "HapticsHal.cpp"
#include <cassert>
int main() {
    HapticsHal hal;
    hal.vibrate(100, 50); // Should log or write
    hal.stop();           // Should log or write
    // No exceptions expected
    return 0;
}
