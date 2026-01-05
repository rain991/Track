plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("de.mannodermaus.android-junit5") version "1.11.0.0"
    id("org.jetbrains.kotlin.plugin.compose")
}

val currenciesApiKey : String = project.findProperty("CURRENCIES_API_KEY") as String? ?: ""

android {
    android.buildFeatures.buildConfig = true
    namespace = "com.savenko.track"
    compileSdk = 35

    defaultConfig {
        testInstrumentationRunnerArguments += mapOf("runnerBuilder" to "de.mannodermaus.junit5.AndroidJUnit5Builder")
        applicationId = "com.savenko.track"
        minSdk = 26
        targetSdk = 35
        versionCode = 37
        versionName = "1.6.1.3"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "CURRENCIES_API_KEY","\"$currenciesApiKey\"" )
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "kotlin/internal/internal.kotlin_builtins"
            excludes += "kotlin/reflect/reflect.kotlin_builtins"
            excludes += "kotlin/kotlin.kotlin_builtins"
        }
    }

}


dependencies {
    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)

    // Data
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Charts
    implementation(libs.vico.compose)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.workmanager)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)

    // Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.splashscreen)

    // WorkManager
    implementation(libs.androidx.work.runtime)
    androidTestImplementation(libs.androidx.work.testing)

    // Tests
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.junit4)
    testRuntimeOnly(libs.junit.vintage)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.coroutines.test)
}
