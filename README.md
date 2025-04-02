
🌥️ ComposeGlassmorphism

ComposeGlassmorphism is a Jetpack Compose library designed to easily add a beautiful, performant, and customizable Glassmorphism blur effect to your Android apps, supporting Android 12+ natively and providing a custom blur solution for older Android versions.

🚀 ##Installation

Add this dependency to your module's build.gradle:

implementation("com.davidnavarro.composeglass:composeglass:1.0.0")

✨ ##How to Use

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

🎨 ##Customization Options

ComposeGlassmorphism allows you to finely tune the blur effect:


#### Parameters

| Parameter | Type     | Implementation     | Description                |
| :-------- | :------- | :----------------- | :------------------------- |
| 📻 `Radius` | `Int` | `Modifier.glassMorphism {radius = 10}`|  **Required**. 0-10 |
| 🎭 `Theme` | `GlassMorphismThemeMode` | `Modifier.glassMorphism {themeMode = GlassMorphismThemeMode.Dark}` | **Not Required**. Auto, Light, Dark |
| 🎨 `blurColor` | `Color?`  | `Modifier.glassMorphism {gradientColors = listOf(Color.Blue, Color.Green)}` |  **Not Required**. |
| 🌈`gradientColors` | `List<Color>?`  | `Modifier.glassMorphism {blurColor = Color.Red.copy(alpha = 0.2f)}` |  **Not Required**. |


📸 ##ScreenShoots
![Android <11 & >12](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/androidBlur.jpg)


## 🎨 Blur Color Background Example

![Android <11 & >12](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/androidBlurColor.png)

## 🌈 Gradient Example
![Android <11 & >12](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/androidGradients.png)



📱 ##Supported Versions

ComposeGlassmorphism supports all modern Android versions:

✅ Android 12+ (uses native RenderEffect API)

✅ Android 11 and lower (custom native blur via JNI/C++)

🛠 ##Contributing

Contributions and improvements are welcome! Feel free to:

Submit an issue to report bugs or request features

Open a pull request to add enhancements

📌 ##License

ComposeGlassmorphism is released under the MIT License.

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
## Contributing

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)



