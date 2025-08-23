// IHapticsHal.h
#pragma once
class IHapticsHal {
public:
    virtual void vibrate(int durationMs, int intensity) = 0;
    virtual void stop() = 0;
    virtual ~IHapticsHal() {}
};
