# Project Overview

This repo demonstrates an end-to-end Android haptics pipeline:
- App → Framework → AIDL → System Service → JNI → HAL → Hardware

See README.md for architecture diagrams and layer breakdowns.

## Layers
- **HAL**: C++ implementation, writes to sysfs or logs
- **JNI**: C++ bridge to HAL
- **Framework**: Java API, permission checks
- **Service**: Java system service, logging
- **App**: Kotlin UI, triggers vibration
- **AIDL**: IPC interface

## Testing
- Unit tests for HAL and Framework
- Build scripts for C++ and Java/Kotlin
