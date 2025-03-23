cmake_minimum_required(VERSION 3.4.1)
add_library(glassblur SHARED blur.cpp)
find_library(
        log-lib
        log
)

find_library(
        jnigraphics-lib
        jnigraphics
)

target_link_libraries(
        glassblur         # <--- Y también aquí
        ${log-lib}
        ${jnigraphics-lib}
)