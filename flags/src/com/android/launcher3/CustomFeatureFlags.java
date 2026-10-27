/*
 * Sinh tu dong boi tools/gen_launcher_flags.py - KHONG sua tay.
 * Nguon: GrapheneOS Launcher3 branch 17 (115 flag).
 */
package com.android.launcher3;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
/** @hide */
public class CustomFeatureFlags implements FeatureFlags {

    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        mGetValueImpl = getValueImpl;
    }
    @Override
    public boolean allAppsSurface() {
        return getValue(Flags.FLAG_ALL_APPS_SURFACE, FeatureFlags::allAppsSurface);
    }

    @Override
    public boolean appLaunchBlur() {
        return getValue(Flags.FLAG_APP_LAUNCH_BLUR, FeatureFlags::appLaunchBlur);
    }

    @Override
    public boolean avoidDisplayCutoutBubbleBar() {
        return getValue(Flags.FLAG_AVOID_DISPLAY_CUTOUT_BUBBLE_BAR, FeatureFlags::avoidDisplayCutoutBubbleBar);
    }

    @Override
    public boolean bindModelUsingRepository() {
        return getValue(Flags.FLAG_BIND_MODEL_USING_REPOSITORY, FeatureFlags::bindModelUsingRepository);
    }

    @Override
    public boolean blurOnMoreSurfaces() {
        return getValue(Flags.FLAG_BLUR_ON_MORE_SURFACES, FeatureFlags::blurOnMoreSurfaces);
    }

    @Override
    public boolean blurredHomeAnimation() {
        return getValue(Flags.FLAG_BLURRED_HOME_ANIMATION, FeatureFlags::blurredHomeAnimation);
    }

    @Override
    public boolean centerSpringLoadedStateVertically() {
        return getValue(Flags.FLAG_CENTER_SPRING_LOADED_STATE_VERTICALLY, FeatureFlags::centerSpringLoadedStateVertically);
    }

    @Override
    public boolean condoPlanner() {
        return getValue(Flags.FLAG_CONDO_PLANNER, FeatureFlags::condoPlanner);
    }

    @Override
    public boolean disableAppAutomationBlur() {
        return getValue(Flags.FLAG_DISABLE_APP_AUTOMATION_BLUR, FeatureFlags::disableAppAutomationBlur);
    }

    @Override
    public boolean doubleTapToSleep() {
        return getValue(Flags.FLAG_DOUBLE_TAP_TO_SLEEP, FeatureFlags::doubleTapToSleep);
    }

    @Override
    public boolean enableAllAppsButtonInHotseat() {
        return getValue(Flags.FLAG_ENABLE_ALL_APPS_BUTTON_IN_HOTSEAT, FeatureFlags::enableAllAppsButtonInHotseat);
    }

    @Override
    public boolean enableAllAppsEduForOverswipe() {
        return getValue(Flags.FLAG_ENABLE_ALL_APPS_EDU_FOR_OVERSWIPE, FeatureFlags::enableAllAppsEduForOverswipe);
    }

    @Override
    public boolean enableAltTabKqsFlatenning() {
        return getValue(Flags.FLAG_ENABLE_ALT_TAB_KQS_FLATENNING, FeatureFlags::enableAltTabKqsFlatenning);
    }

    @Override
    public boolean enableAppAutomationIndicator() {
        return getValue(Flags.FLAG_ENABLE_APP_AUTOMATION_INDICATOR, FeatureFlags::enableAppAutomationIndicator);
    }

    @Override
    public boolean enableAppLockShortcut() {
        return getValue(Flags.FLAG_ENABLE_APP_LOCK_SHORTCUT, FeatureFlags::enableAppLockShortcut);
    }

    @Override
    public boolean enableCollapseSysuiPanelsOnTaskbarClick() {
        return getValue(Flags.FLAG_ENABLE_COLLAPSE_SYSUI_PANELS_ON_TASKBAR_CLICK, FeatureFlags::enableCollapseSysuiPanelsOnTaskbarClick);
    }

    @Override
    public boolean enableComposeForOptionsPopupMenuDialog() {
        return getValue(Flags.FLAG_ENABLE_COMPOSE_FOR_OPTIONS_POPUP_MENU_DIALOG, FeatureFlags::enableComposeForOptionsPopupMenuDialog);
    }

    @Override
    public boolean enableContrastTiles() {
        return getValue(Flags.FLAG_ENABLE_CONTRAST_TILES, FeatureFlags::enableContrastTiles);
    }

    @Override
    public boolean enableCueBarDesktopFormFactor() {
        return getValue(Flags.FLAG_ENABLE_CUE_BAR_DESKTOP_FORM_FACTOR, FeatureFlags::enableCueBarDesktopFormFactor);
    }

    @Override
    public boolean enableCursorDrivenWorkflows() {
        return getValue(Flags.FLAG_ENABLE_CURSOR_DRIVEN_WORKFLOWS, FeatureFlags::enableCursorDrivenWorkflows);
    }

    @Override
    public boolean enableCustomHeightForAllAppsOnCd() {
        return getValue(Flags.FLAG_ENABLE_CUSTOM_HEIGHT_FOR_ALL_APPS_ON_CD, FeatureFlags::enableCustomHeightForAllAppsOnCd);
    }

    @Override
    public boolean enableDragStartEndMultiDispatch() {
        return getValue(Flags.FLAG_ENABLE_DRAG_START_END_MULTI_DISPATCH, FeatureFlags::enableDragStartEndMultiDispatch);
    }

    @Override
    public boolean enableExpandingPauseWorkButton() {
        return getValue(Flags.FLAG_ENABLE_EXPANDING_PAUSE_WORK_BUTTON, FeatureFlags::enableExpandingPauseWorkButton);
    }

    @Override
    public boolean enableExpressiveFolderExpansion() {
        return getValue(Flags.FLAG_ENABLE_EXPRESSIVE_FOLDER_EXPANSION, FeatureFlags::enableExpressiveFolderExpansion);
    }

    @Override
    public boolean enableFallbackOverviewInWindow() {
        return getValue(Flags.FLAG_ENABLE_FALLBACK_OVERVIEW_IN_WINDOW, FeatureFlags::enableFallbackOverviewInWindow);
    }

    @Override
    public boolean enableFileSystemFoldersAsDropTargets() {
        return getValue(Flags.FLAG_ENABLE_FILE_SYSTEM_FOLDERS_AS_DROP_TARGETS, FeatureFlags::enableFileSystemFoldersAsDropTargets);
    }

    @Override
    public boolean enableFixedQsbAtBottom() {
        return getValue(Flags.FLAG_ENABLE_FIXED_QSB_AT_BOTTOM, FeatureFlags::enableFixedQsbAtBottom);
    }

    @Override
    public boolean enableFocusOutline() {
        return getValue(Flags.FLAG_ENABLE_FOCUS_OUTLINE, FeatureFlags::enableFocusOutline);
    }

    @Override
    public boolean enableFolderRefactor() {
        return getValue(Flags.FLAG_ENABLE_FOLDER_REFACTOR, FeatureFlags::enableFolderRefactor);
    }

    @Override
    public boolean enableGrowthNudge() {
        return getValue(Flags.FLAG_ENABLE_GROWTH_NUDGE, FeatureFlags::enableGrowthNudge);
    }

    @Override
    public boolean enableHomeScreenFilesCopyPaste() {
        return getValue(Flags.FLAG_ENABLE_HOME_SCREEN_FILES_COPY_PASTE, FeatureFlags::enableHomeScreenFilesCopyPaste);
    }

    @Override
    public boolean enableHomeScreenFilesRenaming() {
        return getValue(Flags.FLAG_ENABLE_HOME_SCREEN_FILES_RENAMING, FeatureFlags::enableHomeScreenFilesRenaming);
    }

    @Override
    public boolean enableHomeScreenFilesTrashing() {
        return getValue(Flags.FLAG_ENABLE_HOME_SCREEN_FILES_TRASHING, FeatureFlags::enableHomeScreenFilesTrashing);
    }

    @Override
    public boolean enableKqsForceTakeRunningTaskThumbnail() {
        return getValue(Flags.FLAG_ENABLE_KQS_FORCE_TAKE_RUNNING_TASK_THUMBNAIL, FeatureFlags::enableKqsForceTakeRunningTaskThumbnail);
    }

    @Override
    public boolean enableLaterIsLockedCheck() {
        return getValue(Flags.FLAG_ENABLE_LATER_IS_LOCKED_CHECK, FeatureFlags::enableLaterIsLockedCheck);
    }

    @Override
    public boolean enableLauncherIconShapes() {
        return getValue(Flags.FLAG_ENABLE_LAUNCHER_ICON_SHAPES, FeatureFlags::enableLauncherIconShapes);
    }

    @Override
    public boolean enableLauncherOverviewInWindow() {
        return getValue(Flags.FLAG_ENABLE_LAUNCHER_OVERVIEW_IN_WINDOW, FeatureFlags::enableLauncherOverviewInWindow);
    }

    @Override
    public boolean enableLowResThumbnailPreloading() {
        return getValue(Flags.FLAG_ENABLE_LOW_RES_THUMBNAIL_PRELOADING, FeatureFlags::enableLowResThumbnailPreloading);
    }

    @Override
    public boolean enableMouseInteractionChanges() {
        return getValue(Flags.FLAG_ENABLE_MOUSE_INTERACTION_CHANGES, FeatureFlags::enableMouseInteractionChanges);
    }

    @Override
    public boolean enableMultiInstanceMenuTaskbar() {
        return getValue(Flags.FLAG_ENABLE_MULTI_INSTANCE_MENU_TASKBAR, FeatureFlags::enableMultiInstanceMenuTaskbar);
    }

    @Override
    public boolean enableNarrowGridRestore() {
        return getValue(Flags.FLAG_ENABLE_NARROW_GRID_RESTORE, FeatureFlags::enableNarrowGridRestore);
    }

    @Override
    public boolean enableNewTouchpadGestures() {
        return getValue(Flags.FLAG_ENABLE_NEW_TOUCHPAD_GESTURES, FeatureFlags::enableNewTouchpadGestures);
    }

    @Override
    public boolean enableOverviewDesktopTileWallpaperBackground() {
        return getValue(Flags.FLAG_ENABLE_OVERVIEW_DESKTOP_TILE_WALLPAPER_BACKGROUND, FeatureFlags::enableOverviewDesktopTileWallpaperBackground);
    }

    @Override
    public boolean enableOverviewPagination() {
        return getValue(Flags.FLAG_ENABLE_OVERVIEW_PAGINATION, FeatureFlags::enableOverviewPagination);
    }

    @Override
    public boolean enableOverviewSelectTextView() {
        return getValue(Flags.FLAG_ENABLE_OVERVIEW_SELECT_TEXT_VIEW, FeatureFlags::enableOverviewSelectTextView);
    }

    @Override
    public boolean enablePredictiveBackInOverview() {
        return getValue(Flags.FLAG_ENABLE_PREDICTIVE_BACK_IN_OVERVIEW, FeatureFlags::enablePredictiveBackInOverview);
    }

    @Override
    public boolean enablePrivateSpace() {
        return getValue(Flags.FLAG_ENABLE_PRIVATE_SPACE, FeatureFlags::enablePrivateSpace);
    }

    @Override
    public boolean enableQsbOnHotseat() {
        return getValue(Flags.FLAG_ENABLE_QSB_ON_HOTSEAT, FeatureFlags::enableQsbOnHotseat);
    }

    @Override
    public boolean enableRebootUnlockAnimation() {
        return getValue(Flags.FLAG_ENABLE_REBOOT_UNLOCK_ANIMATION, FeatureFlags::enableRebootUnlockAnimation);
    }

    @Override
    public boolean enableRecentsWindowBlur() {
        return getValue(Flags.FLAG_ENABLE_RECENTS_WINDOW_BLUR, FeatureFlags::enableRecentsWindowBlur);
    }

    @Override
    public boolean enableRecentsWindowProtoLog() {
        return getValue(Flags.FLAG_ENABLE_RECENTS_WINDOW_PROTO_LOG, FeatureFlags::enableRecentsWindowProtoLog);
    }

    @Override
    public boolean enableRefactorDigitalWellbeingToast() {
        return getValue(Flags.FLAG_ENABLE_REFACTOR_DIGITAL_WELLBEING_TOAST, FeatureFlags::enableRefactorDigitalWellbeingToast);
    }

    @Override
    public boolean enableResponsiveWorkspace() {
        return getValue(Flags.FLAG_ENABLE_RESPONSIVE_WORKSPACE, FeatureFlags::enableResponsiveWorkspace);
    }

    @Override
    public boolean enableRestoredbtaskModelwriterRefactor() {
        return getValue(Flags.FLAG_ENABLE_RESTOREDBTASK_MODELWRITER_REFACTOR, FeatureFlags::enableRestoredbtaskModelwriterRefactor);
    }

    @Override
    public boolean enableReversibleHomeActionCorner() {
        return getValue(Flags.FLAG_ENABLE_REVERSIBLE_HOME_ACTION_CORNER, FeatureFlags::enableReversibleHomeActionCorner);
    }

    @Override
    public boolean enableSaveActionInOverviewShare() {
        return getValue(Flags.FLAG_ENABLE_SAVE_ACTION_IN_OVERVIEW_SHARE, FeatureFlags::enableSaveActionInOverviewShare);
    }

    @Override
    public boolean enableScalabilityForDesktopExperience() {
        return getValue(Flags.FLAG_ENABLE_SCALABILITY_FOR_DESKTOP_EXPERIENCE, FeatureFlags::enableScalabilityForDesktopExperience);
    }

    @Override
    public boolean enableSimultaneousOverviewTriggerOnExtendedDesktop() {
        return getValue(Flags.FLAG_ENABLE_SIMULTANEOUS_OVERVIEW_TRIGGER_ON_EXTENDED_DESKTOP, FeatureFlags::enableSimultaneousOverviewTriggerOnExtendedDesktop);
    }

    @Override
    public boolean enableStateManagerProtoLog() {
        return getValue(Flags.FLAG_ENABLE_STATE_MANAGER_PROTO_LOG, FeatureFlags::enableStateManagerProtoLog);
    }

    @Override
    public boolean enableStrictMode() {
        return getValue(Flags.FLAG_ENABLE_STRICT_MODE, FeatureFlags::enableStrictMode);
    }

    @Override
    public boolean enableSupportForArchiving() {
        return getValue(Flags.FLAG_ENABLE_SUPPORT_FOR_ARCHIVING, FeatureFlags::enableSupportForArchiving);
    }

    @Override
    public boolean enableSwipeUpMagneticDetach() {
        return getValue(Flags.FLAG_ENABLE_SWIPE_UP_MAGNETIC_DETACH, FeatureFlags::enableSwipeUpMagneticDetach);
    }

    @Override
    public boolean enableSystemDrag() {
        return getValue(Flags.FLAG_ENABLE_SYSTEM_DRAG, FeatureFlags::enableSystemDrag);
    }

    @Override
    public boolean enableSystemDragToOtherApps() {
        return getValue(Flags.FLAG_ENABLE_SYSTEM_DRAG_TO_OTHER_APPS, FeatureFlags::enableSystemDragToOtherApps);
    }

    @Override
    public boolean enableTaskbarA11yMoreOptionsButton() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_A11Y_MORE_OPTIONS_BUTTON, FeatureFlags::enableTaskbarA11yMoreOptionsButton);
    }

    @Override
    public boolean enableTaskbarBehindShade() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_BEHIND_SHADE, FeatureFlags::enableTaskbarBehindShade);
    }

    @Override
    public boolean enableTaskbarCustomization() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_CUSTOMIZATION, FeatureFlags::enableTaskbarCustomization);
    }

    @Override
    public boolean enableTaskbarDragAndDrop() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_DRAG_AND_DROP, FeatureFlags::enableTaskbarDragAndDrop);
    }

    @Override
    public boolean enableTaskbarDragToRemove() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_DRAG_TO_REMOVE, FeatureFlags::enableTaskbarDragToRemove);
    }

    @Override
    public boolean enableTaskbarIconContainer() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_ICON_CONTAINER, FeatureFlags::enableTaskbarIconContainer);
    }

    @Override
    public boolean enableTaskbarIconDesktopClicks() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_ICON_DESKTOP_CLICKS, FeatureFlags::enableTaskbarIconDesktopClicks);
    }

    @Override
    public boolean enableTaskbarNoRecreate() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_NO_RECREATE, FeatureFlags::enableTaskbarNoRecreate);
    }

    @Override
    public boolean enableTaskbarPinning() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_PINNING, FeatureFlags::enableTaskbarPinning);
    }

    @Override
    public boolean enableTaskbarUiThread() {
        return getValue(Flags.FLAG_ENABLE_TASKBAR_UI_THREAD, FeatureFlags::enableTaskbarUiThread);
    }

    @Override
    public boolean enableTasksDragAndDropInOverview() {
        return getValue(Flags.FLAG_ENABLE_TASKS_DRAG_AND_DROP_IN_OVERVIEW, FeatureFlags::enableTasksDragAndDropInOverview);
    }

    @Override
    public boolean enableTransactionalModelWriter() {
        return getValue(Flags.FLAG_ENABLE_TRANSACTIONAL_MODEL_WRITER, FeatureFlags::enableTransactionalModelWriter);
    }

    @Override
    public boolean enableTwoPaneLauncherSettings() {
        return getValue(Flags.FLAG_ENABLE_TWO_PANE_LAUNCHER_SETTINGS, FeatureFlags::enableTwoPaneLauncherSettings);
    }

    @Override
    public boolean enableTwolineAllapps() {
        return getValue(Flags.FLAG_ENABLE_TWOLINE_ALLAPPS, FeatureFlags::enableTwolineAllapps);
    }

    @Override
    public boolean enableTwolineToggle() {
        return getValue(Flags.FLAG_ENABLE_TWOLINE_TOGGLE, FeatureFlags::enableTwolineToggle);
    }

    @Override
    public boolean enableUnfoldStateAnimation() {
        return getValue(Flags.FLAG_ENABLE_UNFOLD_STATE_ANIMATION, FeatureFlags::enableUnfoldStateAnimation);
    }

    @Override
    public boolean enableWidgetPickerBlur() {
        return getValue(Flags.FLAG_ENABLE_WIDGET_PICKER_BLUR, FeatureFlags::enableWidgetPickerBlur);
    }

    @Override
    public boolean enableWidgetsSectionInOptionsMenu() {
        return getValue(Flags.FLAG_ENABLE_WIDGETS_SECTION_IN_OPTIONS_MENU, FeatureFlags::enableWidgetsSectionInOptionsMenu);
    }

    @Override
    public boolean enableWorkspacePageAnimation() {
        return getValue(Flags.FLAG_ENABLE_WORKSPACE_PAGE_ANIMATION, FeatureFlags::enableWorkspacePageAnimation);
    }

    @Override
    public boolean enableWorkspaceSelection() {
        return getValue(Flags.FLAG_ENABLE_WORKSPACE_SELECTION, FeatureFlags::enableWorkspaceSelection);
    }

    @Override
    public boolean enabledFoldersInAllApps() {
        return getValue(Flags.FLAG_ENABLED_FOLDERS_IN_ALL_APPS, FeatureFlags::enabledFoldersInAllApps);
    }

    @Override
    public boolean expandableLongPressMenu() {
        return getValue(Flags.FLAG_EXPANDABLE_LONG_PRESS_MENU, FeatureFlags::expandableLongPressMenu);
    }

    @Override
    public boolean fallbackRevealAnimation() {
        return getValue(Flags.FLAG_FALLBACK_REVEAL_ANIMATION, FeatureFlags::fallbackRevealAnimation);
    }

    @Override
    public boolean fixWidgetSinglePtrResize() {
        return getValue(Flags.FLAG_FIX_WIDGET_SINGLE_PTR_RESIZE, FeatureFlags::fixWidgetSinglePtrResize);
    }

    @Override
    public boolean floatingSearchBar() {
        return getValue(Flags.FLAG_FLOATING_SEARCH_BAR, FeatureFlags::floatingSearchBar);
    }

    @Override
    public boolean forceMonochromeAppIcons() {
        return getValue(Flags.FLAG_FORCE_MONOCHROME_APP_ICONS, FeatureFlags::forceMonochromeAppIcons);
    }

    @Override
    public boolean forceMonochromeAppIconsAdaptColors() {
        return getValue(Flags.FLAG_FORCE_MONOCHROME_APP_ICONS_ADAPT_COLORS, FeatureFlags::forceMonochromeAppIconsAdaptColors);
    }

    @Override
    public boolean gestureNavTouchInputOptimizations() {
        return getValue(Flags.FLAG_GESTURE_NAV_TOUCH_INPUT_OPTIMIZATIONS, FeatureFlags::gestureNavTouchInputOptimizations);
    }

    @Override
    public boolean hideAutomatedTasksInOverview() {
        return getValue(Flags.FLAG_HIDE_AUTOMATED_TASKS_IN_OVERVIEW, FeatureFlags::hideAutomatedTasksInOverview);
    }

    @Override
    public boolean homeScreenEditImprovements() {
        return getValue(Flags.FLAG_HOME_SCREEN_EDIT_IMPROVEMENTS, FeatureFlags::homeScreenEditImprovements);
    }

    @Override
    public boolean ignoreThreeFingerTrackpadForNavHandleLongPress() {
        return getValue(Flags.FLAG_IGNORE_THREE_FINGER_TRACKPAD_FOR_NAV_HANDLE_LONG_PRESS, FeatureFlags::ignoreThreeFingerTrackpadForNavHandleLongPress);
    }

    @Override
    public boolean letterFastScroller() {
        return getValue(Flags.FLAG_LETTER_FAST_SCROLLER, FeatureFlags::letterFastScroller);
    }

    @Override
    public boolean modelRepository() {
        return getValue(Flags.FLAG_MODEL_REPOSITORY, FeatureFlags::modelRepository);
    }

    @Override
    public boolean moveToRestStateForBackgroundApp() {
        return getValue(Flags.FLAG_MOVE_TO_REST_STATE_FOR_BACKGROUND_APP, FeatureFlags::moveToRestStateForBackgroundApp);
    }

    @Override
    public boolean msdlFeedback() {
        return getValue(Flags.FLAG_MSDL_FEEDBACK, FeatureFlags::msdlFeedback);
    }

    @Override
    public boolean nudgePill() {
        return getValue(Flags.FLAG_NUDGE_PILL, FeatureFlags::nudgePill);
    }

    @Override
    public boolean oneGridRotationHandling() {
        return getValue(Flags.FLAG_ONE_GRID_ROTATION_HANDLING, FeatureFlags::oneGridRotationHandling);
    }

    @Override
    public boolean orientationFriendlyDesktopGridSpec() {
        return getValue(Flags.FLAG_ORIENTATION_FRIENDLY_DESKTOP_GRID_SPEC, FeatureFlags::orientationFriendlyDesktopGridSpec);
    }

    @Override
    public boolean privateSpaceAddFloatingMaskView() {
        return getValue(Flags.FLAG_PRIVATE_SPACE_ADD_FLOATING_MASK_VIEW, FeatureFlags::privateSpaceAddFloatingMaskView);
    }

    @Override
    public boolean privateSpaceAnimation() {
        return getValue(Flags.FLAG_PRIVATE_SPACE_ANIMATION, FeatureFlags::privateSpaceAnimation);
    }

    @Override
    public boolean qsbAnimationMinorFixes() {
        return getValue(Flags.FLAG_QSB_ANIMATION_MINOR_FIXES, FeatureFlags::qsbAnimationMinorFixes);
    }

    @Override
    public boolean reduceWorkspaceBlurUsage() {
        return getValue(Flags.FLAG_REDUCE_WORKSPACE_BLUR_USAGE, FeatureFlags::reduceWorkspaceBlurUsage);
    }

    @Override
    public boolean restoreArchivedAppIconsFromDb() {
        return getValue(Flags.FLAG_RESTORE_ARCHIVED_APP_ICONS_FROM_DB, FeatureFlags::restoreArchivedAppIconsFromDb);
    }

    @Override
    public boolean showCreateWidgetBtnInPicker() {
        return getValue(Flags.FLAG_SHOW_CREATE_WIDGET_BTN_IN_PICKER, FeatureFlags::showCreateWidgetBtnInPicker);
    }

    @Override
    public boolean showFilesOnHomeScreen() {
        return getValue(Flags.FLAG_SHOW_FILES_ON_HOME_SCREEN, FeatureFlags::showFilesOnHomeScreen);
    }

    @Override
    public boolean simplifiedLauncherModelBinding() {
        return getValue(Flags.FLAG_SIMPLIFIED_LAUNCHER_MODEL_BINDING, FeatureFlags::simplifiedLauncherModelBinding);
    }

    @Override
    public boolean taskbarQuietModeChangeSupport() {
        return getValue(Flags.FLAG_TASKBAR_QUIET_MODE_CHANGE_SUPPORT, FeatureFlags::taskbarQuietModeChangeSupport);
    }

    @Override
    public boolean tooltipEduCombinator() {
        return getValue(Flags.FLAG_TOOLTIP_EDU_COMBINATOR, FeatureFlags::tooltipEduCombinator);
    }

    @Override
    public boolean translateImeswitcher3buttonsWithBubble() {
        return getValue(Flags.FLAG_TRANSLATE_IMESWITCHER_3BUTTONS_WITH_BUBBLE, FeatureFlags::translateImeswitcher3buttonsWithBubble);
    }

    @Override
    public boolean updateRecentsWmWwmConfiguration() {
        return getValue(Flags.FLAG_UPDATE_RECENTS_WM_WWM_CONFIGURATION, FeatureFlags::updateRecentsWmWwmConfiguration);
    }

    @Override
    public boolean widgetReturnAnimationMinorFixes() {
        return getValue(Flags.FLAG_WIDGET_RETURN_ANIMATION_MINOR_FIXES, FeatureFlags::widgetReturnAnimationMinorFixes);
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return mGetValueImpl.test(flagName, getter);
    }

    public Set<String> getFlagNames() {
        return new HashSet<>(Arrays.asList(
                Flags.FLAG_ALL_APPS_SURFACE,
                Flags.FLAG_APP_LAUNCH_BLUR,
                Flags.FLAG_AVOID_DISPLAY_CUTOUT_BUBBLE_BAR,
                Flags.FLAG_BIND_MODEL_USING_REPOSITORY,
                Flags.FLAG_BLUR_ON_MORE_SURFACES,
                Flags.FLAG_BLURRED_HOME_ANIMATION,
                Flags.FLAG_CENTER_SPRING_LOADED_STATE_VERTICALLY,
                Flags.FLAG_CONDO_PLANNER,
                Flags.FLAG_DISABLE_APP_AUTOMATION_BLUR,
                Flags.FLAG_DOUBLE_TAP_TO_SLEEP,
                Flags.FLAG_ENABLE_ALL_APPS_BUTTON_IN_HOTSEAT,
                Flags.FLAG_ENABLE_ALL_APPS_EDU_FOR_OVERSWIPE,
                Flags.FLAG_ENABLE_ALT_TAB_KQS_FLATENNING,
                Flags.FLAG_ENABLE_APP_AUTOMATION_INDICATOR,
                Flags.FLAG_ENABLE_APP_LOCK_SHORTCUT,
                Flags.FLAG_ENABLE_COLLAPSE_SYSUI_PANELS_ON_TASKBAR_CLICK,
                Flags.FLAG_ENABLE_COMPOSE_FOR_OPTIONS_POPUP_MENU_DIALOG,
                Flags.FLAG_ENABLE_CONTRAST_TILES,
                Flags.FLAG_ENABLE_CUE_BAR_DESKTOP_FORM_FACTOR,
                Flags.FLAG_ENABLE_CURSOR_DRIVEN_WORKFLOWS,
                Flags.FLAG_ENABLE_CUSTOM_HEIGHT_FOR_ALL_APPS_ON_CD,
                Flags.FLAG_ENABLE_DRAG_START_END_MULTI_DISPATCH,
                Flags.FLAG_ENABLE_EXPANDING_PAUSE_WORK_BUTTON,
                Flags.FLAG_ENABLE_EXPRESSIVE_FOLDER_EXPANSION,
                Flags.FLAG_ENABLE_FALLBACK_OVERVIEW_IN_WINDOW,
                Flags.FLAG_ENABLE_FILE_SYSTEM_FOLDERS_AS_DROP_TARGETS,
                Flags.FLAG_ENABLE_FIXED_QSB_AT_BOTTOM,
                Flags.FLAG_ENABLE_FOCUS_OUTLINE,
                Flags.FLAG_ENABLE_FOLDER_REFACTOR,
                Flags.FLAG_ENABLE_GROWTH_NUDGE,
                Flags.FLAG_ENABLE_HOME_SCREEN_FILES_COPY_PASTE,
                Flags.FLAG_ENABLE_HOME_SCREEN_FILES_RENAMING,
                Flags.FLAG_ENABLE_HOME_SCREEN_FILES_TRASHING,
                Flags.FLAG_ENABLE_KQS_FORCE_TAKE_RUNNING_TASK_THUMBNAIL,
                Flags.FLAG_ENABLE_LATER_IS_LOCKED_CHECK,
                Flags.FLAG_ENABLE_LAUNCHER_ICON_SHAPES,
                Flags.FLAG_ENABLE_LAUNCHER_OVERVIEW_IN_WINDOW,
                Flags.FLAG_ENABLE_LOW_RES_THUMBNAIL_PRELOADING,
                Flags.FLAG_ENABLE_MOUSE_INTERACTION_CHANGES,
                Flags.FLAG_ENABLE_MULTI_INSTANCE_MENU_TASKBAR,
                Flags.FLAG_ENABLE_NARROW_GRID_RESTORE,
                Flags.FLAG_ENABLE_NEW_TOUCHPAD_GESTURES,
                Flags.FLAG_ENABLE_OVERVIEW_DESKTOP_TILE_WALLPAPER_BACKGROUND,
                Flags.FLAG_ENABLE_OVERVIEW_PAGINATION,
                Flags.FLAG_ENABLE_OVERVIEW_SELECT_TEXT_VIEW,
                Flags.FLAG_ENABLE_PREDICTIVE_BACK_IN_OVERVIEW,
                Flags.FLAG_ENABLE_PRIVATE_SPACE,
                Flags.FLAG_ENABLE_QSB_ON_HOTSEAT,
                Flags.FLAG_ENABLE_REBOOT_UNLOCK_ANIMATION,
                Flags.FLAG_ENABLE_RECENTS_WINDOW_BLUR,
                Flags.FLAG_ENABLE_RECENTS_WINDOW_PROTO_LOG,
                Flags.FLAG_ENABLE_REFACTOR_DIGITAL_WELLBEING_TOAST,
                Flags.FLAG_ENABLE_RESPONSIVE_WORKSPACE,
                Flags.FLAG_ENABLE_RESTOREDBTASK_MODELWRITER_REFACTOR,
                Flags.FLAG_ENABLE_REVERSIBLE_HOME_ACTION_CORNER,
                Flags.FLAG_ENABLE_SAVE_ACTION_IN_OVERVIEW_SHARE,
                Flags.FLAG_ENABLE_SCALABILITY_FOR_DESKTOP_EXPERIENCE,
                Flags.FLAG_ENABLE_SIMULTANEOUS_OVERVIEW_TRIGGER_ON_EXTENDED_DESKTOP,
                Flags.FLAG_ENABLE_STATE_MANAGER_PROTO_LOG,
                Flags.FLAG_ENABLE_STRICT_MODE,
                Flags.FLAG_ENABLE_SUPPORT_FOR_ARCHIVING,
                Flags.FLAG_ENABLE_SWIPE_UP_MAGNETIC_DETACH,
                Flags.FLAG_ENABLE_SYSTEM_DRAG,
                Flags.FLAG_ENABLE_SYSTEM_DRAG_TO_OTHER_APPS,
                Flags.FLAG_ENABLE_TASKBAR_A11Y_MORE_OPTIONS_BUTTON,
                Flags.FLAG_ENABLE_TASKBAR_BEHIND_SHADE,
                Flags.FLAG_ENABLE_TASKBAR_CUSTOMIZATION,
                Flags.FLAG_ENABLE_TASKBAR_DRAG_AND_DROP,
                Flags.FLAG_ENABLE_TASKBAR_DRAG_TO_REMOVE,
                Flags.FLAG_ENABLE_TASKBAR_ICON_CONTAINER,
                Flags.FLAG_ENABLE_TASKBAR_ICON_DESKTOP_CLICKS,
                Flags.FLAG_ENABLE_TASKBAR_NO_RECREATE,
                Flags.FLAG_ENABLE_TASKBAR_PINNING,
                Flags.FLAG_ENABLE_TASKBAR_UI_THREAD,
                Flags.FLAG_ENABLE_TASKS_DRAG_AND_DROP_IN_OVERVIEW,
                Flags.FLAG_ENABLE_TRANSACTIONAL_MODEL_WRITER,
                Flags.FLAG_ENABLE_TWO_PANE_LAUNCHER_SETTINGS,
                Flags.FLAG_ENABLE_TWOLINE_ALLAPPS,
                Flags.FLAG_ENABLE_TWOLINE_TOGGLE,
                Flags.FLAG_ENABLE_UNFOLD_STATE_ANIMATION,
                Flags.FLAG_ENABLE_WIDGET_PICKER_BLUR,
                Flags.FLAG_ENABLE_WIDGETS_SECTION_IN_OPTIONS_MENU,
                Flags.FLAG_ENABLE_WORKSPACE_PAGE_ANIMATION,
                Flags.FLAG_ENABLE_WORKSPACE_SELECTION,
                Flags.FLAG_ENABLED_FOLDERS_IN_ALL_APPS,
                Flags.FLAG_EXPANDABLE_LONG_PRESS_MENU,
                Flags.FLAG_FALLBACK_REVEAL_ANIMATION,
                Flags.FLAG_FIX_WIDGET_SINGLE_PTR_RESIZE,
                Flags.FLAG_FLOATING_SEARCH_BAR,
                Flags.FLAG_FORCE_MONOCHROME_APP_ICONS,
                Flags.FLAG_FORCE_MONOCHROME_APP_ICONS_ADAPT_COLORS,
                Flags.FLAG_GESTURE_NAV_TOUCH_INPUT_OPTIMIZATIONS,
                Flags.FLAG_HIDE_AUTOMATED_TASKS_IN_OVERVIEW,
                Flags.FLAG_HOME_SCREEN_EDIT_IMPROVEMENTS,
                Flags.FLAG_IGNORE_THREE_FINGER_TRACKPAD_FOR_NAV_HANDLE_LONG_PRESS,
                Flags.FLAG_LETTER_FAST_SCROLLER,
                Flags.FLAG_MODEL_REPOSITORY,
                Flags.FLAG_MOVE_TO_REST_STATE_FOR_BACKGROUND_APP,
                Flags.FLAG_MSDL_FEEDBACK,
                Flags.FLAG_NUDGE_PILL,
                Flags.FLAG_ONE_GRID_ROTATION_HANDLING,
                Flags.FLAG_ORIENTATION_FRIENDLY_DESKTOP_GRID_SPEC,
                Flags.FLAG_PRIVATE_SPACE_ADD_FLOATING_MASK_VIEW,
                Flags.FLAG_PRIVATE_SPACE_ANIMATION,
                Flags.FLAG_QSB_ANIMATION_MINOR_FIXES,
                Flags.FLAG_REDUCE_WORKSPACE_BLUR_USAGE,
                Flags.FLAG_RESTORE_ARCHIVED_APP_ICONS_FROM_DB,
                Flags.FLAG_SHOW_CREATE_WIDGET_BTN_IN_PICKER,
                Flags.FLAG_SHOW_FILES_ON_HOME_SCREEN,
                Flags.FLAG_SIMPLIFIED_LAUNCHER_MODEL_BINDING,
                Flags.FLAG_TASKBAR_QUIET_MODE_CHANGE_SUPPORT,
                Flags.FLAG_TOOLTIP_EDU_COMBINATOR,
                Flags.FLAG_TRANSLATE_IMESWITCHER_3BUTTONS_WITH_BUBBLE,
                Flags.FLAG_UPDATE_RECENTS_WM_WWM_CONFIGURATION,
                Flags.FLAG_WIDGET_RETURN_ANIMATION_MINOR_FIXES));
    }
}
