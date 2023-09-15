// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.dagger.compiler) apply false
}
buildscript {
    repositories {
        // other repositories...
        mavenCentral()
        google()
    }
}