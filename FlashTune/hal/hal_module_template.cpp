// hal_module_template.cpp
// Template for new HAL modules

#include <iostream>

class HardwareModule {
public:
    virtual void init() = 0;
    virtual void performAction() = 0;
    virtual ~HardwareModule() {}
};

class ExampleModule : public HardwareModule {
public:
    void init() override {
        std::cout << "ExampleModule initialized" << std::endl;
    }
    void performAction() override {
        std::cout << "ExampleModule action performed" << std::endl;
    }
};

// Usage:
// ExampleModule mod;
// mod.init();
// mod.performAction();
