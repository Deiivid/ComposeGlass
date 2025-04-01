CrystalCompose ✨ - Glassmorphism Effects for Jetpack Compose

🚀 Introduction

CrystalCompose is a UI library for Jetpack Compose that allows you to create Glassmorphism effects easily and elegantly. Add cards, buttons, and layouts with blurred and semi-transparent backgrounds using just a few lines of code. Perfect for modern and stylish interfaces.

🎨 Features

✅ Glassmorphism effect with blur and transparency.✅ Easy to integrate into any Jetpack Compose project.✅ Support for dark and light themes.✅ Highly customizable (colors, opacity, blur radius).✅ Compatible with Material 3 and any custom theme.

📦 Installation

CrystalCompose is available via JitPack. To add it to your project:

1️⃣ Add JitPack to your settings.gradle.kts:

pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

2️⃣ Add the dependency in your build.gradle.kts:

dependencies {
    implementation("com.github.your-username:CrystalCompose:1.0.0")
}

🎭 Basic Usage

📌 Example: Creating a Glassmorphism effect card

@Composable
fun GlassCardExample() {
    GlassCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Hello, CrystalCompose!", style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}

📌 Customizing the effects

GlassCard(
    blurRadius = 15.dp, // Blur intensity
    backgroundColor = Color.White.copy(alpha = 0.2f), // Background transparency
    borderStroke = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)) // Subtle border
) {
    Text("Text on a Glassmorphism card", color = Color.White)
}

📜 Full Documentation

You can check all components and examples in the official documentation.

🛠️ Contributing

We would love for you to contribute to improving CrystalCompose! 🚀

Fork the repository.

Create a new branch (feature-new).

Make your changes and commit them.

Open a Pull Request.

🏆 Author

Created with ❤️ by David.📧 Contact: [Your Email]🐦 Twitter: [Your Username]

If you like this library, don't forget to ⭐ it on GitHub!

📜 License

This project is licensed under the MIT License.

## 📸 Demo Screenshots

### 🌞 Light Mode

<img src="https://github.com/deiivid/ComposeGlassMorphism/assets/tuimagen1.png" width="300"/>

### 🌚 Dark Mode

<img src="https://github.com/deiivid/ComposeGlassMorphism/assets/tuimagen2.png" width="300"/>




