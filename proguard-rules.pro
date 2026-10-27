# ============================================================================
# R8/ProGuard rules cho "Graphene Launcher" - ban user-app, build RELEASE.
#
# Muc tieu: giam dung luong APK (84 MB dex -> nho hon nhieu) va RAM, nhung
# KHONG lam hong hanh vi. Launcher3 doc rat nhieu thu bang ten:
#   - aapt sinh rule san cho class trong manifest/layout
#   - XML inflation (View/Fragment/Preference) theo ten class
#   - metadata string tro ten class (vd. grid preview provider)
# => chien luoc an toan: giu NGUYEN TEN (khong obfuscate) cho cac package cua
#    launcher + lib AOSP, van cho R8 xoa code khong dung + thu gon resource.
# ============================================================================

# ---- 1. Khong doi ten class/member cua launcher va cac lib AOSP -------------
-keepnames class com.android.launcher3.** { *; }
-keepnames class app.graphene.** { *; }
-keepnames class com.android.systemui.** { *; }
-keepnames class com.android.wm.shell.** { *; }
-keepnames class com.android.quickstep.** { *; }
-keepnames class com.android.internal.** { *; }
-keepnames class com.android.settingslib.** { *; }

# ---- 2. Reflection: class/field goi bang ten tu code cua minh --------------
# GrapheneApp.disableQuickstepReflectively() doc QuickStepContract.sRecentsDisabled
-keep class com.android.quickstep.QuickStepContract { *; }
# A17Compat goi API @hide cua framework (android.security.Flags,
# PackageManager.getEnableAppLockIntentForPackage, View.startVisibilityTracking)
# -> framework khong bi R8 cham toi, khong can rule.
# Utilities.java doc android.os.SystemProperties bang reflection -> nhu tren.

# ---- 3. Component duoc he thong goi bang ten -------------------------------
-keep class * extends android.appwidget.AppWidgetProvider { *; }
-keep class * extends android.app.Service { *; }
-keep class * extends android.content.BroadcastReceiver { *; }
-keep class * extends android.content.ContentProvider { *; }
-keep class * extends android.app.Activity { *; }
-keep class * extends android.view.View { *; }
-keep class * extends android.app.Fragment { *; }
-keep class * extends androidx.fragment.app.Fragment { *; }
-keep class * extends androidx.preference.Preference { *; }
-keep class * extends androidx.preference.PreferenceFragmentCompat { *; }
-keep class * extends androidx.preference.DialogPreference { *; }

# ---- 4. Dagger / Hilt / KSP sinh code, goi bang ten interface --------------
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep @dagger.hilt.android.* class * { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }

# ---- 5. Enum: giu values()/valueOf (R8 full mode co the xoa) ---------------
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    public static ** valueOf(int);
}

# ---- 6. Parcelable / Serializable / proto-lite -----------------------------
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# Protobuf lite (Launcher backups/layout proto) dung descriptor + reflection noi bo
-keep class com.android.launcher3.proto.** { *; }
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    <fields>;
}
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod,
                SourceFile, LineNumberTable, MethodParameters

# ---- 7. ChickenHook RestrictionBypass (native) ----------------------------
-keep class org.chickenhook.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class dev.rikka.tools.refine.** { *; }

# ---- 8. Bo canh bao "missing class" cua cac lib tuy chon ----------------
# (chi tat canh bao, khong anh huong viec xoa code)
-dontwarn kotlin.**
-dontwarn kotlinx.**
-dontwarn org.jetbrains.**
-dontwarn org.intellij.**
-dontwarn dagger.**
-dontwarn javax.**
-dontwarn androidx.**
-dontwarn com.google.protobuf.**
-dontwarn com.google.common.**
-dontwarn com.google.errorprone.**
-dontwarn com.google.j2objc.**
-dontwarn org.checkerframework.**
-dontwarn org.codehaus.**
-dontwarn org.conscrypt.**
-dontwarn sun.misc.**
-dontwarn com.android.launcher3.**
-dontwarn app.graphene.**
# Flavor noQuickstep: cac class wm.shell.* (bubbles/split/desktop) nam trong
# WindowManager-Shell-16.jar va systemUI/shared nhung tham chieu cheo cac class
# khong co trong build nay (R8 coi la loi "Missing class"). Doan code do khong
# bao gio chay khi khong phai Recents provider => tat canh bao la dung.
-dontwarn com.android.wm.**
-dontwarn libcore.**
-dontwarn android.**
-dontwarn com.android.internal.**
-dontwarn com.android.systemui.**
-dontwarn org.apache.**
