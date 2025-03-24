cmake_minimum_required(VERSION 3.4.1)
add_library(
        glassblur
        SHARED
        blur.cpp
)
find_library(
        log-lib
        log
)

find_library(
        jnigraphics-lib
        jnigraphics
)

target_sources(glassblur
        PRIVATE
        RenderScriptToolkit.cpp
)

target_link_libraries(
        glassblur
        ${log-lib}
        ${jnigraphics-lib}
)