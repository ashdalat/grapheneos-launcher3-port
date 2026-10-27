plugins {
    id(libs.plugins.android.library.get().pluginId)
    id("kotlin-parcelize")
}

android {
    namespace = "com.android.cuebarlib"

    lint {
        abortOnError = false
    }
}

// cuebarlib duoc GrapheneOS/AOSP viet tren API @hide -> can framework jar khi compile.
dependencies {
    val prebuilts = File(rootProject.projectDir, "prebuilts/libs")
    compileOnly(
        fileTree(prebuilts) {
            include("framework-*.jar", "SystemUI-core-*.jar", "SystemUI-statsd-*.jar", "WindowManager-Shell-*.jar")
        }
    )
}