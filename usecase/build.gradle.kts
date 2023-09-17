plugins {
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 34
    namespace = "com.example.usecase"

    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":repository"))
    implementation(project(":model"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.coroutines)

    implementation(libs.timber.log)
    implementation(libs.moshi.converter)
    implementation(libs.moshi)


    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)
}