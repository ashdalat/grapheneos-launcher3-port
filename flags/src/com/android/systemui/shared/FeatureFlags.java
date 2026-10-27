package com.android.systemui.shared;

/** @hide */
public interface FeatureFlags {




    boolean ambientAod();



    boolean bouncerAreaExclusion();



    boolean clockReactiveSmartspaceLayout();



    boolean clockReactiveVariants();



    boolean cursorHotCorner();



    boolean enableHomeDelay();



    boolean enableLppAssistInvocationEffect();



    boolean enableLppAssistInvocationHapticEffect();



    boolean exampleSharedFlag();



    boolean extendedWallpaperEffects();



    boolean extendibleThemeManager();



    boolean lockscreenCustomClocks();



    boolean newCustomizationPickerUi();



    boolean newTouchpadGesturesTutorial();



    boolean returnAnimationFrameworkLibrary();



    boolean returnAnimationFrameworkLongLived();



    boolean screenshotContextUrl();



    boolean shadeAllowBackGesture();



    boolean sidefpsControllerRefactor();



    boolean smartspaceSportsCardBackground();



    boolean smartspaceUiUpdate();



    boolean smartspaceUiUpdateResources();



    boolean smartspaceWeatherUseMonochromeFontIcons();



    boolean statusBarConnectedDisplays();



    boolean threeButtonCornerSwipe();



    boolean usePreferredImageEditor();

    /*
     * Cac flag moi cua A17 (ban A16 trong flags module khong co).
     * Them dang `default` de khong phai sua FeatureFlagsImpl/CustomFeatureFlags.
     * Gia tri lay theo AOSP 17 release config.
     */

    /** workspace_items_label_hidden (systemui.aconfig) */
    default boolean workspaceItemsLabelHidden() {
        return true;
    }

    /** enable_recents_in_taskbar */
    default boolean enableRecentsInTaskbar() {
        return true;
    }
}
