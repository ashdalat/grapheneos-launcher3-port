package com.android.quickstep;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

/**
 * Graphene port: stub user-app.
 * TouchInteractionService gốc cần STATUS_BAR_SERVICE (system mới bind được).
 * Ở user-app không được grant -> trả null để không crash,
 * launcher chạy HOME thường (kiểu Neo: bỏ QuickSwitch).
 * Khi nào root + QuickSwitch thì service gốc mới có tác dụng.
 */
public class TouchInteractionServiceStub extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        // user-app: không init SysUIConnectionComponent ở đây
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // báo system là không handle gesture/recents
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
