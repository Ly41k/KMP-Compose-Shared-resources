plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlinMultiplatform)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlinCocoapods)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.androidLibrary)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.jetbrainsCompose)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.multiplatformResources)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(compose.animation)

                api(libs.multiplatform.resources.compose)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
        }
    }
}

android {
    namespace = "com.example.kmpcomposesharedresources"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.example.kmpcomposesharedresources"
    multiplatformResourcesClassName = "SharedRes"
}