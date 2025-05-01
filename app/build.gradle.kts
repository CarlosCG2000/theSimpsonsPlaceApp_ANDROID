
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization) // Serialization
    kotlin("kapt")
    alias(libs.plugins.hilt)
    // alias(libs.plugins.ksp) // NUEVO
    alias(libs.plugins.screenshot) // Test de captura de pantalla
}

android {
    namespace = "es.upsa.mimo.thesimpsonplace"
    compileSdk = 35
    flavorDimensions += "source"

    defaultConfig {
        applicationId = "es.upsa.mimo.thesimpsonplace"
        minSdk = 24
        targetSdk = 35
        versionCode = 4 // modificar el número de versión para cada release en producción en la play store
        versionName = "1.3.0" // modificar el número de versión para cada release en producción en la play store

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Solo en desarrollo: activar logs, analytics, tracking, debuggers, etc.
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("Boolean", "ENABLE_LOGGING", "true")
            buildConfigField("Boolean", "JSON_TEST", "false")
//           isMinifyEnabled = false
//           signingConfigs = signingConfigs.debug
        }

        create("beta") {
            // Para beta testing
            initWith(getByName("debug")) // o "release" si quieres
            buildConfigField("Boolean", "ENABLE_LOGGING", "false")
            buildConfigField("Boolean", "JSON_TEST", "true")
            isDebuggable = true
        }

        release {
            // Para producción
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("Boolean", "ENABLE_LOGGING", "false")
            buildConfigField("Boolean", "JSON_TEST", "false")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    productFlavors {
        maybeCreate("mock").apply {
            dimension = "source"
            applicationIdSuffix = ".mock"
            buildConfigField("String", "DATA_SOURCE", "\"mock\"")
        }
        maybeCreate("remote").apply {
            dimension = "source"
            buildConfigField("String", "DATA_SOURCE", "\"remote\"")
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
        buildConfig = true
    }

    experimentalProperties["android.experimental.enableScreenshotTest"] = true // Test de captura de pantalla
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
    implementation(libs.androidx.navigation.compose)// Navigation compose
    implementation(libs.kotlinx.serialization.json) // Serialization
    implementation(libs.androidx.constraintlayout.compose)  // Constraint Layout
    implementation(libs.androidx.lifecycle.viewmodel.compose) // View Model
    implementation(libs.coil.compose) // Coil Compose (cargar imagenes web)
    implementation(libs.androidx.material.icons.extended) // Material Icons Extension (para tener mas iconos)
    implementation(libs.gson) // GSON
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.navigation)
    // ksp(libs.dagger.hilt.compiler)
    kapt(libs.dagger.hilt.compiler)
    // ksp(libs.androidx.room.compiler) // NUEVO
    kapt(libs.androidx.room.compiler)
    implementation(libs.google.material) // NUEVO
    implementation(libs.androidx.navigation.ui.ktx) // NUEVO
    implementation(libs.okhttp) // NUEVO
    implementation(libs.okhttp.logging.interceptor) // NUEVO

    // Esta configuración de dependencia se utiliza para agregar bibliotecas que solo son necesarias para ejecutar pruebas unitarias locales
    // Ejemplos comunes de bibliotecas que se agregan con testImplementation son JUnit (para escribir pruebas unitarias en Java/Kotlin) y Mockito (para crear objetos simulados).
    testImplementation(libs.junit)
    // Nuevas dependencias de prueba
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    screenshotTestImplementation(libs.androidx.compose.ui.tooling) // Test de captura de pantalla

    // Esta configuración de dependencia se utiliza para agregar bibliotecas que son necesarias para ejecutar pruebas de instrumentación. Estas pruebas se ejecutan en un dispositivo Android real o en un emulador.
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))
}


