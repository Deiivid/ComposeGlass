cmake_minimum_required(VERSION 3.4.1)

add_library(
        renderscript_toolkit
        SHARED
        renderscript_toolkit.cpp
)

find_library(
        log-lib
        log
)

# 🔧 Añade esta línea
find_library(
        jnigraphics-lib
        jnigraphics
)

target_link_libraries(
        renderscript_toolkit
        ${log-lib}
        ${jnigraphics-lib}   # <- Aquí enlazamos jnigraphics
)