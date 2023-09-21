plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.hilt.plugin.get().pluginId)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.satellitehub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.satellitehub"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":model"))
    implementation(project(":domain"))

    implementation(libs.compose.runtime)
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.material)
    implementation(libs.compose.material.size)
    implementation(libs.compose.animation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.controller)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.navigation)
    implementation(libs.compose.hilt)

    implementation(libs.compose.icons)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)

    implementation(libs.appcompat)
    implementation(libs.core.ktx)

    implementation(libs.lifecycle.viewmodel.ktx)

    implementation(libs.coroutines)

    implementation(libs.timber.log)
    implementation(libs.moshi)

    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)
}