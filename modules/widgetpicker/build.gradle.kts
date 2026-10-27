// widgetpicker (module rieng cua Launcher3 A17) - ban Gradle hoa.
// Soong goc: android_library name "widget_picker_component".
// Layout chuan Gradle: src/main/java, src/main/res, src/main/AndroidManifest.xml
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "com.android.launcher3.widgetpicker"

    buildFeatures {
        compose = true
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    val prebuilts = File(rootProject.projectDir, "prebuilts/libs")
    compileOnly(
        fileTree(prebuilts) {
            include("framework-*.jar", "SystemUI-core-*.jar", "SystemUI-statsd-*.jar", "WindowManager-Shell-*.jar")
        }
    )
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.window)
    implementation(libs.google.material)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}