# Android-ID-and-Battery

A simple Android application that displays "Hello World" built with Kotlin.

## Features

- Simple Hello World display
- Built with Kotlin
- Material Design themes
- Supports both light and dark themes
- Minimum SDK 24 (Android 7.0)
- Target SDK 33 (Android 13)

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/androidid/
│   │   └── MainActivity.kt          # Main activity in Kotlin
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # Main layout with Hello World text
│   │   ├── values/
│   │   │   ├── colors.xml           # Color resources
│   │   │   ├── strings.xml          # String resources
│   │   │   └── themes.xml           # Light theme
│   │   └── values-night/
│   │       └── themes.xml           # Dark theme
│   └── AndroidManifest.xml          # App manifest
├── build.gradle                     # App-level Gradle build file
└── proguard-rules.pro              # ProGuard configuration
```

## Building

To build the project:

```bash
./gradlew build
```

To install on a device:

```bash
./gradlew installDebug
```

## Requirements

- Android Studio or IntelliJ IDEA with Android plugin
- Android SDK with API level 33
- Kotlin plugin
- Java 8 or higher