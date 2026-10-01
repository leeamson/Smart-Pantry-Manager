# Smart Pantry Manager

Smart Pantry Manager is a Java Android app for keeping track of ingredients at home and finding recipes that can be made with them. Add ingredients with their quantities, units, and optional expiry dates; the app compares the pantry with its recipe collection and suggests recipes only when all required ingredients and quantities are available. The goal is to make leftover ingredients easier to use and help reduce food waste.

## Features

- Add, view, edit, and delete pantry ingredients.
- Save an optional expiry date for each ingredient.
- Browse recipe suggestions and open a recipe to see its ingredients and method.
- Match ingredient names despite capitalization and common plural variations.
- Compare quantities when units are compatible, including gram/kilogram and millilitre/litre conversions.
- Store pantry and recipe data locally, so the app works offline.

## Database Choice

The app uses SQLite through Android's `SQLiteOpenHelper`. SQLite is a good fit for this project because the pantry is personal, the data is structured, and the core features do not require a server or an internet connection. The database is stored on the device, so pantry data persists between app launches and is available offline. It also supports the create, read, update, and delete operations needed to manage ingredients. The app creates its tables and seeds 18 starter recipes when the database is first created.

## Requirements

- Android Studio with Android SDK Platform 34 installed.
- JDK 17 for the Android Gradle Plugin.
- An Android emulator or physical Android device running Android 7.0 (API 24) or later.
- Internet access the first time Gradle syncs, so Gradle can download the configured wrapper distribution and project dependencies.

## Setup and Run in Android Studio

1. Open Android Studio and choose **Open**.
2. Select the project root folder containing this README, `settings.gradle`, and `gradlew`.
3. Allow Gradle sync to finish. If prompted, install Android SDK Platform 34 using **Tools > SDK Manager**.
4. Start an emulator from **Tools > Device Manager**, or connect an Android device with USB debugging enabled.
5. Select the `app` run configuration and your device, then click **Run**.
6. In the app, add ingredients from the Pantry screen and open Suggested Recipes to see what can be made.

## Build and Install from a Terminal

Run these commands from the project root. The Gradle wrapper downloads and uses the Gradle version configured for this project.

On macOS or Linux:

```sh
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

On Windows Command Prompt:

```bat
gradlew.bat :app:assembleDebug
gradlew.bat :app:installDebug
```

The build task creates a debug APK at `app/build/outputs/apk/debug/app-debug.apk`. The install task installs it to a running emulator or connected device. Android Studio can also build and install the app using the Run steps above.

## Recipe Matching

Ingredient names are normalized for capitalization, common plural endings, and a small set of aliases. Units are grouped as mass, volume, or count; kilograms and litres are converted to their gram and millilitre base units for quantity comparisons. A recipe is suggested only if every listed ingredient is present in a compatible unit and the pantry quantity is at least the recipe's required amount.
