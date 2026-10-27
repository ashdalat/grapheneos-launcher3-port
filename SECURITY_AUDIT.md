# SECURITY AUDIT — Graphene Launcher (bản port cài như app thường)

Ngày audit: 20/09/2026 · Thiết bị: Pixel 8 (`shiba`), GrapheneOS 17 / Android 17 (SDK 37,
`CP2A.260805.005`) · Cách làm: audit tĩnh (đọc code) + audit động (mổ APK, quét 1,3 triệu chuỗi dex).

APK được audit:

| Bản | File | Dung lượng | sha256 |
|---|---|---|---|
| release (R8) | `GrapheneLauncher-release.apk` | 12.260.277 byte (~11,7 MiB) | `2843442d…c1c0b1` |
| debug | `GrapheneLauncher-debug.apk` | 93.618.411 byte (~89 MiB) | `03364736…ad6af38` |

Bằng chứng thô: `tools/audit_src.txt`, `tools/audit_apk.txt`, `tools/audit_dex.txt`,
`tools/audit_dex_detail.txt`, `docs/audit/perms_dev.txt`, `docs/audit/verify_release.txt`.

---

## 1. Kết luận ngắn

| Câu hỏi | Kết quả |
|---|---|
| Có xin quyền `android.permission.INTERNET`? | **KHÔNG** — không có trong manifest nguồn, manifest đã merge, và cả APK release lẫn debug (`aapt2 dump badging/permissions`) |
| Có thư viện mạng (okhttp/retrofit/coil/libsu/Volley…)? | **KHÔNG** — quét dex: `okhttp3`=0, `retrofit2`=0, `coil/`=0, `com/topjohnwu`=0, `HttpURLConnection`=0, `java/net/Socket`=0, `SslSocket`=0 |
| Có code tự mở socket / gửi dữ liệu ra ngoài? | **KHÔNG** — trong source app chỉ có `java.net.URISyntaxException` (bắt lỗi parse), không có `openConnection`/`new URL()`/`Socket`/`WebView.loadUrl` |
| Có tracker/analytics/crash reporter? | **KHÔNG** — không có Firebase/Crashlytics/Sentry/Play Services/Amplitude/Mixpanel trong dependency lẫn APK |
| Có link http/https ẩn? | Có URL nhưng **toàn bộ là hằng số vô hại**: header license, XML namespace, link trong comment, 1 URL trong layout mặc định (mục 4). Không endpoint nào được gọi |
| Có bản code hook lạ? | Có `ChickenHook RestrictionBypass` + `dev.rikka.tools.refine` — lib nguồn mở để gọi API `@hide`; **không liên quan mạng** (mục 6) |

**Cơ sở kết luận:** app không có quyền INTERNET ⇒ dù code có muốn gửi dữ liệu thì kernel cũng chặn,
vì quyền INTERNET chỉ được cấp qua manifest + gid `inet`. Cộng thêm không có thư viện mạng nào
trong APK ⇒ rủi ro "ăn cắp data qua mạng" ở mức code là **không có**.

---

## 2. Quyền thực tế (`aapt2 dump permissions` + `dumpsys package`)

Được cấp khi cài (normal permission, không hỏi người dùng):
`RECEIVE_BOOT_COMPLETED`, `SET_WALLPAPER`, `SET_WALLPAPER_HINTS`, `VIBRATE`,
`REQUEST_DELETE_PACKAGES`, `QUERY_ALL_PACKAGES`, `GRID_CONTROL` (tự khai báo),
`DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`, `OTHER_SENSORS`.

Khai báo nhưng **bị hệ thống từ chối** (signature|privileged):
`BIND_APPWIDGET`, `LOCK_APPS`, `READ_DEVICE_CONFIG`, `ROTATE_SURFACE_FLINGER`,
`WAKEUP_SURFACE_FLINGER`, `com.android.systemui.permission.LAUNCH_ACCESSIBILITY_SHORTCUT_CHOOSER_DIALOG`.

Khai báo nhưng **chưa được cấp** (runtime permission, phải người dùng đồng ý):
`POST_NOTIFICATIONS` (false), `CALL_PHONE` (false), `READ_EXTERNAL_STORAGE` (false).

Không có: `INTERNET`, `ACCESS_NETWORK_STATE`, `ACCESS_WIFI_STATE`, `ACCESS_FINE_LOCATION`,
`READ_CONTACTS`, `READ_SMS`, `READ_CALL_LOG`, `CAMERA`, `RECORD_AUDIO`, `READ_PHONE_STATE`.

`QUERY_ALL_PACKAGES`: launcher **phải** thấy danh sách app đã cài để vẽ drawer — quyền bình thường
của mọi launcher (Nova/Lawnchair/GrapheneOS Launcher gốc đều xin). Nó cho biết *có app nào*,
không đọc được dữ liệu bên trong app đó.

---

## 3. Code: không có đường ra mạng

| Kiểm tra | Kết quả |
|---|---|
| `HttpURLConnection`, `URLConnection`, `openConnection`, `new URL(` | 0 |
| `java.net.*` trong source app | chỉ `java.net.URISyntaxException` (3 file: `DefaultLayoutParser`, `SecondaryDropTarget`, `DatabaseHelper`) |
| `WebView` / `loadUrl` | 0 lần dùng |
| `Runtime.exec`, `ProcessBuilder`, `DexClassLoader`, `System.loadLibrary` | 0 |
| `Class.forName` | 3 chỗ, đều là class **hệ thống**: `android.os.SystemProperties` (đọc prop), `android.security.Flags` (A17Compat), `com.android.quickstep.QuickStepContract` (gate GrapheneApp) |
| `getDeviceId`/`getImei`/`ANDROID_ID`/`LocationManager`/`AccountManager`/`CallLog` | 0 |

---

## 4. Các URL http/https tồn tại trong source (đã phân loại hết)

| URL | Ở đâu | Có bị gọi không? |
|---|---|---|
| `http://www.apache.org/licenses/LICENSE-2.0` (~1.000 dòng) | header license file AOSP | Không — comment |
| `http://schemas.android.com/apk/res/android`, `res-auto`, `apk/androidx.appfunctions` | XML namespace của Android/AAPT | Không — namespace, không phải địa chỉ mạng |
| `http://b/…`, `https://r.android.com/…`, `goo.gle/compose-feedback`, `youtrack.jetbrains.com/issue/KT-*`, `carbon.googleplex.com/…`, `shortn/…`, `bottosson.github.io/…oklab`, `developer.android.com/…foldables`, `w3.org/2000/svg`, `xmlpull.org/…` | comment/annotation trong code AOSP, Kotlin, Compose (bug id, tài liệu) | Không |
| `https://policies.google.com/terms?hl=`, `https://policies.google.com/privacy/embedded?hl=` | thư viện `systemUI/shared` (SystemUI AOSP) cho hộp thoại điều khoản | Không — app không có luồng nào mở 2 link này, và không có quyền mạng |
| `https://github.com/LawnchairLauncher/lawnchair/issues/6817` | comment trong source port | Không |
| `http://www.example.com/` (2 chỗ) | `res/xml/default_workspace_*.xml`, mục `<resolve>` để chọn **icon browser mặc định** | Launcher chỉ tạo `Intent.ACTION_VIEW` rồi **trao cho trình duyệt**; launcher không tự tải |

Chuỗi trong APK release còn lại (quét dex): chỉ `http://schemas.android.com/...`, `xmlpull.org`,
`goo.gle/compose-feedback`, `youtrack.jetbrains.com` — namespace/annotation. APK **không** chứa
`assets/*.js`, `*.html`; native lib chỉ có `libandroidx.graphics.path.so` (đường cong cho
`graphics-shapes`) và `libnrb.so` (native của RestrictionBypass, mục 6); asset chỉ có
`assets/dexopt/baseline.prof(.profm)`.

---

## 5. Dữ liệu được lưu ở đâu

Launcher lưu **cục bộ** trong `/data/user/0/<package>/` (sandbox riêng, app khác không đọc được
nếu không root):

- `databases/launcher.db` — workspace/hotseat/db nội bộ (SQLite)
- `shared_prefs/*.xml` — tuỳ chọn (grid, theme, cờ tính năng)
- `files/` — file backup layout dạng protobuf (`LauncherBackupAgent`), bitmap icon tự cắt cho `<adaptive-icon>`
- `cache/` — cache icon/bitmap

Không có `android:sharedUserId`, không dùng external storage (`READ/WRITE_EXTERNAL_STORAGE` không
được cấp), không gửi dữ liệu đi đâu. Backup qua `adb backup` chỉ xảy ra khi *người dùng* chạy lệnh.

---

## 6. Điểm cần biết cho trung thực (không phải rủi ro ăn cắp data)

1. **`org.chickenhook.restrictionbypass` (ChickenHook RestrictionBypass) + `libnrb.so`** được đóng
   gói cùng app. Đây là thư viện nguồn mở (GPL-3, `com.github.ChickenHook`) để **vượt chặn gọi API
   `@hide`** bằng native hook — cần cho việc launcher gọi API hệ thống (grid, widget host, taskbar…).
   Nó **không** đụng tới mạng, không đọc dữ liệu app khác, nhưng đúng là một "hook". Muốn thuần
   AOSP hơn: bỏ `libs.chickenhook.restrictionbypass` trong `build.gradle` rồi build lại (sẽ phải bỏ
   các API `@hide` tương ứng).
2. **`dev.rikka.tools.refine.runtime`** — helper để truy cập API `@hide` (không hook, không mạng).
3. **Bản debug có `android:debuggable="true"`** ⇒ ai có ADB (hoặc app lợi dụng lỗi khác) có thể
   `run-as`/attach vào app đó. **Bản release đã bỏ attribute này** (đã kiểm tra `aapt2 dump badging`
   không còn `application-debuggable`) ⇒ đây là một lý do nữa để dùng bản release.
4. **`READ_DEVICE_CONFIG`** khai báo (AOSP cần cho DeviceConfig flags) nhưng hệ thống **không cấp**
   cho app thường ⇒ code đọc DeviceConfig trả mặc định, không rò rỉ.
5. Các **URL hằng số** ở mục 4 vẫn nằm trong code (bình thường với AOSP). Muốn "sạch chuỗi tuyệt
   đối" thì xoá comment chứa link và sửa `res/xml/default_workspace_5x5.xml`.
6. **Widget**: khi bạn bấm "Create" trong hộp thoại của hệ thống, quyền bind widget được cấp cho
   launcher **theo từng widget**; nếu tick "Always allow" thì máy ghi nhớ. Không liên quan mạng,
   nhưng là quyền nhạy cảm (widget có thể hiển thị nội dung app khác).

---

## 7. Lệnh tự kiểm chứng lại (trên máy build)

```fish
# 1. Có quyền INTERNET không? (kỳ vọng: "KHONG CO android.permission.INTERNET (sach)")
bash tools/verify_release.sh; less docs/audit/verify_release.txt

# 2. Quét chuỗi dex tìm thư viện mạng/tracker (kỳ vọng mọi dòng = 0)
bash tools/audit_dex.sh; less tools/audit_dex.txt

# 3. Quét toàn bộ source: URL, API mạng, exec/reflection, tracker
bash tools/audit.sh; less tools/audit_src.txt

# 4. Danh sách quyền + granted trên máy thật
aapt2 dump permissions GrapheneLauncher-release.apk
adb shell dumpsys package app.graphene.launcher | grep granted=
```
