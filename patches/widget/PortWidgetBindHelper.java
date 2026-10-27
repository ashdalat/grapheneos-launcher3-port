package com.android.launcher3.util;

import android.appwidget.AppWidgetManager;
import android.content.Context;

/**
 * Graphene port: BIND_APPWIDGET ở system là auto-grant (privileged),
 * ở user-app phải xin thủ công qua AppWidgetManager.requestBindAppWidget().
 * Gọi helper này trước khi bind widget, nếu false thì mở activity xin quyền.
 */
public final class PortWidgetBindHelper {
    private PortWidgetBindHelper() {}

    public static boolean canBindWidget(Context context, int appWidgetId,
            android.content.ComponentName provider) {
        AppWidgetManager mgr = context.getSystemService(AppWidgetManager.class);
        if (mgr == null) return false;
        try {
            return mgr.bindAppWidgetIdIfAllowed(appWidgetId, provider);
        } catch (Exception e) {
            return false;
        }
    }
}
