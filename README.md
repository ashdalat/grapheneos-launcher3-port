# GrapheneOS Launcher3 Port (Gradle)

> **Standalone Gradle-based port of GrapheneOS Launcher3 (branch 17, Android 17 / SDK 37).**  
> Supports two build targets: **running directly on a physical GrapheneOS phone** as a standalone user app, or **running on an Android Emulator** with full Quickstep integration.

---

## 📌 Project Overview

By default, GrapheneOS Launcher3 relies on the AOSP Soong build system (`Android.bp`) with system-level privileged configurations (`platform_apis: true`, `privileged: true`, `overrides: Home/Launcher3`). This binds the launcher strictly to ROM builds and prevents building via Gradle or installing as an independent app.

This project refactors the launcher into a modern **Gradle** multi-module architecture (inspired by Lawnchair 16-dev and Neo-Launcher):
- **Code package:** `com.android.launcher3`
- **Application ID:** `app.graphene.launcher` (runs side-by-side without conflicting with or overwriting the stock GrapheneOS launcher)
- **100% Offline & Privacy-Focused:** `INTERNET` permission = 0. Zero tracking libraries (no Firebase, Crashlytics, Telemetry, or network stacks).

---

## 🚀 The Two Build Methods

The project provides two primary flavors to suit different use cases:

### 📱 Method 1: Build for Physical GrapheneOS Phone (Standalone User App - Recommended)
> Installs and runs directly on a GrapheneOS phone (e.g., Pixel series). Runs completely side-by-side as a standard application without overwriting system packages.

* **Flavor:** `noQuickstepStandalone`
* **Release build (R8 optimized + Shrink Resources, ~11.7 MB - Recommended for daily use):**
  ```bash
  ./gradlew assembleNoQuickstepStandaloneRelease
  ```
* **Debug build (For development / debugging):**
  ```bash
  ./gradlew assembleNoQuickstepStandaloneDebug
  ```
* **Output APK path:**
  * Release: `build/outputs/apk/noQuickstepStandalone/release/graphene-port-noQuickstep-standalone-release.apk`
  * Debug: `build/outputs/apk/noQuickstepStandalone/debug/graphene-port-noQuickstep-standalone-debug.apk`
* **Installation:**
  ```bash
  # Install via ADB:
  adb install -r build/outputs/apk/noQuickstepStandalone/release/graphene-port-noQuickstep-standalone-release.apk
  ```
  *Or transfer the APK file directly to your phone storage and tap to install.*
* **Set as Default Home App:**
  Go to **Settings -> Apps -> Default apps -> Home app** -> select **Graphene Launcher**.

---

### 💻 Method 2: Build for Android Emulator (Full Quickstep / Recents Provider)
> Designed for the Android SDK Emulator (AVD) environment. Fully integrates Quickstep, full-screen gesture navigation, Taskbar, and Recents Overview.

* **Flavor:** `withQuickstepStandalone`
* **Release build:**
  ```bash
  ./gradlew assembleWithQuickstepStandaloneRelease
  ```
* **Debug build:**
  ```bash
  ./gradlew assembleWithQuickstepStandaloneDebug
  ```
* **Output APK path:**
  * Release: `build/outputs/apk/withQuickstepStandalone/release/graphene-port-withQuickstep-standalone-release.apk`
  * Debug: `build/outputs/apk/withQuickstepStandalone/debug/graphene-port-withQuickstep-standalone-debug.apk`
* **Purpose:**
  * Testing and running on an Android Virtual Device (Emulator) with system-level Quickstep and Recents integration enabled.

---

## 🛠 Prerequisites & Environment

* **JDK:** OpenJDK 21 (Java 21)
  ```bash
  export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
  ```
* **Android SDK:**
  * `compileSdk`: 37 (Android 17)
  * `targetSdk`: 37
  * `minSdk`: 30 (Android 11+)
  * `buildToolsVersion`: 37.0.0

---

## 🔒 Security & Privacy Audit

This port strictly adheres to the security and privacy principles of GrapheneOS:
1. **Zero INTERNET Permission:** The APK manifest does not declare or request network access.
2. **Zero Trackers / Telemetry:** Free from any analytics, metrics, crashlytics, or 3rd-party ad SDKs.
3. **Completely Offline:** Runs purely locally on your device.
4. For raw audit logs and verification details, see [SECURITY_AUDIT.md](SECURITY_AUDIT.md) and the `docs/audit/` directory.

---

## 📂 Project Structure

```
├── aconfig/                            # aconfig feature flags for Android 17
├── compatLib/                          # API compatibility layer for Android 10 -> 16
├── docs/
│   └── audit/                          # Security audit and R8 verification reports
├── prebuilts/libs/                     # Framework JARs supporting compileOnly
├── quickstep/                          # Quickstep source (Recents, Taskbar, Gestures)
├── res/                                # AOSP launcher resources
├── res_graphene/                       # Graphene adaptive icons and assets
├── src/                                # Main Java/Kotlin source code of Launcher3
├── src_no_quickstep/                   # Stubs for the standalone flavor
├── tools/                              # Audit scripts and helper tools
│   ├── audit.sh                        # Permission and network call scanner
│   ├── audit_dex.sh                    # DEX string inspector
│   ├── gen_launcher_flags.py           # Flag generator from aconfig
│   └── vendor_private_colors.py        # Framework private color mapper
├── build.gradle                        # Root Gradle build script
├── settings.gradle                     # Gradle module definitions
└── INSTALL.md                          # Detailed setup & configuration guide
```

---

## 📜 License

This project inherits the **Apache License 2.0** from the upstream GrapheneOS Launcher3 project and the Android Open Source Project (AOSP).
