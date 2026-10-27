# GrapheneOS Launcher3 Port

> **Port của GrapheneOS Launcher3 (branch 17, Android 17 / SDK 37) sang hệ thống Gradle.**  
> Hỗ trợ 2 hình thức build: **chạy trên điện thoại GrapheneOS** hoặc **chạy trên máy ảo (Android Emulator)**.

---

## 📌 Tổng quan dự án

Mặc định, Launcher3 của GrapheneOS sử dụng hệ thống build Soong (`Android.bp`) với các cấu hình gắn chặt vào ROM hệ thống (`platform_apis: true`, `privileged: true`, `overrides: Home/Launcher3`). Điều này khiến source gốc không thể build bằng Gradle hoặc cài đặt linh hoạt.

Dự án này chuyển đổi sang **Gradle**, sử dụng kiến trúc module linh hoạt:
- **Package name code:** `com.android.launcher3`
- **Application ID:** `app.graphene.launcher` (không ghi đè hay xung đột với launcher mặc định của GrapheneOS)
- **Hoàn toàn Offline & Bảo mật:** Quyền `INTERNET` = 0, không có thư viện theo dõi (Firebase, Crashlytics, Telemetry) hay network stack nào.

---

## 🚀 HƯỚNG DẪN 2 CÁCH BUILD

Dự án cung cấp 2 flavor chính tương ứng với 2 mục đích sử dụng:

### 📱 Cách 1: Build chạy trên ĐIỆN THOẠI GRAPHENEOS
> Cài đặt và chạy trực tiếp trên điện thoại GrapheneOS (Pixel...). Ứng dụng chạy song song an toàn, không xung đột hay ghi đè lên launcher gốc của máy.

- **Flavor:** `noQuickstepStandalone`
- **Lệnh build bản Release (Tối ưu R8 + Shrink Resources, dung lượng nhẹ ~11.7 MB - Khuyên dùng):**
  ```bash
  ./gradlew assembleNoQuickstepStandaloneRelease
  ```
- **Lệnh build bản Debug (Dành cho việc kiểm tra logcat / gỡ lỗi):**
  ```bash
  ./gradlew assembleNoQuickstepStandaloneDebug
  ```
- **File APK xuất ra:**
  - Release: `build/outputs/apk/noQuickstepStandalone/release/graphene-port-noQuickstep-standalone-release.apk`
  - Debug: `build/outputs/apk/noQuickstepStandalone/debug/graphene-port-noQuickstep-standalone-debug.apk`
- **Cách cài đặt lên điện thoại:**
  ```bash
  # Cài qua ADB
  adb install -r build/outputs/apk/noQuickstepStandalone/release/graphene-port-noQuickstep-standalone-release.apk
  ```
  *Hoặc copy file APK vào điện thoại GrapheneOS và mở lên cài đặt.*
- **Đặt làm launcher mặc định:**
  Vào **Settings -> Apps -> Default apps -> Home app** -> chọn **Graphene Launcher**.

---

### 💻 Cách 2: Build chạy trên MÁY ẢO (Android Emulator)
> Dành cho môi trường máy ảo (Android Virtual Device / Emulator). Tích hợp toàn diện Quickstep, cử chỉ vuốt đa nhiệm (Recents Overview) và Taskbar.

- **Flavor:** `withQuickstepStandalone`
- **Lệnh build bản Release:**
  ```bash
  ./gradlew assembleWithQuickstepStandaloneRelease
  ```
- **Lệnh build bản Debug:**
  ```bash
  ./gradlew assembleWithQuickstepStandaloneDebug
  ```
- **File APK xuất ra:**
  - Release: `build/outputs/apk/withQuickstepStandalone/release/graphene-port-withQuickstep-standalone-release.apk`
  - Debug: `build/outputs/apk/withQuickstepStandalone/debug/graphene-port-withQuickstep-standalone-debug.apk`
- **Mục đích:**
  - Chạy thử nghiệm trên máy ảo Android SDK Emulator với đầy đủ tính năng Quickstep và Recents tích hợp.

---

## 🛠 Yêu cầu môi trường build

- **JDK:** OpenJDK 21 (Java 21)
  ```bash
  export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
  ```
- **Android SDK:**
  - `compileSdk`: 37 (Android 17)
  - `targetSdk`: 37
  - `minSdk`: 30 (Android 11 trở lên)
  - `buildToolsVersion`: 37.0.0

---

## 🔒 Báo cáo bảo mật (Security Audit)

Bản port đảm bảo tiêu chuẩn bảo mật của GrapheneOS:
1. **0 INTERNET Permission:** File APK hoàn toàn không chứa quyền truy cập mạng.
2. **0 Trackers / Telemetry:** Không sử dụng Google Analytics, Firebase, Sentry hay bất kỳ SDK bên thứ 3 nào.
3. **100% Offline:** Đảm bảo quyền riêng tư tuyệt đối.
4. Chi tiết audit: xem tại [SECURITY_AUDIT.md](SECURITY_AUDIT.md) và thư mục `docs/audit/`.

---

## 📂 Cấu trúc dự án

```
├── aconfig/                            # aconfig flags cho Android 17
├── compatLib/                          # Lớp tương thích API Android 10 -> 16
├── docs/
│   └── audit/                          # Bằng chứng kiểm tra bảo mật và R8
├── prebuilts/libs/                     # Framework jars hỗ trợ compileOnly
├── quickstep/                          # Mã nguồn Quickstep (Recents, Taskbar, Gestures)
├── res/                                # Tài nguyên giao diện AOSP
├── res_graphene/                       # Icon adaptive và assets riêng của Graphene
├── src/                                # Mã nguồn Java/Kotlin chính của Launcher3
├── src_no_quickstep/                   # Stub cho flavor standalone
├── tools/                              # Scripts audit và công cụ hỗ trợ
│   ├── audit.sh                        # Script quét quyền và network calls
│   ├── audit_dex.sh                    # Script quét chuỗi trong dex
│   ├── gen_launcher_flags.py           # Sinh aconfig flags
│   └── vendor_private_colors.py        # Chuyển đổi mã màu nội bộ
├── build.gradle                        # Cấu hình Gradle gốc
├── settings.gradle                     # Khai báo module
└── INSTALL.md                          # Hướng dẫn chi tiết cài đặt & cấu hình
```

---

## 📜 Giấy phép (License)

Dự án này kế thừa giấy phép **Apache 2.0** từ dự án gốc GrapheneOS Launcher3 và Android Open Source Project (AOSP).
