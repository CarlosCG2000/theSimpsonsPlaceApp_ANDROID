plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization) // Serialization
    kotlin("kapt")
    alias(libs.plugins.hilt)
    // alias(libs.plugins.ksp)
}

android {
    namespace = "es.upsa.mimo.thesimpsonplace"
    compileSdk = 35

    defaultConfig {
        applicationId = "es.upsa.mimo.thesimpsonplace"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.serialization.json) // Serialization
    implementation(libs.androidx.constraintlayout.compose)  // Constraint Layout
    implementation(libs.androidx.lifecycle.viewmodel.compose) // View Model
    implementation(libs.androidx.navigation.compose) // Navigation compose
    implementation(libs.coil.compose) // Coil Compose (cargar imagenes web)
    implementation(libs.androidx.material.icons.extended) // Material Icons Extension (para tener mas iconos)
    implementation(libs.gson)  // GSON
    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.navigation)
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.appcompat) // Agrega esto
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    // ksp(libs.androidx.room.compiler)
    //    implementation(libs.charts)
    //    implementation(libs.mpandroidchart)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}