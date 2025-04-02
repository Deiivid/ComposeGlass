🌥️ ComposeGlass

ComposeGlass is a Jetpack Compose library designed to easily add a beautiful, performant, and customizable Glassmorphism blur effect to your Android apps, supporting Android 12+ natively and providing a custom blur solution for older Android versions.

🚀 Installation

Add this dependency to your module's build.gradle:

implementation("com.davidnavarro.composeglass:composeglass:1.0.0")

✨ How to Use

Applying the glassmorphism blur effect is straightforward:

```kotlin
Box(
    modifier = Modifier.glassMorphism {
        radius = 10
        gradientColors = listOf(Color.Cyan, Color.Magenta)
    }
) {
    Text("Beautiful Glass Blur!")
}
```

🎨 Customization Options

ComposeGlass allows you to finely tune the blur effect:

✅ Blur Radius

Controls how strong the blur effect is:

```kotlin
Modifier.glassMorphism {
    radius = 15 // Range: 1 - 25
}
```

🎭 Theme Modes

Adjust the blur effect for light or dark themes:
```kotlin
Modifier.glassMorphism {
    themeMode = GlassMorphismThemeMode.Dark // Auto | Light | Dark
}
```

🌈 Gradient Colors

Customize your blur effect with gradients:
```kotlin
Modifier.glassMorphism {
    gradientColors = listOf(Color.Blue, Color.Green)
}
```

🎨 Blur Background Color

Set a base color for the blur:

Modifier.glassMorphism {
    blurColor = Color.Red.copy(alpha = 0.2f)
}

🌙 Dark Mode & 🌞 Light Mode Examples

Dark Mode

Light Mode





📸 Showcase

🎯 Gradient Example



🎯 Blur Color Example



🎯 Background Customization Example



📱 Supported Versions

ComposeGlass supports all modern Android versions:

✅ Android 12+ (uses native RenderEffect API)

✅ Android 11 and lower (custom native blur via JNI/C++)

🛠 Contributing

Contributions and improvements are welcome! Feel free to:

Submit an issue to report bugs or request features

Open a pull request to add enhancements

📌 License

ComposeGlass is released under the MIT License.

MIT License

Copyright (c) 2025 David Navarro

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

✨ Happy coding! 🚀
