/*
 * GrapheneApp - application class cho ban "Graphene Launcher" user-app.
 *
 * Vai tro: thay cho com.android.launcher3.LauncherApplication trong manifest.
 * Day chinh la "cua" de launcher chay duoc nhu app thuong ma khong crash:
 *
 *  - Tren launcher he thong, package nay duoc cap quyen he thong nen no la
 *    Recents provider (config_recentsComponentName tro vao chinh no).
 *  - Khi cai nhu app thuong (applicationId app.graphene.launcher), he thong
 *    KHONG cap STATUS_BAR_SERVICE / MANAGE_ACTIVITY_TASKS, nen khong the bind
 *    TouchInteractionService va khong the doc task stack.
 *
 * => Tuong tu Lawnchair (LawnchairApp): kiem tra xem minh co phai Recents
 *    provider khong. Neu khong: tat duong Quickstep (chi con vai tro HOME).
 *    Viec tat lam bang reflection de class nay compile duoc o CA HAI flavor
 *    (noQuickstep khong he co class com.android.quickstep.*).
 */
package app.graphene.launcher;

import android.content.Context;
import android.util.Log;

import com.android.launcher3.LauncherApplication;

public class GrapheneApp extends LauncherApplication {

    private static final String TAG = "GrapheneApp";

    private static boolean sIsRecentsEnabled = false;
    private static boolean sRecentsChecked = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sIsRecentsEnabled = computeRecentsEnabled(this);
        sRecentsChecked = true;
        Log.i(TAG, "Graphene Launcher user-app, isRecentsProvider=" + sIsRecentsEnabled);

        if (!sIsRecentsEnabled) {
            disableQuickstepReflectively();
        }
    }

    /** Co phai Recents provider cua he thong khong (doc config_recentsComponentName). */
    public static boolean isRecentsEnabled(Context context) {
        if (!sRecentsChecked) {
            sIsRecentsEnabled = computeRecentsEnabled(context);
            sRecentsChecked = true;
        }
        return sIsRecentsEnabled;
    }

    private static boolean computeRecentsEnabled(Context context) {
        try {
            final int resId = context.getResources().getIdentifier(
                    "config_recentsComponentName", "string", "android");
            if (resId == 0) {
                return false;
            }
            final String expected = context.getPackageName()
                    + "/com.android.quickstep.RecentsActivity";
            final String actual = context.getString(resId);
            boolean match = expected.equals(actual);
            if (!match) {
                Log.i(TAG, "Not system Recents provider. system=" + actual
                        + " expected=" + expected);
            }
            return match;
        } catch (Throwable t) {
            Log.w(TAG, "Cannot read config_recentsComponentName", t);
            return false;
        }
    }

    /**
     * Tat Quickstep: QuickStepContract.sRecentsDisabled = true.
     * Dung reflection vi flavor noQuickstep khong co class nay.
     */
    private static void disableQuickstepReflectively() {
        try {
            final Class<?> contract = Class.forName("com.android.quickstep.QuickStepContract");
            final java.lang.reflect.Field field =
                    contract.getDeclaredField("sRecentsDisabled");
            field.setAccessible(true);
            field.setBoolean(null, true);
            Log.i(TAG, "QuickStepContract.sRecentsDisabled = true");
        } catch (Throwable t) {
            // Khong co class Quickstep trong build nay - khong sao.
            Log.i(TAG, "Quickstep not present in this build, nothing to disable");
        }
    }
}