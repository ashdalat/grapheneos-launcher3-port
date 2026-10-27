package com.android.launcher3;

/**
 * Graphene port branch 17: thay genrule Soong launcher-build-config.
 * Copy file này vào app/src_build_config/com/android/launcher3/BuildConfig.java
 * khi dựng app module Gradle.
 */
public final class BuildConfig {
    public static final String APPLICATION_ID = "app.graphene.launcher";

    public static final boolean IS_STUDIO_BUILD = true;
    public static final boolean IS_DEBUG_DEVICE = false;

    public static final boolean QSB_ON_FIRST_SCREEN = false;
    public static final boolean WIDGET_ON_FIRST_SCREEN = false;

    public static final boolean WIDGETS_ENABLED = true;
    public static final boolean NOTIFICATION_DOTS_ENABLED = true;
}
