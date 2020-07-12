# Developer Option Quick Setting Tiles
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
Quick setting app that allows developer to quickly change developer options without going into the Developer Option menu.

## Available Quick
* Don't Keep Activities

## Prerequisite
* ADB (recommended)
* Rooted Device

### ADB
ADB should be available when you install Android Studio (Requires modify bash_profile) or you can use homebrew to get ADB

### Rooted Device
You should have SU permission which allows you to bypass [Installation](#Installation step)

## Installation
First, clone this repo and install the app.

If your device is rooted and under SU admin, you should not need to go through this step. If for
some reason there is an error, do go through this step. 

Using ADB, you must grant following permissions to the app:

```bash
adb shell pm grant com.cheesycoder.developeroptionshortcut android.permission.WRITE_SECURE_SETTINGS
adb shell pm grant com.cheesycoder.developeroptionshortcut android.permission.SET_ALWAYS_FINISH
```

These permissions lets this app to access developer options without being system applications.

You can now add quick setting tile by pulling down the quick setting menu and add developer option tiles.
