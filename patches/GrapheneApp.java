package app.graphene.launcher;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.res.Resources;
import android.util.Log;
import com.android.launcher3.LauncherApplication;
import com.android.quickstep.RecentsActivity;
import com.android.systemui.shared.system.QuickStepContract;

/**
 * GrapheneApp: copy co che LawnchairApp.kt:60-73,192-214.
 * - compatible: SDK trong QUICKSTEP_MIN..MAX (debug mo rong 0..100000)
 * - isRecentsComponent: config_recentsComponentName == package nay + RecentsActivity
 * - recentsEnabled = compatible && isRecentsComponent
 * - onCreate: QuickStepContract.sRecentsDisabled = !recentsEnabled
 * User-app khong phai provider -> chay HOME thuong, khong crash.
 */
public class GrapheneApp extends LauncherApplication {
    private static final String TAG = "GrapheneApp";
    private static GrapheneApp sInstance;
    private Boolean mIsRecentsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        QuickStepContract.sRecentsDisabled = !isRecentsEnabled();
    }

    public static GrapheneApp getInstance() {
        return sInstance;
    }

    public static boolean isRecentsEnabled() {
        GrapheneApp app = sInstance;
        if (app == null) return false;
        return app.recentsEnabled();
    }

    private boolean recentsEnabled() {
        return isCompatible() && isRecentsComponent();
    }

    private boolean isCompatible() {
        int sdk = android.os.Build.VERSION.SDK_INT;
        return sdk >= com.android.launcher3.BuildConfig.QUICKSTEP_MIN_SDK
                && sdk <= com.android.launcher3.BuildConfig.QUICKSTEP_MAX_SDK;
    }

    @SuppressLint("DiscouragedApi")
    private boolean isRecentsComponent() {
        if (mIsRecentsComponent != null) return mIsRecentsComponent;
        boolean result = false;
        try {
            Resources res = getResources();
            int resId = res.getIdentifier("config_recentsComponentName", "string", "android");
            if (resId != 0) {
                ComponentName recents = ComponentName.unflattenFromString(res.getString(resId));
                if (recents != null) {
                    result = recents.getPackageName().equals(getPackageName())
                            && recents.getClassName().equals(RecentsActivity.class.getName());
                    if (!result) {
                        Log.d(TAG, "config_recentsComponentName (" + recents
                                + ") is not Graphene launcher, disabling recents");
                    }
                }
            } else {
                Log.d(TAG, "config_recentsComponentName not found, disabling recents");
            }
        } catch (Exception e) {
            Log.d(TAG, "recents check failed, disabling recents", e);
        }
        mIsRecentsComponent = result;
        return result;
    }
}
