# GRAPHENE LAUNCHER (user-app) - HƯỚNG DẪN CÀI

> **TRẠNG THÁI: ĐÃ CÀI VÀ TEST OK** trên Pixel 8 (`shiba`), GrapheneOS 17
> (`CP2A.260805.005`, SDK 37, security patch 2026-08-05), ngày 19-20/09/2026.
> Không crash, không ANR; đang được set làm **Home mặc định** và chạy tốt.
> Bản **release (R8 + minify)** đã build, cài và test xong ngày 20/09/2026.
> Chi tiết test: mục cuối `PROGRESS.txt` (PHẦN 2A audit, PHẦN 2B release).
> Audit bảo mật (INTERNET / link ẩn / gửi dữ liệu): xem `SECURITY_AUDIT.md`.

## File APK

| | **release (nên dùng)** | debug |
|---|---|---|
| File | `GrapheneLauncher-release.apk` | `.../GrapheneLauncher-debug.apk` |
| Dung lượng | **11,7 MiB** (12.260.277 byte) | 89 MiB (93.618.411 byte) |
| package | `app.graphene.launcher` | `app.graphene.launcher.debug` |
| sha256 | `2843442da8cf9f413996b2a59f7566bccc246ca2a16c254c903545b085c1c0b1` | `0336473625334f6d86f7e5093d500a2b71692e60ad37bebdbe01fbc67ad6af38` |
| R8/minify | có (1 dex 7,5 MB) | không (22 dex 84 MB) |
| debuggable | **không** | có (`android:debuggable="true"`) |
| RAM (PSS, idle) | **~180 MB** | ~243 MB |
| Khởi động lạnh | **884 ms** | 1733-1901 ms |

Cả hai bản được ký bằng cùng một cert (debug keystore của máy build) nên sau này build lại
đều `adb install -r` nâng cấp tại chỗ được, không mất dữ liệu. Hai bản là **hai package khác
nhau** ⇒ dữ liệu layout riêng, không tự chuyển sang nhau.

## Cách cài (không cần root)

**Cách 1 - cắm cáp USB, bật USB debugging:**
```fish
# bản release (khuyến nghị)
adb install -r GrapheneLauncher-release.apk
# hoặc bản debug (nếu cần gỡ lỗi / xem log chi tiết)
adb install -r GrapheneLauncher-debug.apk
```

**Cách 2 - copy file APK vào điện thoại rồi bấm cài** (cho phép "Cài app từ nguồn không xác định").

## Đặt làm launcher mặc định

**Cách 1 - trên điện thoại:** Settings → Apps → Default apps → **Home app** → chọn
**Graphene Launcher**.

**Cách 2 - qua adb (đã test OK):**
```fish
# bản release
adb shell cmd package set-home-activity \
    app.graphene.launcher/com.android.launcher3.Launcher
# bản debug
adb shell cmd package set-home-activity \
    app.graphene.launcher.debug/com.android.launcher3.Launcher
```

## Quay lại launcher gốc / gỡ app

```fish
# về launcher GrapheneOS gốc
adb shell cmd package set-home-activity \
    com.android.launcher3/.uioverrides.QuickstepLauncher
# gỡ bản release (xoá luôn dữ liệu layout của bản này)
adb uninstall app.graphene.launcher
# gỡ bản debug (không còn cần thiết khi đã dùng bản release)
adb uninstall app.graphene.launcher.debug
```

## Kết quả test trên máy thật (19-20/09/2026)

| Hạng mục | Kết quả |
|---|---|
| `adb install -r -d` | `Success`, code path `/data/app/...` (đúng là user app) |
| Home candidate | `app.graphene.launcher.debug/com.android.launcher3.Launcher`, song song với launcher gốc |
| Khởi động lạnh | `Status: ok`, COLD, 1901 ms, `logcat -b crash` **0 dòng** |
| Gate GrapheneApp | `Graphene Launcher user-app, isRecentsProvider=false` (đúng thiết kế) |
| Render home | wallpaper + icon + hotseat + page indicator OK |
| App drawer | search bar + grid app + label OK, không exception |
| Phím HOME | từ app khác bấm HOME → về launcher này OK |
| Recents (nút vuông) | **Mở được** - nhưng do QuickStep của launcher gốc cung cấp, không phải recents của bản port |
| Long-press home | menu Wallpaper & style / Widgets / Apps list / Create Folder / Home organizer / Home settings OK |
| Default layout (`<resolve>`) | Resolve đúng hết: hotseat Phone/Messaging/Settings/**Vanadium**/Camera, workspace Proton Mail/Gallery/App Store |
| Thêm widget (bind) | **OK** - Android hỏi 1 lần "Create widget and allow access?" (kèm checkbox "Always allow ..."), bấm Create → widget Shopee hiện trên home, kéo/resize được |
| Kéo-thả icon/widget | **OK** - kéo widget từ (231,536) → (643,909), đổi vị trí đúng; long-press widget → Remove sạch, home về nguyên trạng |
| Long-press icon app | Menu "App info" / "Remove" OK, không exception |
| Quyền thực tế | Được cấp: `RECEIVE_BOOT_COMPLETED`, `SET_WALLPAPER(_HINTS)`, `VIBRATE`, `REQUEST_DELETE_PACKAGES`, `QUERY_ALL_PACKAGES`… Không được cấp: `BIND_APPWIDGET`, `LOCK_APPS`, `READ_DEVICE_CONFIG`… (đặc quyền hệ thống) |
| **Bản release (R8)**: cài đặt | `adb install -r` → `Success`; set home → `isDefault=true` (package `app.graphene.launcher`) |
| **Bản release**: khởi động / RAM | COLD **884 ms**, **PSS ~180 MB** / RSS 333 MB (debug: 1901 ms, PSS ~243 MB / RSS 394 MB) |
| **Bản release**: crash buffer | 0 dòng sau toàn bộ lượt test (home, drawer, menu, widget bind/remove, settings) |
| **Bản release**: Widget picker (Compose) | Hiện Facebook / Shopee / Quick Actions + tab Featured/Browse — sống sót R8 |
| **Bản release**: bind widget | Dialog hệ thống "Create widget and allow access?" → Create → log `App widget created with id: 10` → render dữ liệu thật → Remove sạch |
| **Bản release**: Home settings (`androidx.preference` inflate XML) | Hiện "Notification dots", "Add app icons to home screen", "For new apps" — sống sót R8 |
| Audit bảo mật | **Không có quyền `INTERNET`**, 0 thư viện mạng trong dex, 0 code gửi dữ liệu, release bỏ `debuggable` (xem `SECURITY_AUDIT.md`) |

> Công cụ tự test: `python3 tools/ui.py [từ_khoá]` để dump UI hiện tại kèm toạ độ tap
> (long-press thật bằng `input motionevent DOWN/UP`, không dùng `input swipe`).


## Có ghi đè launcher gốc không?

**KHÔNG.** Launcher gốc của GrapheneOS là system app `com.android.launcher3`, còn bản port là
`app.graphene.launcher` (release) hoặc `app.graphene.launcher.debug` (debug) — khác package → cài
song song, muốn quay lại launcher gốc chỉ cần đổi Home app về "Launcher3".

## Khác gì so với launcher gốc?

Bản này build từ **chính source GrapheneOS Launcher3 branch 17** (Android 17), nhưng chạy như
**app thường** nên mất các đặc quyền hệ thống:

| Tính năng | Trạng thái |
|---|---|
| Home/workspace/app drawer/folder/widget | Chạy bình thường |
| Icon mới (Graphene hexagon) | Đã đổi riêng |
| Grid 4x5, responsive profiles, theming | Giữ nguyên code GrapheneOS |
| **Recents (nút vuông / vuốt lên)** | **Vẫn dùng được** nhờ QuickStep của launcher gốc (`com.android.launcher3/com.android.quickstep.RecentsActivity` - đã test OK): có card, nút Screenshot, search pill. Nhưng đây là overview của launcher gốc, không phải recents tích hợp của bản port (không có live tile/split-screen/taskbar của Graphene). Muốn recents "của chính mình": cần flavor `withQuickstepStandalone` + quyền hệ thống `STATUS_BAR_SERVICE` (root + QuickSwitch: `su -c /data/adb/modules/quickswitch/quickswitch --ch=app.graphene.launcher.debug`) |
| App Functions (androidx.appfunctions) | Đã bỏ (module mainline, không có trên Maven) |
| App Lock (khoá app) | Hạn chế (API @hide A17) - đọc bằng reflection, có thì chạy |
| Storage/Contact Scopes shortcut | Không hiện (API riêng của GrapheneOS, không có trong jar AOSP dùng để build) |
| Bubble shortcut → Taskbar | Stub (chỉ có ở bản Quickstep) |
| Thêm widget | Chạy được (đã test trên cả debug lẫn release), nhưng lần đầu thêm **mỗi** widget Android hỏi "Create widget and allow access?" → tick "Always allow Graphene Launcher…" để tắt hẳn. Vì `BIND_APPWIDGET` là quyền đặc quyền, app thường không được cấp tự động |
| Taskbar / desktop mode / màn hình ngoài | Chưa test - taskbar thuộc Quickstep của launcher gốc chứ không phải của bản port |
| Lưu/khôi phục layout (backup & restore) | Chưa test |
| Xoay màn hình, grid responsive, tablet/foldable | Code GrapheneOS giữ nguyên nhưng chưa test trên máy |
| Cập nhật bảo mật | Bản port **không** nhận OTA của GrapheneOS; mỗi lần launcher gốc được cập nhật phải build lại thủ công |

## Hạn chế & thiếu sót (đã kiểm chứng trên máy)

1. **Recents không phải của bản port** - vẫn do QuickStep của launcher gốc phục vụ
   (`ShellRecents: recents occluded 3p home`). Card, nút Screenshot, search pill đều có, nhưng
   không phải recents tích hợp (không có live tile/split-screen/taskbar của Graphene). Muốn bản
   port tự làm recents → build flavor `withQuickstepStandalone` + root/QuickSwitch (đang bị chặn,
   xem `PROGRESS.txt`).
2. **Widget cần 1 lần đồng ý** cho mỗi widget, do thiếu `BIND_APPWIDGET` (đặc quyền hệ thống).
3. **Mất vài shortcut riêng của GrapheneOS**: "Storage Scopes" và "Contact Scopes" trong menu
   icon không hiện; **App Functions** (workspacefunctions) đã bỏ hẳn; **ModelProxyProvider**
   (`ACCESS_LAUNCHER_DATA`) không còn tác dụng.
4. **App Lock (khoá app)** phụ thuộc API @hide A17 + quyền `LOCK_APPS` (không được cấp) → có thể
   không khoá được app từ launcher.
5. **`POST_NOTIFICATIONS` chưa được cấp** - nếu cần thông báo/tips thì vào
   Settings → Apps → Graphene Launcher → Notifications bật tay.
6. **Dung lượng/RAM** - đã xử lý xong bằng bản release (R8 + shrinkResources):
   APK 11,7 MiB (debug 89 MiB), PSS ~180 MB (debug ~243 MB), cold start 884 ms (debug ~1,9 s).
   Bản debug vẫn để lại trong máy để gỡ lỗi khi cần; có thể gỡ bằng
   `adb uninstall app.graphene.launcher.debug`.
7. **Hai launcher giữ layout riêng**: layout cũ của launcher gốc không tự chuyển sang; bản port
   dùng default layout của GrapheneOS (đã xác nhận đúng).
8. **Chưa test**: tạo folder, gợi ý app (suggestions) trong hotseat, sao lưu/khôi phục layout,
   xoay màn hình / large screen, desktop mode, work profile / private space.


## Các file quan trọng

- `PROGRESS.txt` - nhật ký build đầy đủ: mọi lỗi và cách fix (23 mục + PHẦN 2A audit + PHẦN 2B release)
- `SECURITY_AUDIT.md` - audit bảo mật: quyền, URL, thư viện mạng, dữ liệu lưu ở đâu
- `build.gradle` - cấu hình app + root (namespace `com.android.launcher3`, applicationId `app.graphene.launcher`; signingConfigs + buildTypes.release có R8)
- `proguard-rules.pro` - rule R8 cho bản release (keep tên class launcher/lib AOSP, keep reflection)
- `settings.gradle` - danh sách module
- `tools/gen_launcher_flags.py` - sinh 105 aconfig flag A17 từ `aconfig/*.aconfig`
- `tools/vendor_private_colors.py` - vendor 54 màu private của framework (`androidprv:` → `dcv_`)
- `src/app/graphene/launcher/GrapheneApp.java` - class application, gate Recents
- `src/com/android/launcher3/util/A17Compat.java` - cầu nối API @hide mới của A17
- `res_graphene/` - icon mới

- `ui.py` - dump UI hiện tại kèm toạ độ tap (`python3 tools/ui.py [từ_khoá]`)
- `audit.sh`, `audit_dex.sh`, `audit_apk.sh` - audit mã nguồn / dex / APK
- `verify_release.sh` - kiểm tra APK release (quyền, cert, dex, thư viện mạng)
- `build_release.sh`, `test_release.sh`, `ram_widget.sh`, `extra_test.sh` - build & test bản release

## Build lại

```fish
cd ./
set -x JAVA_HOME /usr/lib/jvm/java-21-openjdk

# bản release (R8 + minify) - dùng hàng ngày
./gradlew :assembleNoQuickstepStandaloneRelease --no-daemon
# -> build/outputs/apk/noQuickstepStandalone/release/graphene-port-noQuickstep-standalone-release.apk

# bản debug (để gỡ lỗi, không tối ưu)
./gradlew :assembleNoQuickstepStandaloneDebug --no-daemon
```

APK release được ký bằng `~/.android/debug.keystore` (khai báo trong `signingConfigs.userKey`) nên
`adb install -r` luôn nâng cấp tại chỗ được. Nếu muốn ký bằng keystore riêng thì sửa
`signingConfigs.userKey` trong `build.gradle` — lưu ý khi đó phải gỡ app cũ trước khi cài.

