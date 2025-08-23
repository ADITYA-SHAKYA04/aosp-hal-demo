// HapticsHal.cpp
#include "IHapticsHal.h"
#include <iostream>

#include <fstream>
#include <unistd.h>

class HapticsHal : public IHapticsHal {
public:
    void vibrate(int durationMs, int intensity) override {
        const char* path = "/sys/class/timed_output/vibrator/enable";
        if (access(path, W_OK) == 0) {
            std::ofstream vib(path);
            if (vib.is_open()) {
                vib << durationMs;
                vib.close();
                std::cout << "[HAL] Wrote " << durationMs << "ms to sysfs" << std::endl;
            } else {
                std::cout << "[HAL] Failed to open sysfs node" << std::endl;
            }
        } else {
            std::cout << "[HAL] Vibrate: " << durationMs << "ms, Intensity: " << intensity << " (dummy/log)" << std::endl;
        }
    }
    void stop() override {
        const char* path = "/sys/class/timed_output/vibrator/enable";
        if (access(path, W_OK) == 0) {
            std::ofstream vib(path);
            if (vib.is_open()) {
                vib << 0;
                vib.close();
                std::cout << "[HAL] Stopped vibration via sysfs" << std::endl;
            } else {
                std::cout << "[HAL] Failed to open sysfs node" << std::endl;
            }
        } else {
            std::cout << "[HAL] Stop vibration (dummy/log)" << std::endl;
        }
    }
};
