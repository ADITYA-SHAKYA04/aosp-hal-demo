cmake_minimum_required(VERSION 3.22.1)
project(led_jni)

add_library(
    led_jni
    SHARED
    ../../../../jni/led_jni.cpp
    ../../../../hal/led_hal.cpp
)

find_library(
    log-lib
    log
)

target_include_directories(
    led_jni PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/../../../../hal
    $ENV{ANDROID_NDK_HOME}/sources/android/native_app_glue
    $ENV{ANDROID_NDK_HOME}/sysroot/usr/include
)
target_link_libraries(
    led_jni
    ${log-lib}
)
