import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //id("com.google.devtools.ksp")
    id("de.mannodermaus.android-junit5") version "1.11.0.0"
    id("org.jetbrains.kotlin.plugin.compose")
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use(::load)
    }
}

val currenciesApiKey: String = providers.gradleProperty("CURRENCIES_API_KEY").orNull
    ?: System.getenv("CURRENCIES_API_KEY")
    ?: localProperties.getProperty("CURRENCIES_API_KEY")
    ?: ""

android {
    android.buildFeatures.buildConfig = true
    namespace = "com.savenko.track"
    compileSdk = 36


    kotlin{
        compilerOptions{
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

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

    buildFeatures {
        compose = true
    }


    lint{
        disable.addAll(setOf("ComposableDestinationInComposeScope", "ComposableNavGraphInComposeScope", "WrongStartDestinationType", "WrongStartDestinationType", "WrongStartDestinationType"))
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
    implementation(project(":shared"))

    implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.room.ktx)
    implementation(libs.vico.compose)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.splashscreen)
    implementation(libs.kotlinx.datetime)

    //ksp(libs.androidx.room.compiler)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.junit4)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.coroutines.test)

    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.vintage)

    androidTestImplementation(libs.espresso.core)
}
