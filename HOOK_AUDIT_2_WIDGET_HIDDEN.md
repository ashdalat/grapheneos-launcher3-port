# Audit hooks 3-5: Widget + Hidden API + Secure Settings (Graphene 17)

## 3. BIND_APPWIDGET — widget bind
- Graphene: `AndroidManifest-common.xml BIND_APPWIDGET`,
  `src/.../qsb/OseWidgetManager.kt:116-128` allocate + bindAppWidgetIdIfAllowed,
  fail thì deleteAppWidgetId.
- Lỗi: system auto-grant, user-app trả false lần đầu. Docs AppWidgetManager:
  phải mở dialog `requestBindAppWidget` (ACTION_APPWIDGET_BIND).
- Lawnchair/Neo: giữ bindAppWidgetIdIfAllowed, false thì mở activity xin quyền.
- Port: `patches/widget/PortWidgetBindHelper.java` đã tạo.

## 4. Hidden / @hide API — non-SDK restrictions
- Graphene: `platform_apis:true` gọi thẳng `com.android.internal.*`,
  `com.android.systemui.*`, `IActivityTaskManager, IActivityManager,
  IWindowManager, IAppWidgetService (QuickstepProcessInitializer.java:82-96)`,
  `window/appwidget/os/security flags (security mới 17)`, Protolog.
- Lỗi: docs restrictions-non-sdk-interfaces: user-app SDK không thấy @hide,
  runtime blacklist chặn reflection (9+ siết tới 17).
- Lawnchair: `prebuilts/libs/framework-16.jar + SystemUI-core/statsd +
  WindowManager-Shell compileOnly` (build.gradle:109-123,378,418-420)
  + `Rikka refine + ChickenHook restrictionbypass` + hidden-api module
  + compatLib reflection guard ATLEAST_*.
- Port: dùng 4 jar -16 tạm cho 17, giữ refine.

## 5. WRITE_SECURE_SETTINGS + quyền system 17 mới
- Graphene: `quickstep/AndroidManifest.xml WRITE_SECURE_SETTINGS`,
  `TestInformationProvider authorities=${applicationId}.TestInfo enabled=false`.
- Lỗi: WRITE_SECURE_SETTINGS là signature|preinstalled. enabled=false sẵn
  nên không crash, chỉ test mới bật.
- Lawnchair giữ enabled=false. Quyền 17 mới `REPORT_UI_LATENCY_STATS,
  EXECUTE_APP_FUNCTIONS_SYSTEM, PERSONAL_CONTEXT_* x4, MANAGE_KEY_GESTURES,
  ACCESS_HIDDEN_PROFILES_FULL, ROTATE/WAKEUP_SURFACE_FLINGER, MONITOR_INPUT,
  SYSTEM_APPLICATION_OVERLAY, INTERNAL_SYSTEM_WINDOW, STATUS_BAR*`:
  khai báo cho compile, runtime try/catch + degrade.
- Neo: FAQ No QuickSwitch, no plans — bỏ hẳn recents provider cho gọn.

## Kết luận
1. Thêm GrapheneApp copy `LawnchairApp.kt:60-73,192-214,244-278`.
2. Copy RecentTasksList guard + TaskStackChangeListeners try/catch + Compat factory.
3. Widget requestBindAppWidget dialog. 4. Build noQuickstep trước.
