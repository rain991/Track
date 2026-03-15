import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
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
val generatedConfigDir = layout.buildDirectory.dir("generated/source/runtimeConfig/commonMain/kotlin")
val escapedCurrenciesApiKey = currenciesApiKey
    .replace("\\", "\\\\")
    .replace("\"", "\\\"")
val generateRuntimeConfig by tasks.registering {
    outputs.dir(generatedConfigDir)
    doLast {
        val outputFile = generatedConfigDir.get()
            .file("com/savenko/track/shared/data/ktor/GeneratedRuntimeConfig.kt")
            .asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(
            """
            package com.savenko.track.shared.data.ktor

            internal object GeneratedRuntimeConfig {
                const val CURRENCIES_API_KEY: String = "$escapedCurrenciesApiKey"
            }
            """.trimIndent() + "\n"
        )
    }
}

tasks.matching {
    it.name.startsWith("compileKotlin") || it.name.startsWith("kspKotlin")
}.configureEach {
    dependsOn(generateRuntimeConfig)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            kotlin.srcDir(generatedConfigDir)
        }
        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel.navigation)

            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(libs.dautovicharis.charts.line)
            implementation(libs.androidx.datastore)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.kermit)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.savenko.track.shared.resources"
}

android {
    namespace = "com.savenko.track.shared"
    compileSdk = 36
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 26
        buildConfigField("String", "CURRENCIES_API_KEY", "\"$currenciesApiKey\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}
