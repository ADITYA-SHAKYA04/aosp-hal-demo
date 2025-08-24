// LedManagerTestTemplate.java
// Template for new framework API tests

package com.example.led;

public abstract class LedManagerTestTemplate {
    protected LedManager manager;
    public LedManagerTestTemplate(LedManager manager) {
        this.manager = manager;
    }
    public abstract void runAllTests();
}
