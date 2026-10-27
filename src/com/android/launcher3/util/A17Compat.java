/*
 * Cau noi cho cac API chi co tren Android 17 (khong co trong framework-16.jar dung khi build).
 *
 * Ly do: GrapheneOS Launcher3 branch 17 dung vai API @hide moi cua A17:
 *   - android.security.Flags.appLockApis()
 *   - android.appwidget.AppWidgetHostView#startVisibilityTracking()/stopVisibilityTracking()
 * Goi bang reflection => compile duoc voi jar A16, va van chay dung tren may Android 17.
 * Neu API khong co (may cu / bi hidden-api chan) thi coi nhu tat tinh nang, khong crash.
 */
package com.android.launcher3.util;

public final class A17Compat {

    private A17Compat() {}

    /** android.security.Flags.appLockApis() */
    public static boolean appLockApis() {
        try {
            return (boolean) Class.forName("android.security.Flags")
                    .getMethod("appLockApis")
                    .invoke(null);
        } catch (Throwable t) {
            return false;
        }
    }

    /** android.appwidget.AppWidgetHostView#startVisibilityTracking() */
    public static void startVisibilityTracking(Object view) {
        invokeNoArg(view, "startVisibilityTracking");
    }

    /** android.appwidget.AppWidgetHostView#stopVisibilityTracking() */
    public static void stopVisibilityTracking(Object view) {
        invokeNoArg(view, "stopVisibilityTracking");
    }

    /**
     * android.content.pm.PackageManager#getEnableAppLockIntentForPackage(String, boolean)
     * (API @hide moi cua A17 - tinh nang App Lock)
     */
    public static android.app.PendingIntent getEnableAppLockIntentForPackage(
            android.content.pm.PackageManager pm, String packageName, boolean enabled) {
        if (pm == null) {
            return null;
        }
        try {
            return (android.app.PendingIntent) android.content.pm.PackageManager.class
                    .getMethod("getEnableAppLockIntentForPackage", String.class, boolean.class)
                    .invoke(pm, packageName, enabled);
        } catch (Throwable t) {
            return null;
        }
    }

    private static void invokeNoArg(Object target, String method) {
        if (target == null) {
            return;
        }
        try {
            target.getClass().getMethod(method).invoke(target);
        } catch (Throwable ignored) {
            // API khong co tren ban Android nay -> bo qua.
        }
    }
}