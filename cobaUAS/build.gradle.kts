// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}