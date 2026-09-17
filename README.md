# Smart Pantry Manager

A Java Android app that records leftover ingredients and only suggests recipes for which **every required ingredient and quantity** is in the pantry. It helps reduce food waste by making practical use of what is already at home.

## Database
The app uses an on-device SQLite database through `SQLiteOpenHelper`. SQLite was chosen because the pantry must remain available offline, data persists after closing the app, and it supports the required Create, Read, Update and Delete operations without external configuration. On first launch, 18 recipes are seeded into the database.

## Run
1. Open this folder in Android Studio.
2. Let Gradle sync, then choose an Android emulator or connected device.
3. Click Run. Add pantry items from the Pantry screen, then open Suggested Recipes.

## Core matching rule
Ingredient names are normalised (case, plural endings and a few aliases), and quantities are compared in compatible units. A recipe is suggested only when every one of its ingredient requirements is satisfied.
