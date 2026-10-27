/*
 * Thay the class `com.android.wm.shell.shared.bubbles.BubbleFlagHelper` cua AOSP 17
 * (ban WindowManager-Shell A16 dung trong port khong co).
 * Giu nguyen package/ten/ham de code Launcher3 A17 compile duoc.
 * Neu sau nay swap wmshell len A17 thi XOA file nay.
 */
package com.android.wm.shell.shared.bubbles

/** Flag cho tinh nang bubble. */
object BubbleFlagHelper {

    /** enable_create_any_bubble - AOSP 17 bat mac dinh. */
    @JvmStatic
    fun enableCreateAnyBubble(): Boolean = true
}