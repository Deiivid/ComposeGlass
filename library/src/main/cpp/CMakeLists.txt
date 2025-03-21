cmake_minimum_required(VERSION 3.4.1)

add_library(
        nativeblur
        SHARED
        blur.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        nativeblur
        ${log-lib}
)