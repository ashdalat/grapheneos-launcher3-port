# Checklist sửa hook system -> user app (GrapheneOS branch 17, máy Android 17)

Base SDK: Android 17 = API 37. Lawnchair 16-dev đã dùng
`buildTools 37.0.0 + compileSdk release(37){minorApiLevel=2} + targetSdk 37` -> khớp.

## A. Build system (làm trước)
- [ ] Copy từ `lawnchair-ref/`: `gradle/libs.versions.toml, prebuilts/libs/*.jar,
  compatLib/, wmshell/, hidden-api/, systemUI/, platform_frameworks_libs_systemui/ -> libs-systemui/,
  flags/, dagger/, lawnchair/src_build_config/ -> app/src_build_config/`
- [ ] Copy từ `lawnchair-ref/quickstep/src_no_quickstep` hoặc `grapheneos-launcher3/src_no_quickstep`
  làm flavor `withoutQuickstep`
- [ ] `app/build.gradle`: `namespace=com.android.launcher3`,
  `applicationId=app.graphene.launcher`, `minSdk 26-30, targetSdk 37, compileSdk 37`,
  `compileOnly framework/SystemUI-core/SystemUI-statsd/WindowManager-Shell`,
  `protobuf lite`, `Rikka refine + ChickenHook restrictionbypass`
- [ ] `src_build_config/BuildConfig.java`: `APPLICATION_ID=app.graphene.launcher,
  IS_STUDIO_BUILD=true, IS_DEBUG_DEVICE=false` (thay genrule Soong)

## B. Manifest (giữ khai báo cho compile pass, runtime degrade)
- [ ] `AndroidManifest.xml`: giữ `package=com.android.launcher3`,
  activity `com.android.launcher3.Launcher` intent `HOME/DEFAULT/LAUNCHER_APP`
- [ ] `AndroidManifest-common.xml`: `authorities=${applicationId}.settings/.grid_control/.TestInfo`,
  `READ/WRITE_SETTINGS signatureOrSystem` (tự cấp cho chính mình)
- [ ] `quickstep/AndroidManifest.xml` (17 mới, nhiều nhất):
  - Giữ `TouchInteractionService (STATUS_BAR_SERVICE)` nhưng wrap `SecurityException` ->
    không bind được thì chạy HOME thường (kiểu Neo bỏ QuickSwitch)
  - `RecentsActivity, ProxyActivityStarter, GestureSandbox, AllSet(REBOOT),
    QuickstepWidgetPickerActivity, QuickstepAddItemActivity` giữ cho compile
  - 17 thêm: `READ_REMOTE_TASKS, REQUEST_TASK_HANDOFF, REPORT_UI_LATENCY_STATS,
    EXECUTE_APP_FUNCTIONS_SYSTEM, PERSONAL_CONTEXT_* x4,
    MANAGE_KEY_GESTURES(featureFlag)` -> user-app không được grant, bỏ qua

## C. Code Java/Kotlin (sửa theo lỗi `assembleWithoutQuickstepDebug`)
1. `com.android.internal.* / com.android.systemui.* / android.appwidget.flags,
   android.os.flags, com.android.window.flags` -> đã có stub `compileOnly` + refine
2. `ActivityManagerWrapper, RecentsModel, TaskStackChangeListeners,
   SystemUiProxy, TouchInteractionService` -> dùng `compatLibVBaklava` (tạm cho 17),
   không có thì fallback `src_no_quickstep`
3. `BIND_APPWIDGET` -> xin `AppWidgetManager.requestBindAppWidget()` thủ công
4. `WRITE_SECURE_SETTINGS (TestInformationProvider)` -> `enabled=false` đã có sẵn
5. `ROTATE/WAKEUP_SURFACE_FLINGER, ACCESS_HIDDEN_PROFILES_FULL, MONITOR_INPUT,
   SYSTEM_APPLICATION_OVERLAY, PERSONAL_CONTEXT_*` -> catch + disable tính năng
6. `Protolog / aconfig android.security.flags (mới 17)` -> thay bằng `BuildConfig` tĩnh
   hoặc copy `flags/` từ Lawnchair

## D. Test trên máy Android 17
- [ ] `./gradlew :app:assembleWithoutQuickstepDebug`
- [ ] `adb install -r *.apk` -> `Settings -> Apps -> Default Apps -> Home App`
- [ ] Có widget thì cấp bind thủ công, gesture recents hệ thống sẽ mất (đúng kiểu Neo)
- [ ] Muốn recents sau: root + QuickSwitch
  `su -c /data/adb/modules/quickswitch/quickswitch --ch=app.graphene.launcher` + reboot
  (Android 14+ không dùng app QuickSwitch, phải dùng lệnh; 17 chưa có compatLib riêng
  nên có thể crash -> về lại provider mặc định)
