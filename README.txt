# Foobar

Foobar is a Python library for dealing with word pluralization.

## Installation

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.

```bash
pip install foobar
```🌙 Dark Mode Example  
![Dark Mode Example](https://raw.githubusercontent.com/Deiivid/Glassmorphism-Compose/master/screenshoots/android13_dark1.png)

## Usage

```kotlin
import foobar

# returns 'words'
foobar.pluralize('word')

# returns 'geese'
foobar.pluralize('goose')

# returns 'phenomenon'
foobar.singularize('phenomena')
```

🌥️ ComposeGlassmorphism

ComposeGlassmorphism is a Jetpack Compose library designed to easily add a beautiful, performant, and customizable Glassmorphism blur effect to your Android apps, supporting Android 12+ natively and providing a custom blur solution for older Android versions.

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

ComposeGlassmorphism allows you to finely tune the blur effect:

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
```kotlin
Modifier.glassMorphism {
    blurColor = Color.Red.copy(alpha = 0.2f)
}
```

📸 ScreenShoots

Android 11📱
![Light Mode Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android11light.png)
🌙 Dark Mode Example
![Light Mode Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android11light.png)
🌞 Light Mode Example
![Dark Mode Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android11_darkmode.png)

Android 13📱

🌙 Dark Mode Example
![Dark Mode Example](https://raw.githubusercontent.com/Deiivid/Glassmorphism-Compose/master/screenshoots/android13_dark1.png)
![Dark Mode Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android13_dark2.png)

🌞 Light Mode Example
![Light Mode Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android13_light.png)
![Light Mode Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android13_light2.png)

## 🎨 Blur Color Background Example

![Blur Color Background](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android13_blur_color_background.png)

## 🌈 Gradient Example

🌙 Dark Mode Example
![Gradient Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android13_dark_gradients.png)

🌞 Light Mode Example
![Gradient Example](https://github.com/Deiivid/Glassmorphism-Compose/blob/master/screenshoots/android13_light_gradients.png)



📱 Supported Versions

ComposeGlassmorphism supports all modern Android versions:

✅ Android 12+ (uses native RenderEffect API)

✅ Android 11 and lower (custom native blur via JNI/C++)

🛠 Contributing

Contributions and improvements are welcome! Feel free to:

Submit an issue to report bugs or request features

Open a pull request to add enhancements

📌 License

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

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
