# Specify the minimum required CMake version
cmake_minimum_required(VERSION 3.4.1)

# Establecer el estándar de C++
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
# Define a shared native library called 'GlassMorphism' and add the source file 'blur.cpp'
add_library(
        GlassMorphism
        SHARED
        blur.cpp
)

# Locate the Android log library to enable native logging with __android_log_print
find_library(
        log-lib
        log
)

# Locate the jnigraphics library, used for handling Bitmaps in native code
find_library(
        jnigraphics-lib
        jnigraphics
)

# Add additional source files to the target (in this case, RenderScriptToolkit.cpp)
target_sources(GlassMorphism
        PRIVATE
        RenderScriptToolkit.cpp
)

# Link the glassblur library with the required system libraries
target_link_libraries(
        GlassMorphism
        ${log-lib}
        ${jnigraphics-lib}
)