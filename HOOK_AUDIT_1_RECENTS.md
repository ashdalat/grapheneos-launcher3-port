# Audit hooks 1-2: Recents + Tasks (Graphene 17, không máy test)

## 1. TouchInteractionService — QUICKSTEP_SERVICE / STATUS_BAR_SERVICE
- Graphene: `quickstep/AndroidManifest.xml:68-73` service permission
  STATUS_BAR_SERVICE + `TouchInteractionService.kt:29-56` onBind component.binder.
- Lỗi user-app: STATUS_BAR_SERVICE là signature|privileged, SystemUI chỉ bind
  Recents provider (`config_recentsComponentName`). App thường không grant.
- Lawnchair: `LawnchairApp.kt:192-214 checkRecentsComponent()` đọc
  `android.string.config_recentsComponentName`, so packageName + RecentsActivity.
  Sai -> `QuickStepContract.sRecentsDisabled=true` (dòng 70).
  `RecentTasksList.java:111 if (isRecentsEnabled)` mới đăng ký listener.
  Lệch thì dialog `showQuickstepWarningIfNecessary` (dòng 244).
- Port: copy cơ chế isRecentsEnabled, đổi package thành app.graphene.launcher.
  Stub đã tạo: `patches/quickstep/TouchInteractionServiceStub.java`.

## 2. RecentsModel / RecentTasksList / SystemUiProxy — MANAGE_ACTIVITY_TASKS
- Graphene: `RecentsModel.kt`, `RecentTasksList` qua
  `mSysUiProxy.getRunningTasks/registerRecentTasksListener`,
  `ActivityManagerWrapper.startActivityFromRecents/takeTaskSnapshot`.
- Lỗi: cần MANAGE_ACTIVITY_TASKS, START_TASKS_FROM_RECENTS, REMOVE_TASKS,
  READ_REMOTE_TASKS + REQUEST_TASK_HANDOFF (mới 17). App thường bị từ chối.
- Lawnchair:
  + `TaskStackChangeListeners.java:165-180,182-198`: `if (!ATLEAST_V) return`
    + try/catch NoClassDefFoundError|Exception.
  + `RecentTasksList.java:111-194`: `if (isRecentsEnabled) { ATLEAST_U thì
    IRecentTasksListener, else ATLEAST_Q thì TaskStackChangeListener }`.
  + `ActivityManagerWrapper.java:157-217`: mọi call try/catch, fail trả false.
  + `LawnchairQuickstepCompat.kt:17-69`: factory theo SDK, 17 dùng tạm VBaklava.
- Port: copy compatLib + guard, 17 chưa có compat riêng.
