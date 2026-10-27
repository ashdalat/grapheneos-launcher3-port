/*
 * Thay the class `com.android.wm.shell.shared.bubbles.logging.EntryPoint` cua AOSP 17.
 *
 * Ly do: ban port dung WindowManager-Shell A16 (jar + module wmshell A16) khong co class
 * nay, nhung Launcher3 A17 van tham chieu (bubble shortcut tren icon menu).
 * Giu nguyen package + ten + cac enum value de code A17 compile duoc.
 * Neu sau nay swap wmshell len A17 thi XOA file nay (tranh trung class).
 */
package com.android.wm.shell.shared.bubbles.logging

/** Nguon mo bubble tu icon menu. */
enum class EntryPoint {
    ALL_APPS_ICON_MENU,
    HOTSEAT_ICON_MENU,
    TASKBAR_ICON_MENU,
    LAUNCHER_ICON_MENU,
}